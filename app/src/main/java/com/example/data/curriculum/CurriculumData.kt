package com.example.data.curriculum

import com.example.data.model.CourseLevel
import com.example.data.model.Lesson
import com.example.data.model.ModuleQuiz
import com.example.data.model.QuizQuestion

object CurriculumData {

    val levels: List<CourseLevel> by lazy {
        listOf(
            CourseLevel(
                id = 1,
                title = "Level 1 — Python Fundamentals",
                subtitle = "Philosophy, variables, types, math & string formatting",
                description = "Master the core syntax, memory model, arithmetic operators, PEP 8 standards, and modern f-strings.",
                icon = "🐍",
                colorHex = 0xFF3B82F6, // Blue
                lessons = Level1_Fundamentals.lessons,
                quiz = ModuleQuiz(
                    levelId = 1,
                    title = "Level 1 Quiz: Python Fundamentals",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q1_1",
                            question = "What is the primary design philosophy behind Python as outlined in PEP 20 (The Zen of Python)?",
                            options = listOf("Maximum compilation speed", "Readability counts and simplicity", "Strict static typing", "Memory pointers"),
                            correctOptionIndex = 1,
                            explanation = "Python emphasizes clean, human-readable syntax and simplicity."
                        ),
                        QuizQuestion(
                            id = "q1_2",
                            question = "What does the expression 17 // 4 evaluate to?",
                            codeSnippet = "result = 17 // 4\nprint(result)",
                            options = listOf("4.25", "4", "5", "1"),
                            correctOptionIndex = 1,
                            explanation = "Floor division // truncates the decimal remainder and yields 4."
                        ),
                        QuizQuestion(
                            id = "q1_3",
                            question = "Which of the following is a valid PEP 8 variable name?",
                            options = listOf("2nd_score", "user_score", "user-score", "class"),
                            correctOptionIndex = 1,
                            explanation = "user_score is valid snake_case. 2nd_score starts with a digit, user-score has a hyphen, and class is a reserved keyword."
                        ),
                        QuizQuestion(
                            id = "q1_4",
                            question = "What is the output of f'Total: {5 * 4}'?",
                            codeSnippet = "print(f\"Total: {5 * 4}\")",
                            options = listOf("Total: {5 * 4}", "Total: 20", "Total: 54", "SyntaxError"),
                            correctOptionIndex = 1,
                            explanation = "f-strings evaluate Python expressions inside curly braces at runtime."
                        ),
                        QuizQuestion(
                            id = "q1_5",
                            question = "Are Python strings mutable?",
                            options = listOf("Yes, any character can be modified", "No, strings are immutable sequences", "Only single-quoted strings", "Only inside loops"),
                            correctOptionIndex = 1,
                            explanation = "Strings in Python are immutable; attempting item assignment raises a TypeError."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 2,
                title = "Level 2 — Control Flow & Loops",
                subtitle = "Conditionals, while, for, range, break, continue & comprehensions",
                description = "Direct programmatic logic through conditional decision trees, iterative loops, and concise list comprehensions.",
                icon = "🔀",
                colorHex = 0xFF10B981, // Emerald
                lessons = Level2_ControlFlow.lessons,
                quiz = ModuleQuiz(
                    levelId = 2,
                    title = "Level 2 Quiz: Control Flow & Loops",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q2_1",
                            question = "How many numbers does range(1, 6) produce?",
                            codeSnippet = "for n in range(1, 6):\n    pass",
                            options = listOf("6", "5", "4", "7"),
                            correctOptionIndex = 1,
                            explanation = "range(1, 6) produces numbers 1, 2, 3, 4, 5 (5 numbers in total)."
                        ),
                        QuizQuestion(
                            id = "q2_2",
                            question = "What does the 'continue' keyword do inside a loop body?",
                            options = listOf("Terminates the loop entirely", "Skips to the next iteration", "Pauses execution", "Resets variables"),
                            correctOptionIndex = 1,
                            explanation = "continue immediately jumps to the beginning of the next iteration."
                        ),
                        QuizQuestion(
                            id = "q2_3",
                            question = "When does the 'else' block of a for loop execute?",
                            codeSnippet = "for item in items:\n    if item == target: break\nelse:\n    print('Completed')",
                            options = listOf("When the loop is broken by break", "When the loop completes without encountering a break", "Always", "Only if an exception is thrown"),
                            correctOptionIndex = 1,
                            explanation = "Loop else blocks execute if the loop completes all iterations without hitting a break."
                        ),
                        QuizQuestion(
                            id = "q2_4",
                            question = "What is the result of [x * 2 for x in [1, 2, 3] if x > 1]?",
                            codeSnippet = "res = [x * 2 for x in [1, 2, 3] if x > 1]\nprint(res)",
                            options = listOf("[2, 4, 6]", "[4, 6]", "[2, 4]", "[6]"),
                            correctOptionIndex = 1,
                            explanation = "2 and 3 satisfy x > 1, producing [4, 6]."
                        ),
                        QuizQuestion(
                            id = "q2_5",
                            question = "What is the truth value of 'not (5 > 2 and 3 < 1)'?",
                            options = listOf("False", "True", "None", "Error"),
                            correctOptionIndex = 1,
                            explanation = "(5 > 2 and 3 < 1) is False, and 'not False' evaluates to True."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 3,
                title = "Level 3 — Data Structures",
                subtitle = "Lists, tuples, dictionaries, sets & nested collections",
                description = "Harness Python's versatile collection types, algorithmic complexities, and associative mappings.",
                icon = "📦",
                colorHex = 0xFFF59E0B, // Amber
                lessons = Level3_DataStructures.lessons,
                quiz = ModuleQuiz(
                    levelId = 3,
                    title = "Level 3 Quiz: Data Structures",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q3_1",
                            question = "Which collection type is immutable?",
                            options = listOf("List", "Dictionary", "Tuple", "Set"),
                            correctOptionIndex = 2,
                            explanation = "Tuples cannot be altered after instantiation."
                        ),
                        QuizQuestion(
                            id = "q3_2",
                            question = "What is the average time complexity for key lookup in a Python dictionary?",
                            options = listOf("O(N)", "O(1)", "O(log N)", "O(N^2)"),
                            correctOptionIndex = 1,
                            explanation = "Dictionaries use hash tables, achieving O(1) average lookup time."
                        ),
                        QuizQuestion(
                            id = "q3_3",
                            question = "What does the method dict.get('missing', 0) return if 'missing' is not a key in dict?",
                            options = listOf("KeyError", "None", "0", "False"),
                            correctOptionIndex = 2,
                            explanation = "dict.get() returns the fallback value (0 in this case) without raising an exception."
                        ),
                        QuizQuestion(
                            id = "q3_4",
                            question = "What is the result of {1, 2, 3} & {2, 3, 4}?",
                            options = listOf("{1, 2, 3, 4}", "{2, 3}", "{1, 4}", "{}"),
                            correctOptionIndex = 1,
                            explanation = "& is the set intersection operator, returning common elements {2, 3}."
                        ),
                        QuizQuestion(
                            id = "q3_5",
                            question = "What does lst[-1] retrieve in a Python list?",
                            options = listOf("The first item", "The last item", "The middle item", "Raises IndexError"),
                            correctOptionIndex = 1,
                            explanation = "Index -1 accesses the last element of the sequence."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 4,
                title = "Level 4 — Functions & Scope",
                subtitle = "def, return, default parameters, *args, **kwargs, LEGB & lambdas",
                description = "Construct modular, reusable functions, navigate variable namespaces, and implement functional patterns.",
                icon = "⚙️",
                colorHex = 0xFF8B5CF6, // Purple
                lessons = Level4_Functions.lessons,
                quiz = ModuleQuiz(
                    levelId = 4,
                    title = "Level 4 Quiz: Functions & Scope",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q4_1",
                            question = "What does LEGB stand for in Python scope resolution?",
                            options = listOf("Local, Enclosing, Global, Built-in", "List, Element, Group, Block", "Linear, Exponential, Geometric, Binary", "None"),
                            correctOptionIndex = 0,
                            explanation = "LEGB represents the order of namespace search for identifiers."
                        ),
                        QuizQuestion(
                            id = "q4_2",
                            question = "What data structure collects arbitrary keyword arguments via **kwargs?",
                            options = listOf("Tuple", "List", "Dictionary", "Set"),
                            correctOptionIndex = 2,
                            explanation = "**kwargs packs keyword arguments into a Python dictionary."
                        ),
                        QuizQuestion(
                            id = "q4_3",
                            question = "What is a lambda function in Python?",
                            options = listOf("A multi-line recursive class", "An anonymous single-expression function", "A thread lock", "A compiler directive"),
                            correctOptionIndex = 1,
                            explanation = "Lambda creates lightweight, anonymous single-expression callables."
                        ),
                        QuizQuestion(
                            id = "q4_4",
                            question = "What happens if a recursive function is missing a base case?",
                            options = listOf("Compiles normally", "RecursionError: maximum recursion depth exceeded", "Returns 0", "Turns into a generator"),
                            correctOptionIndex = 1,
                            explanation = "Without a base case, recursion continues until the call stack limit is reached, raising RecursionError."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 5,
                title = "Level 5 — Modules & File I/O",
                subtitle = "Standard library, __name__ == '__main__', with statement & JSON",
                description = "Organize code into modular packages, safely manage file resources, and serialize structured JSON data.",
                icon = "📁",
                colorHex = 0xFFEC4899, // Pink
                lessons = Level5_ModulesFiles.lessons,
                quiz = ModuleQuiz(
                    levelId = 5,
                    title = "Level 5 Quiz: Modules & File I/O",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q5_1",
                            question = "Why is the 'with open(...)' pattern preferred for file handling?",
                            options = listOf("It encrypts the file", "It automatically and reliably closes the file even if exceptions occur", "It speeds up network I/O", "It makes files read-only"),
                            correctOptionIndex = 1,
                            explanation = "Context managers guarantee deterministic resource closure."
                        ),
                        QuizQuestion(
                            id = "q5_2",
                            question = "What is the value of __name__ when a script is executed directly?",
                            options = listOf("'__init__'", "'__main__'", "'root'", "'module'"),
                            correctOptionIndex = 1,
                            explanation = "Top-level executed scripts have __name__ set to '__main__'."
                        ),
                        QuizQuestion(
                            id = "q5_3",
                            question = "Which function converts a Python dictionary into a JSON string?",
                            options = listOf("json.dump()", "json.loads()", "json.dumps()", "json.parse()"),
                            correctOptionIndex = 2,
                            explanation = "json.dumps() serializes a Python object to a JSON formatted string."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 6,
                title = "Level 6 — Error & Exception Handling",
                subtitle = "Try, except, finally, custom exceptions & defensive programming",
                description = "Engineer fault-tolerant systems using robust exception handling, custom domain error types, and guaranteed cleanup.",
                icon = "🛡️",
                colorHex = 0xFFEF4444, // Red
                lessons = Level6_Exceptions.lessons,
                quiz = ModuleQuiz(
                    levelId = 6,
                    title = "Level 6 Quiz: Error Handling",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q6_1",
                            question = "Which block is guaranteed to execute in a try-except structure, even after a return statement?",
                            options = listOf("else", "finally", "catch", "defer"),
                            correctOptionIndex = 1,
                            explanation = "The finally block always runs for guaranteed cleanup."
                        ),
                        QuizQuestion(
                            id = "q6_2",
                            question = "What keyword is used to trigger an exception intentionally?",
                            options = listOf("throw", "raise", "error", "panic"),
                            correctOptionIndex = 1,
                            explanation = "Python uses 'raise' to raise an exception."
                        ),
                        QuizQuestion(
                            id = "q6_3",
                            question = "What base class should custom user-defined exceptions inherit from?",
                            options = listOf("BaseException", "Exception", "Error", "Throwable"),
                            correctOptionIndex = 1,
                            explanation = "Inheriting from Exception is standard practice in Python."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 7,
                title = "Level 7 — Object-Oriented Programming",
                subtitle = "Classes, objects, self, inheritance, properties & dunder methods",
                description = "Model domain logic through object-oriented design, inheritance hierarchies, encapsulation, and magic dunder methods.",
                icon = "🏛️",
                colorHex = 0xFF06B6D4, // Cyan
                lessons = Level7_OOP.lessons,
                quiz = ModuleQuiz(
                    levelId = 7,
                    title = "Level 7 Quiz: Object-Oriented Programming",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q7_1",
                            question = "What does the first parameter 'self' represent in instance methods?",
                            options = listOf("The parent class", "The instance on which the method was called", "A global pointer", "The module"),
                            correctOptionIndex = 1,
                            explanation = "'self' is the explicit reference to the current object instance."
                        ),
                        QuizQuestion(
                            id = "q7_2",
                            question = "Which built-in function allows calling parent class methods from a subclass?",
                            options = listOf("parent()", "base()", "super()", "this()"),
                            correctOptionIndex = 2,
                            explanation = "super() delegates method lookups to the superclass."
                        ),
                        QuizQuestion(
                            id = "q7_3",
                            question = "Which dunder method is invoked by print() and str()?",
                            options = listOf("__repr__", "__str__", "__text__", "__show__"),
                            correctOptionIndex = 1,
                            explanation = "__str__ returns the user-friendly string representation."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 8,
                title = "Level 8 — Advanced Python & Metaprogramming",
                subtitle = "Iterators, generators with yield, decorators & introspection",
                description = "Unlock memory-efficient streaming generators, higher-order function decorators, and dynamic Python metaprogramming.",
                icon = "⚡",
                colorHex = 0xFFF97316, // Orange
                lessons = Level8_Advanced.lessons,
                quiz = ModuleQuiz(
                    levelId = 8,
                    title = "Level 8 Quiz: Advanced Python",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q8_1",
                            question = "What keyword creates a generator function that produces values lazily?",
                            options = listOf("generate", "yield", "stream", "lazy"),
                            correctOptionIndex = 1,
                            explanation = "'yield' turns a function into a lazy generator iterator."
                        ),
                        QuizQuestion(
                            id = "q8_2",
                            question = "What is a decorator in Python?",
                            options = listOf("A function that wraps and modifies the behavior of another function", "A GUI widget", "A string formatter", "A memory profiler"),
                            correctOptionIndex = 0,
                            explanation = "Decorators take a callable and extend or modify its behavior dynamically."
                        ),
                        QuizQuestion(
                            id = "q8_3",
                            question = "Which exception indicates that an iterator has reached the end of its sequence?",
                            options = listOf("IndexError", "StopIteration", "EOFError", "KeyError"),
                            correctOptionIndex = 1,
                            explanation = "StopIteration is raised by next() when no items remain."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 9,
                title = "Level 9 — Real-World Portfolio Projects",
                subtitle = "CLI tools, data pipelines, automated cleaners & package design",
                description = "Synthesize all concepts into full-featured real-world applications: task trackers, data analysis pipelines, and automation tools.",
                icon = "🚀",
                colorHex = 0xFF14B8A6, // Teal
                lessons = Level9_Projects.lessons,
                quiz = ModuleQuiz(
                    levelId = 9,
                    title = "Level 9 Quiz: Portfolio Projects",
                    passingScorePercent = 70,
                    xpReward = 150,
                    questions = listOf(
                        QuizQuestion(
                            id = "q9_1",
                            question = "What is the primary objective of data cleaning before statistical analysis?",
                            options = listOf("Encrypting rows", "Filtering out null, malformed, or corrupt values", "Compressing to zip", "Converting numbers to strings"),
                            correctOptionIndex = 1,
                            explanation = "Cleaning data removes bad inputs and guarantees downstream statistical integrity."
                        ),
                        QuizQuestion(
                            id = "q9_2",
                            question = "Which architectural principle improves maintainability across large multi-file projects?",
                            options = listOf("Modular separation of concerns", "Putting everything into one file", "Avoiding all classes", "Global variables"),
                            correctOptionIndex = 0,
                            explanation = "Separating concerns into distinct modules and classes enhances clarity and testing."
                        )
                    )
                )
            ),
            CourseLevel(
                id = 10,
                title = "Level 10 — Python for AI & Machine Learning",
                subtitle = "NumPy arrays, linear regression, perceptrons, activations & modern LLMs",
                description = "Step into Artificial Intelligence: vectorized math, neural network forward propagation, activation functions, and generative AI.",
                icon = "🧠",
                colorHex = 0xFF6366F1, // Indigo
                lessons = Level10_AIML.lessons,
                quiz = ModuleQuiz(
                    levelId = 10,
                    title = "Level 10 Quiz: Python for AI & ML",
                    passingScorePercent = 70,
                    xpReward = 200,
                    questions = listOf(
                        QuizQuestion(
                            id = "q10_1",
                            question = "Why are NumPy arrays vastly faster than Python lists for numerical computations?",
                            options = listOf("They don't use memory", "Contiguous memory storage and SIMD C/CUDA vectorization", "They only hold strings", "They are interpreted in real-time"),
                            correctOptionIndex = 1,
                            explanation = "NumPy arrays store elements contiguously in memory and execute vectorized operations in compiled C/CUDA."
                        ),
                        QuizQuestion(
                            id = "q10_2",
                            question = "What is the output of the ReLU activation function relu(z) = max(0, z) for z = -4.5?",
                            options = listOf("-4.5", "0", "4.5", "1.0"),
                            correctOptionIndex = 1,
                            explanation = "ReLU clamps all negative values to 0."
                        ),
                        QuizQuestion(
                            id = "q10_3",
                            question = "In the linear regression formula y = wx + b, what is 'w' called?",
                            options = listOf("Weight (slope)", "Width", "Window", "Warning"),
                            correctOptionIndex = 0,
                            explanation = "w is the weight parameter adjusted during model training."
                        ),
                        QuizQuestion(
                            id = "q10_4",
                            question = "What is the role of activation functions in deep neural networks?",
                            options = listOf("Formatting print statements", "Introducing non-linearity so networks can learn complex patterns", "Closing database connections", "Sorting lists"),
                            correctOptionIndex = 1,
                            explanation = "Non-linear activations allow multi-layer neural networks to approximate complex mathematical functions."
                        )
                    )
                )
            )
        )
    }

    val totalLessonsCount: Int by lazy {
        levels.sumOf { it.lessons.size }
    }

    fun findLessonById(lessonId: String): Lesson? {
        for (lvl in levels) {
            for (les in lvl.lessons) {
                if (les.id == lessonId) return les
            }
        }
        return null
    }

    fun getNextLesson(currentLessonId: String): Lesson? {
        val all = levels.flatMap { it.lessons }
        val idx = all.indexOfFirst { it.id == currentLessonId }
        return if (idx != -1 && idx + 1 < all.size) all[idx + 1] else null
    }

    fun getPreviousLesson(currentLessonId: String): Lesson? {
        val all = levels.flatMap { it.lessons }
        val idx = all.indexOfFirst { it.id == currentLessonId }
        return if (idx > 0) all[idx - 1] else null
    }

    fun getLevelById(levelId: Int): CourseLevel? {
        return levels.firstOrNull { it.id == levelId }
    }
}
