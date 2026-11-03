
# DAA Assignment 4
## Experiment Design and Analysis

### 1. Experiment Design Card

#### Edit Distance

**Question:**
How do recursive, DP table and two-row implementations
differ in computational cost and memory usage?

**Sizes:**
- Recursive: 2, 4, 6, 8
- DP Table: 100, 200, 400, 800
- Two Rows: 100, 200, 400, 800

**Inputs:**
1. Random strings: Generated using a fixed seed.
2. Mismatch-heavy strings: Strings containing different
   repeated characters to exercise edit operations.

**Metrics:**
- Recursive calls.
- DP cells computed.
- Execution time in milliseconds.
- Median time across 3 runs.

**Prediction:**
Recursive execution will grow rapidly.
The table and two-row versions should show
quadratic growth for equal-length strings.
Two rows should use substantially less memory.

#### String Search

**Question:**
How do Naive and KMP compare on ordinary and
repetitive inputs?

**Sizes:**
1,000, 2,000, 4,000 and 8,000 characters.

**Inputs:**
1. Early mismatch: Pattern differs from the text
   at the first character.
2. Repetitive near-match: Repeated A characters
   with a pattern consisting mostly of A characters
   followed by B.

**Metrics:**
- Character comparisons.
- Execution time in milliseconds.
- Median time across 3 runs.

**Prediction:**
Naive search should perform poorly on repetitive
near-match inputs. KMP should avoid repeated
comparisons using its LPS array.

### 2. Experimental Method

All inputs will be generated with fixed random seeds.
Each configuration will be executed three times.
The median execution time will be reported alongside
individual measurements.

### 3. Results and Conclusions

To be completed after running the experiments.
