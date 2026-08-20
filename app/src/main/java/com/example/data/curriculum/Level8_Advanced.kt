package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level8_Advanced {
    val lessons = listOf(
        Lesson(
            id = "l8_1",
            levelId = 8,
            orderNumber = 1,
            title = "Iterators & The Iterator Protocol",
            subtitle = "Understanding iter(), next(), StopIteration, and iterable mechanics",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# The Python Iterator Protocol

An **iterable** is an object capable of returning its members one by one. An **iterator** is the actual object that maintains state during traversal.

### The Protocol:
- `__iter__()`: Returns the iterator object itself.
- `__next__()`: Returns the next value from the sequence. Raises `StopIteration` when no more elements remain.
- `iter(obj)` and `next(iterator)` are the built-in hooks.
            """.trimIndent(),
            syntax = "iterator = iter(collection)\nval = next(iterator)",
            codeExample = """# Iterator protocol demonstration
numbers = [10, 20, 30]
it = iter(numbers)

print(next(it))
print(next(it))
print(next(it))
# Calling next(it) again would raise StopIteration
""",
            expectedOutput = "10\n20\n30",
            commonMistakes = listOf(
                "Assuming iterators can be rewound or reset (once consumed, an iterator is exhausted; you must create a new one)."
            ),
            keyTakeaways = listOf(
                "For loops in Python internally use iter() and next().",
                "StopIteration signals the end of the iteration sequence.",
                "Iterators enable lazy evaluation of large datasets."
            ),
            practiceTask = PracticeTask(
                title = "Extract via Next",
                description = "Given letters = ['A', 'B'], create an iterator using iter(letters) and print next(it) twice on separate lines.",
                starterCode = "letters = ['A', 'B']\n# Iterate and print\n",
                expectedOutput = "A\nB",
                solutionCode = "letters = ['A', 'B']\nit = iter(letters)\nprint(next(it))\nprint(next(it))",
                hint = "it = iter(letters); print(next(it))"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which exception is raised by an iterator when no more elements are available?",
                options = listOf("IndexError", "StopIteration", "EndOfListError", "KeyError"),
                correctIndex = 1,
                explanation = "StopIteration is the standard exception raised when an iterator is exhausted."
            )
        ),
        Lesson(
            id = "l8_2",
            levelId = 8,
            orderNumber = 2,
            title = "Generators & The `yield` Keyword",
            subtitle = "Memory-efficient stream processing and lazy evaluation",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Generators & Lazy Evaluation

A **generator** is a function that produces a sequence of values lazily using the `yield` statement instead of `return`.

### Key Advantages:
- **Zero Memory Overhead**: Yields one item at a time rather than building an entire million-item list in memory.
- Pauses execution and remembers its state between calls.
            """.trimIndent(),
            syntax = "def count_up_to(n):\n    count = 1\n    while count <= n:\n        yield count\n        count += 1",
            codeExample = """# Generator function concept
def count_down(start):
    n = start
    while n > 0:
        yield n
        n -= 1

for num in count_down(3):
    print(f"Count: {num}")
""",
            expectedOutput = "Count: 3\nCount: 2\nCount: 1",
            commonMistakes = listOf(
                "Confusing yield with return (yield pauses execution and can be called repeatedly; return exits).",
                "Trying to index a generator (gen[0] raises TypeError; iterate or use next())."
            ),
            keyTakeaways = listOf(
                "Generators compute values on-demand (lazy evaluation).",
                "Essential when processing large log files, streams, or infinite series.",
                "Generator expressions use parentheses: `(x * 2 for x in data)`."
            ),
            practiceTask = PracticeTask(
                title = "Create Generator Sequence",
                description = "Define a generator gen() that yields 1, then yields 2, then yields 3. Iterate through it and print each item.",
                starterCode = "# Define and iterate generator\n",
                expectedOutput = "1\n2\n3",
                solutionCode = "def gen():\n    yield 1\n    yield 2\n    yield 3\n\nfor val in gen():\n    print(val)",
                hint = "def gen(): yield 1; yield 2; yield 3"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What keyword turns a standard Python function into a generator?",
                options = listOf("generate", "yield", "stream", "lazy"),
                correctIndex = 1,
                explanation = "The 'yield' keyword transforms a function into a generator factory."
            )
        ),
        Lesson(
            id = "l8_3",
            levelId = 8,
            orderNumber = 3,
            title = "Decorators & Higher-Order Functions",
            subtitle = "Wrapping and enhancing function behavior with @decorator syntax",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Python Decorators

A **decorator** is a callable that takes a function as input, extends or modifies its behavior, and returns a new function.

### The `@` Syntactic Sugar:
```python
@my_decorator
def target():
    pass
# Equivalent to: target = my_decorator(target)
```

Commonly used for logging, timing, authentication, and caching (`@functools.lru_cache`).
            """.trimIndent(),
            syntax = "def decorator(func):\n    def wrapper(*args, **kwargs):\n        # before\n        res = func(*args, **kwargs)\n        # after\n        return res\n    return wrapper",
            codeExample = """# Decorator concept
def log_execution(func):
    def wrapper(name):
        print(f"Starting execution of {func.__name__}...")
        res = func(name)
        print(f"Finished {func.__name__}.")
        return res
    return wrapper

@log_execution
def greet(name):
    print(f"Hello, {name}!")

greet("Alex")
""",
            expectedOutput = "Starting execution of greet...\nHello, Alex!\nFinished greet.",
            commonMistakes = listOf(
                "Forgetting to return the inner wrapper from the decorator function.",
                "Forgetting *args and **kwargs in the wrapper, preventing it from accepting arbitrary parameters."
            ),
            keyTakeaways = listOf(
                "Decorators adhere to the Open/Closed Principle (modifying behavior without changing source).",
                "@decorator is syntactic sugar for func = decorator(func)."
            ),
            practiceTask = PracticeTask(
                title = "Print Decorator Output",
                description = "Print 'Before' on line 1, 'Core Logic' on line 2, and 'After' on line 3.",
                starterCode = "# Print decorator flow\n",
                expectedOutput = "Before\nCore Logic\nAfter",
                solutionCode = "print(\"Before\")\nprint(\"Core Logic\")\nprint(\"After\")",
                hint = "Use 3 print statements."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is a decorator in Python?",
                options = listOf(
                    "A graphic design tool",
                    "A function that takes another function and extends its behavior",
                    "A syntax error validator",
                    "A file compression algorithm"
                ),
                correctIndex = 1,
                explanation = "A decorator takes a function as an argument and wraps/extends its execution."
            )
        ),
        Lesson(
            id = "l8_4",
            levelId = 8,
            orderNumber = 4,
            title = "Level 8 Mastery Review & Summary",
            subtitle = "Consolidating iterators, generators, decorators, and metaprogramming",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 8 Milestone: Advanced Python Mastered!

You now possess advanced Python programming knowledge:
- The Iterator Protocol (`__iter__`, `__next__`, `StopIteration`)
- Memory-efficient generator streaming with `yield`
- Decorator mechanics & function wrappers
- Metaprogramming and dynamic runtime inspection

In **Level 9**, we build **Real-World Portfolio Projects**!
            """.trimIndent(),
            syntax = "# Level 8 Complete",
            codeExample = """# Advanced summary
level = 8
topic = "Advanced Python & Metaprogramming"
print(f"Level {level}: {topic} Mastered!")
""",
            expectedOutput = "Level 8: Advanced Python & Metaprogramming Mastered!",
            commonMistakes = listOf(
                "Applying complex decorators where simple helper functions would suffice."
            ),
            keyTakeaways = listOf(
                "Advanced Python features enable high-performance library design.",
                "Take the Level 8 Quiz to earn XP!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 8 Complete",
                description = "Print 'Level 8 Complete! Onward to Portfolio Projects.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 8 Complete! Onward to Portfolio Projects.",
                solutionCode = "print(\"Level 8 Complete! Onward to Portfolio Projects.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which built-in module provides memoization caching via @lru_cache?",
                options = listOf("math", "functools", "itertools", "collections"),
                correctIndex = 1,
                explanation = "functools provides @lru_cache and other higher-order utilities."
            )
        )
    )
}
