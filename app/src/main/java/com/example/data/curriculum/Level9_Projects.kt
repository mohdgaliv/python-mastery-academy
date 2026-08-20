package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level9_Projects {
    val lessons = listOf(
        Lesson(
            id = "l9_1",
            levelId = 9,
            orderNumber = 1,
            title = "Project: CLI Task Manager & Tracker",
            subtitle = "Architecting a modular command-line task management application",
            estimatedMinutes = 15,
            xpReward = 100,
            conceptExplanation = """
# Portfolio Project 1: CLI Task Manager

In this project, we implement an interactive Task Manager that supports:
1. Adding tasks with priority (`High`, `Medium`, `Low`).
2. Marking tasks as complete.
3. Filtering pending vs completed tasks.
4. Exporting tasks to JSON format.
            """.trimIndent(),
            syntax = "# Full OOP CLI Application Architecture",
            codeExample = """# Complete Task Manager Implementation
class TaskManager:
    def __init__(self):
        self.tasks = []

    def add_task(self, title, priority="Medium"):
        task = {"id": len(self.tasks) + 1, "title": title, "priority": priority, "done": False}
        self.tasks.append(task)
        return f"Added task #{task['id']}: {title}"

    def complete_task(self, task_id):
        for t in self.tasks:
            if t["id"] == task_id:
                t["done"] = True
                return f"Completed #{task_id}"
        return "Not found"

    def summary(self):
        done = sum(1 for t in self.tasks if t["done"])
        return f"Total: {len(self.tasks)}, Completed: {done}"

manager = TaskManager()
print(manager.add_task("Master Python Core", "High"))
print(manager.add_task("Build Portfolio", "High"))
print(manager.complete_task(1))
print(manager.summary())
""",
            expectedOutput = "Added task #1: Master Python Core\nAdded task #2: Build Portfolio\nCompleted #1\nTotal: 2, Completed: 1",
            commonMistakes = listOf(
                "Hardcoding IDs without dynamically indexing list elements."
            ),
            keyTakeaways = listOf(
                "Modular class design simplifies state management.",
                "Real projects combine lists, dicts, conditionals, and OOP."
            ),
            practiceTask = PracticeTask(
                title = "Run Task Manager",
                description = "Define a list tasks = ['Task 1', 'Task 2']. Append 'Task 3' and print len(tasks).",
                starterCode = "tasks = ['Task 1', 'Task 2']\n# Append and print\n",
                expectedOutput = "3",
                solutionCode = "tasks = ['Task 1', 'Task 2']\ntasks.append('Task 3')\nprint(len(tasks))",
                hint = "tasks.append('Task 3'); print(len(tasks))"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the best data structure to store a collection of task records with keys like title, done, and priority?",
                options = listOf("List of dictionaries", "Set of integers", "Tuple of booleans", "Single string"),
                correctIndex = 0,
                explanation = "A list of dictionaries is the standard structure for collection of structured records."
            )
        ),
        Lesson(
            id = "l9_2",
            levelId = 9,
            orderNumber = 2,
            title = "Project: Automated Data Cleaner & Stats Analyzer",
            subtitle = "Processing raw tabular datasets and calculating metrics",
            estimatedMinutes = 15,
            xpReward = 100,
            conceptExplanation = """
# Portfolio Project 2: Data Analyzer

Data engineering is one of Python's primary industry uses.

### Steps:
1. Cleaning missing or malformed records.
2. Filtering outliers.
3. Calculating statistical measures (mean, median, variance).
            """.trimIndent(),
            syntax = "# Data pipeline architecture",
            codeExample = """# Data Cleaner & Statistical Analyzer
raw_sales = [120, 150, None, 200, 180, 0, 250]

# Pipeline step 1: Filter None and 0
cleaned = [x for x in raw_sales if x is not None and x > 0]

# Pipeline step 2: Calculate metrics
total_sales = sum(cleaned)
avg_sales = total_sales / len(cleaned)
max_sale = max(cleaned)

print(f"Cleaned Records: {cleaned}")
print("Total Sales: $" + f"{total_sales}")
print("Average Sale: $" + f"{round(avg_sales, 2)}")
print("Max Sale: $" + f"{max_sale}")
""",
            expectedOutput = "Cleaned Records: [120, 150, 200, 180, 250]\nTotal Sales: $900\nAverage Sale: $180.0\nMax Sale: $250",
            commonMistakes = listOf(
                "Calculating average before filtering out None values (causes TypeError in arithmetic)."
            ),
            keyTakeaways = listOf(
                "Data cleaning pipelines prevent downstream analysis errors.",
                "List comprehensions provide clean data transformation pipelines."
            ),
            practiceTask = PracticeTask(
                title = "Clean and Average",
                description = "Given data = [10, 20, 30], compute the average = sum(data) / len(data) and print it.",
                starterCode = "data = [10, 20, 30]\n# Compute and print\n",
                expectedOutput = "20.0",
                solutionCode = "data = [10, 20, 30]\nprint(sum(data) / len(data))",
                hint = "print(sum(data) / len(data))"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What should you always do before calculating statistical metrics on raw real-world data?",
                options = listOf(
                    "Encrypt the data",
                    "Filter out null/None and corrupted records (data cleaning)",
                    "Convert all numbers to strings",
                    "Print each row individually"
                ),
                correctIndex = 1,
                explanation = "Cleaning and validating data is essential before metric calculation."
            )
        ),
        Lesson(
            id = "l9_3",
            levelId = 9,
            orderNumber = 3,
            title = "Level 9 Mastery Review & Summary",
            subtitle = "Consolidating end-to-end Python applications and portfolio engineering",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 9 Milestone: Portfolio Projects Mastered!

You have developed full project implementation capabilities:
- Designing modular state managers
- Implementing robust data pipelines
- Structuring software for real-world reliability

In our final **Level 10**, we conquer the frontier: **Python for AI & Machine Learning**!
            """.trimIndent(),
            syntax = "# Level 9 Complete",
            codeExample = """# Milestone check
level = 9
topic = "Real-World Portfolio Projects"
print(f"Level {level}: {topic} Mastered! Ready for AI/ML.")
""",
            expectedOutput = "Level 9: Real-World Portfolio Projects Mastered! Ready for AI/ML.",
            commonMistakes = listOf(
                "Not organizing projects into modular classes and helper functions."
            ),
            keyTakeaways = listOf(
                "Portfolio projects demonstrate tangible end-to-end proficiency.",
                "Take the Level 9 Quiz to earn XP!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 9 Complete",
                description = "Print 'Level 9 Complete! Onward to AI & Machine Learning.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 9 Complete! Onward to AI & Machine Learning.",
                solutionCode = "print(\"Level 9 Complete! Onward to AI & Machine Learning.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What design pattern improves testability and readability in larger Python applications?",
                options = listOf(
                    "Putting all code in one 10,000-line function",
                    "Separating code into modular classes, functions, and packages",
                    "Avoiding all variable assignments",
                    "Using only global variables"
                ),
                correctIndex = 1,
                explanation = "Modular architecture makes code maintainable, reusable, and testable."
            )
        )
    )
}
