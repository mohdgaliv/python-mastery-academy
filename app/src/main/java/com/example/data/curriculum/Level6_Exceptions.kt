package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level6_Exceptions {
    val lessons = listOf(
        Lesson(
            id = "l6_1",
            levelId = 6,
            orderNumber = 1,
            title = "Exceptions: Try, Except & Else",
            subtitle = "Gracefully handling runtime errors and preventing application crashes",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Error Handling in Python

Exceptions are runtime errors that disrupt the normal flow of a program.

### Structure:
- `try`: Contains code that might raise an exception.
- `except ExceptionType`: Catches and handles specific errors.
- `else`: Runs **only if no exception** occurred in the try block.
- `finally`: Always runs, regardless of whether an exception was raised.
            """.trimIndent(),
            syntax = "try:\n    # risky code\nexcept ValueError:\n    # handle error\nelse:\n    # no error\nfinally:\n    # always runs",
            codeExample = """# Safe parsing with exception handling
def safe_divide(a, b):
    try:
        result = a / b
    except ZeroDivisionError:
        return "Error: Cannot divide by zero!"
    return result

print(safe_divide(10, 2))
print(safe_divide(10, 0))
""",
            expectedOutput = "5.0\nError: Cannot divide by zero!",
            commonMistakes = listOf(
                "Bare except clauses ('except:') that catch system interrupts and hide bugs.",
                "Swallowing exceptions silently without logging or error messages."
            ),
            keyTakeaways = listOf(
                "Catch specific exception types rather than generic Exception.",
                "The else block runs only if the try block succeeded without exceptions."
            ),
            practiceTask = PracticeTask(
                title = "Safe Division Function",
                description = "Define a function divide(a, b) that returns a / b or 'Division Error' if b == 0. Call divide(8, 2) and divide(8, 0).",
                starterCode = "# Define and call divide\n",
                expectedOutput = "4.0\nDivision Error",
                solutionCode = "def divide(a, b):\n    try:\n        return a / b\n    except ZeroDivisionError:\n        return \"Division Error\"\n\nprint(divide(8, 2))\nprint(divide(8, 0))",
                hint = "Use try/except ZeroDivisionError."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "When does the 'else' block inside a try-except structure execute?",
                options = listOf(
                    "Only when an exception occurs",
                    "Only when no exception occurs in the try block",
                    "Always, after finally",
                    "Only if the program terminates"
                ),
                correctIndex = 1,
                explanation = "The else block executes only when the try block completes successfully without any exception."
            )
        ),
        Lesson(
            id = "l6_2",
            levelId = 6,
            orderNumber = 2,
            title = "The `finally` Clause & Resource Safety",
            subtitle = "Guaranteed execution for cleanup, disconnects, and lock releases",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# The `finally` Clause

The `finally` block is guaranteed to execute **under all circumstances**, even if an unhandled exception is raised, or if a `return`, `break`, or `continue` is called inside the `try` block.

### Primary Purpose:
Ensuring cleanup tasks (closing network sockets, releasing mutexes, flushing buffers) always take place.
            """.trimIndent(),
            syntax = "try:\n    # code\nfinally:\n    # always runs",
            codeExample = """# Demonstrating guaranteed cleanup with finally
def run_database_query(query_ok):
    print("Opening connection...")
    try:
        if not query_ok:
            raise ValueError("Query failed!")
        print("Query executed successfully.")
    except ValueError as e:
        print(f"Handled error: {e}")
    finally:
        print("Closing connection (guaranteed).")

run_database_query(True)
print("---")
run_database_query(False)
""",
            expectedOutput = "Opening connection...\nQuery executed successfully.\nClosing connection (guaranteed).\n---\nOpening connection...\nHandled error: Query failed!\nClosing connection (guaranteed).",
            commonMistakes = listOf(
                "Using return inside finally (silently overwrites return values and suppresses exceptions)."
            ),
            keyTakeaways = listOf(
                "finally executes no matter what happens in try/except.",
                "Essential for resource cleanup and resetting invariants."
            ),
            practiceTask = PracticeTask(
                title = "Demonstrate Finally Output",
                description = "Print 'Processing...' on line 1 and 'Cleanup Complete' on line 2.",
                starterCode = "# Print statements\n",
                expectedOutput = "Processing...\nCleanup Complete",
                solutionCode = "print(\"Processing...\")\nprint(\"Cleanup Complete\")",
                hint = "Use print() for each line."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Will a finally block execute if a return statement is encountered in the try block?",
                options = listOf("No, return exits immediately", "Yes, finally still executes before returning", "Only in debug mode", "Only if an error occurred"),
                correctIndex = 1,
                explanation = "The finally block is guaranteed to execute before the function actually returns."
            )
        ),
        Lesson(
            id = "l6_3",
            levelId = 6,
            orderNumber = 3,
            title = "Raising Exceptions & Custom Error Classes",
            subtitle = "Creating domain-specific exceptions and defensive invariants",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Raising & Custom Exceptions

You can manually trigger exceptions using the `raise` keyword when business rules or preconditions are violated.

### Custom Exception Classes:
Inherit from Python's built-in `Exception` class to create descriptive domain-specific error types:
```python
class InsufficientFundsError(Exception):
    \"\"\"Raised when account balance is too low.\"\"\"
    pass
```
            """.trimIndent(),
            syntax = "raise ValueError('Invalid input')\nclass CustomError(Exception):\n    pass",
            codeExample = """# Custom domain exception
class InvalidAgeError(Exception):
    pass

def register_voter(age):
    if age < 18:
        raise InvalidAgeError(f"Age {age} is below minimum voting age 18.")
    return "Voter registered successfully."

try:
    print(register_voter(20))
    print(register_voter(15))
except InvalidAgeError as e:
    print(f"Registration Error: {e}")
""",
            expectedOutput = "Voter registered successfully.\nRegistration Error: Age 15 is below minimum voting age 18.",
            commonMistakes = listOf(
                "Inheriting custom exceptions from BaseException instead of Exception (can break interrupt handling like KeyboardInterrupt).",
                "Raising raw strings (raise 'Error' is illegal in Python 3; you must raise Exception instances)."
            ),
            keyTakeaways = listOf(
                "Use 'raise' to signal invalid states early (Fail-Fast principle).",
                "Custom exceptions make API errors self-documenting and easy to catch."
            ),
            practiceTask = PracticeTask(
                title = "Raise and Catch Check",
                description = "Define a function check_positive(n) that raises ValueError('Negative') if n < 0, else returns n. Test with n = 5 and print the result.",
                starterCode = "# Define and test check_positive\n",
                expectedOutput = "5",
                solutionCode = "def check_positive(n):\n    if n < 0:\n        raise ValueError(\"Negative\")\n    return n\n\nprint(check_positive(5))",
                hint = "if n < 0: raise ValueError(\"Negative\")"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which base class should all standard user-defined exceptions inherit from?",
                options = listOf("BaseException", "Exception", "Error", "Throwable"),
                correctIndex = 1,
                explanation = "User-defined exceptions should inherit from the built-in Exception class."
            )
        ),
        Lesson(
            id = "l6_4",
            levelId = 6,
            orderNumber = 4,
            title = "Level 6 Mastery Review & Summary",
            subtitle = "Consolidating resilient error handling and exception flows",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 6 Milestone: Error Handling Mastered!

You have developed skills in defensive Python programming:
- `try`, `except`, `else`, `finally` architecture
- Handling built-in exceptions (`ValueError`, `TypeError`, `KeyError`, `ZeroDivisionError`)
- Defensive programming with `raise`
- Creating custom domain exception hierarchies

In **Level 7**, we step into the core paradigm: **Object-Oriented Programming (OOP)**!
            """.trimIndent(),
            syntax = "# Level 6 Complete",
            codeExample = """# Milestone verification
level = 6
topic = "Error & Exception Handling"
print(f"Level {level}: {topic} Mastered!")
""",
            expectedOutput = "Level 6: Error & Exception Handling Mastered!",
            commonMistakes = listOf(
                "Using exceptions for ordinary control flow instead of genuine error conditions."
            ),
            keyTakeaways = listOf(
                "EAFP (Easier to Ask for Forgiveness than Permission) is an idiomatic Python design philosophy.",
                "Take the Level 6 Quiz to claim your XP!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 6 Complete",
                description = "Print 'Level 6 Complete! Onward to Object-Oriented Programming.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 6 Complete! Onward to Object-Oriented Programming.",
                solutionCode = "print(\"Level 6 Complete! Onward to Object-Oriented Programming.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which keyword is used to trigger an exception manually in Python?",
                options = listOf("throw", "raise", "trigger", "emit"),
                correctIndex = 1,
                explanation = "Python uses the 'raise' keyword to raise exceptions."
            )
        )
    )
}
