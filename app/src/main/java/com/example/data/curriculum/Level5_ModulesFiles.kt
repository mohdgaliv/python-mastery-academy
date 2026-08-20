package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level5_ModulesFiles {
    val lessons = listOf(
        Lesson(
            id = "l5_1",
            levelId = 5,
            orderNumber = 1,
            title = "Importing Modules & The Standard Library",
            subtitle = "Leveraging Python's 'batteries included' philosophy",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Python Standard Library & Modules

Python comes with rich built-in libraries ("batteries included"):
- `math`: Mathematical constants and trigonometric functions
- `random`: Pseudo-random number generation
- `datetime`: Dates, timestamps, and time arithmetic
- `json`: JSON serialization and parsing
- `os` / `pathlib`: Operating system interactions and file paths

### Import Syntax Variants:
- `import math` -> `math.sqrt(16)`
- `from math import sqrt, pi` -> `sqrt(16)`
- `import datetime as dt` (aliasing)
            """.trimIndent(),
            syntax = "import module_name\nfrom module import item\nimport module as alias",
            codeExample = """# Importing and using math and random principles
import math

radius = 5
area = math.pi * (radius ** 2)

print(f"Pi: {round(math.pi, 2)}")
print(f"Circle Area: {round(area, 2)}")
print(f"Square Root of 64: {math.sqrt(64)}")
""",
            expectedOutput = "Pi: 3.14\nCircle Area: 78.54\nSquare Root of 64: 8.0",
            commonMistakes = listOf(
                "Using wildcard imports 'from module import *' (pollutes namespace and creates naming collisions).",
                "Naming your own script file the same as a standard module (e.g. math.py)."
            ),
            keyTakeaways = listOf(
                "Python's standard library provides battle-tested utilities.",
                "Explicit imports ('from module import func') clarify module dependencies."
            ),
            practiceTask = PracticeTask(
                title = "Compute Square Root",
                description = "Import math, calculate the square root of 100, and print the result.",
                starterCode = "# Import and compute\n",
                expectedOutput = "10.0",
                solutionCode = "import math\nprint(math.sqrt(100))",
                hint = "Use math.sqrt(100)."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Why is 'from math import *' discouraged in PEP 8?",
                options = listOf(
                    "It causes syntax errors",
                    "It pollutes the current namespace and can silently overwrite names",
                    "It slows down math calculations",
                    "It is deprecated in Python 3"
                ),
                correctIndex = 1,
                explanation = "Wildcard imports pollute the namespace and make it hard to tell where functions originate."
            )
        ),
        Lesson(
            id = "l5_2",
            levelId = 5,
            orderNumber = 2,
            title = "The `__name__ == '__main__'` Idiom",
            subtitle = "Controlling module execution vs import behavior",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Module Execution & __name__

Whenever Python runs a script directly, it sets the special built-in variable `__name__` to `'__main__'`.

If the file is imported into another script, `__name__` is set to the module's actual file name.

### Why This Matters:
This allows a Python file to act **both as an importable library and as a standalone executable script**.
            """.trimIndent(),
            syntax = "if __name__ == '__main__':\n    # code executed only when run directly\n    main()",
            codeExample = """# Idiomatic Python entry point
def calculate_grade(score):
    return "Pass" if score >= 60 else "Fail"

if __name__ == "__main__":
    print("Running script directly:")
    result = calculate_grade(85)
    print(f"Grade: {result}")
""",
            expectedOutput = "Running script directly:\nGrade: Pass",
            commonMistakes = listOf(
                "Forgetting double underscores (typing _name_ instead of __name__).",
                "Putting executable CLI code in root scope instead of inside the guard."
            ),
            keyTakeaways = listOf(
                "__name__ is '__main__' only when the script is run directly.",
                "Prevents top-level execution code from running during imports."
            ),
            practiceTask = PracticeTask(
                title = "Create Entry Guard",
                description = "Define a function run() that prints 'App Started'. Add the `if __name__ == '__main__':` guard to call run().",
                starterCode = "# Define and guard\n",
                expectedOutput = "App Started",
                solutionCode = "def run():\n    print(\"App Started\")\n\nif __name__ == '__main__':\n    run()",
                hint = "if __name__ == '__main__': run()"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the value of __name__ when a Python file is run directly?",
                options = listOf("'root'", "'__main__'", "'default'", "'__file__'"),
                correctIndex = 1,
                explanation = "Python sets __name__ = '__main__' for the top-level script executed."
            )
        ),
        Lesson(
            id = "l5_3",
            levelId = 5,
            orderNumber = 3,
            title = "Context Managers & The `with` Statement",
            subtitle = "Deterministic resource management, file locks, and cleanup",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# The `with` Statement & Context Managers

Opening resources (files, database connections, locks) requires guaranteed closure, even if exceptions occur.

### The `with` Pattern:
```python
with open("data.txt", "r") as file:
    content = file.read()
# File is automatically closed here!
```

This adheres to the **Context Manager Protocol** (`__enter__` and `__exit__`).
            """.trimIndent(),
            syntax = "with open('filename', 'mode') as f:\n    f.read()",
            codeExample = """# File I/O structure simulation
# In Python, standard usage:
file_modes = {
    "r": "Read (default)",
    "w": "Write (overwrites)",
    "a": "Append (adds to end)",
    "r+": "Read and Write"
}

for mode, desc in file_modes.items():
    print(f"Mode '{mode}': {desc}")
""",
            expectedOutput = "Mode 'r': Read (default)\nMode 'w': Write (overwrites)\nMode 'a': Append (adds to end)\nMode 'r+': Read and Write",
            commonMistakes = listOf(
                "Using manual open() and close() instead of the with statement.",
                "Using 'w' mode when you intended to append ('a'), accidentally overwriting data."
            ),
            keyTakeaways = listOf(
                "Always use 'with open()' for file handling.",
                "Context managers guarantee resource cleanup even during crashes."
            ),
            practiceTask = PracticeTask(
                title = "Print File Modes",
                description = "Print 'Mode w: Write' and 'Mode a: Append' on separate lines.",
                starterCode = "# Print modes\n",
                expectedOutput = "Mode w: Write\nMode a: Append",
                solutionCode = "print(\"Mode w: Write\")\nprint(\"Mode a: Append\")",
                hint = "Use print statements for the modes."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the primary benefit of the 'with open(...)' context manager?",
                options = listOf(
                    "It makes reading 10x faster",
                    "It guarantees the file is properly closed when exiting the block",
                    "It converts text into JSON",
                    "It encrypts the file"
                ),
                correctIndex = 1,
                explanation = "The with statement guarantees the file descriptor is closed automatically."
            )
        ),
        Lesson(
            id = "l5_4",
            levelId = 5,
            orderNumber = 4,
            title = "JSON Serialization & Parsing",
            subtitle = "Converting between Python dicts/lists and JSON strings",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Working with JSON in Python

JSON (JavaScript Object Notation) is the standard data interchange format for modern APIs.

### Python's `json` Module:
- `json.dumps(obj)`: Serializes Python object to a JSON formatted **string**.
- `json.loads(str)`: Parses a JSON **string** into a Python dict/list.
- `json.dump(obj, file)`: Writes JSON directly to a file stream.
- `json.load(file)`: Reads JSON from a file stream.
            """.trimIndent(),
            syntax = "import json\njson_str = json.dumps(data)\ndata = json.loads(json_str)",
            codeExample = """# JSON serialization simulation
# Python data types map directly to JSON equivalents:
type_mapping = {
    "dict": "JSON Object",
    "list": "JSON Array",
    "str": "JSON String",
    "int/float": "JSON Number",
    "True/False": "true/false",
    "None": "null"
}

for py_t, json_t in type_mapping.items():
    print(f"Python {py_t} -> {json_t}")
""",
            expectedOutput = "Python dict -> JSON Object\nPython list -> JSON Array\nPython str -> JSON String\nPython int/float -> JSON Number\nPython True/False -> true/false\nPython None -> null",
            commonMistakes = listOf(
                "Confusing json.load() (reads from file) with json.loads() (parses string).",
                "Attempting to serialize non-serializable objects (like datetime or custom classes) without a custom encoder."
            ),
            keyTakeaways = listOf(
                "dumps/loads work on strings (the 's' stands for string).",
                "dump/load work on file streams.",
                "Python None serializes to JSON null."
            ),
            practiceTask = PracticeTask(
                title = "Print JSON Equivalents",
                description = "Print 'Python None -> JSON null' and 'Python True -> JSON true'.",
                starterCode = "# Print equivalents\n",
                expectedOutput = "Python None -> JSON null\nPython True -> JSON true",
                solutionCode = "print(\"Python None -> JSON null\")\nprint(\"Python True -> JSON true\")",
                hint = "Use print() for each line."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What function converts a Python dictionary into a JSON string?",
                options = listOf("json.dump()", "json.loads()", "json.dumps()", "json.parse()"),
                correctIndex = 2,
                explanation = "json.dumps() (dump string) serializes a Python object to a JSON string."
            )
        ),
        Lesson(
            id = "l5_5",
            levelId = 5,
            orderNumber = 5,
            title = "Level 5 Mastery Review & Summary",
            subtitle = "Consolidating modules, files, and data serialization",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 5 Milestone: Modules & File I/O Mastered!

You now understand modular Python development:
- Standard library modules (`math`, `datetime`, `random`, `json`, `os`)
- Idiomatic `if __name__ == '__main__':` pattern
- Context managers & deterministic `with` statement
- JSON serialization and parsing mechanics

In **Level 6**, we master robustness: **Error & Exception Handling**!
            """.trimIndent(),
            syntax = "# Level 5 Complete",
            codeExample = """# Milestone summary
level = 5
topic = "Modules & File I/O"
print(f"Level {level}: {topic} successfully mastered!")
""",
            expectedOutput = "Level 5: Modules & File I/O successfully mastered!",
            commonMistakes = listOf(
                "Neglecting to close open file handles when not using context managers."
            ),
            keyTakeaways = listOf(
                "Modular code separates concerns into clean components.",
                "Take the Level 5 Quiz to earn XP!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 5 Complete",
                description = "Print 'Level 5 Complete! Onward to Error Handling.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 5 Complete! Onward to Error Handling.",
                solutionCode = "print(\"Level 5 Complete! Onward to Error Handling.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which file mode opens a file for appending without overwriting existing content?",
                options = listOf("'r'", "'w'", "'a'", "'x'"),
                correctIndex = 2,
                explanation = "Mode 'a' (append) adds new data to the end of the file."
            )
        )
    )
}
