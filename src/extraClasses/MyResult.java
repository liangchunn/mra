package extraClasses;

/**
 * Used as a tuple to store the the phenomenon of leaveGroup where
 * `first` is used as the result if the leaveGroup is successful or not and
 * `second` is used to store the type of the leave group:
 *      1: for admin leave and delete group
 *      2: for normal user leaving a group
 */
public final class MyResult {
    private final boolean first;
    private final int second;

    public MyResult(boolean first, int second) {
        this.first = first;
        this.second = second;
    }

    public boolean getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
