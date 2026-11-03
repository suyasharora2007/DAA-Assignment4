
package daa;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class EditDistanceTest {

    @Test
    void testReferenceValues() {

        assertAll(
            () -> checkAll("kitten", "sitting", 3),
            () -> checkAll("flaw", "lawn", 2),
            () -> checkAll("intention", "execution", 5),
            () -> checkAll("", "abc", 3),
            () -> checkAll("abc", "abc", 0)
        );
    }

    @Test
    void testEmptyStrings() {

        assertAll(
            () -> checkAll("", "", 0),
            () -> checkAll("", "hello", 5),
            () -> checkAll("hello", "", 5)
        );
    }

    @Test
    void testRandomStrings() {

        Random random = new Random(42);
        String alphabet = "abc";

        for (int test = 0; test < 100; test++) {

            String a = randomString(random, alphabet);
            String b = randomString(random, alphabet);

            int expected = EditDistance.table(
                a, b, new Counter()
            );

            assertEquals(expected,
                EditDistance.recursive(a, b, new Counter()),
                "Recursive mismatch: " + a + " / " + b
            );

            assertEquals(expected,
                EditDistance.twoRows(a, b, new Counter()),
                "Two-row mismatch: " + a + " / " + b
            );
        }
    }

    private void checkAll(String a, String b, int expected) {

        assertEquals(expected,
            EditDistance.recursive(a, b, new Counter()));

        assertEquals(expected,
            EditDistance.table(a, b, new Counter()));

        assertEquals(expected,
            EditDistance.twoRows(a, b, new Counter()));
    }

    private String randomString(Random random, String alphabet) {

        int length = random.nextInt(6);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(
                random.nextInt(alphabet.length())
            ));
        }

        return result.toString();
    }
}
