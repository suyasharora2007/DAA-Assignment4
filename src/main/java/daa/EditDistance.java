
package daa;

public class EditDistance {

    /*
     * A. RECURSIVE APPROACH
     *
     * State: solve(i,j) represents the minimum operations needed
     * to convert the first i characters of a into the first j of b.
     *
     * Transition: Take minimum of insertion, deletion and replacement.
     * Base case: If either prefix is empty, return the other prefix length.
     */

    public static int recursive(String a, String b, Counter counter) {

        return recursiveHelper(a, b, a.length(), b.length(), counter);
    }

    private static int recursiveHelper(
            String a, String b, int i, int j, Counter counter) {

        counter.increment();

        if (i == 0) {
            return j;
        }

        if (j == 0) {
            return i;
        }

        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return recursiveHelper(a, b, i - 1, j - 1, counter);
        }

        int insert = recursiveHelper(a, b, i, j - 1, counter);

        int delete = recursiveHelper(a, b, i - 1, j, counter);

        int replace = recursiveHelper(a, b, i - 1, j - 1, counter);

        return 1 + Math.min(insert,
                Math.min(delete, replace));
    }


    /*
     * B. DYNAMIC PROGRAMMING TABLE
     *
     * State: dp[i][j] stores the minimum edit distance between
     * prefixes of lengths i and j.
     *
     * Transition: Minimum of insertion, deletion and replacement.
     * Base case: First row and column represent empty prefixes.
     */

    public static int table(String a, String b, Counter counter) {

        int n = a.length();
        int m = b.length();

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= m; j++) {

                counter.increment();

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    dp[i][j] = dp[i - 1][j - 1];

                } else {

                    int insert = dp[i][j - 1];

                    int delete = dp[i - 1][j];

                    int replace = dp[i - 1][j - 1];

                    dp[i][j] = 1 + Math.min(
                            insert,
                            Math.min(delete, replace)
                    );
                }
            }
        }

        return dp[n][m];
    }


    /*
     * C. TWO-ROW OPTIMIZATION
     *
     * State: prev[j] and curr[j] store consecutive DP rows.
     *
     * Transition: Apply the same insertion, deletion and replacement
     * recurrence using the previous row and current row.
     * Base case: Initialize the previous row with empty-prefix costs.
     */

    public static int twoRows(String a, String b, Counter counter) {

        int n = a.length();
        int m = b.length();

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= n; i++) {

            curr[0] = i;

            for (int j = 1; j <= m; j++) {

                counter.increment();

                if (a.charAt(i - 1) == b.charAt(j - 1)) {

                    curr[j] = prev[j - 1];

                } else {

                    int insert = curr[j - 1];

                    int delete = prev[j];

                    int replace = prev[j - 1];

                    curr[j] = 1 + Math.min(
                            insert,
                            Math.min(delete, replace)
                    );
                }
            }

            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[m];
    }
}
