
package daa;

/**
 * Counts important operations performed by an algorithm.
 */
public class Counter {

    private long count;

    public void increment() {
        count++;
    }

    public long getCount() {
        return count;
    }

    public void reset() {
        count = 0;
    }
}
