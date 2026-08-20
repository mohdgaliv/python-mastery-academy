package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level1_Fundamentals {
    val lessons = listOf(
        Lesson(
            id = "l1_1",
            levelId = 1,
            orderNumber = 1,
            title = "Introduction to Python & Philosophy",
            subtitle = "Discover the elegance, readability, and power of Python",
            estimatedMinutes = 8,
            xpReward = 50,
            conceptExplanation = """
# Welcome to Python Mastery Academy

Python was created by **Guido van Rossum** and released in 1991. It was designed with a core philosophy: **readability counts**.

### The Zen of Python (PEP 20)
Key guiding aphorisms include:
- Beautiful is better than ugly.
- Explicit is better than implicit.
- Simple is better than complex.
- Readability counts.

Python is an **interpreted, high-level, dynamically-typed** programming language with automatic memory management. It is widely used in Web Development, Data Science, Artificial Intelligence, Automation, and DevOps.
            """.trimIndent(),
            syntax = "print(object, ...)",
            codeExample = """# Your first Python instruction
print("Hello, Python Mastery Academy!")
print("Readability counts.")
""",
            expectedOutput = "Hello, Python Mastery Academy!\nReadability counts.",
            commonMistakes = listOf(
                "Forgetting parentheses in print() - In Python 3, print is a function, not a statement.",
                "Mismatched quotation marks like print('Hello\")"
            ),
            keyTakeaways = listOf(
                "Python is interpreted and emphasizes code clarity and simplicity.",
                "Case-sensitive syntax: print is valid, Print is an error.",
                "Dynamic typing: You don't need to declare variable types explicitly."
            ),
            practiceTask = PracticeTask(
                title = "Print Your Academy Greeting",
                description = "Write Python code to output: 'Python is awesome!' on the first line, and 'Let's master coding!' on the second line.",
                starterCode = "# Write your print statements here\n",
                expectedOutput = "Python is awesome!\nLet's master coding!",
                solutionCode = "print(\"Python is awesome!\")\nprint(\"Let's master coding!\")",
                hint = "Use two separate print() calls."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which of the following is true about Python?",
                options = listOf(
                    "Python requires explicit memory allocation.",
                    "Python emphasizes code readability and clean syntax.",
                    "Python is purely compiled directly to machine code.",
                    "Variables must be declared with their data type."
                ),
                correctIndex = 1,
                explanation = "Python was intentionally designed around PEP 20 emphasizing readability and simplicity."
            )
        ),
        Lesson(
            id = "l1_2",
            levelId = 1,
            orderNumber = 2,
            title = "Variables & Naming Conventions",
            subtitle = "Storing data and adhering to PEP 8 standards",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Variables in Python

A variable is a symbolic name that acts as a pointer or reference to an object in memory. In Python, variables are created when you first assign a value to them using the `=` operator.

### PEP 8 Naming Conventions:
- **Snake Case**: Variable and function names should be lowercase with words separated by underscores (`user_age`, `total_score`).
- **Constants**: Use ALL_CAPS for values intended to remain unchanged (`MAX_RETRIES`, `PI`).
- **Rules**: Names cannot start with numbers, cannot contain spaces or hyphens, and cannot be Python keywords (like `class`, `def`, `if`).
            """.trimIndent(),
            syntax = "variable_name = value",
            codeExample = """# Variable assignments
student_name = "Alex"
current_streak = 7
is_enrolled = True

print(student_name)
print(current_streak)
print(is_enrolled)
""",
            expectedOutput = "Alex\n7\nTrue",
            commonMistakes = listOf(
                "Starting variable names with digits (e.g. 2nd_user = 'Sam')",
                "Using reserved Python keywords as variable names (e.g. class = 10)"
            ),
            keyTakeaways = listOf(
                "Variables are dynamically bound to objects in memory.",
                "Follow PEP 8 snake_case conventions for readability.",
                "Assignment (=) assigns right-hand side expressions to the left-hand variable."
            ),
            practiceTask = PracticeTask(
                title = "Create Profile Variables",
                description = "Create two variables: language with value 'Python' and level with value 1. Then print both.",
                starterCode = "# Declare variables and print them\n",
                expectedOutput = "Python\n1",
                solutionCode = "language = \"Python\"\nlevel = 1\nprint(language)\nprint(level)",
                hint = "Assign string to language and integer to level."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which variable name adheres strictly to PEP 8 standards?",
                options = listOf("userAge", "user_age", "User_Age", "UserAge"),
                correctIndex = 1,
                explanation = "PEP 8 specifies lowercase words separated by underscores (snake_case) for variable names."
            )
        ),
        Lesson(
            id = "l1_3",
            levelId = 1,
            orderNumber = 3,
            title = "Data Types & Type Conversion",
            subtitle = "Working with int, float, str, bool, and typecasting",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Core Built-in Data Types

Python provides several primitive types:
- `int`: Whole numbers (`42`, `-15`, `0`)
- `float`: Real numbers with decimal points (`3.14`, `-0.001`)
- `str`: Text sequences (`"Python"`, `'Academy'`)
- `bool`: Truth values (`True`, `False`)

### Type Conversion (Casting)
You can convert between types using built-in functions:
- `int("10")` -> `10`
- `float("3.14")` -> `3.14`
- `str(100)` -> `"100"`
- `bool(1)` -> `True`
            """.trimIndent(),
            syntax = "type(obj)\nint(val), float(val), str(val), bool(val)",
            codeExample = """# Type conversion example
age_str = "25"
age_num = int(age_str)
next_year = age_num + 1

price = 19.99
print(type(next_year))
print(type(price))
print(next_year)
""",
            expectedOutput = "<class 'int'>\n<class 'float'>\n26",
            commonMistakes = listOf(
                "Attempting to add string and integer directly without casting: '10' + 5 causes TypeError.",
                "Converting non-numeric strings to int: int('abc') causes ValueError."
            ),
            keyTakeaways = listOf(
                "Use type() to inspect the runtime type of any object.",
                "Python is strongly typed: it will not implicitly convert incompatible types in arithmetic.",
                "Explicit casting prevents runtime TypeErrors."
            ),
            practiceTask = PracticeTask(
                title = "Cast and Calculate",
                description = "Given a string num1 = '40' and int num2 = 2, convert num1 to an int, add num2, and print the total.",
                starterCode = "num1 = \"40\"\nnum2 = 2\n# Convert and calculate\n",
                expectedOutput = "42",
                solutionCode = "num1 = \"40\"\nnum2 = 2\ntotal = int(num1) + num2\nprint(total)",
                hint = "Use int(num1) before addition."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the output of type(10.0)?",
                options = listOf("<class 'int'>", "<class 'float'>", "<class 'double'>", "<class 'number'>"),
                correctIndex = 1,
                explanation = "10.0 contains a decimal point, making it a floating-point number (<class 'float'>)."
            )
        ),
        Lesson(
            id = "l1_4",
            levelId = 1,
            orderNumber = 4,
            title = "Basic Operators & Arithmetic Expressions",
            subtitle = "Arithmetic, modulus, floor division, and exponentiation",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Python Arithmetic Operators

Python supports full mathematical expressions:
- `+` Addition
- `-` Subtraction
- `*` Multiplication
- `/` True division (always returns float: `7 / 2 = 3.5`)
- `//` Floor division (quotient truncated: `7 // 2 = 3`)
- `%` Modulus (remainder: `7 % 2 = 1`)
- `**` Exponentiation (`2 ** 3 = 8`)

### Operator Precedence (PEMDAS)
Parentheses `()` override standard order of operations.
            """.trimIndent(),
            syntax = "result = a + b * (c ** d)",
            codeExample = """# Arithmetic operations
a = 15
b = 4

print(a + b)
print(a / b)
print(a // b)
print(a % b)
print(b ** 3)
""",
            expectedOutput = "19\n3.75\n3\n3\n64",
            commonMistakes = listOf(
                "Confusing single division / (returns float) with floor division // (returns int quotient).",
                "Using ^ for exponentiation (in Python, ^ is bitwise XOR; use ** for powers)."
            ),
            keyTakeaways = listOf(
                "True division / always results in a float.",
                "Floor division // truncates towards negative infinity.",
                "** is the power operator."
            ),
            practiceTask = PracticeTask(
                title = "Calculate Remainder & Power",
                description = "Calculate 17 modulo 5, and 3 to the power of 4. Print both results.",
                starterCode = "# Perform the calculations\n",
                expectedOutput = "2\n81",
                solutionCode = "print(17 % 5)\nprint(3 ** 4)",
                hint = "Use % for remainder and ** for exponent."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the result of 20 // 6 in Python?",
                options = listOf("3.333", "3", "2", "4"),
                correctIndex = 1,
                explanation = "Floor division // discards the fractional part and returns 3."
            )
        ),
        Lesson(
            id = "l1_5",
            levelId = 1,
            orderNumber = 5,
            title = "String Basics & Essential Methods",
            subtitle = "Indexing, immutability, case conversion, and manipulation",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# String Fundamentals

Strings in Python are immutable sequences of Unicode characters.

### Common String Methods:
- `.upper()` / `.lower()`: Case transformations
- `.strip()`: Removes leading/trailing whitespace
- `.replace(old, new)`: Replaces occurrences
- `.split(delimiter)`: Splits into a list of substrings
- `.startswith(prefix)` / `.endswith(suffix)`: Boolean checks
- `len(s)`: String length
            """.trimIndent(),
            syntax = "s.upper()\ns.strip()\ns.replace(a, b)",
            codeExample = """# String manipulation
text = "  python programming academy  "
cleaned = text.strip()
title_case = cleaned.title()

print(title_case)
print(len(cleaned))
print(cleaned.replace("academy", "mastery"))
""",
            expectedOutput = "Python Programming Academy\n26\npython programming mastery",
            commonMistakes = listOf(
                "Thinking string methods modify strings in-place (strings are immutable, methods return a new string).",
                "Index out of range when indexing past len(s) - 1."
            ),
            keyTakeaways = listOf(
                "Strings are immutable in Python.",
                "Negative indexing allows accessing characters from the end (-1 is last character).",
                "String methods always return new strings."
            ),
            practiceTask = PracticeTask(
                title = "Transform and Clean String",
                description = "Given raw = '  master python  ', clean the whitespace and convert it to uppercase. Print the result.",
                starterCode = "raw = \"  master python  \"\n# Transform here\n",
                expectedOutput = "MASTER PYTHON",
                solutionCode = "raw = \"  master python  \"\nprint(raw.strip().upper())",
                hint = "Chain .strip() and .upper()."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Can you modify a character in a string directly (e.g. s[0] = 'X')?",
                options = listOf("Yes, always", "No, strings are immutable", "Only if it is ASCII", "Only inside functions"),
                correctIndex = 1,
                explanation = "Python strings are immutable; item assignment raises a TypeError."
            )
        ),
        Lesson(
            id = "l1_6",
            levelId = 1,
            orderNumber = 6,
            title = "String Formatting (f-strings & PEP 498)",
            subtitle = "Modern, readable interpolation with formatted string literals",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Modern String Formatting with f-strings

Introduced in Python 3.6, **f-strings** (formatted string literals) provide the most readable, concise, and fast way to interpolate expressions inside strings.

### Syntax:
Prefix the string with `f` or `F` and place variables or expressions inside curly braces `{}`.

### Expressions Inside f-strings:
You can evaluate any valid Python expression inside `{}`:
- `{name.upper()}`
- `{price * 1.08}`
- `{count + 5}`
            """.trimIndent(),
            syntax = "f\"Hello, {name}! Total: {score * 2}\"",
            codeExample = """# F-string interpolation
user = "Jordan"
xp = 1250
level = 3

message = f"Learner {user} has {xp} XP and is on Level {level}."
print(message)
print(f"XP needed for next level: {2000 - xp}")
""",
            expectedOutput = "Learner Jordan has 1250 XP and is on Level 3.\nXP needed for next level: 750",
            commonMistakes = listOf(
                "Forgetting the leading 'f' prefix (e.g., \"Hello {name}\" outputs the literal text).",
                "Using backslashes inside f-string expressions in older Python versions."
            ),
            keyTakeaways = listOf(
                "f-strings are the PEP 8 recommended method for string interpolation.",
                "Expressions inside `{}` are evaluated at runtime.",
                "f-strings are significantly faster than % formatting or str.format()."
            ),
            practiceTask = PracticeTask(
                title = "Format a Progress Card",
                description = "Given course = 'Python' and percent = 100, create an f-string: 'Course: Python | Progress: 100%' and print it.",
                starterCode = "course = \"Python\"\npercent = 100\n# Print formatted string\n",
                expectedOutput = "Course: Python | Progress: 100%",
                solutionCode = "course = \"Python\"\npercent = 100\nprint(f\"Course: {course} | Progress: {percent}%\")",
                hint = "Use f\"Course: {course} | Progress: {percent}%\""
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What prefix is required to create a formatted string literal?",
                options = listOf("s", "f", "format", "$"),
                correctIndex = 1,
                explanation = "Prefixing a string with 'f' or 'F' designates it as an f-string."
            )
        ),
        Lesson(
            id = "l1_7",
            levelId = 1,
            orderNumber = 7,
            title = "Comments, Docstrings & Documentation",
            subtitle = "Writing self-documenting code and PEP 257 docstrings",
            estimatedMinutes = 8,
            xpReward = 50,
            conceptExplanation = """
# Comments & Docstrings

Code is read far more often than it is written. Proper documentation keeps codebases maintainable.

### Single-Line Comments:
Use `#` for inline comments or notes.

### Multi-Line Strings & Docstrings:
Use triple quotes (`\"\"\"` or `'''`) at the beginning of modules, classes, or functions to document their behavior (Docstrings). Tools like Sphinx and IDEs parse docstrings automatically.
            """.trimIndent(),
            syntax = "# Single line comment\n\"\"\"Docstring explanation\"\"\"",
            codeExample = """# Calculate total score with bonus
base_score = 100
bonus = 25 # High-speed bonus

total = base_score + bonus
print(total)
""",
            expectedOutput = "125",
            commonMistakes = listOf(
                "Over-commenting obvious code (e.g. x = 1 # assign 1 to x).",
                "Letting comments get out of sync with code after refactoring."
            ),
            keyTakeaways = listOf(
                "# is used for comments ignored by the interpreter.",
                "Docstrings are accessible programmatically via obj.__doc__.",
                "Good comments explain 'why', not 'what'."
            ),
            practiceTask = PracticeTask(
                title = "Document and Compute",
                description = "Add a comment '# Compute area' and print the area of a rectangle with width 5 and height 8.",
                starterCode = "# Write comment and calculation\n",
                expectedOutput = "40",
                solutionCode = "# Compute area\nwidth = 5\nheight = 8\nprint(width * height)",
                hint = "Compute width * height and print the value."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which character starts a single-line comment in Python?",
                options = listOf("//", "/*", "#", "--"),
                correctIndex = 2,
                explanation = "Python uses the # hash symbol for single line comments."
            )
        ),
        Lesson(
            id = "l1_8",
            levelId = 1,
            orderNumber = 8,
            title = "Math Operations & Built-in Functions",
            subtitle = "Working with abs, round, min, max, and numerical utilities",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Python Numerical Built-ins

Python includes powerful numerical utilities out-of-the-box:
- `abs(x)`: Absolute value
- `round(x, n)`: Rounds float to n decimal places
- `min(a, b, ...)`: Returns minimum value
- `max(a, b, ...)`: Returns maximum value
- `sum(iterable)`: Sums all elements
- `pow(x, y)`: Same as `x ** y`
            """.trimIndent(),
            syntax = "abs(x)\nround(x)\nmin(a, b)\nmax(a, b)",
            codeExample = """# Numeric built-in functions
scores = [85, 92, 78, 96, 88]

print(min(scores))
print(max(scores))
print(sum(scores))
print(abs(-42))
print(round(3.75))
""",
            expectedOutput = "78\n96\n439\n42\n4",
            commonMistakes = listOf(
                "Passing non-iterable items to sum() without proper wrapping.",
                "Expecting round() to always round .5 up (Python uses round-half-to-even bankers rounding)."
            ),
            keyTakeaways = listOf(
                "min() and max() accept multiple arguments or single iterables.",
                "abs() works on integers and floating-point values.",
                "sum() computes the arithmetic sum of an iterable."
            ),
            practiceTask = PracticeTask(
                title = "Find Min, Max and Range",
                description = "Given values a = -10 and b = 25, print abs(a), max(a, b), and min(a, b).",
                starterCode = "a = -10\nb = 25\n# Compute and print\n",
                expectedOutput = "10\n25\n-10",
                solutionCode = "a = -10\nb = 25\nprint(abs(a))\nprint(max(a, b))\nprint(min(a, b))",
                hint = "Use abs(), max(), and min()."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does abs(-15.5) return?",
                options = listOf("-15.5", "15.5", "15", "-16"),
                correctIndex = 1,
                explanation = "abs() returns the positive magnitude (absolute value) of the number."
            )
        ),
        Lesson(
            id = "l1_9",
            levelId = 1,
            orderNumber = 9,
            title = "Writing Your First Complete Python Script",
            subtitle = "Bringing variables, math, and formatted output together",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Building a Cohesive Script

Now let's synthesize everything learned in Level 1:
1. Declaring variables with proper snake_case names.
2. Casting and performing mathematical calculations.
3. Formatting results with clean f-strings.
4. Outputting structured reports.
            """.trimIndent(),
            syntax = "# Full program structure\n# 1. Inputs/Constants\n# 2. Logic/Calculations\n# 3. Output",
            codeExample = """# Student Performance Report
student = "Maya"
quiz1 = 90
quiz2 = 85
quiz3 = 95

total = quiz1 + quiz2 + quiz3
average = total / 3

print(f"Student: {student}")
print(f"Total Score: {total}")
print(f"Average: {round(average)}")
""",
            expectedOutput = "Student: Maya\nTotal Score: 270\nAverage: 90",
            commonMistakes = listOf(
                "Dividing before summing when calculating averages (precedence error).",
                "Mixing up variable names between definition and output."
            ),
            keyTakeaways = listOf(
                "Structure scripts cleanly: inputs -> calculations -> formatted output.",
                "Combine f-strings with arithmetic for dynamic reports."
            ),
            practiceTask = PracticeTask(
                title = "Create an Order Invoice",
                description = "Given item = 'Python Book', price = 20, qty = 3. Compute total = price * qty and print: 'Order: 3 x Python Book = $60'.",
                starterCode = "item = \"Python Book\"\nprice = 20\nqty = 3\n# Output invoice\n",
                expectedOutput = "Order: 3 x Python Book = $60",
                solutionCode = "item = \"Python Book\"\nprice = 20\nqty = 3\ntotal = price * qty\nprint(f\"Order: {qty} x {item} = " + "$" + "{total}\")",
                hint = "Use an f-string with qty, item, and total."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the recommended layout for a basic sequential script?",
                options = listOf(
                    "Output first, then imports, then logic",
                    "Inputs & variables -> Processing & calculations -> Formatted output",
                    "Put all code into one long print statement",
                    "Comments are required on every single line"
                ),
                correctIndex = 1,
                explanation = "A clear sequential flow (Inputs -> Processing -> Output) is standard practice."
            )
        ),
        Lesson(
            id = "l1_10",
            levelId = 1,
            orderNumber = 10,
            title = "Level 1 Mastery Review & Summary",
            subtitle = "Recap of fundamental foundations before control flow",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 1 Milestone: Fundamentals Mastered!

You have laid a solid foundation in Python:
- Dynamic typing & memory model
- PEP 8 snake_case conventions
- Types (`int`, `float`, `str`, `bool`) and type casting
- Arithmetic operators (`+`, `-`, `*`, `/`, `//`, `%`, `**`)
- String immutability & built-in methods
- Modern f-string interpolation
- Numerical built-in functions

Next up in **Level 2**, we unlock logic: conditionals, decision branching, and loops!
            """.trimIndent(),
            syntax = "# Level 1 Complete",
            codeExample = """# Milestone verification
level = 1
name = "Python Fundamentals"
status = "Mastered"

print(f"Level {level}: {name} - Status: {status}!")
""",
            expectedOutput = "Level 1: Python Fundamentals - Status: Mastered!",
            commonMistakes = listOf(
                "Skipping practice exercises before moving to complex topics."
            ),
            keyTakeaways = listOf(
                "Python fundamentals form the bedrock of all advanced programming and AI libraries.",
                "Take the Level 1 Quiz to validate your knowledge and earn bonus XP!"
            ),
            practiceTask = PracticeTask(
                title = "Milestone Badge Print",
                description = "Print 'Level 1 Complete! Ready for Control Flow.'",
                starterCode = "# Print confirmation\n",
                expectedOutput = "Level 1 Complete! Ready for Control Flow.",
                solutionCode = "print(\"Level 1 Complete! Ready for Control Flow.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which data type represents true/false values?",
                options = listOf("int", "str", "bool", "float"),
                correctIndex = 2,
                explanation = "bool (Boolean) represents True and False values."
            )
        )
    )
}
