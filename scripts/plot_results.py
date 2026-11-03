
import csv
import statistics
from pathlib import Path
from collections import defaultdict

import matplotlib.pyplot as plt
from matplotlib.ticker import LogLocator, LogFormatterMathtext

# --------------------------------------------------
# CONFIGURATION
# --------------------------------------------------

CSV_FILE = Path("results/results.csv")
OUTPUT_DIR = Path("results/plots")

OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

if not CSV_FILE.exists():
    raise FileNotFoundError("results/results.csv not found!")

with CSV_FILE.open("r", newline="") as file:
    rows = list(csv.DictReader(file))

groups = defaultdict(list)

for row in rows:
    key = (
        row["algorithm"],
        row["input"],
        int(row["n"])
    )
    groups[key].append(row)

data = []

for (algorithm, input_type, n), runs in groups.items():

    times = [float(r["time_ms"]) for r in runs]
    counters = [int(r["counter"]) for r in runs]

    data.append({
        "algorithm": algorithm,
        "input": input_type,
        "n": n,
        "time": statistics.median(times),
        "counter": statistics.median(counters),
        "memory": int(runs[0]["memory_cells"])
    })


# --------------------------------------------------
# SCIENTIFIC NOTATION FORMATTER
# --------------------------------------------------

def format_power10(ax):

    for axis in [ax.xaxis, ax.yaxis]:

        axis.set_major_locator(LogLocator(base=10))
        axis.set_major_formatter(
            LogFormatterMathtext(base=10)
        )


# --------------------------------------------------
# SAVE PLOT WITH TAKEAWAY
# --------------------------------------------------

def finish_plot(filename, takeaway):

    plt.tight_layout()
    plt.subplots_adjust(bottom=0.23)

    plt.figtext(
        0.5,
        0.04,
        "Takeaway: " + takeaway,
        ha="center",
        wrap=True,
        fontsize=9
    )

    plt.savefig(
        OUTPUT_DIR / filename,
        dpi=300,
        bbox_inches="tight"
    )

    plt.close()

    print("Generated:", filename)


# --------------------------------------------------
# PLOT 1: EDIT DISTANCE EXECUTION TIME
# --------------------------------------------------

plt.figure(figsize=(10, 6))

for algorithm in ["Recursive", "DP-Table", "Two-Rows"]:

    points = [
        r for r in data
        if r["algorithm"] == algorithm
        and r["input"] == "random"
        and r["time"] > 0
    ]

    points.sort(key=lambda r: r["n"])

    if points:
        plt.plot(
            [r["n"] for r in points],
            [r["time"] for r in points],
            marker="o",
            linewidth=2,
            label=algorithm
        )

ax = plt.gca()
ax.set_xscale("log")
ax.set_yscale("log")

format_power10(ax)

plt.title(
    "Edit Distance: Execution Time Comparison",
    fontsize=14
)
plt.xlabel("String length n (characters)")
plt.ylabel("Median execution time (milliseconds)")

plt.legend()
plt.grid(True, which="both", alpha=0.3)

finish_plot(
    "edit_distance_time.png",
    "Recursive execution grows rapidly, while both DP "
    "implementations have polynomial growth."
)


# --------------------------------------------------
# PLOT 2: STRING SEARCH COMPARISONS
# --------------------------------------------------

plt.figure(figsize=(10, 6))

for algorithm in ["Naive", "KMP"]:

    for input_type in [
        "early-mismatch",
        "repetitive-near-match"
    ]:

        points = [
            r for r in data
            if r["algorithm"] == algorithm
            and r["input"] == input_type
            and r["counter"] > 0
        ]

        points.sort(key=lambda r: r["n"])

        if points:
            plt.plot(
                [r["n"] for r in points],
                [r["counter"] for r in points],
                marker="o",
                linewidth=2,
                label=f"{algorithm} - {input_type}"
            )

ax = plt.gca()
ax.set_xscale("log")
ax.set_yscale("log")

format_power10(ax)

plt.title(
    "String Search: Character Comparison Analysis",
    fontsize=14
)
plt.xlabel("Text length n (characters)")
plt.ylabel("Median character comparisons (count)")

plt.legend()
plt.grid(True, which="both", alpha=0.3)

finish_plot(
    "string_search_comparisons.png",
    "Repetitive near-match inputs expose the additional "
    "character comparisons performed by Naive search."
)


# --------------------------------------------------
# PLOT 3: DP MEMORY COMPARISON
# --------------------------------------------------

plt.figure(figsize=(10, 6))

for algorithm in ["DP-Table", "Two-Rows"]:

    points = [
        r for r in data
        if r["algorithm"] == algorithm
        and r["input"] == "random"
        and r["memory"] > 0
    ]

    points.sort(key=lambda r: r["n"])

    if points:
        plt.plot(
            [r["n"] for r in points],
            [r["memory"] for r in points],
            marker="o",
            linewidth=2,
            label=algorithm
        )

ax = plt.gca()
ax.set_xscale("log")
ax.set_yscale("log")

format_power10(ax)

plt.title(
    "Edit Distance: Auxiliary DP Storage Comparison",
    fontsize=14
)
plt.xlabel("String length n (characters)")
plt.ylabel("DP storage cells (count)")

plt.legend()
plt.grid(True, which="both", alpha=0.3)

finish_plot(
    "memory_comparison.png",
    "Two Rows requires linear auxiliary storage compared "
    "with the quadratic storage of the full DP table."
)


# --------------------------------------------------
# COMPLETION
# --------------------------------------------------

print("\nAll three plots generated successfully!")
print("Output directory:", OUTPUT_DIR.resolve())
print("Total CSV records:", len(rows))
print("Total configurations:", len(groups))
