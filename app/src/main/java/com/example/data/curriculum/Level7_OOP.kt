package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level7_OOP {
    val lessons = listOf(
        Lesson(
            id = "l7_1",
            levelId = 7,
            orderNumber = 1,
            title = "OOP Mental Model: Classes, Objects & __init__",
            subtitle = "Modeling real-world entities with state and behavior",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Object-Oriented Programming (OOP) in Python

OOP is a programming paradigm that organizes software design around **objects** rather than functions and logic.

### Core Concepts:
- **Class**: The blueprint or template for creating objects.
- **Object / Instance**: A concrete instance of a class in memory.
- **`__init__`**: The constructor / initializer method that runs when a new instance is created.
- **`self`**: The explicit reference to the current object instance.
            """.trimIndent(),
            syntax = "class ClassName:\n    def __init__(self, arg):\n        self.arg = arg",
            codeExample = """# Defining a Class and instantiating Objects
class Learner:
    def __init__(self, name, track):
        self.name = name
        self.track = track
        self.xp = 0

    def earn_xp(self, amount):
        self.xp += amount
        return f"{self.name} earned {amount} XP! Total: {self.xp}"

# Create instance
alex = Learner("Alex", "Python Mastery")
print(alex.earn_xp(100))
print(alex.earn_xp(50))
""",
            expectedOutput = "Alex earned 100 XP! Total: 100\nAlex earned 50 XP! Total: 150",
            commonMistakes = listOf(
                "Forgetting 'self' as the first parameter of instance methods.",
                "Using class variables when instance attributes (self.x) were intended."
            ),
            keyTakeaways = listOf(
                "Classes bundle data (attributes) and behavior (methods).",
                "__init__ initializes new instance state.",
                "self refers to the specific instance calling the method."
            ),
            practiceTask = PracticeTask(
                title = "Create a Book Class",
                description = "Define a class Book with __init__(self, title, pages) and a method info(self) that returns f\"{self.title} ({self.pages} pages)\". Instantiate with 'Python Mastery', 300 and print info().",
                starterCode = "# Define Book class\n",
                expectedOutput = "Python Mastery (300 pages)",
                solutionCode = "class Book:\n    def __init__(self, title, pages):\n        self.title = title\n        self.pages = pages\n    def info(self):\n        return f\"{self.title} ({self.pages} pages)\"\n\nb = Book(\"Python Mastery\", 300)\nprint(b.info())",
                hint = "Define class Book with __init__ and info."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does the 'self' parameter represent inside an instance method?",
                options = listOf(
                    "The global class definition",
                    "The current instance of the class",
                    "A copy of the Python interpreter",
                    "A static reference to the module"
                ),
                correctIndex = 1,
                explanation = "'self' is the explicit reference to the instance of the class on which the method is called."
            )
        ),
        Lesson(
            id = "l7_2",
            levelId = 7,
            orderNumber = 2,
            title = "Inheritance & Method Overriding",
            subtitle = "Reusing code across class hierarchies with super()",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Inheritance & super()

Inheritance allows a **child (subclass)** to inherit all attributes and methods from a **parent (superclass)**.

### Subclassing Syntax:
`class Subclass(ParentClass):`

### Method Overriding & super():
The child class can override a parent method to provide specialized behavior, while calling `super().method()` to retain the base behavior.
            """.trimIndent(),
            syntax = "class Child(Parent):\n    def __init__(self, arg1, arg2):\n        super().__init__(arg1)\n        self.arg2 = arg2",
            codeExample = """# Inheritance in action
class User:
    def __init__(self, username):
        self.username = username

    def get_role(self):
        return "Standard User"

class Admin(User):
    def get_role(self):
        return "Administrator (Elevated Privileges)"

admin = Admin("root_user")
print(f"{admin.username}: {admin.get_role()}")
""",
            expectedOutput = "root_user: Administrator (Elevated Privileges)",
            commonMistakes = listOf(
                "Forgetting to call super().__init__() in child class constructors.",
                "Deep, brittle inheritance trees (prefer composition when possible)."
            ),
            keyTakeaways = listOf(
                "Inheritance fosters code reuse and specialization.",
                "super() provides access to parent class methods.",
                "isinstance(obj, Class) checks class and inheritance membership."
            ),
            practiceTask = PracticeTask(
                title = "Create a Subclass",
                description = "Define class Animal with method speak(self) returning 'Noise'. Define class Dog(Animal) overriding speak(self) to return 'Bark'. Print Dog().speak().",
                starterCode = "# Define classes\n",
                expectedOutput = "Bark",
                solutionCode = "class Animal:\n    def speak(self):\n        return \"Noise\"\n\nclass Dog(Animal):\n    def speak(self):\n        return \"Bark\"\n\nprint(Dog().speak())",
                hint = "class Dog(Animal): def speak(self): return 'Bark'"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "How do you call a parent class method from an overridden child class method?",
                options = listOf("this.method()", "parent.method()", "super().method()", "base.method()"),
                correctIndex = 2,
                explanation = "super() delegates method calls to the parent (super) class."
            )
        ),
        Lesson(
            id = "l7_3",
            levelId = 7,
            orderNumber = 3,
            title = "Encapsulation & Property Decorators",
            subtitle = "Data hiding, private attributes, and @property getters/setters",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Encapsulation & Properties

Encapsulation restricts direct access to internal object state to maintain invariants.

### Conventions:
- `_variable`: Protected convention (intended for internal/subclass use).
- `__variable`: Private (triggers name mangling to prevent accidental overwrites).

### The `@property` Decorator:
Allows accessing methods like regular attributes with full validation on set.
            """.trimIndent(),
            syntax = "class Account:\n    @property\n    def balance(self):\n        return self._balance\n    @balance.setter\n    def balance(self, val):\n        self._balance = val",
            codeExample = """# Encapsulated Bank Account
class BankAccount:
    def __init__(self, owner, initial_deposit):
        self.owner = owner
        self._balance = initial_deposit

    @property
    def balance(self):
        return self._balance

    def deposit(self, amount):
        if amount > 0:
            self._balance += amount
            return "Deposited $" + f"{amount}. New Balance: $" + f"{self._balance}"
        return "Invalid amount."

acc = BankAccount("Jordan", 500)
print(acc.deposit(150))
print("Current Balance: $" + f"{acc.balance}")
""",
            expectedOutput = "Deposited $150. New Balance: $650\nCurrent Balance: $650",
            commonMistakes = listOf(
                "Accessing and mutating private/protected attributes directly instead of using methods/properties."
            ),
            keyTakeaways = listOf(
                "Leading underscore _ denotes protected internal variables.",
                "@property transforms a getter method into attribute syntax.",
                "Setters enable runtime validation before changing values."
            ),
            practiceTask = PracticeTask(
                title = "Create Property Getter",
                description = "Define class Circle with self._radius = 5. Add @property def radius(self): return self._radius. Print Circle().radius.",
                starterCode = "# Define Circle with property\n",
                expectedOutput = "5",
                solutionCode = "class Circle:\n    def __init__(self):\n        self._radius = 5\n    @property\n    def radius(self):\n        return self._radius\n\nprint(Circle().radius)",
                hint = "Use @property above def radius(self):"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does the @property decorator allow in Python?",
                options = listOf(
                    "Encrypts the variable in memory",
                    "Allows calling a method using attribute syntax (obj.attr instead of obj.attr())",
                    "Makes the class static",
                    "Prevents the class from being inherited"
                ),
                correctIndex = 1,
                explanation = "@property allows methods to be accessed as attributes without trailing parentheses."
            )
        ),
        Lesson(
            id = "l7_4",
            levelId = 7,
            orderNumber = 4,
            title = "Special Dunder Methods: __str__, __repr__ & __len__",
            subtitle = "Customizing string representations and Pythonic protocol integration",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# Magic / Dunder (Double Underscore) Methods

Dunder methods allow custom classes to hook into Python's built-in syntax and operators.

### Essential Dunder Methods:
- `__str__(self)`: Human-readable string for `print(obj)` and `str(obj)`.
- `__repr__(self)`: Unambiguous developer representation.
- `__len__(self)`: Hook for `len(obj)`.
- `__eq__(self, other)`: Hook for `==` equality.
- `__add__(self, other)`: Hook for `+` operator.
            """.trimIndent(),
            syntax = "def __str__(self):\n    return f'Human readable'\ndef __len__(self):\n    return count",
            codeExample = """# Implementing dunder methods
class Classroom:
    def __init__(self, name, students):
        self.name = name
        self.students = students

    def __str__(self):
        return f"Classroom: {self.name} ({len(self.students)} students)"

    def __len__(self):
        return len(self.students)

room = Classroom("Python 101", ["Alex", "Sam", "Maya"])
print(str(room))
print(f"Total enrolled: {len(room)}")
""",
            expectedOutput = "Classroom: Python 101 (3 students)\nTotal enrolled: 3",
            commonMistakes = listOf(
                "Returning non-string types from __str__ or __repr__ (raises TypeError).",
                "Returning negative integers or non-ints from __len__."
            ),
            keyTakeaways = listOf(
                "Dunder methods make custom objects feel native in Python.",
                "__str__ is for end-users, __repr__ is for developers.",
                "__len__ integrates your class with len()."
            ),
            practiceTask = PracticeTask(
                title = "Implement __str__",
                description = "Define class Item with self.name = 'Widget'. Add __str__(self) returning f\"Item: {self.name}\". Print str(Item()).",
                starterCode = "# Define Item with __str__\n",
                expectedOutput = "Item: Widget",
                solutionCode = "class Item:\n    def __init__(self):\n        self.name = \"Widget\"\n    def __str__(self):\n        return f\"Item: {self.name}\"\n\nprint(str(Item()))",
                hint = "def __str__(self): return f\"Item: {self.name}\""
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Which dunder method is called when passing an object to print() or str()?",
                options = listOf("__print__", "__str__", "__text__", "__format__"),
                correctIndex = 1,
                explanation = "print() and str() invoke the __str__ dunder method."
            )
        ),
        Lesson(
            id = "l7_5",
            levelId = 7,
            orderNumber = 5,
            title = "Level 7 Mastery Review & Summary",
            subtitle = "Consolidating classes, inheritance, encapsulation, and dunder methods",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Level 7 Milestone: OOP Mastered!

You have mastered the Object-Oriented paradigm in Python:
- Classes, instances, and the `__init__` constructor
- The role of the `self` keyword
- Inheritance hierarchies and `super()`
- Encapsulation & `@property` decorators
- Magic dunder methods (`__str__`, `__len__`, `__repr__`)

In **Level 8**, we explore **Advanced Python: Iterators, Generators, Decorators, and Metaprogramming**!
            """.trimIndent(),
            syntax = "# Level 7 Complete",
            codeExample = """# OOP summary
class Milestone:
    def __init__(self, level, name):
        self.level = level
        self.name = name

    def __str__(self):
        return f"Level {self.level}: {self.name} - 100% Complete!"

m = Milestone(7, "Object-Oriented Programming")
print(str(m))
""",
            expectedOutput = "Level 7: Object-Oriented Programming - 100% Complete!",
            commonMistakes = listOf(
                "Over-engineering simple scripts with unnecessary complex class hierarchies."
            ),
            keyTakeaways = listOf(
                "OOP bundles state and behavior into cohesive objects.",
                "Take the Level 7 Quiz to unlock your XP!"
            ),
            practiceTask = PracticeTask(
                title = "Print Level 7 Complete",
                description = "Print 'Level 7 Complete! Onward to Advanced Metaprogramming.'",
                starterCode = "# Print statement\n",
                expectedOutput = "Level 7 Complete! Onward to Advanced Metaprogramming.",
                solutionCode = "print(\"Level 7 Complete! Onward to Advanced Metaprogramming.\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the primary method used to initialize new instance attributes in Python?",
                options = listOf("__start__", "__construct__", "__init__", "__new__"),
                correctIndex = 2,
                explanation = "__init__ is the initializer method for new object instances."
            )
        )
    )
}
