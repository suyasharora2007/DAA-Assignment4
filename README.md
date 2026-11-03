# DAA Assignment 4
## Dynamic Programming and String Algorithms

**Student:** Suyash Arora  
**University:** Astana IT University  
**Location:** Astana, Kazakhstan  
**Group:** SE-2521  
**Course:** Design and Analysis of Algorithms  
**Assignment:** 4 – Dynamic Programming and String Algorithms

---

## 1. Project Overview

This project implements and experimentally evaluates dynamic programming and string-matching algorithms.

### Edit Distance

Three implementations are provided:

1. Recursive approach without memoization.
2. Bottom-up Dynamic Programming using a 2D table.
3. Space-optimized Dynamic Programming using two rows.

### String Searching

Two string-matching algorithms are implemented:

1. Naive String Search.
2. Knuth-Morris-Pratt (KMP), including its LPS preprocessing function.

The implementations support overlapping matches and handle the required edge cases.

---

## 2. Technology Stack

- Java 17 compatibility
- Apache Maven
- JUnit 5
- Python 3
- Matplotlib
- Git and GitHub

---

## 3. Project Structure

```text
DAA-Assignment4/
│
├── src/
│   ├── main/java/daa/
│   │   ├── Counter.java
│   │   ├── EditDistance.java
│   │   ├── StringSearch.java
│   │   └── ExperimentRunner.java
│   │
│   └── test/java/daa/
│       ├── EditDistanceTest.java
│       └── StringSearchTest.java
│
├── results/
│   ├── results.csv
│   └── plots/
│       ├── edit_distance_time.png
│       ├── string_search_comparisons.png
│       └── memory_comparison.png
│
├── scripts/
│   └── plot_results.py
│
├── pom.xml
├── README.md
├── REPORT.md
└── .gitignore
```

---

## 4. Requirements

Install the following:

- JDK 17 or newer
- Apache Maven
- Python 3
- Matplotlib

---

## 5. Build the Project

Open the terminal in the project directory:

```bash
mvn clean compile
```

---

## 6. Run Automated Tests

Execute:

```bash
mvn clean test
```

The JUnit 5 test suite validates:

- Edit Distance reference values.
- Empty string handling.
- Random Edit Distance comparisons.
- LPS reference arrays.
- Naive and KMP search results.
- Overlapping matches.
- Invalid empty patterns.
- Patterns longer than the text.
- Randomized string-search comparisons.

---

## 7. Run Experiments

Execute:

```bash
java -cp target/classes daa.ExperimentRunner
```

The experiment runner generates reproducible inputs and records:

- Algorithm name.
- Input type.
- Input size.
- Run number.
- Execution time.
- Median execution time.
- Operation counter.
- DP storage cells.

The results are saved in:

`results/results.csv`

---

## 8. Generate Plots

Install Matplotlib:

```bash
python -m pip install matplotlib
```

Generate all plots:

```bash
python scripts/plot_results.py
```

The generated plots are saved in `results/plots/`.

### Generated Visualizations

1. Edit Distance – Execution Time Comparison.
2. String Search – Character Comparison Analysis.
3. Edit Distance – Auxiliary DP Storage Comparison.

---

## 9. Experimental Report

The detailed experimental analysis is available in:

`REPORT.md`

It includes:

- Experiment Design Card.
- Experimental results.
- Three performance plots.
- Prediction versus observation.
- Algorithm complexity analysis.
- Short-answer questions.
- Execution-time discussion.
- Conclusion.

---

## 10. GitHub Repository

**Repository:**  
https://github.com/suyasharora2007/DAA-Assignment4

**Main branch:** `main`  
**Final release tag:** `v1.0`

---

## 11. AI Usage Acknowledgement

AI tools were used for learning assistance, implementation guidance, debugging, test-case design and reviewing experimental analysis. The student reviewed and tested the submitted implementation and is responsible for understanding the algorithms and final results.

---

**Submitted by:** Suyash Arora  
**Group:** SE-2521  
**Astana IT University | Astana, Kazakhstan**

**End of README**
