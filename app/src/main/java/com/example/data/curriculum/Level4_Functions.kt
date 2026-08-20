package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level4_Functions {
    val lessons = listOf(
        Lesson(
            id = "l4_1",
            levelId = 4,
            orderNumber = 1,
            title = "Defining & Calling Functions",
            subtitle = "Encapsulating reusable logic with def and return statements",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Python Functions

Functions are reusable blocks of code defined with the `def` keyword.

### Key Rules:
- Functions can take inputs (parameters) and produce outputs (`return`).
- If no `return` statement is specified, Python implicitly returns `None`.
- Promotes the **DRY (Don't Repeat Yourself)** principle.
            """.trimIndent(),
            syntax = "def function_name(param1, param2):\n    return result",
            codeExample = """# Defining a function
def greet_learner(name):
    return f"Welcome to the Academy, {name}!"

msg = greet_learner("Jordan")
print(msg)
""",
            expectedOutput = "Welcome to the Academy, Jordan!",
            commonMistakes = listOf(
                "Forgetting the return keyword (resulting in None when assigning the result).",
                "Calling the function before defining it in the script."
            ),
            keyTakeaways = listOf(
                "def defines a function; () invokes it.",
                "return passes data back to the caller.",
                "Parameters are inputs; arguments are the values supplied when calling."
            ),
            practiceTask = PracticeTask(
                title = "Create a Square Function",
                description = "Define a function square(n) that returns n * n. Call square(6) and print the result.",
                starterCode = "# Define and call square\n",
                expectedOutput = "36",
                solutionCode = "def square(n):\n    return n * n\n\nprint(square(6))",
                hint = "def square(n): return n * n"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does a Python function return if there is no explicit 'return' statement?",
                options = listOf("0", "False", "None", "Empty string"),
                correctIndex = 2,
                explanation = "Functions without an explicit return statement implicitly return None."
            )
        ),
        Lesson(
            id = "l4_2",
            levelId = 4,
            orderNumber = 2,
            title = "Parameters, Defaults & Keyword Arguments",
            subtitle = "Positional vs keyword arguments and default parameter values",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Function Arguments & Default Parameters

Python allows specifying default values for parameters, making them optional during calls.

### Rules:
- Positional arguments are passed in order.
- Keyword arguments specify `param_name=value` explicitly.
- Default parameters **must follow** non-default parameters in the definition signature.
            """.trimIndent(),
            syntax = "def calculate(base, multiplier=1):\n    return base * multiplier",
            codeExample = """# Function with default argument
def calculate_xp(lessons_completed, xp_per_lesson=50):
    return lessons_completed * xp_per_lesson

print(calculate_xp(4))       # Uses default 50
print(calculate_xp(4, 100))  # Overrides with 100
""",
            expectedOutput = "200\n400",
            commonMistakes = listOf(
                "Placing default parameters before non-default ones: def f(a=1, b) causes SyntaxError.",
                "Using mutable objects (like lists or dicts) as default arguments (leads to persistent shared state)."
            ),
            keyTakeaways = listOf(
                "Default values make function arguments optional.",
                "Keyword arguments improve call-site clarity.",
                "Always use None for mutable defaults (e.g. def f(lst=None))."
            ),
            practiceTask = PracticeTask(
                title = "Power with Default Exponent",
                description = "Define power(base, exp=2) returning base ** exp. Print power(5) and power(2, 3).",
                starterCode = "# Define power function\n",
                expectedOutput = "25\n8",
                solutionCode = "def power(base, exp=2):\n    return base ** exp\n\nprint(power(5))\nprint(power(2, 3))",
                hint = "def power(base, exp=2): return base ** exp"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which parameter definition is invalid in Python?",
                options = listOf(
                    "def calc(a, b=10):",
                    "def calc(a=10, b):",
                    "def calc(a, b, c=1):",
                    "def calc(a=1, b=2):"
                ),
                correctIndex = 1,
                explanation = "Non-default arguments cannot follow default arguments in parameter lists."
            )
        ),
        Lesson(
            id = "l4_3",
            levelId = 4,
            orderNumber = 3,
            title = "Variable-Length Arguments: *args and **kwargs",
            subtitle = "Accepting arbitrary positional and keyword arguments",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# *args & **kwargs

When writing flexible APIs or wrapper functions:
- `*args`: Packs extra positional arguments into a **tuple**.
- `**kwargs`: Packs extra keyword arguments into a **dictionary**.
            """.trimIndent(),
            syntax = "def func(*args, **kwargs):\n    pass",
            codeExample = """# Flexible argument handling
def calculate_total(*numbers):
    return sum(numbers)

print(calculate_total(10, 20, 30))
print(calculate_total(5, 15))
""",
            expectedOutput = "60\n20",
            commonMistakes = listOf(
                "Wrong order in definition: Standard order must be positional, *args, default, **kwargs."
            ),
            keyTakeaways = listOf(
                "*args packs positional arguments into a tuple.",
                "**kwargs packs named keyword arguments into a dict.",
                "Widely used in decorators and subclassing."
            ),
            practiceTask = PracticeTask(
                title = "Multiply All Arguments",
                description = "Write a function multiply_all(*nums) that multiplies all given numbers and returns the product. Print multiply_all(2, 3, 4).",
                starterCode = "# Define multiply_all\n",
                expectedOutput = "24",
                solutionCode = "def multiply_all(*nums):\n    result = 1\n    for n in nums:\n        result *= n\n    return result\n\nprint(multiply_all(2, 3, 4))",
                hint = "Loop through nums and multiply."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What type does *args receive inside a function body?",
                options = listOf("List", "Tuple", "Dictionary", "Set"),
                correctIndex = 1,
                explanation = "*args collects variable positional arguments into a Tuple."
            )
        ),
        Lesson(
            id = "l4_4",
            levelId = 4,
            orderNumber = 4,
            title = "Variable Scope & LEGB Rule",
            subtitle = "Understanding Local, Enclosing, Global, and Built-in namespaces",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# The LEGB Scope Resolution Rule

When looking up a variable name, Python checks scopes in this strict order:
1. **L**ocal: Inside the current function.
2. **E**nclosing: In enclosing/outer functions (closures).
3. **G**lobal: At the top module level.
4. **B**uilt-in: Python's built-in namespace (`len`, `range`, `print`).

### The global & nonlocal keywords:
- `global var`: Allows modifying a module-level global variable from inside a function.
- `nonlocal var`: Allows modifying an enclosing outer function's variable.
            """.trimIndent(),
            syntax = "global var_name\nnonlocal var_name",
            codeExample = """# Scope demonstration
x = "Global"

def test_scope():
    x = "Local"
    print(f"Inside: {x}")

test_scope()
print(f"Outside: {x}")
""",
            expectedOutput = "Inside: Local\nOutside: Global",
            commonMistakes = listOf(
                "UnboundLocalError: Reading a global variable then assigning to it in the same function without global.",
                "Overusing global variables (harmful for code modularity and testability)."
            ),
            keyTakeaways = listOf(
                "LEGB defines how Python searches for identifier names.",
                "Variables created inside functions default to local scope.",
                "Keep functions pure and avoid relying on mutable globals."
            ),
            practiceTask = PracticeTask(
                title = "Local Variable Isolation",
                description = "Given global val = 100, define a function modify() that creates a local val = 50 and prints it. Then call modify() and print global val.",
                starterCode = "val = 100\n# Write function and prints\n",
                expectedOutput = "50\n100",
                solutionCode = "val = 100\ndef modify():\n    val = 50\n    print(val)\n\nmodify()\nprint(val)",
                hint = "Define local val = 50 inside modify."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does the LEGB acronym stand for?",
                options = listOf(
                    "Local, Enclosing, Global, Built-in",
                    "List, Element, Group, Block",
                    "Lexical, External, Generic, Boolean",
                    "Loop, Expression, Generator, Branch"
                ),
                correctIndex = 0,
                explanation = "LEGB stands for Local, Enclosing, Global, Built-in scope hierarchy."
            )
        ),
        Lesson(
            id = "l4_5",
            levelId = 4,
            orderNumber = 5,
            title = "Lambda Functions & Anonymous Callables",
            subtitle = "Single-expression inline functions for quick transformations",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Lambda Functions

A `lambda` function is a small, anonymous function defined inline with a single expression.

### Syntax:
`lambda arguments: expression`

### Use Cases:
Commonly passed as callback arguments to sorting keys, `map()`, or `filter()`.
            """.trimIndent(),
            syntax = "lambda x, y: x + y",
            codeExample = """# Lambda expressions
double = lambda x: x * 2
print(double(7))

# Used with custom sorting
students = [("Sam", 85), ("Maya", 95), ("Leo", 78)]
students.sort(key=lambda item: item[1]) # Sort by score
print(students)
""",
            expectedOutput = "14\n[('Leo', 78), ('Sam', 85), ('Maya', 95)]",
            commonMistakes = listOf(
                "Trying to include multiple statements or assignments inside a lambda (lambdas only accept single expressions).",
                "Assigning lambdas to variables when standard def is clearer (PEP 8 recommends def over lambda for named functions)."
            ),
            keyTakeaways = listOf(
                "Lambdas are anonymous, single-expression functions.",
                "Ideal as short key functions for sorted(), min(), max()."
            ),
            practiceTask = PracticeTask(
                title = "Create a Cube Lambda",
                description = "Define a lambda cube = lambda x: x ** 3. Print cube(3).",
                starterCode = "# Define and print\n",
                expectedOutput = "27",
                solutionCode = "cube = lambda x: x ** 3\nprint(cube(3))",
                hint = "cube = lambda x: x ** 3"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is a major limitation of lambda functions in Python?",
                options = listOf(
                    "They cannot return values",
                    "They can only contain a single expression",
                    "They cannot accept arguments",
                    "They only work on strings"
                ),
                correctIndex = 1,
                explanation = "Lambda bodies are restricted to a single evaluated expression."
            )
        ),
        Lesson(
            id = "l4_6",
            levelId = 4,
            orderNumber = 6,
            title = "Recursion Fundamentals & Base Cases",
            subtitle = "Functions calling themselves to solve divide-and-conquer problems",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Recursion in Python

A recursive function is a function that calls itself to break a large problem down into simpler sub-problems.

### Essential Components:
1. **Base Case**: The stopping condition that prevents infinite recursion.
2. **Recursive Step**: The call to itself with modified arguments moving closer to the base case.
            """.trimIndent(),
            syntax = "def factorial(n):\n    if n <= 1: return 1\n    return n * factorial(n - 1)",
            codeExample = """# Recursive factorial
def factorial(n):
    if n <= 1: # Base case
        return 1
    return n * factorial(n - 1) # Recursive step

print(f"5! = {factorial(5)}")
""",
            expectedOutput = "5! = 120",
            commonMistakes = listOf(
                "Forgetting the base case (results in RecursionError: maximum recursion depth exceeded).",
                "Deep recursion causing stack overflow on large inputs without memoization."
            ),
            keyTakeaways = listOf(
                "Every recursive function MUST have at least one base case.",
                "Recursion elegantly solves tree traversal and combinatorial problems."
            ),
            practiceTask = PracticeTask(
                title = "Recursive Countdown",
                description = "Write a recursive function countdown(n) that prints n, and calls countdown(n-1) while n > 0. Stop when n == 0. Call countdown(3).",
                starterCode = "# Define and call countdown\n",
                expectedOutput = "3\n2\n1",
                solutionCode = "def countdown(n):\n    if n <= 0:\n        return\n    print(n)\n    countdown(n - 1)\n\ncountdown(3)",
                hint = "Print n then call countdown(n - 1)."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What happens if a recursive function lacks a valid base case?",
                options = listOf(
                    "Returns 0 automatically",
                    "Raises RecursionError",
                    "Converts to a while loop",
                    "SyntaxError during compilation"
                ),
                correctIndex = 1,
                explanation = "Missing base cases cause infinite recursion until the call stack limit triggers a RecursionError."
            )
        ),
        Lesson(
            id = "l4_7",
            levelId = 4,
            orderNumber = 7,
            title = "Level 4 Mastery Review & Summary",
            subtitle = "Consolidating functions, arguments, scope, and recursion",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 4 Milestone: Functions Mastered!

You have mastered procedural and functional abstraction in Python:
- `def` signatures & `return` mechanics
- Default parameters & keyword arguments
- `*args` and `**kwargs`
- LEGB scope resolution
- Lambda anonymous callables
- Recursive problem solving

In **Level 5**, we explore **Modules, Packages, and File I/O (Text, JSON, CSV)**!
            """.trimIndent(),
            syntax = "# Level 4 Complete",
            codeExample = """# Clean helper pipeline
def format_user_card(name, role="Student", **details):
    return f"[{role}] {name} - Total: {len(details)} details"

print(format_user_card("Alex", xp=500, level=4))
""",
            expectedOutput = "[Student] Alex - Total: 2 details",
            commonMistakes = listOf(
                "Writing monolithic functions with too many side effects."
            ),
            keyTakeaways = listOf(
                "Functions encapsulate logic into testable units.",
                "Take the Level 4 Quiz to claim your XP!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 4 Complete",
                description = "Print 'Level 4 Complete! Onward to Modules and Files.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 4 Complete! Onward to Modules and Files.",
                solutionCode = "print(\"Level 4 Complete! Onward to Modules and Files.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which keyword is used to declare a function in Python?",
                options = listOf("function", "fn", "def", "func"),
                correctIndex = 2,
                explanation = "Python uses the 'def' keyword to define functions."
            )
        )
    )
}
