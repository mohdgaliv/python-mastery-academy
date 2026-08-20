package com.example.data.curriculum

import com.example.data.model.ChallengeDifficulty
import com.example.data.model.ChallengeTestCase
import com.example.data.model.CodingChallenge

object ChallengesData {
    val challenges = listOf(
        CodingChallenge(
            id = "c_1",
            title = "Two Sum Target",
            topic = "Algorithms & Dictionaries",
            difficulty = ChallengeDifficulty.EASY,
            xpReward = 100,
            description = "Write a function `two_sum(nums, target)` that returns the indices of the two numbers in `nums` that add up to `target` as a list `[idx1, idx2]`. Each input has exactly one solution.",
            requirements = listOf(
                "Accept a list of integers `nums` and integer `target`.",
                "Return a list containing the two 0-based indices.",
                "Optimal solution uses a hash map in O(N) time."
            ),
            starterCode = """def two_sum(nums, target):
    # Your code here
    seen = {}
    for i, n in enumerate(nums):
        diff = target - n
        if diff in seen:
            return [seen[diff], i]
        seen[n] = i
    return []

# Test call
print(two_sum([2, 7, 11, 15], 9))
""",
            testCases = listOf(
                ChallengeTestCase("nums=[2, 7, 11, 15], target=9", "[0, 1]", "print(two_sum([2, 7, 11, 15], 9))"),
                ChallengeTestCase("nums=[3, 2, 4], target=6", "[1, 2]", "print(two_sum([3, 2, 4], 6))")
            ),
            hints = listOf(
                "Can you use a dictionary to store previously seen numbers and their indices?",
                "For each number n, check if (target - n) exists in the dictionary."
            ),
            solutionCode = """def two_sum(nums, target):
    seen = {}
    for i, n in enumerate(nums):
        diff = target - n
        if diff in seen:
            return [seen[diff], i]
        seen[n] = i
    return []
""",
            explanation = "By caching seen values in a dictionary, we find complements in O(1) time per element for O(N) total time."
        ),
        CodingChallenge(
            id = "c_2",
            title = "Reverse String In-Place",
            topic = "Strings & Slicing",
            difficulty = ChallengeDifficulty.EASY,
            xpReward = 75,
            description = "Write a function `reverse_string(s)` that returns the reversed version of the string `s`.",
            requirements = listOf(
                "Accept a string `s`.",
                "Return the reversed string."
            ),
            starterCode = """def reverse_string(s):
    # Your code here
    pass

print(reverse_string("python"))
""",
            testCases = listOf(
                ChallengeTestCase("s='python'", "nohtyp", "print(reverse_string('python'))"),
                ChallengeTestCase("s='academy'", "ymedaca", "print(reverse_string('academy'))")
            ),
            hints = listOf(
                "Consider Python's slice step syntax `s[::-1]`.",
                "Or iterate backwards with a loop."
            ),
            solutionCode = """def reverse_string(s):
    return s[::-1]
""",
            explanation = "s[::-1] steps backward through the entire string in O(N) time."
        ),
        CodingChallenge(
            id = "c_3",
            title = "Palindrome Checker",
            topic = "Strings & Logic",
            difficulty = ChallengeDifficulty.EASY,
            xpReward = 75,
            description = "Write a function `is_palindrome(s)` that returns `True` if `s` is a palindrome (reads same forwards and backwards, case-insensitive), otherwise `False`.",
            requirements = listOf(
                "Case-insensitive comparison.",
                "Return True or False."
            ),
            starterCode = """def is_palindrome(s):
    # Your code here
    cleaned = s.lower()
    return cleaned == cleaned[::-1]

print(is_palindrome("Radar"))
print(is_palindrome("Python"))
""",
            testCases = listOf(
                ChallengeTestCase("s='Radar'", "True", "print(is_palindrome('Radar'))"),
                ChallengeTestCase("s='Python'", "False", "print(is_palindrome('Python'))")
            ),
            hints = listOf(
                "Convert the string to lowercase first using .lower().",
                "Compare with the reversed string."
            ),
            solutionCode = """def is_palindrome(s):
    cleaned = s.lower()
    return cleaned == cleaned[::-1]
""",
            explanation = "Converting to lowercase and checking equality with its slice reverse efficiently solves palindrome detection."
        ),
        CodingChallenge(
            id = "c_4",
            title = "Fibonacci Generator",
            topic = "Recursion & Loops",
            difficulty = ChallengeDifficulty.MEDIUM,
            xpReward = 125,
            description = "Write a function `fibonacci(n)` that returns the n-th Fibonacci number where `fib(0) = 0`, `fib(1) = 1`, and `fib(n) = fib(n-1) + fib(n-2)`.",
            requirements = listOf(
                "Accept non-negative integer n.",
                "Return the n-th Fibonacci number."
            ),
            starterCode = """def fibonacci(n):
    # Your code here
    if n <= 0:
        return 0
    if n == 1:
        return 1
    a, b = 0, 1
    for _ in range(2, n + 1):
        a, b = b, a + b
    return b

print(fibonacci(7))
""",
            testCases = listOf(
                ChallengeTestCase("n=7", "13", "print(fibonacci(7))"),
                ChallengeTestCase("n=10", "55", "print(fibonacci(10))")
            ),
            hints = listOf(
                "Iterative dynamic programming with two variables runs in O(N) time and O(1) space.",
                "Keep track of previous two values a and b."
            ),
            solutionCode = """def fibonacci(n):
    if n <= 0: return 0
    if n == 1: return 1
    a, b = 0, 1
    for _ in range(2, n + 1):
        a, b = b, a + b
    return b
""",
            explanation = "Iterative accumulation calculates the n-th Fibonacci number in linear time without stack recursion overhead."
        ),
        CodingChallenge(
            id = "c_5",
            title = "Find All Duplicates in List",
            topic = "Sets & Data Structures",
            difficulty = ChallengeDifficulty.MEDIUM,
            xpReward = 125,
            description = "Write a function `find_duplicates(nums)` that returns a sorted list of all numbers that appear more than once in `nums`.",
            requirements = listOf(
                "Accept a list of integers.",
                "Return a sorted list of unique duplicate values."
            ),
            starterCode = """def find_duplicates(nums):
    # Your code here
    seen = set()
    duplicates = set()
    for n in nums:
        if n in seen:
            duplicates.add(n)
        else:
            seen.add(n)
    return sorted(list(duplicates))

print(find_duplicates([4, 3, 2, 7, 8, 2, 3, 1]))
""",
            testCases = listOf(
                ChallengeTestCase("nums=[4,3,2,7,8,2,3,1]", "[2, 3]", "print(find_duplicates([4, 3, 2, 7, 8, 2, 3, 1]))"),
                ChallengeTestCase("nums=[1, 1, 2]", "[1]", "print(find_duplicates([1, 1, 2]))")
            ),
            hints = listOf(
                "Use a set to track elements seen so far.",
                "When an element is already in the set, add it to the duplicates set."
            ),
            solutionCode = """def find_duplicates(nums):
    seen = set()
    duplicates = set()
    for n in nums:
        if n in seen:
            duplicates.add(n)
        else:
            seen.add(n)
    return sorted(list(duplicates))
""",
            explanation = "Two sets give O(N) scanning and deduplication with sorted() output."
        ),
        CodingChallenge(
            id = "c_6",
            title = "Valid Parentheses Syntax Checker",
            topic = "Stacks & Algorithms",
            difficulty = ChallengeDifficulty.HARD,
            xpReward = 175,
            description = "Write a function `is_valid_brackets(s)` that determines if the input string containing brackets `'()', '{}', '[]'` is valid. Brackets must be closed in the correct order.",
            requirements = listOf(
                "Open brackets must be closed by the same type of brackets.",
                "Open brackets must be closed in the correct order.",
                "Return True or False."
            ),
            starterCode = """def is_valid_brackets(s):
    # Your code here
    stack = []
    mapping = {")": "(", "}": "{", "]": "["}
    for char in s:
        if char in mapping:
            top = stack.pop() if stack else '#'
            if mapping[char] != top:
                return False
        else:
            stack.append(char)
    return len(stack) == 0

print(is_valid_brackets("()[]{}"))
print(is_valid_brackets("(]"))
""",
            testCases = listOf(
                ChallengeTestCase("s='()[]{}'", "True", "print(is_valid_brackets('()[]{}'))"),
                ChallengeTestCase("s='([{}])'", "True", "print(is_valid_brackets('([{}])'))"),
                ChallengeTestCase("s='(]'", "False", "print(is_valid_brackets('(]'))")
            ),
            hints = listOf(
                "Use a stack (list in Python) to push open brackets.",
                "When a closing bracket is found, pop from the stack and verify matching type."
            ),
            solutionCode = """def is_valid_brackets(s):
    stack = []
    mapping = {")": "(", "}": "{", "]": "["}
    for char in s:
        if char in mapping:
            top = stack.pop() if stack else '#'
            if mapping[char] != top:
                return False
        else:
            stack.append(char)
    return len(stack) == 0
""",
            explanation = "Using a LIFO stack allows validating nested syntactic structure in O(N) time."
        )
    )

    fun getChallengeById(id: String): CodingChallenge? {
        return challenges.firstOrNull { it.id == id }
    }
}
