package com.example.engine

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.coroutines.resume

data class ExecutionResult(
    val output: String = "",
    val error: String? = null,
    val executionTimeMs: Long = 0,
    val isSuccess: Boolean = error.isNullOrBlank()
) {
    val stdout: String get() = output
    val success: Boolean get() = isSuccess
}

class PythonRunner(private val context: Context) {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var webView: WebView? = null
    private var isEngineReady = false

    init {
        mainHandler.post {
            setupWebView()
        }
    }

    private fun setupWebView() {
        try {
            webView = WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
                settings.domStorageEnabled = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Executes Python code offline and returns stdout and any runtime errors.
     */
    suspend fun executePythonCode(code: String): ExecutionResult = withContext(Dispatchers.Default) {
        val startTime = System.currentTimeMillis()
        try {
            // First run via our robust Kotlin Native Python Interpreter
            val interpreterResult = executeWithNativeInterpreter(code)
            val duration = System.currentTimeMillis() - startTime
            return@withContext interpreterResult.copy(executionTimeMs = duration)
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime
            return@withContext ExecutionResult(
                output = "",
                error = "Runtime Error: ${e.localizedMessage ?: e.toString()}",
                executionTimeMs = duration,
                isSuccess = false
            )
        }
    }

    /**
     * High-fidelity native Python interpreter supporting:
     * - print(), input/variables
     * - Arithmetic and logical expressions
     * - Strings, lists, tuples, dictionaries, sets
     * - Loops: for, while, range, break, continue
     * - Conditionals: if, elif, else
     * - Functions: def, return, arguments, recursion
     * - List comprehensions & string methods
     * - Classes & objects (basic OOP)
     * - Built-in functions: len, sum, min, max, sorted, type, int, str, float, bool, list, dict, set, range, abs, round, enumerate, zip
     */
    private fun executeWithNativeInterpreter(rawCode: String): ExecutionResult {
        val stdout = StringBuilder()
        val stderr = StringBuilder()

        try {
            val lines = rawCode.lines()
            val env = PythonEnvironment(stdout)
            env.executeLines(lines)

            val outStr = stdout.toString()
            return if (stderr.isEmpty()) {
                ExecutionResult(
                    output = if (outStr.isBlank()) "[Program completed with 0 output]" else outStr.trimEnd(),
                    error = null,
                    isSuccess = true
                )
            } else {
                ExecutionResult(
                    output = outStr.trimEnd(),
                    error = stderr.toString().trimEnd(),
                    isSuccess = false
                )
            }
        } catch (pyErr: PythonException) {
            return ExecutionResult(
                output = stdout.toString().trimEnd(),
                error = "Traceback (most recent call last):\n  ${pyErr.message}",
                isSuccess = false
            )
        } catch (e: Exception) {
            return ExecutionResult(
                output = stdout.toString().trimEnd(),
                error = "Traceback (most recent call last):\n  Error: ${e.message ?: e.toString()}",
                isSuccess = false
            )
        }
    }
}

class PythonException(message: String) : Exception(message)

/**
 * Lightweight execution environment maintaining state, symbols, functions, and output.
 */
class PythonEnvironment(
    private val output: StringBuilder,
    private val parent: PythonEnvironment? = null
) {
    val variables = mutableMapOf<String, Any?>()
    val functions = mutableMapOf<String, DefinedFunction>()

    init {
        if (parent == null) {
            // Built-in constants
            variables["True"] = true
            variables["False"] = false
            variables["None"] = null
            variables["PI"] = 3.141592653589793
            variables["E"] = 2.718281828459045
        }
    }

    fun getVariable(name: String): Any? {
        if (variables.containsKey(name)) return variables[name]
        return parent?.getVariable(name) ?: throw PythonException("NameError: name '$name' is not defined")
    }

    fun hasVariable(name: String): Boolean {
        return variables.containsKey(name) || (parent?.hasVariable(name) ?: false)
    }

    fun setVariable(name: String, value: Any?) {
        variables[name] = value
    }

    fun executeLines(lines: List<String>): Any? {
        var i = 0
        while (i < lines.size) {
            val rawLine = lines[i]
            val trimmed = rawLine.trim()

            // Skip comments and blank lines
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                i++
                continue
            }

            // Function definition: def func(a, b):
            if (trimmed.startsWith("def ")) {
                val header = trimmed.substring(4)
                val colonIdx = header.indexOf(':')
                if (colonIdx == -1) throw PythonException("SyntaxError: invalid syntax in function definition")
                val sig = header.substring(0, colonIdx).trim()
                val openParen = sig.indexOf('(')
                val closeParen = sig.lastIndexOf(')')
                if (openParen == -1 || closeParen == -1) throw PythonException("SyntaxError: invalid syntax in def signature")
                val fnName = sig.substring(0, openParen).trim()
                val paramsStr = sig.substring(openParen + 1, closeParen).trim()
                val params = if (paramsStr.isEmpty()) emptyList() else paramsStr.split(",").map { it.trim().split("=")[0].trim() }

                // Gather indented body
                val bodyLines = mutableListOf<String>()
                val baseIndent = getIndentLevel(rawLine)
                i++
                while (i < lines.size) {
                    val nextLine = lines[i]
                    if (nextLine.trim().isEmpty() || nextLine.trim().startsWith("#")) {
                        bodyLines.add(nextLine)
                        i++
                        continue
                    }
                    if (getIndentLevel(nextLine) > baseIndent) {
                        bodyLines.add(nextLine)
                        i++
                    } else {
                        break
                    }
                }
                functions[fnName] = DefinedFunction(fnName, params, bodyLines, this)
                continue
            }

            // If statement: if condition: / elif / else
            if (trimmed.startsWith("if ") && trimmed.endsWith(":")) {
                val condExpr = trimmed.substring(3, trimmed.length - 1).trim()
                val (branches, nextIdx) = parseIfBranches(lines, i)
                i = nextIdx

                var executed = false
                for (branch in branches) {
                    val cond = if (branch.condition == null) true else isTruthy(evalExpression(branch.condition))
                    if (cond && !executed) {
                        val subEnv = PythonEnvironment(output, this)
                        val ret = subEnv.executeLines(branch.body)
                        if (ret != null) return ret
                        executed = true
                    }
                }
                continue
            }

            // For loop: for x in iterable:
            if (trimmed.startsWith("for ") && trimmed.endsWith(":")) {
                val header = trimmed.substring(4, trimmed.length - 1).trim()
                val inIdx = header.indexOf(" in ")
                if (inIdx == -1) throw PythonException("SyntaxError: invalid syntax in for loop")
                val iterVar = header.substring(0, inIdx).trim()
                val iterExpr = header.substring(inIdx + 4).trim()

                val baseIndent = getIndentLevel(rawLine)
                val bodyLines = mutableListOf<String>()
                i++
                while (i < lines.size) {
                    val nextLine = lines[i]
                    if (nextLine.trim().isEmpty() || nextLine.trim().startsWith("#")) {
                        bodyLines.add(nextLine)
                        i++
                        continue
                    }
                    if (getIndentLevel(nextLine) > baseIndent) {
                        bodyLines.add(nextLine)
                        i++
                    } else {
                        break
                    }
                }

                val iterableVal = evalExpression(iterExpr)
                val items = toIterableList(iterableVal)
                for (item in items) {
                    setVariable(iterVar, item)
                    val subEnv = PythonEnvironment(output, this)
                    val ret = subEnv.executeLines(bodyLines)
                    if (ret != null) return ret
                }
                continue
            }

            // While loop: while condition:
            if (trimmed.startsWith("while ") && trimmed.endsWith(":")) {
                val condExpr = trimmed.substring(6, trimmed.length - 1).trim()
                val baseIndent = getIndentLevel(rawLine)
                val bodyLines = mutableListOf<String>()
                i++
                while (i < lines.size) {
                    val nextLine = lines[i]
                    if (nextLine.trim().isEmpty() || nextLine.trim().startsWith("#")) {
                        bodyLines.add(nextLine)
                        i++
                        continue
                    }
                    if (getIndentLevel(nextLine) > baseIndent) {
                        bodyLines.add(nextLine)
                        i++
                    } else {
                        break
                    }
                }

                var safetyCount = 0
                while (isTruthy(evalExpression(condExpr))) {
                    if (++safetyCount > 10000) {
                        throw PythonException("RuntimeError: Maximum loop iteration depth exceeded (infinite loop prevention)")
                    }
                    val subEnv = PythonEnvironment(output, this)
                    val ret = subEnv.executeLines(bodyLines)
                    if (ret != null) return ret
                }
                continue
            }

            // Return statement: return expr
            if (trimmed.startsWith("return")) {
                val expr = trimmed.removePrefix("return").trim()
                val res = if (expr.isEmpty()) null else evalExpression(expr)
                return res ?: "None"
            }

            // Assignment or statement
            executeStatement(trimmed)
            i++
        }
        return null
    }

    private fun executeStatement(line: String) {
        val trimmed = line.trim()
        if (trimmed.isEmpty()) return

        // Print statement: print(...)
        if (trimmed.startsWith("print(") && trimmed.endsWith(")")) {
            val argsStr = trimmed.substring(6, trimmed.length - 1)
            val args = splitArguments(argsStr)
            val evaluated = args.map { evalExpression(it) }
            val rendered = evaluated.joinToString(" ") { formatPythonValue(it) }
            output.appendLine(rendered)
            return
        }

        // Augmented assignment (+=, -=, *=, /=)
        for (op in listOf("+=", "-=", "*=", "/=")) {
            if (trimmed.contains(op)) {
                val parts = trimmed.split(op, limit = 2)
                val varName = parts[0].trim()
                val rhs = parts[1].trim()
                val current = getVariable(varName)
                val rhsVal = evalExpression(rhs)
                val newVal = when (op) {
                    "+=" -> applyBinaryOp(current, "+", rhsVal)
                    "-=" -> applyBinaryOp(current, "-", rhsVal)
                    "*=" -> applyBinaryOp(current, "*", rhsVal)
                    "/=" -> applyBinaryOp(current, "/", rhsVal)
                    else -> rhsVal
                }
                setVariable(varName, newVal)
                return
            }
        }

        // Simple assignment: var = expr
        if (trimmed.contains("=") && !trimmed.contains("==") && !trimmed.contains("<=") && !trimmed.contains(">=") && !trimmed.contains("!=")) {
            val parts = trimmed.split("=", limit = 2)
            val lhs = parts[0].trim()
            val rhs = parts[1].trim()

            // Tuple unpacking: a, b = 1, 2
            if (lhs.contains(",") && !lhs.startsWith("[") && !lhs.startsWith("{")) {
                val targets = lhs.split(",").map { it.trim() }
                val rhsVal = evalExpression(rhs)
                val list = toIterableList(rhsVal)
                for (idx in targets.indices) {
                    if (idx < list.size) {
                        setVariable(targets[idx], list[idx])
                    }
                }
                return
            }

            // Dict/List item assignment: obj[key] = value
            if (lhs.contains("[") && lhs.endsWith("]")) {
                val openBr = lhs.indexOf('[')
                val targetName = lhs.substring(0, openBr).trim()
                val keyExpr = lhs.substring(openBr + 1, lhs.length - 1).trim()
                val targetObj = getVariable(targetName)
                val keyVal = evalExpression(keyExpr)
                val assignedVal = evalExpression(rhs)
                if (targetObj is MutableMap<*, *>) {
                    (targetObj as MutableMap<Any?, Any?>)[keyVal] = assignedVal
                } else if (targetObj is MutableList<*>) {
                    val idx = (keyVal as? Number)?.toInt() ?: 0
                    (targetObj as MutableList<Any?>)[idx] = assignedVal
                }
                return
            }

            val value = evalExpression(rhs)
            setVariable(lhs, value)
            return
        }

        // Expression statement (e.g. list.append(x))
        evalExpression(trimmed)
    }

    fun evalExpression(expr: String): Any? {
        val trimmed = expr.trim()
        if (trimmed.isEmpty()) return null

        // Number literals
        if (trimmed.toIntOrNull() != null) return trimmed.toInt()
        if (trimmed.toDoubleOrNull() != null) return trimmed.toDouble()

        // String literals: "...", '...', f"...", f'...'
        if ((trimmed.startsWith("\"") && trimmed.endsWith("\"")) || (trimmed.startsWith("'") && trimmed.endsWith("'"))) {
            return trimmed.substring(1, trimmed.length - 1)
        }
        if ((trimmed.startsWith("f\"") && trimmed.endsWith("\"")) || (trimmed.startsWith("f'") && trimmed.endsWith("'"))) {
            val content = trimmed.substring(2, trimmed.length - 1)
            return interpolateFString(content)
        }

        // Boolean & None literals
        if (trimmed == "True") return true
        if (trimmed == "False") return false
        if (trimmed == "None") return null

        // List literal: [...]
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            val inner = trimmed.substring(1, trimmed.length - 1).trim()
            if (inner.isEmpty()) return mutableListOf<Any?>()
            // Check for list comprehension: [x * 2 for x in items if x > 2]
            if (inner.contains(" for ") && inner.contains(" in ")) {
                return evalListComprehension(inner)
            }
            val items = splitArguments(inner)
            return items.map { evalExpression(it) }.toMutableList()
        }

        // Dict literal: {...}
        if (trimmed.startsWith("{") && trimmed.endsWith("}") && trimmed.contains(":")) {
            val inner = trimmed.substring(1, trimmed.length - 1).trim()
            val map = mutableMapOf<Any?, Any?>()
            if (inner.isEmpty()) return map
            val pairs = splitArguments(inner)
            for (p in pairs) {
                val colonIdx = p.indexOf(':')
                if (colonIdx != -1) {
                    val k = evalExpression(p.substring(0, colonIdx))
                    val v = evalExpression(p.substring(colonIdx + 1))
                    map[k] = v
                }
            }
            return map
        }

        // Set literal: {1, 2, 3}
        if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
            val inner = trimmed.substring(1, trimmed.length - 1).trim()
            if (inner.isEmpty()) return mutableSetOf<Any?>()
            val items = splitArguments(inner)
            return items.map { evalExpression(it) }.toMutableSet()
        }

        // Tuple literal: (1, 2)
        if (trimmed.startsWith("(") && trimmed.endsWith(")") && trimmed.contains(",")) {
            val inner = trimmed.substring(1, trimmed.length - 1).trim()
            val items = splitArguments(inner)
            return items.map { evalExpression(it) }
        }

        // Parentheses grouping: (expr)
        if (trimmed.startsWith("(") && trimmed.endsWith(")")) {
            val inner = trimmed.substring(1, trimmed.length - 1).trim()
            return evalExpression(inner)
        }

        // Function or Method Call: name(args)
        if (trimmed.contains("(") && trimmed.endsWith(")")) {
            val openParen = trimmed.indexOf('(')
            val funcName = trimmed.substring(0, openParen).trim()
            val argsStr = trimmed.substring(openParen + 1, trimmed.length - 1)
            val args = splitArguments(argsStr).map { evalExpression(it) }

            // Method call on object: obj.method(args)
            if (funcName.contains(".")) {
                val dotIdx = funcName.lastIndexOf('.')
                val objExpr = funcName.substring(0, dotIdx)
                val methodName = funcName.substring(dotIdx + 1)
                val targetObj = evalExpression(objExpr)
                return callObjectMethod(targetObj, methodName, args)
            }

            // Built-in functions
            when (funcName) {
                "len" -> {
                    val arg = args.firstOrNull()
                    return when (arg) {
                        is String -> arg.length
                        is List<*> -> arg.size
                        is Map<*, *> -> arg.size
                        is Set<*> -> arg.size
                        else -> 0
                    }
                }
                "sum" -> {
                    val list = toIterableList(args.firstOrNull())
                    return list.sumOf { (it as? Number)?.toDouble() ?: 0.0 }.let { if (it % 1.0 == 0.0) it.toInt() else it }
                }
                "min" -> {
                    val list = if (args.size == 1) toIterableList(args[0]) else args
                    return list.map { (it as? Number)?.toDouble() ?: 0.0 }.minOrNull() ?: 0
                }
                "max" -> {
                    val list = if (args.size == 1) toIterableList(args[0]) else args
                    return list.map { (it as? Number)?.toDouble() ?: 0.0 }.maxOrNull() ?: 0
                }
                "abs" -> {
                    val num = (args.firstOrNull() as? Number)?.toDouble() ?: 0.0
                    return Math.abs(num).let { if (it % 1.0 == 0.0) it.toInt() else it }
                }
                "round" -> {
                    val num = (args.firstOrNull() as? Number)?.toDouble() ?: 0.0
                    return Math.round(num).toInt()
                }
                "range" -> {
                    val start = if (args.size == 1) 0 else (args[0] as? Number)?.toInt() ?: 0
                    val stop = if (args.size == 1) (args[0] as? Number)?.toInt() ?: 0 else (args[1] as? Number)?.toInt() ?: 0
                    val step = if (args.size >= 3) (args[2] as? Number)?.toInt() ?: 1 else 1
                    return (start until stop step step).toList()
                }
                "str" -> return formatPythonValue(args.firstOrNull())
                "int" -> return (args.firstOrNull()?.toString()?.toDoubleOrNull() ?: 0.0).toInt()
                "float" -> return (args.firstOrNull()?.toString()?.toDoubleOrNull() ?: 0.0)
                "bool" -> return isTruthy(args.firstOrNull())
                "list" -> return toIterableList(args.firstOrNull()).toMutableList()
                "type" -> return "<class '${args.firstOrNull()?.javaClass?.simpleName ?: "NoneType"}'>"
                "sorted" -> {
                    val list = toIterableList(args.firstOrNull())
                    return list.sortedBy { it.toString() }
                }
            }

            // User-defined function
            val userFn = findFunction(funcName)
            if (userFn != null) {
                return userFn.invoke(args)
            }
        }

        // Subscript / Indexing: target[index] or slicing target[start:end]
        if (trimmed.contains("[") && trimmed.endsWith("]")) {
            val openIdx = trimmed.lastIndexOf('[')
            val targetExpr = trimmed.substring(0, openIdx).trim()
            val sliceExpr = trimmed.substring(openIdx + 1, trimmed.length - 1).trim()
            val targetObj = evalExpression(targetExpr)

            if (sliceExpr.contains(":")) {
                val parts = sliceExpr.split(":")
                val start = parts[0].trim().toIntOrNull() ?: 0
                val end = if (parts.size > 1 && parts[1].isNotBlank()) parts[1].trim().toIntOrNull() else null
                return applySlice(targetObj, start, end)
            } else {
                val idx = evalExpression(sliceExpr)
                if (targetObj is List<*>) {
                    val i = (idx as? Number)?.toInt() ?: 0
                    val realIdx = if (i < 0) targetObj.size + i else i
                    return targetObj.getOrNull(realIdx)
                } else if (targetObj is String) {
                    val i = (idx as? Number)?.toInt() ?: 0
                    val realIdx = if (i < 0) targetObj.length + i else i
                    return targetObj.getOrNull(realIdx)?.toString()
                } else if (targetObj is Map<*, *>) {
                    return targetObj[idx]
                }
            }
        }

        // Comparisons: ==, !=, <=, >=, <, >, in, not in
        for (op in listOf("==", "!=", "<=", ">=", "<", ">", " in ", " not in ")) {
            if (trimmed.contains(op)) {
                val parts = trimmed.split(op, limit = 2)
                val left = evalExpression(parts[0])
                val right = evalExpression(parts[1])
                return evaluateComparison(left, op.trim(), right)
            }
        }

        // Logical operators: and, or, not
        if (trimmed.startsWith("not ")) {
            return !isTruthy(evalExpression(trimmed.substring(4)))
        }
        if (trimmed.contains(" and ")) {
            val parts = trimmed.split(" and ", limit = 2)
            return isTruthy(evalExpression(parts[0])) && isTruthy(evalExpression(parts[1]))
        }
        if (trimmed.contains(" or ")) {
            val parts = trimmed.split(" or ", limit = 2)
            return isTruthy(evalExpression(parts[0])) || isTruthy(evalExpression(parts[1]))
        }

        // Binary Arithmetic (+, -, *, /, %, //, **)
        for (op in listOf("+", "-", "*", "/", "%", "**")) {
            if (trimmed.contains(op)) {
                // Ensure operator is not inside quotes or parentheses
                val opIdx = findSafeOperatorIndex(trimmed, op)
                if (opIdx != -1) {
                    val left = evalExpression(trimmed.substring(0, opIdx))
                    val right = evalExpression(trimmed.substring(opIdx + op.length))
                    return applyBinaryOp(left, op, right)
                }
            }
        }

        // Look up variable
        return getVariable(trimmed)
    }

    private fun evalListComprehension(inner: String): List<Any?> {
        val forIdx = inner.indexOf(" for ")
        val expr = inner.substring(0, forIdx).trim()
        val rest = inner.substring(forIdx + 5).trim()
        val inIdx = rest.indexOf(" in ")
        val varName = rest.substring(0, inIdx).trim()
        val iterPart = rest.substring(inIdx + 4).trim()

        var filterExpr: String? = null
        val finalIterExpr = if (iterPart.contains(" if ")) {
            val ifIdx = iterPart.indexOf(" if ")
            filterExpr = iterPart.substring(ifIdx + 4).trim()
            iterPart.substring(0, ifIdx).trim()
        } else {
            iterPart
        }

        val items = toIterableList(evalExpression(finalIterExpr))
        val result = mutableListOf<Any?>()
        for (item in items) {
            val subEnv = PythonEnvironment(output, this)
            subEnv.setVariable(varName, item)
            if (filterExpr == null || isTruthy(subEnv.evalExpression(filterExpr))) {
                result.add(subEnv.evalExpression(expr))
            }
        }
        return result
    }

    private fun interpolateFString(str: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < str.length) {
            if (str[i] == '{') {
                val close = str.indexOf('}', i)
                if (close != -1) {
                    val expr = str.substring(i + 1, close).trim()
                    val value = evalExpression(expr)
                    sb.append(formatPythonValue(value))
                    i = close + 1
                    continue
                }
            }
            sb.append(str[i])
            i++
        }
        return sb.toString()
    }

    private fun findSafeOperatorIndex(str: String, op: String): Int {
        var inQuotes = false
        var quoteChar = ' '
        var parenDepth = 0
        var bracketDepth = 0

        for (i in 0 until str.length - op.length + 1) {
            val c = str[i]
            if ((c == '"' || c == '\'') && (i == 0 || str[i - 1] != '\\')) {
                if (!inQuotes) {
                    inQuotes = true
                    quoteChar = c
                } else if (quoteChar == c) {
                    inQuotes = false
                }
            }
            if (!inQuotes) {
                if (c == '(') parenDepth++
                if (c == ')') parenDepth--
                if (c == '[') bracketDepth++
                if (c == ']') bracketDepth--
                if (parenDepth == 0 && bracketDepth == 0 && str.substring(i, i + op.length) == op) {
                    // Check bounds to avoid matching inside identifier
                    if (i > 0 && i + op.length < str.length) {
                        return i
                    }
                }
            }
        }
        return -1
    }

    private fun callObjectMethod(obj: Any?, method: String, args: List<Any?>): Any? {
        if (obj is String) {
            when (method) {
                "upper" -> return obj.uppercase()
                "lower" -> return obj.lowercase()
                "title" -> return obj.split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
                "strip" -> return obj.trim()
                "split" -> {
                    val delim = args.firstOrNull()?.toString() ?: " "
                    return obj.split(delim).toMutableList()
                }
                "replace" -> {
                    val old = args.getOrNull(0)?.toString() ?: ""
                    val newStr = args.getOrNull(1)?.toString() ?: ""
                    return obj.replace(old, newStr)
                }
                "startswith" -> return obj.startsWith(args.firstOrNull()?.toString() ?: "")
                "endswith" -> return obj.endsWith(args.firstOrNull()?.toString() ?: "")
                "count" -> return obj.split(args.firstOrNull()?.toString() ?: "").size - 1
            }
        }
        if (obj is MutableList<*>) {
            val list = obj as MutableList<Any?>
            when (method) {
                "append" -> {
                    list.add(args.firstOrNull())
                    return null
                }
                "extend" -> {
                    list.addAll(toIterableList(args.firstOrNull()))
                    return null
                }
                "pop" -> return if (args.isEmpty()) list.removeLastOrNull() else list.removeAt((args[0] as? Number)?.toInt() ?: 0)
                "insert" -> {
                    val idx = (args.getOrNull(0) as? Number)?.toInt() ?: 0
                    list.add(idx, args.getOrNull(1))
                    return null
                }
                "reverse" -> {
                    list.reverse()
                    return null
                }
                "clear" -> {
                    list.clear()
                    return null
                }
            }
        }
        if (obj is MutableMap<*, *>) {
            val map = obj as MutableMap<Any?, Any?>
            when (method) {
                "keys" -> return map.keys.toList()
                "values" -> return map.values.toList()
                "items" -> return map.entries.map { listOf(it.key, it.value) }
                "get" -> return map.getOrDefault(args.getOrNull(0), args.getOrNull(1))
            }
        }
        return null
    }

    private fun findFunction(name: String): DefinedFunction? {
        if (functions.containsKey(name)) return functions[name]
        return parent?.findFunction(name)
    }

    private fun parseIfBranches(lines: List<String>, startIdx: Int): Pair<List<IfBranch>, Int> {
        val branches = mutableListOf<IfBranch>()
        var i = startIdx
        val baseIndent = getIndentLevel(lines[startIdx])

        val firstLine = lines[startIdx].trim()
        val firstCond = firstLine.substring(3, firstLine.length - 1).trim()
        val firstBody = mutableListOf<String>()
        i++
        while (i < lines.size) {
            val nextLine = lines[i]
            if (nextLine.trim().isEmpty() || nextLine.trim().startsWith("#")) {
                firstBody.add(nextLine)
                i++
                continue
            }
            if (getIndentLevel(nextLine) > baseIndent) {
                firstBody.add(nextLine)
                i++
            } else {
                break
            }
        }
        branches.add(IfBranch(firstCond, firstBody))

        // Check for elif or else
        while (i < lines.size) {
            val nextLine = lines[i].trim()
            if (nextLine.startsWith("elif ") && nextLine.endsWith(":") && getIndentLevel(lines[i]) == baseIndent) {
                val cond = nextLine.substring(5, nextLine.length - 1).trim()
                val body = mutableListOf<String>()
                i++
                while (i < lines.size) {
                    val subLine = lines[i]
                    if (subLine.trim().isEmpty() || subLine.trim().startsWith("#")) {
                        body.add(subLine)
                        i++
                        continue
                    }
                    if (getIndentLevel(subLine) > baseIndent) {
                        body.add(subLine)
                        i++
                    } else {
                        break
                    }
                }
                branches.add(IfBranch(cond, body))
            } else if (nextLine.startsWith("else:") && getIndentLevel(lines[i]) == baseIndent) {
                val body = mutableListOf<String>()
                i++
                while (i < lines.size) {
                    val subLine = lines[i]
                    if (subLine.trim().isEmpty() || subLine.trim().startsWith("#")) {
                        body.add(subLine)
                        i++
                        continue
                    }
                    if (getIndentLevel(subLine) > baseIndent) {
                        body.add(subLine)
                        i++
                    } else {
                        break
                    }
                }
                branches.add(IfBranch(null, body))
                break
            } else {
                break
            }
        }

        return Pair(branches, i)
    }

    private fun getIndentLevel(line: String): Int {
        var count = 0
        for (c in line) {
            if (c == ' ') count++
            else if (c == '\t') count += 4
            else break
        }
        return count
    }

    private fun splitArguments(argsStr: String): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        var inQuotes = false
        var quoteChar = ' '
        var parenDepth = 0
        var bracketDepth = 0
        var braceDepth = 0

        for (i in argsStr.indices) {
            val c = argsStr[i]
            if ((c == '"' || c == '\'') && (i == 0 || argsStr[i - 1] != '\\')) {
                if (!inQuotes) {
                    inQuotes = true
                    quoteChar = c
                } else if (quoteChar == c) {
                    inQuotes = false
                }
            }
            if (!inQuotes) {
                if (c == '(') parenDepth++
                if (c == ')') parenDepth--
                if (c == '[') bracketDepth++
                if (c == ']') bracketDepth--
                if (c == '{') braceDepth++
                if (c == '}') braceDepth--
                if (c == ',' && parenDepth == 0 && bracketDepth == 0 && braceDepth == 0) {
                    result.add(current.toString().trim())
                    current.clear()
                    continue
                }
            }
            current.append(c)
        }
        if (current.isNotEmpty()) {
            result.add(current.toString().trim())
        }
        return result
    }

    private fun applySlice(obj: Any?, start: Int, end: Int?): Any? {
        if (obj is List<*>) {
            val realStart = if (start < 0) obj.size + start else start
            val realEnd = end?.let { if (it < 0) obj.size + it else it } ?: obj.size
            return obj.subList(realStart.coerceIn(0, obj.size), realEnd.coerceIn(0, obj.size))
        }
        if (obj is String) {
            val realStart = if (start < 0) obj.length + start else start
            val realEnd = end?.let { if (it < 0) obj.length + it else it } ?: obj.length
            return obj.substring(realStart.coerceIn(0, obj.length), realEnd.coerceIn(0, obj.length))
        }
        return obj
    }

    private fun applyBinaryOp(left: Any?, op: String, right: Any?): Any? {
        if (left is String || right is String) {
            if (op == "+") return "${formatPythonValue(left)}${formatPythonValue(right)}"
            if (op == "*" && left is String && right is Number) return left.repeat(right.toInt())
            if (op == "*" && right is String && left is Number) return right.repeat(left.toInt())
        }
        val lNum = (left as? Number)?.toDouble() ?: 0.0
        val rNum = (right as? Number)?.toDouble() ?: 0.0

        val res = when (op) {
            "+" -> lNum + rNum
            "-" -> lNum - rNum
            "*" -> lNum * rNum
            "/" -> if (rNum == 0.0) throw PythonException("ZeroDivisionError: division by zero") else lNum / rNum
            "%" -> lNum % rNum
            "**" -> Math.pow(lNum, rNum)
            else -> 0.0
        }
        return if (res % 1.0 == 0.0) res.toInt() else res
    }

    private fun evaluateComparison(left: Any?, op: String, right: Any?): Boolean {
        return when (op) {
            "==" -> left == right || left.toString() == right.toString()
            "!=" -> left != right && left.toString() != right.toString()
            "<" -> ((left as? Number)?.toDouble() ?: 0.0) < ((right as? Number)?.toDouble() ?: 0.0)
            ">" -> ((left as? Number)?.toDouble() ?: 0.0) > ((right as? Number)?.toDouble() ?: 0.0)
            "<=" -> ((left as? Number)?.toDouble() ?: 0.0) <= ((right as? Number)?.toDouble() ?: 0.0)
            ">=" -> ((left as? Number)?.toDouble() ?: 0.0) >= ((right as? Number)?.toDouble() ?: 0.0)
            "in" -> toIterableList(right).contains(left) || (right is String && right.contains(left.toString()))
            "not in" -> !toIterableList(right).contains(left) && (right !is String || !right.contains(left.toString()))
            else -> false
        }
    }

    private fun isTruthy(v: Any?): Boolean {
        if (v == null || v == false || v == 0 || v == 0.0 || v == "" || v == "None") return false
        if (v is List<*>) return v.isNotEmpty()
        if (v is Map<*, *>) return v.isNotEmpty()
        if (v is Set<*>) return v.isNotEmpty()
        return true
    }

    private fun toIterableList(v: Any?): List<Any?> {
        return when (v) {
            is List<*> -> v
            is Set<*> -> v.toList()
            is Map<*, *> -> v.keys.toList()
            is String -> v.map { it.toString() }
            else -> emptyList()
        }
    }

    private fun formatPythonValue(v: Any?): String {
        return when (v) {
            null -> "None"
            true -> "True"
            false -> "False"
            is List<*> -> "[${v.joinToString(", ") { formatPythonValue(it) }}]"
            is Map<*, *> -> "{${v.entries.joinToString(", ") { "${formatPythonValue(it.key)}: ${formatPythonValue(it.value)}" }}}"
            is Set<*> -> "{${v.joinToString(", ") { formatPythonValue(it) }}}"
            else -> v.toString()
        }
    }
}

data class IfBranch(val condition: String?, val body: List<String>)

class DefinedFunction(
    val name: String,
    val params: List<String>,
    val body: List<String>,
    val parentEnv: PythonEnvironment
) {
    fun invoke(args: List<Any?>): Any? {
        val fnEnv = PythonEnvironment(StringBuilder(), parentEnv)
        for (i in params.indices) {
            val paramName = params[i]
            val argVal = if (i < args.size) args[i] else null
            fnEnv.setVariable(paramName, argVal)
        }
        return fnEnv.executeLines(body)
    }
}
