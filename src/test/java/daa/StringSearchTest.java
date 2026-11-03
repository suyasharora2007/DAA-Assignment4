
package daa;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class StringSearchTest {

    @Test
    void testReferenceSearchValues() {

        assertEquals(
                List.of(10),
                StringSearch.naive(
                        "ABABDABACDABABCABAB",
                        "ABABCABAB",
                        new Counter()
                )
        );

        assertEquals(
                List.of(10),
                StringSearch.kmp(
                        "ABABDABACDABABCABAB",
                        "ABABCABAB",
                        new Counter()
                )
        );
    }

    @Test
    void testOverlappingMatches() {

        assertEquals(
                List.of(0, 1, 2, 3),
                StringSearch.naive("AAAAA", "AA", new Counter())
        );

        assertEquals(
                List.of(0, 1, 2, 3),
                StringSearch.kmp("AAAAA", "AA", new Counter())
        );

        assertEquals(
                List.of(0, 2, 4),
                StringSearch.naive("ABABABA", "ABA", new Counter())
        );

        assertEquals(
                List.of(0, 2, 4),
                StringSearch.kmp("ABABABA", "ABA", new Counter())
        );
    }

    @Test
    void testLPSReferenceValues() {

        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                StringSearch.lps("AAACAAAA")
        );

        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                StringSearch.lps("ABABCABAB")
        );
    }

    @Test
    void testEmptyPatternThrowsException() {

        assertThrows(
                IllegalArgumentException.class,
                () -> StringSearch.naive("ABCDE", "", new Counter())
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> StringSearch.kmp("ABCDE", "", new Counter())
        );
    }

    @Test
    void testPatternLongerThanText() {

        assertEquals(
                List.of(),
                StringSearch.naive("ABC", "ABCDE", new Counter())
        );

        assertEquals(
                List.of(),
                StringSearch.kmp("ABC", "ABCDE", new Counter())
        );
    }

   
@Test
void testRandomTexts() {

    Random random = new Random(42);
    String alphabet = "ABC";

    for (int test = 0; test < 100; test++) {

        String text = randomString(random, alphabet, 30);

        // Pattern must never be empty.
        String pattern = randomString(random, alphabet, 8);

        if (pattern.isEmpty()) {
            pattern = "A";
        }

        List<Integer> naiveResult =
                StringSearch.naive(text, pattern, new Counter());

        List<Integer> kmpResult =
                StringSearch.kmp(text, pattern, new Counter());

        assertEquals(
                naiveResult,
                kmpResult,
                "Mismatch for text: " + text
                        + ", pattern: " + pattern
        );
    }
}


    private String randomString(
            Random random, String alphabet, int maxLength) {

        int length = random.nextInt(maxLength + 1);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {

            result.append(
                    alphabet.charAt(
                            random.nextInt(alphabet.length())
                    )
            );
        }

        return result.toString();
    }
}
