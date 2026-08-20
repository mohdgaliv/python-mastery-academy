package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level2_ControlFlow {
    val lessons = listOf(
        Lesson(
            id = "l2_1",
            levelId = 2,
            orderNumber = 1,
            title = "Conditional Logic: If, Elif & Else",
            subtitle = "Directing program execution paths based on boolean evaluations",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Decision Making in Python

Conditionals evaluate expressions to `True` or `False` and branch execution accordingly.

### Syntax Structure:
```python
if condition1:
    # Executes when condition1 is True
elif condition2:
    # Executes when condition2 is True
else:
    # Executes when all above conditions are False
```

### Indentation:
Python uses whitespace indentation (standard 4 spaces) rather than curly braces `{}` to define code blocks.
            """.trimIndent(),
            syntax = "if condition:\n    pass\nelif other:\n    pass\nelse:\n    pass",
            codeExample = """# Conditional evaluation
score = 85

if score >= 90:
    grade = "A"
elif score >= 80:
    grade = "B"
elif score >= 70:
    grade = "C"
else:
    grade = "F"

print(f"Final Grade: {grade}")
""",
            expectedOutput = "Final Grade: B",
            commonMistakes = listOf(
                "Mixing tabs and spaces for indentation.",
                "Using assignment = instead of equality comparison == inside conditions."
            ),
            keyTakeaways = listOf(
                "Python code blocks are defined strictly by consistent indentation.",
                "Only the first matching True branch is executed.",
                "The else branch is optional but catches any unhandled cases."
            ),
            practiceTask = PracticeTask(
                title = "Check Pass / Fail",
                description = "Given score = 75, write an if-else statement that prints 'Passed' if score >= 60, otherwise 'Failed'.",
                starterCode = "score = 75\n# Check condition and print\n",
                expectedOutput = "Passed",
                solutionCode = "score = 75\nif score >= 60:\n    print(\"Passed\")\nelse:\n    print(\"Failed\")",
                hint = "Use if score >= 60: print(\"Passed\")"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "How does Python define blocks of code within an if statement?",
                options = listOf("Curly braces {}", "Parentheses ()", "Indentation (whitespace)", "Semicolons"),
                correctIndex = 2,
                explanation = "Python uses consistent indentation to delineate code blocks."
            )
        ),
        Lesson(
            id = "l2_2",
            levelId = 2,
            orderNumber = 2,
            title = "Logical & Comparison Operators",
            subtitle = "Combining conditions using and, or, not, and comparison chaining",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Logical Operators & Short-Circuit Evaluation

Combine multiple boolean expressions:
- `and`: Evaluates to `True` only if **both** operands are True.
- `or`: Evaluates to `True` if **at least one** operand is True.
- `not`: Inverts boolean state (`not True` -> `False`).

### Python Chained Comparisons:
Python elegantly supports mathematical chained comparisons like `18 <= age <= 65` instead of `age >= 18 and age <= 65`.
            """.trimIndent(),
            syntax = "cond1 and cond2\ncond1 or cond2\nnot cond\n0 <= x <= 100",
            codeExample = """# Chained and compound conditions
age = 22
has_id = True

if age >= 18 and has_id:
    print("Access Granted")

x = 15
if 10 <= x <= 20:
    print("x is within range [10, 20]")
""",
            expectedOutput = "Access Granted\nx is within range [10, 20]",
            commonMistakes = listOf(
                "Using & and | (bitwise operators) instead of boolean 'and' and 'or'.",
                "Writing 'if x == 1 or 2' instead of 'if x == 1 or x == 2'."
            ),
            keyTakeaways = listOf(
                "'and' and 'or' perform short-circuit evaluation for efficiency.",
                "Chained comparisons like 0 < x < 10 are unique and idiomatic in Python."
            ),
            practiceTask = PracticeTask(
                title = "Compound Condition Check",
                description = "Given is_admin = True and is_logged_in = True, print 'Authorized' if both are True, else 'Denied'.",
                starterCode = "is_admin = True\nis_logged_in = True\n# Check and print\n",
                expectedOutput = "Authorized",
                solutionCode = "is_admin = True\nis_logged_in = True\nif is_admin and is_logged_in:\n    print(\"Authorized\")\nelse:\n    print(\"Denied\")",
                hint = "Use if is_admin and is_logged_in:"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does the expression 'not False' evaluate to?",
                options = listOf("None", "True", "False", "Error"),
                correctIndex = 1,
                explanation = "'not' inverts the truth value, making False become True."
            )
        ),
        Lesson(
            id = "l2_3",
            levelId = 2,
            orderNumber = 3,
            title = "For Loops & the range() Function",
            subtitle = "Iterating over sequences, intervals, and step ranges",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# For Loops & range()

In Python, `for` loops iterate over the elements of any iterable sequence (lists, strings, ranges, dictionaries).

### The range() function:
- `range(stop)`: 0 up to (not including) stop
- `range(start, stop)`: start up to stop
- `range(start, stop, step)`: increments by step
            """.trimIndent(),
            syntax = "for item in iterable:\n    pass\nfor i in range(start, stop, step):\n    pass",
            codeExample = """# For loop with range
for i in range(1, 6):
    print(f"Step {i}")

# Iterating over a string
for char in "PY":
    print(f"Char: {char}")
""",
            expectedOutput = "Step 1\nStep 2\nStep 3\nStep 4\nStep 5\nChar: P\nChar: Y",
            commonMistakes = listOf(
                "Expecting range(1, 5) to include 5 (it stops at 4; range is half-open [start, stop)).",
                "Modifying a list while actively iterating over it."
            ),
            keyTakeaways = listOf(
                "range() is a memory-efficient generator of arithmetic progressions.",
                "For loops directly extract items without requiring manual index counters."
            ),
            practiceTask = PracticeTask(
                title = "Print Multiples of 3",
                description = "Use a for loop and range to print numbers 3, 6, 9 (each on a new line).",
                starterCode = "# For loop with range\n",
                expectedOutput = "3\n6\n9",
                solutionCode = "for n in range(3, 10, 3):\n    print(n)",
                hint = "Use range(3, 10, 3)."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the last number generated by range(0, 10, 2)?",
                options = listOf("10", "8", "9", "12"),
                correctIndex = 1,
                explanation = "range(0, 10, 2) generates 0, 2, 4, 6, 8 (10 is excluded)."
            )
        ),
        Lesson(
            id = "l2_4",
            levelId = 2,
            orderNumber = 4,
            title = "While Loops & State-Driven Iteration",
            subtitle = "Repeating code based on dynamic runtime conditions",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# While Loops

A `while` loop executes as long as its target condition evaluates to `True`.

### Avoiding Infinite Loops:
Ensure that the loop body modifies a state variable so that the loop condition eventually becomes `False`.
            """.trimIndent(),
            syntax = "while condition:\n    # code to repeat\n    # state update",
            codeExample = """# Counting down with a while loop
countdown = 3

while countdown > 0:
    print(f"T-minus {countdown}")
    countdown -= 1

print("Liftoff!")
""",
            expectedOutput = "T-minus 3\nT-minus 2\nT-minus 1\nLiftoff!",
            commonMistakes = listOf(
                "Forgetting to decrement/increment the counter, creating an infinite loop.",
                "Off-by-one errors in loop boundaries."
            ),
            keyTakeaways = listOf(
                "Use while loops when the number of iterations is not known in advance.",
                "Always guarantee a termination condition."
            ),
            practiceTask = PracticeTask(
                title = "Double Until Limit",
                description = "Start with val = 1. Double it in a while loop as long as val < 10, printing val at each step.",
                starterCode = "val = 1\n# Write while loop\n",
                expectedOutput = "1\n2\n4\n8",
                solutionCode = "val = 1\nwhile val < 10:\n    print(val)\n    val *= 2",
                hint = "Print before multiplying by 2."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What causes an infinite loop in a while loop?",
                options = listOf(
                    "The condition never evaluates to False",
                    "Using a boolean variable",
                    "Indenting the body",
                    "Printing inside the loop"
                ),
                correctIndex = 0,
                explanation = "If the condition remains True perpetually, the loop never terminates."
            )
        ),
        Lesson(
            id = "l2_5",
            levelId = 2,
            orderNumber = 5,
            title = "Loop Control: Break, Continue & Pass",
            subtitle = "Fine-grained control over loop execution and placeholders",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Loop Control Statements

- `break`: Immediately terminates the innermost loop.
- `continue`: Skips the remainder of the current iteration and jumps to the next.
- `pass`: A null operation placeholder when syntax requires a statement.
            """.trimIndent(),
            syntax = "break\ncontinue\npass",
            codeExample = """# Demonstrating break and continue
for n in range(1, 6):
    if n == 2:
        continue # Skip 2
    if n == 5:
        break    # Stop before 5
    print(n)
""",
            expectedOutput = "1\n3\n4",
            commonMistakes = listOf(
                "Using break outside of a loop (causes SyntaxError).",
                "Confusing pass (does nothing, continues execution) with continue (skips to next iteration)."
            ),
            keyTakeaways = listOf(
                "break is essential for early-exit algorithms.",
                "continue allows filtering iterations without deep nesting.",
                "pass allows defining empty code blocks during scaffolding."
            ),
            practiceTask = PracticeTask(
                title = "Skip Even Numbers",
                description = "Iterate numbers 1 to 5. If number is even (n % 2 == 0), skip using continue. Print the odd numbers.",
                starterCode = "for n in range(1, 6):\n    # Filter and print\n",
                expectedOutput = "1\n3\n5",
                solutionCode = "for n in range(1, 6):\n    if n % 2 == 0:\n        continue\n    print(n)",
                hint = "Use if n % 2 == 0: continue"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does the 'continue' keyword do inside a loop?",
                options = listOf(
                    "Exits the loop entirely",
                    "Skips the rest of current iteration and moves to next",
                    "Pauses execution for 1 second",
                    "Restarts the entire loop from iteration 0"
                ),
                correctIndex = 1,
                explanation = "continue skips directly to the next iteration of the loop."
            )
        ),
        Lesson(
            id = "l2_6",
            levelId = 2,
            orderNumber = 6,
            title = "Nested Loops & Grid Traversal",
            subtitle = "Multi-dimensional iterations, coordinate grids, and matrices",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Nested Loops

Placing one loop inside another allows multi-dimensional data traversal (rows and columns, matrices, pixels).

For every single iteration of the outer loop, the inner loop runs its entire sequence.
            """.trimIndent(),
            syntax = "for row in outer:\n    for col in inner:\n        pass",
            codeExample = """# 2D Coordinate generation
for x in range(1, 3):
    for y in range(1, 3):
        print(f"({x}, {y})")
""",
            expectedOutput = "(1, 1)\n(1, 2)\n(2, 1)\n(2, 2)",
            commonMistakes = listOf(
                "Accidentally using the same variable name for both outer and inner loops.",
                "Deep nesting leading to exponential time complexity (O(N^2), O(N^3))."
            ),
            keyTakeaways = listOf(
                "Inner loops run completely for each outer loop step.",
                "Useful for tabular datasets, games, and matrices."
            ),
            practiceTask = PracticeTask(
                title = "Print a 2x2 Grid",
                description = "Use nested loops to print row 1 to 2 and col 1 to 2 in format 'R1C1', 'R1C2', 'R2C1', 'R2C2'.",
                starterCode = "# Nested loops\n",
                expectedOutput = "R1C1\nR1C2\nR2C1\nR2C2",
                solutionCode = "for r in range(1, 3):\n    for c in range(1, 3):\n        print(f\"R{r}C{c}\")",
                hint = "Use print(f\"R{r}C{c}\")."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "How many times does the inner body execute with outer range(3) and inner range(4)?",
                options = listOf("7", "12", "4", "3"),
                correctIndex = 1,
                explanation = "3 * 4 = 12 total iterations."
            )
        ),
        Lesson(
            id = "l2_7",
            levelId = 2,
            orderNumber = 7,
            title = "Loop Else Clauses (Python Idiom)",
            subtitle = "Executing completion code when loops finish without breaking",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# The Loop `else` Clause

A unique feature of Python is that both `for` and `while` loops can have an `else` block.

### Behavior:
The `else` block executes **only if the loop finishes naturally** (i.e. was NOT interrupted by a `break` statement). This is ideal for search algorithms.
            """.trimIndent(),
            syntax = "for item in items:\n    if condition:\n        break\nelse:\n    # executes if no break occurred",
            codeExample = """# Search algorithm using loop else
target = 7
numbers = [1, 3, 5, 7, 9]

for n in numbers:
    if n == target:
        print(f"Found {target}!")
        break
else:
    print(f"{target} not found in list.")
""",
            expectedOutput = "Found 7!",
            commonMistakes = listOf(
                "Thinking loop else works like if else (loop else is a 'no-break' handler)."
            ),
            keyTakeaways = listOf(
                "Loop else runs when the loop completes all iterations normally.",
                "If break triggers, the else block is completely bypassed."
            ),
            practiceTask = PracticeTask(
                title = "Search with Fallback",
                description = "Given numbers = [2, 4, 6] and target = 5. Search for target. If found, break and print 'Found'. In the loop else block, print 'Not Found'.",
                starterCode = "numbers = [2, 4, 6]\ntarget = 5\n# Search loop\n",
                expectedOutput = "Not Found",
                solutionCode = "numbers = [2, 4, 6]\ntarget = 5\nfor n in numbers:\n    if n == target:\n        print(\"Found\")\n        break\nelse:\n    print(\"Not Found\")",
                hint = "Use for n in numbers: ... else: print(\"Not Found\")"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "When does the 'else' block attached to a for loop execute?",
                options = listOf(
                    "When the loop is terminated by 'break'",
                    "When the loop completes without encountering a 'break'",
                    "On every iteration",
                    "Only when an exception occurs"
                ),
                correctIndex = 1,
                explanation = "Loop else executes only when the iteration sequence is exhausted without a break."
            )
        ),
        Lesson(
            id = "l2_8",
            levelId = 2,
            orderNumber = 8,
            title = "Introduction to List Comprehensions",
            subtitle = "Transforming and filtering collections in a single expressive line",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# List Comprehensions

List comprehensions provide a concise, readable syntax for creating new lists by transforming or filtering existing iterables.

### Syntax:
`[expression for item in iterable if condition]`
            """.trimIndent(),
            syntax = "[expr for x in iterable if condition]",
            codeExample = """# Transforming with list comprehension
numbers = [1, 2, 3, 4, 5]
squares = [x ** 2 for x in numbers]
even_squares = [x ** 2 for x in numbers if x % 2 == 0]

print(squares)
print(even_squares)
""",
            expectedOutput = "[1, 4, 9, 16, 25]\n[4, 16]",
            commonMistakes = listOf(
                "Writing overly complex multi-line comprehensions that harm readability (PEP 8 recommends standard loops if too complex)."
            ),
            keyTakeaways = listOf(
                "List comprehensions are faster and more idiomatic than map() + filter().",
                "Syntax: [output_expression for variable in source if predicate]."
            ),
            practiceTask = PracticeTask(
                title = "Create Doubled List",
                description = "Given nums = [2, 4, 6], use a list comprehension to create a list of doubled values and print it.",
                starterCode = "nums = [2, 4, 6]\n# Comprehension\n",
                expectedOutput = "[4, 8, 12]",
                solutionCode = "nums = [2, 4, 6]\ndoubled = [x * 2 for x in nums]\nprint(doubled)",
                hint = "Use [x * 2 for x in nums]."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the output of [x for x in range(3)]?",
                options = listOf("[0, 1, 2, 3]", "[1, 2, 3]", "[0, 1, 2]", "[0, 1]"),
                correctIndex = 2,
                explanation = "range(3) produces 0, 1, 2, so the list comprehension creates [0, 1, 2]."
            )
        ),
        Lesson(
            id = "l2_9",
            levelId = 2,
            orderNumber = 9,
            title = "Level 2 Mastery Review & Logic Challenge",
            subtitle = "Consolidating conditionals, loops, control keywords, and comprehensions",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 2 Milestone: Control Flow Mastered!

You have mastered programming control flow:
- `if`, `elif`, `else` conditional trees
- Chained comparisons & short-circuit booleans
- `for` loops & the `range()` function
- `while` loops & loop invariants
- `break`, `continue`, `pass`
- Nested multi-dimensional iterations
- The idiomatic loop `else` clause
- Elegant list comprehensions

In **Level 3**, we dive into Python's rich data structures: Lists, Tuples, Dictionaries, and Sets!
            """.trimIndent(),
            syntax = "# Level 2 Complete",
            codeExample = """# Synthesis example: FizzBuzz in Python
for n in range(1, 6):
    if n % 3 == 0:
        print("Fizz")
    elif n % 5 == 0:
        print("Buzz")
    else:
        print(n)
""",
            expectedOutput = "1\n2\nFizz\n4\nBuzz",
            commonMistakes = listOf(
                "Checking n % 3 before n % 15 in full FizzBuzz."
            ),
            keyTakeaways = listOf(
                "Control flow determines the algorithmic path of your program.",
                "Take the Level 2 Quiz to unlock more XP and solidify your mastery!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 2 Complete",
                description = "Print 'Level 2 Complete! Onward to Data Structures.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 2 Complete! Onward to Data Structures.",
                solutionCode = "print(\"Level 2 Complete! Onward to Data Structures.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which keyword creates a loop that continues until its condition is False?",
                options = listOf("for", "while", "repeat", "loop"),
                correctIndex = 1,
                explanation = "The while keyword creates a condition-driven loop."
            )
        )
    )
}
