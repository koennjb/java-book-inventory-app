package numberlist;

/**
 * This class defines an exception which is to be thrown when an invalid index
 * is given to an array.
 *
 * @author Koenn Becker
 * @version 1.0.0 - 5/3/2018
 */
public class InvalidIndexException extends Exception {

    private int minIndex;
    private int maxIndex;
    private int indexUsed;

    /**
     * This constructor takes the min, max and given indexes for an array and
     * creates a error message using those values.
     *
     * @param minIndex the minimum index an array can have
     * @param maxIndex the maximum index that can be accessed in an array
     * @param indexUsed the used index that threw the exception
     */
    public InvalidIndexException(int minIndex, int maxIndex, int indexUsed) {
        super(indexUsed + " is an invalid index. Range: " + minIndex
                + "-" + maxIndex);
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.indexUsed = indexUsed;
    }

    /**
     * Returns minimum index of an array
     *
     * @return Returns minimum index of an array.
     */
    public int getMinIndex() {
        return minIndex;
    }

    /**
     * Returns the maximum index of an array.
     *
     * @return the max index of an array.
     */
    public int getMaxIndex() {
        return maxIndex;
    }

    /**
     * The index used that caused an error.
     *
     * @return the index used that caused an error.
     */
    public int getIndexUsed() {
        return indexUsed;
    }

}
