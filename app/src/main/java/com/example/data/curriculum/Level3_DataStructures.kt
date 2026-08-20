package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level3_DataStructures {
    val lessons = listOf(
        Lesson(
            id = "l3_1",
            levelId = 3,
            orderNumber = 1,
            title = "Lists: Creation, Indexing & Mutability",
            subtitle = "Ordered, mutable sequences of heterogeneous objects",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Python Lists

A `list` is an ordered, mutable sequence of items enclosed in square brackets `[]`.

### Key Characteristics:
- **Ordered**: Elements maintain their insertion order.
- **Mutable**: You can add, remove, and replace items after creation.
- **Heterogeneous**: Can store elements of different data types.
- **0-Indexed**: The first element is at index `0`, the last at `-1`.
            """.trimIndent(),
            syntax = "my_list = [item1, item2, ...]\nmy_list[0]\nmy_list[-1]",
            codeExample = """# Creating and indexing lists
fruits = ["apple", "banana", "cherry"]
fruits[1] = "blueberry" # Mutation

print(fruits)
print(f"First: {fruits[0]}, Last: {fruits[-1]}")
print(f"Count: {len(fruits)}")
""",
            expectedOutput = "['apple', 'blueberry', 'cherry']\nFirst: apple, Last: cherry\nCount: 3",
            commonMistakes = listOf(
                "Trying to access an index >= len(list) (causes IndexError: list index out of range).",
                "Assuming list assignment copies the list (b = a creates a reference, not a copy)."
            ),
            keyTakeaways = listOf(
                "Lists are mutable and 0-indexed.",
                "Negative indices count backward from the end.",
                "len(list) returns the total number of items."
            ),
            practiceTask = PracticeTask(
                title = "Access First and Last",
                description = "Given items = [10, 20, 30, 40], print items[0] and items[-1] on separate lines.",
                starterCode = "items = [10, 20, 30, 40]\n# Print first and last\n",
                expectedOutput = "10\n40",
                solutionCode = "items = [10, 20, 30, 40]\nprint(items[0])\nprint(items[-1])",
                hint = "Use items[0] and items[-1]."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does index -1 refer to in a Python list?",
                options = listOf("The first item", "The last item", "An invalid index", "The middle item"),
                correctIndex = 1,
                explanation = "Negative indices count from the end, so -1 is the last element."
            )
        ),
        Lesson(
            id = "l3_2",
            levelId = 3,
            orderNumber = 2,
            title = "List Methods & Slicing",
            subtitle = "append, pop, insert, sort, and slice notation [start:stop:step]",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# List Methods & Slicing

### Common Methods:
- `.append(x)`: Adds `x` to the end.
- `.insert(i, x)`: Inserts `x` at index `i`.
- `.pop([i])`: Removes and returns item at index `i` (default last).
- `.remove(x)`: Removes the first occurrence of value `x`.
- `.sort()`: Sorts list in-place.
- `.reverse()`: Reverses list in-place.

### Slice Notation:
`list[start:stop:step]` extracts a sublist from `start` up to (excluding) `stop`.
            """.trimIndent(),
            syntax = "lst.append(val)\nlst.pop()\nlst[1:4]",
            codeExample = """# List operations and slicing
numbers = [10, 20, 30, 40, 50]
numbers.append(60)

print(numbers)
print(numbers[1:4]) # Slicing indices 1, 2, 3
print(numbers[:2])  # First 2 items
print(numbers[3:])  # From index 3 to end
""",
            expectedOutput = "[10, 20, 30, 40, 50, 60]\n[20, 30, 40]\n[10, 20]\n[40, 50, 60]",
            commonMistakes = listOf(
                "Calling result = list.append(x) (append modifies in-place and returns None!).",
                "Confusing remove(value) with pop(index)."
            ),
            keyTakeaways = listOf(
                "Slicing creates a new list shallow copy.",
                "In-place methods (.append, .sort) return None.",
                "Omitting start defaults to 0, omitting stop defaults to len."
            ),
            practiceTask = PracticeTask(
                title = "Append and Slice",
                description = "Given colors = ['red', 'green'], append 'blue'. Then print the last 2 colors using a slice [:].",
                starterCode = "colors = ['red', 'green']\n# Append and slice\n",
                expectedOutput = "['green', 'blue']",
                solutionCode = "colors = ['red', 'green']\ncolors.append('blue')\nprint(colors[1:])",
                hint = "colors.append('blue') then print(colors[1:])."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the return value of list.append('item')?",
                options = listOf("The new list", "The appended item", "None", "The new list length"),
                correctIndex = 2,
                explanation = "list.append() modifies the list in place and returns None."
            )
        ),
        Lesson(
            id = "l3_3",
            levelId = 3,
            orderNumber = 3,
            title = "Tuples & Immutability",
            subtitle = "Fixed sequences, unpacking, and dictionary keys",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Tuples

A `tuple` is an ordered, **immutable** sequence of items defined using parentheses `()`.

### Why Use Tuples?
- **Safety**: Prevents accidental data modification.
- **Performance**: Faster and consume less memory than lists.
- **Hashable**: Can be used as dictionary keys and set elements (unlike lists).
- **Unpacking**: Assigning tuple elements directly to variables: `x, y = (10, 20)`.
            """.trimIndent(),
            syntax = "point = (10, 20)\nx, y = point",
            codeExample = """# Tuples and unpacking
coords = (1920, 1080)
width, height = coords

print(f"Resolution: {width}x{height}")
print(type(coords))
""",
            expectedOutput = "Resolution: 1920x1080\n<class 'tuple'>",
            commonMistakes = listOf(
                "Creating a single-element tuple without a trailing comma: (5) is an int; (5,) is a tuple.",
                "Attempting item assignment on a tuple (e.g. t[0] = 1 causes TypeError)."
            ),
            keyTakeaways = listOf(
                "Tuples are immutable sequences.",
                "Tuple unpacking is clean and widely used for multiple return values.",
                "Single element tuples require a trailing comma: `(x,)`."
            ),
            practiceTask = PracticeTask(
                title = "Unpack RGB Color",
                description = "Given rgb = (255, 128, 0), unpack into r, g, b and print 'Red: 255, Green: 128, Blue: 0'.",
                starterCode = "rgb = (255, 128, 0)\n# Unpack and print\n",
                expectedOutput = "Red: 255, Green: 128, Blue: 0",
                solutionCode = "rgb = (255, 128, 0)\nr, g, b = rgb\nprint(f\"Red: {r}, Green: {g}, Blue: {b}\")",
                hint = "r, g, b = rgb then use f-string."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "How do you create a tuple with a single element 'x'?",
                options = listOf("('x')", "('x',)", "tuple['x']", "['x']"),
                correctIndex = 1,
                explanation = "A trailing comma is mandatory for single-element tuples: ('x',)."
            )
        ),
        Lesson(
            id = "l3_4",
            levelId = 3,
            orderNumber = 4,
            title = "Dictionaries: Key-Value Hash Maps",
            subtitle = "O(1) lookups, keys, values, and associative mappings",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Python Dictionaries (dict)

A `dict` is an ordered (Python 3.7+), mutable mapping of unique, hashable **keys** to arbitrary **values**.

### Properties:
- Defined with curly braces `{}` and colon separator `key: value`.
- **O(1) average lookup time** backed by hash tables.
- Keys must be immutable (strings, numbers, tuples).
- Access values via `dict[key]` or safe `dict.get(key, default)`.
            """.trimIndent(),
            syntax = "d = {'key': 'value'}\nd['key']\nd.get('key', default)",
            codeExample = """# Dictionary operations
student = {
    "name": "Jordan",
    "course": "Python Mastery",
    "xp": 350
}

# Update and add
student["level"] = 2
student["xp"] += 50

print(student["name"])
print(student.get("badge", "No Badge"))
print(student["xp"])
""",
            expectedOutput = "Jordan\nNo Badge\n400",
            commonMistakes = listOf(
                "Accessing non-existent key with d['missing'] (raises KeyError; use .get() instead).",
                "Using a list as a dictionary key (lists are mutable and unhashable)."
            ),
            keyTakeaways = listOf(
                "Dictionaries provide fast associative lookups.",
                ".get(key, default) prevents KeyErrors when looking up optional keys.",
                "Dictionary keys must be hashable objects."
            ),
            practiceTask = PracticeTask(
                title = "Create User Dict",
                description = "Create a dict user with 'username': 'py_coder' and 'level': 5. Print user['username'].",
                starterCode = "# Create dict and print\n",
                expectedOutput = "py_coder",
                solutionCode = "user = {\"username\": \"py_coder\", \"level\": 5}\nprint(user[\"username\"])",
                hint = "user = {'username': 'py_coder', 'level': 5}"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What happens when you look up a missing key with d.get('key', 'default')?",
                options = listOf(
                    "Raises KeyError",
                    "Returns 'default'",
                    "Inserts the key into the dictionary",
                    "Returns None always"
                ),
                correctIndex = 1,
                explanation = ".get() returns the fallback default value without raising an exception."
            )
        ),
        Lesson(
            id = "l3_5",
            levelId = 3,
            orderNumber = 5,
            title = "Dictionary Iteration & Methods",
            subtitle = "keys(), values(), items(), and dict comprehension",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Dictionary Methods & Iteration

### Core Methods:
- `.keys()`: Returns view of all keys.
- `.values()`: Returns view of all values.
- `.items()`: Returns view of `(key, value)` pairs for clean iteration.
- `.update(other_dict)`: Merges another dict.
- `.pop(key)`: Removes key and returns its value.
            """.trimIndent(),
            syntax = "for k, v in d.items():\n    pass",
            codeExample = """# Iterating over key-value pairs
inventory = {"apples": 10, "oranges": 5, "pears": 8}

for fruit, count in inventory.items():
    print(f"{fruit}: {count} in stock")
""",
            expectedOutput = "apples: 10 in stock\noranges: 5 in stock\npears: 8 in stock",
            commonMistakes = listOf(
                "Iterating directly over dict (for x in d) yields only keys, not (key, value) pairs.",
                "Modifying dictionary keys while iterating directly over the dictionary."
            ),
            keyTakeaways = listOf(
                "Use .items() with tuple unpacking for clean key-value iteration.",
                ".values() allows calculating sums, averages, or min/max on values."
            ),
            practiceTask = PracticeTask(
                title = "Sum Inventory Values",
                description = "Given prices = {'a': 10, 'b': 20, 'c': 30}, calculate the sum of values and print the total.",
                starterCode = "prices = {'a': 10, 'b': 20, 'c': 30}\n# Sum values\n",
                expectedOutput = "60",
                solutionCode = "prices = {'a': 10, 'b': 20, 'c': 30}\ntotal = sum(prices.values())\nprint(total)",
                hint = "Use sum(prices.values())."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which dictionary method returns pairs of (key, value) for looping?",
                options = listOf(".pairs()", ".items()", ".entries()", ".all()"),
                correctIndex = 1,
                explanation = "d.items() yields (key, value) tuples."
            )
        ),
        Lesson(
            id = "l3_6",
            levelId = 3,
            orderNumber = 6,
            title = "Sets & Mathematical Set Operations",
            subtitle = "Unique elements, union, intersection, difference, and membership testing",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Python Sets (set)

A `set` is an unordered collection of **unique**, hashable elements defined using curly braces `{}` or `set()`.

### Set Properties & Operations:
- Duplicates are automatically discarded.
- Fast `O(1)` membership testing with `in`.
- **Union (`|`)**: All elements from both sets.
- **Intersection (`&`)**: Elements common to both sets.
- **Difference (`-`)**: Elements in set A but not set B.
            """.trimIndent(),
            syntax = "s = {1, 2, 3}\ns1 | s2  # union\ns1 & s2  # intersection",
            codeExample = """# Set operations
a = {1, 2, 3, 4}
b = {3, 4, 5, 6}

print(f"Union: {a | b}")
print(f"Intersection: {a & b}")
print(f"Difference: {a - b}")
""",
            expectedOutput = "Union: {1, 2, 3, 4, 5, 6}\nIntersection: {3, 4}\nDifference: {1, 2}",
            commonMistakes = listOf(
                "Using {} to create an empty set (that creates an empty dict; use set() for empty sets).",
                "Attempting to index a set like s[0] (sets are unordered and unindexed)."
            ),
            keyTakeaways = listOf(
                "Sets automatically deduplicate collections.",
                "Membership checking ('x in set') is O(1) compared to O(N) for lists.",
                "Mathematical set operators: | (union), & (intersection), - (difference)."
            ),
            practiceTask = PracticeTask(
                title = "Deduplicate and Intersect",
                description = "Given s1 = {1, 2, 3} and s2 = {2, 3, 4}, print their intersection.",
                starterCode = "s1 = {1, 2, 3}\ns2 = {2, 3, 4}\n# Intersection\n",
                expectedOutput = "{2, 3}",
                solutionCode = "s1 = {1, 2, 3}\ns2 = {2, 3, 4}\nprint(s1 & s2)",
                hint = "Use s1 & s2."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "How do you instantiate an empty set in Python?",
                options = listOf("{}", "set()", "[]", "empty_set()"),
                correctIndex = 1,
                explanation = "{} creates an empty dictionary; set() creates an empty set."
            )
        ),
        Lesson(
            id = "l3_7",
            levelId = 3,
            orderNumber = 7,
            title = "Nested Data Structures",
            subtitle = "Lists of dicts, matrix grids, and JSON-like structures",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Nested Data Structures

In real-world applications, data is organized hierarchically:
- Lists of dictionaries (representing database rows or API responses).
- Dictionaries of lists (representing tags, categories, or relationships).
- 2D Matrix lists (`grid[row][col]`).
            """.trimIndent(),
            syntax = "users = [{'id': 1, 'name': 'A'}, {'id': 2, 'name': 'B'}]",
            codeExample = """# Processing nested structures
students = [
    {"name": "Alice", "score": 95},
    {"name": "Bob", "score": 82},
    {"name": "Charlie", "score": 88}
]

for s in students:
    if s["score"] >= 90:
        print(f"Top Student: {s['name']} with {s['score']}")
""",
            expectedOutput = "Top Student: Alice with 95",
            commonMistakes = listOf(
                "Confusing indexing orders when traversing nested lists and dicts.",
                "Mutating a nested list in one place and not realizing shared references."
            ),
            keyTakeaways = listOf(
                "Nested structures mirror JSON payloads from web services.",
                "Chain brackets for deep traversal: `data[0]['scores'][1]`."
            ),
            practiceTask = PracticeTask(
                title = "Extract Nested Data",
                description = "Given data = [{'name': 'Dev', 'skills': ['Python', 'SQL']}], print the first skill of the user.",
                starterCode = "data = [{'name': 'Dev', 'skills': ['Python', 'SQL']}]\n# Print skill\n",
                expectedOutput = "Python",
                solutionCode = "data = [{'name': 'Dev', 'skills': ['Python', 'SQL']}]\nprint(data[0]['skills'][0])",
                hint = "Access data[0]['skills'][0]."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "How do you access 'val' in matrix = [[1, 2], ['val', 4]]?",
                options = listOf("matrix[0][1]", "matrix[1][0]", "matrix[1][1]", "matrix[0][0]"),
                correctIndex = 1,
                explanation = "Row 1 (second sublist), Col 0 (first item in that sublist) is 'val'."
            )
        ),
        Lesson(
            id = "l3_8",
            levelId = 3,
            orderNumber = 8,
            title = "Choosing the Right Data Structure",
            subtitle = "Time complexity trade-offs: List vs Tuple vs Dict vs Set",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Selecting the Optimal Structure

| Structure | Syntax | Mutable? | Ordered? | Lookup by Key/Value | When to Use |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **List** | `[1, 2]` | Yes | Yes | `O(N)` | Ordered sequences, frequently updated |
| **Tuple** | `(1, 2)` | No | Yes | `O(N)` | Fixed records, function returns, dict keys |
| **Dict** | `{'a': 1}` | Yes | Yes (3.7+) | `O(1)` | Key-value associations, fast lookups |
| **Set** | `{1, 2}` | Yes | No | `O(1)` | Uniqueness, fast membership checks, math ops |
            """.trimIndent(),
            syntax = "# Choosing based on complexity and mutability requirements",
            codeExample = """# Demonstrating fast membership with Set vs List
valid_ids = {101, 102, 103, 104}

check_id = 103
if check_id in valid_ids:
    print(f"ID {check_id} is verified (O(1) check).")
""",
            expectedOutput = "ID 103 is verified (O(1) check).",
            commonMistakes = listOf(
                "Using lists for millions of 'item in list' checks instead of sets (O(N) vs O(1))."
            ),
            keyTakeaways = listOf(
                "Lists for ordered sequences.",
                "Tuples for immutable records.",
                "Dicts for mappings.",
                "Sets for uniqueness and O(1) membership checks."
            ),
            practiceTask = PracticeTask(
                title = "Remove Duplicates with Set",
                description = "Given raw_list = [1, 2, 2, 3, 4, 4], convert to set to deduplicate, and print the set.",
                starterCode = "raw_list = [1, 2, 2, 3, 4, 4]\n# Deduplicate and print\n",
                expectedOutput = "{1, 2, 3, 4}",
                solutionCode = "raw_list = [1, 2, 2, 3, 4, 4]\nprint(set(raw_list))",
                hint = "Use set(raw_list)."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which data structure provides average O(1) time complexity for membership testing ('item in container')?",
                options = listOf("List", "Tuple", "Set", "String"),
                correctIndex = 2,
                explanation = "Sets use hash tables, allowing O(1) average lookup and membership checks."
            )
        ),
        Lesson(
            id = "l3_9",
            levelId = 3,
            orderNumber = 9,
            title = "Level 3 Mastery Review & Summary",
            subtitle = "Solidifying core collection types in Python",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 3 Milestone: Data Structures Mastered!

You now possess command of Python's primary data structures:
- Lists (indexing, slicing, mutation, methods)
- Tuples (immutability, unpacking, records)
- Dictionaries (key-value hash maps, fast lookup, `.items()`)
- Sets (uniqueness, union, intersection, O(1) lookups)
- Nested collections (APIs, tables, JSON trees)

In **Level 4**, we master reusable execution units: **Functions, Arguments, Scope, and Lambdas**!
            """.trimIndent(),
            syntax = "# Level 3 Complete",
            codeExample = """# Complete data summary
summary = {
    "level": 3,
    "topic": "Data Structures",
    "structures": ["list", "tuple", "dict", "set"],
    "ready": True
}
print(f"{summary['topic']} Completed: {len(summary['structures'])} core structures mastered!")
""",
            expectedOutput = "Data Structures Completed: 4 core structures mastered!",
            commonMistakes = listOf(
                "Forgetting that dicts and sets cannot hold unhashable mutable objects as keys."
            ),
            keyTakeaways = listOf(
                "Data structures form the memory backbone of Python programs.",
                "Take the Level 3 Quiz to claim your XP!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 3 Complete",
                description = "Print 'Level 3 Complete! Onward to Functions.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 3 Complete! Onward to Functions.",
                solutionCode = "print(\"Level 3 Complete! Onward to Functions.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which collection is immutable?",
                options = listOf("List", "Dictionary", "Tuple", "Set"),
                correctIndex = 2,
                explanation = "Tuples cannot be modified after creation (immutable)."
            )
        )
    )
}
