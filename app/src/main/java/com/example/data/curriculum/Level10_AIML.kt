package com.example.data.curriculum

import com.example.data.model.Lesson
import com.example.data.model.MiniQuizQuestion
import com.example.data.model.PracticeTask

object Level10_AIML {
    val lessons = listOf(
        Lesson(
            id = "l10_1",
            levelId = 10,
            orderNumber = 1,
            title = "Python's Central Role in Modern AI & ML",
            subtitle = "The ecosystem: NumPy, Pandas, Scikit-Learn, PyTorch, and LLMs",
            estimatedMinutes = 10,
            xpReward = 50,
            conceptExplanation = """
# Python: The Lingua Franca of Artificial Intelligence

Why has Python become the undisputed foundation of AI and Data Science?
1. **Expressive, Minimalist Syntax**: Lets researchers focus on math and model architecture rather than boilerplate.
2. **C/C++ & GPU Acceleration**: Python binds directly to high-speed optimized C/CUDA engines (NumPy, PyTorch, TensorFlow).
3. **Rich Ecosystem**:
   - **NumPy**: N-dimensional array vectorization.
   - **Pandas**: Tabular DataFrame manipulation.
   - **Scikit-Learn**: Classical machine learning algorithms.
   - **PyTorch / TensorFlow**: Deep learning neural network tensors.
   - **Transformers & Gemini**: Large language models and generative AI.
            """.trimIndent(),
            syntax = "# AI/ML Stack in Python\n# NumPy -> Pandas -> Scikit-Learn -> PyTorch -> LLMs",
            codeExample = """# High-level AI/ML ecosystem map
ai_stack = [
    ("NumPy", "Tensors & Vectorized Math"),
    ("Pandas", "Tabular Data & Features"),
    ("Scikit-Learn", "Regression, Trees & Clustering"),
    ("PyTorch", "Deep Learning & Neural Networks"),
    ("Gemini API", "Generative AI & Multimodal LLMs")
]

for lib, purpose in ai_stack:
    print(f"[{lib}] -> {purpose}")
""",
            expectedOutput = "[NumPy] -> Tensors & Vectorized Math\n[Pandas] -> Tabular Data & Features\n[Scikit-Learn] -> Regression, Trees & Clustering\n[PyTorch] -> Deep Learning & Neural Networks\n[Gemini API] -> Generative AI & Multimodal LLMs",
            commonMistakes = listOf(
                "Trying to use pure Python nested loops for multi-gigabyte matrix multiplications instead of vectorized tensor operations."
            ),
            keyTakeaways = listOf(
                "Python serves as the high-level orchestration interface for GPU-accelerated computing.",
                "Vectorization replaces slow iterative loops with SIMD operations."
            ),
            practiceTask = PracticeTask(
                title = "Print AI Stack",
                description = "Print 'NumPy' on line 1, 'Pandas' on line 2, and 'PyTorch' on line 3.",
                starterCode = "# Print libraries\n",
                expectedOutput = "NumPy\nPandas\nPyTorch",
                solutionCode = "print(\"NumPy\")\nprint(\"Pandas\")\nprint(\"PyTorch\")",
                hint = "Use print() for each library name."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "Why is Python so dominant in Machine Learning?",
                options = listOf(
                    "It has no types at all",
                    "Clean syntax backed by high-performance C/CUDA accelerated tensor libraries",
                    "It is the only language that runs on GPUs",
                    "It requires no installation"
                ),
                correctIndex = 1,
                explanation = "Python combines an intuitive interface with lightning-fast compiled C/CUDA backends."
            )
        ),
        Lesson(
            id = "l10_2",
            levelId = 10,
            orderNumber = 2,
            title = "NumPy & Vectorization Fundamentals",
            subtitle = "High-performance arrays, broadcasting, and element-wise math",
            estimatedMinutes = 12,
            xpReward = 50,
            conceptExplanation = """
# NumPy & Vectorization

NumPy (Numerical Python) is the foundation of scientific computing.

### Why NumPy Arrays Over Python Lists?
- **Contiguous Memory**: Stored in contiguous memory blocks for cache locality.
- **Vectorization**: Operations like `arr * 2` apply to all elements simultaneously in C without slow Python loop overhead.
- **Broadcasting**: Performing arithmetic between arrays of differing compatible shapes.
            """.trimIndent(),
            syntax = "import numpy as np\narr = np.array([1, 2, 3])\nresult = arr * 2",
            codeExample = """# Vectorized math concept
# Vector addition concept:
v1 = [1.0, 2.0, 3.0]
v2 = [4.0, 5.0, 6.0]

# Element-wise addition
v_sum = [a + b for a, b in zip(v1, v2)]

# Dot product: sum(a * b)
dot_product = sum(a * b for a, b in zip(v1, v2))

print(f"v1 + v2 = {v_sum}")
print(f"Dot Product (v1 . v2) = {dot_product}")
""",
            expectedOutput = "v1 + v2 = [5.0, 7.0, 9.0]\nDot Product (v1 . v2) = 32.0",
            commonMistakes = listOf(
                "Using list multiplication ([1, 2] * 2 duplicates the list to [1, 2, 1, 2]; NumPy array * 2 multiplies each element)."
            ),
            keyTakeaways = listOf(
                "Vectorization executes parallel mathematical operations.",
                "Dot products and matrix multiplications form the basis of neural network layers."
            ),
            practiceTask = PracticeTask(
                title = "Compute Vector Dot Product",
                description = "Given a = [2, 3] and b = [4, 5], compute dot_product = sum(x * y for x, y in zip(a, b)) and print it.",
                starterCode = "a = [2, 3]\nb = [4, 5]\n# Compute dot product\n",
                expectedOutput = "23",
                solutionCode = "a = [2, 3]\nb = [4, 5]\ndot = a[0]*b[0] + a[1]*b[1]\nprint(dot)",
                hint = "Calculate (2 * 4) + (3 * 5)."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What does [1, 2, 3] * 2 do in pure Python vs a NumPy array?",
                options = listOf(
                    "Both multiply elements to [2, 4, 6]",
                    "Python list duplicates to [1, 2, 3, 1, 2, 3]; NumPy array multiplies to [2, 4, 6]",
                    "Both duplicate the list",
                    "Raises a TypeError in Python"
                ),
                correctIndex = 1,
                explanation = "Python lists repeat elements on *, while NumPy arrays perform element-wise arithmetic."
            )
        ),
        Lesson(
            id = "l10_3",
            levelId = 10,
            orderNumber = 3,
            title = "Linear Regression & Machine Learning Mental Model",
            subtitle = "Supervised learning, features, labels, and gradient descent",
            estimatedMinutes = 15,
            xpReward = 100,
            conceptExplanation = """
# Machine Learning Foundations

Machine Learning algorithms learn patterns from data rather than following explicitly hardcoded rules.

### Core Concepts:
- **Features (X)**: The input measurements or attributes (e.g. square footage, bedrooms).
- **Labels (y)**: The target ground truth to predict (e.g. house price).
- **Linear Regression Equation**:
  y = mx + b (or y = w · x + b)
  Where w is the weight (slope) and b is the bias (intercept).
- **Loss Function**: Measures prediction error (e.g. Mean Squared Error).
            """.trimIndent(),
            syntax = "y_pred = weight * x + bias\nloss = (y_pred - y_true) ** 2",
            codeExample = """# Linear Regression Model Prediction in Pure Python
class SimpleLinearRegressor:
    def __init__(self, weight, bias):
        self.w = weight
        self.b = bias

    def predict(self, x):
        return (self.w * x) + self.b

# Model trained to estimate house prices in thousands based on size (100s sq ft)
# Model: price = 50 * size + 20
model = SimpleLinearRegressor(weight=50, bias=20)

sample_sizes = [10, 15, 20] # 1000, 1500, 2000 sq ft
for size in sample_sizes:
    price = model.predict(size)
    print(f"Predicted price for size {size}: " + "$" + f"{price}k")
""",
            expectedOutput = "Predicted price for size 10: $520k\nPredicted price for size 15: $770k\nPredicted price for size 20: $1020k",
            commonMistakes = listOf(
                "Training and testing models on the exact same dataset without a train/test split (leads to overfitting)."
            ),
            keyTakeaways = listOf(
                "Supervised learning predicts labels from input features.",
                "Linear regression computes weighted combinations of inputs.",
                "Optimization algorithms (like Gradient Descent) adjust weights to minimize loss."
            ),
            practiceTask = PracticeTask(
                title = "Predict with Linear Formula",
                description = "Given w = 3, b = 4, x = 5. Compute y = (w * x) + b and print y.",
                starterCode = "w = 3\nb = 4\nx = 5\n# Compute prediction\n",
                expectedOutput = "19",
                solutionCode = "w = 3\nb = 4\nx = 5\ny = (w * x) + b\nprint(y)",
                hint = "y = (w * x) + b"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "In the formula y = wx + b, what do 'w' and 'b' represent?",
                options = listOf(
                    "Weight and Bias (model parameters)",
                    "Width and Base",
                    "Warning and Bug",
                    "Window and Buffer"
                ),
                correctIndex = 0,
                explanation = "w is the weight (slope) and b is the bias (intercept) of the linear model."
            )
        ),
        Lesson(
            id = "l10_4",
            levelId = 10,
            orderNumber = 4,
            title = "Neural Networks & Artificial Neurons from Scratch",
            subtitle = "Weights, biases, activation functions (ReLU, Sigmoid), and forward pass",
            estimatedMinutes = 15,
            xpReward = 100,
            conceptExplanation = """
# Artificial Neural Networks

An artificial neuron (Perceptron) calculates a weighted sum of its inputs, adds a bias, and passes the result through a non-linear **activation function**.

### Activation Functions:
- **ReLU (Rectified Linear Unit)**: $\max(0, z)$ — standard for hidden layers.
- **Sigmoid**: $\frac{1}{1 + e^{-z}}$ — squashes output between 0 and 1 (probabilities).
            """.trimIndent(),
            syntax = "def relu(z):\n    return max(0, z)",
            codeExample = """# Artificial Neuron Forward Pass in Python
def relu(z):
    return max(0, z)

inputs = [0.5, 0.8, -0.2]
weights = [1.2, 0.4, 0.9]
bias = 0.1

# Weighted sum: z = (x1*w1 + x2*w2 + x3*w3) + b
z = sum(x * w for x, w in zip(inputs, weights)) + bias
output = relu(z)

print(f"Weighted Sum (z): {round(z, 2)}")
print(f"Neuron Output (ReLU): {round(output, 2)}")
""",
            expectedOutput = "Weighted Sum (z): 0.84\nNeuron Output (ReLU): 0.84",
            commonMistakes = listOf(
                "Omitting activation functions (without non-linear activations, deep neural networks collapse into a single linear regression)."
            ),
            keyTakeaways = listOf(
                "Neurons compute: output = activation(weights . inputs + bias).",
                "Non-linear activations allow networks to learn complex decision boundaries.",
                "Deep networks stack multiple layers of neurons."
            ),
            practiceTask = PracticeTask(
                title = "Compute ReLU Activation",
                description = "Define a function relu(z) that returns max(0, z). Test with relu(-3.5) and relu(4.2) and print both.",
                starterCode = "# Define and test relu\n",
                expectedOutput = "0\n4.2",
                solutionCode = "def relu(z):\n    return max(0, z)\n\nprint(relu(-3.5))\nprint(relu(4.2))",
                hint = "def relu(z): return max(0, z)"
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the output of the ReLU activation function for an input of -5.0?",
                options = listOf("-5.0", "0", "1.0", "-1.0"),
                correctIndex = 1,
                explanation = "ReLU returns max(0, x), so any negative number produces 0."
            )
        ),
        Lesson(
            id = "l10_5",
            levelId = 10,
            orderNumber = 5,
            title = "Course Graduation & Certificate Milestone",
            subtitle = "You are now equipped from fundamentals to AI engineering!",
            estimatedMinutes = 10,
            xpReward = 150,
            conceptExplanation = """
# Congratulations Graduate!

You have completed the full 10-Level Python Mastery Academy Curriculum:
1. **Python Fundamentals & Syntax**
2. **Control Flow, Conditionals & Loops**
3. **Data Structures (Lists, Tuples, Dicts, Sets)**
4. **Functions, Scope, *args/**kwargs & Recursion**
5. **Modules, Packages & File I/O (JSON/CSV)**
6. **Error & Exception Handling Invariants**
7. **Object-Oriented Programming (OOP & Dunder Methods)**
8. **Advanced Python, Generators, Iterators & Decorators**
9. **Real-World Portfolio Projects & Data Pipelines**
10. **Python for Artificial Intelligence & Machine Learning**

You are now eligible to claim and verify your **Official Graduation Certificate**!
            """.trimIndent(),
            syntax = "# Python Mastery Complete\nprint('Python Master Developer!')",
            codeExample = """# Final graduation message
academy = "Python Mastery Academy"
status = "Graduation Complete"
skills = ["Core", "OOP", "Data Structures", "APIs", "AI/ML"]

print(f"Academy: {academy}")
print(f"Status: {status}")
print(f"Certified in: {', '.join(skills)}")
""",
            expectedOutput = "Academy: Python Mastery Academy\nStatus: Graduation Complete\nCertified in: Core, OOP, Data Structures, APIs, AI/ML",
            commonMistakes = listOf(
                "Stopping learning (continue building real-world software and exploring open-source projects!)."
            ),
            keyTakeaways = listOf(
                "You have comprehensive mastery of Python across all 10 core levels.",
                "Visit the Certificate tab to view, print, or share your graduation certificate!"
            ),
            practiceTask = PracticeTask(
                title = "Print Graduation Statement",
                description = "Print 'I am a Certified Python Master Developer!'",
                starterCode = "# Print statement\n",
                expectedOutput = "I am a Certified Python Master Developer!",
                solutionCode = "print(\"I am a Certified Python Master Developer!\")",
                hint = "Use print() with the exact text."
            ),
            miniQuiz = MiniQuizQuestion(
                question = "What is the best way to maintain and grow your new Python programming mastery?",
                options = listOf(
                    "Never write code again",
                    "Build projects, solve coding challenges, and explore AI frameworks",
                    "Delete your development environment",
                    "Memorize syntax without running it"
                ),
                correctIndex = 1,
                explanation = "Building real projects and continuous practice solidifies mastery."
            )
        )
    )
}
