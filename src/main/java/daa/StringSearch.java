
package daa;

import java.util.ArrayList;
import java.util.List;

public class StringSearch {

    /*
     * A. NAIVE STRING SEARCH
     *
     * State: Check whether pattern P matches text T starting at index i.
     * Transition: Compare characters one by one; stop at first mismatch.
     * Base case: Every valid starting position is checked.
     *
     * Time: O((n-m+1) * m), worst case O(n*m)
     * Space: O(1) auxiliary space, excluding result list.
     */

    public static List<Integer> naive(
            String text, String pattern, Counter counter) {

        validatePattern(pattern);

        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return matches;
        }

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m) {

                counter.increment();

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }

                j++;
            }

            if (j == m) {
                matches.add(i);
            }
        }

        return matches;
    }


    /*
     * B. LPS ARRAY (LONGEST PROPER PREFIX
     * WHICH IS ALSO A SUFFIX)
     *
     * State: lps[i] stores the longest proper prefix length
     * that is also a suffix for pattern[0...i].
     *
     * Transition: Extend the current prefix on a match;
     * otherwise fall back using the previous LPS value.
     *
     * Base case: lps[0] = 0.
     */

    public static int[] lps(String pattern) {

        validatePattern(pattern);

        int m = pattern.length();

        int[] lps = new int[m];

        int length = 0;
        int i = 1;

        while (i < m) {

            if (pattern.charAt(i) == pattern.charAt(length)) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {

                    length = lps[length - 1];

                } else {

                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }


    /*
     * C. KMP STRING SEARCH
     *
     * State: i tracks the text index and j tracks the pattern index.
     *
     * Transition: On mismatch, use LPS to avoid rechecking
     * previously matched pattern characters.
     *
     * Base case: A complete match occurs when j == pattern length.
     * After a match, fall back using LPS to find overlapping matches.
     *
     * Time: O(n + m)
     * Space: O(m) for the LPS array, excluding result list.
     */

    public static List<Integer> kmp(
            String text, String pattern, Counter counter) {

        validatePattern(pattern);

        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return matches;
        }

        int[] lpsArray = lps(pattern);

        int i = 0;
        int j = 0;

        while (i < n) {

            counter.increment();

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == m) {

                    matches.add(i - j);

                    // Continue from the LPS value to allow overlaps.
                    j = lpsArray[j - 1];
                }

            } else {

                if (j != 0) {

                    j = lpsArray[j - 1];

                } else {

                    i++;
                }
            }
        }

        return matches;
    }


    private static void validatePattern(String pattern) {

        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException(
                    "Pattern must not be empty."
            );
        }
    }
}
