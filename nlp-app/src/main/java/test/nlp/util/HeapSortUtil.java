package test.nlp.util;

/**
 * 堆排序
 */
public class HeapSortUtil {

    /**
     * 调整小顶堆
     * 
     * @param array
     */
    public static <T extends Comparable<T>> void minHeapSiftUp(T[] array, int i) {
        T current = array[i];
        while (i > 0) {
            int parentIndex = (i - 1) >> 1;
            T parent = array[parentIndex];
            if (current.compareTo(parent) < 0) {
                array[i] = array[parentIndex];
                i = parentIndex;
            } else {
                break;
            }
        }
        array[i] = current;
    }

    /**
     * 调整小顶堆
     * 
     * @param array
     */
    public static <T extends Comparable<T>> void minheapHeapify(T[] array) {
        for (int i = (array.length >> 1) - 1; i >= 0; i--) {
            minHeapSiftDown(array, i, array.length);
        }
    }

    /**
     * 调整小顶堆
     * 
     * @param array
     * @param i
     */
    public static <T extends Comparable<T>> void minHeapSiftDown(T[] array, int i) {
        minHeapSiftDown(array, i, array.length);
    }

    /**
     * 调整小顶堆
     * 
     * @param array
     * @param i
     */
    public static <T extends Comparable<T>> void minHeapSiftDown(T[] array, int i, int length) {
        int limit = (length >> 1) - 1;
        while (i <= limit) {
            int lchildIndex = (i << 1) + 1;
            int rchildIndex = (i << 1) + 2;
            T lchild = array[lchildIndex];
            T rchild = null;
            if (rchildIndex < length) {
                rchild = array[rchildIndex];
            }

            int minIndex = lchildIndex;
            if (rchild != null && rchild.compareTo(lchild) < 0) {
                minIndex = rchildIndex;
            }
            if (array[i].compareTo(array[minIndex]) <= 0) {
                break;
            }
            swap(array, i, minIndex);
            i = minIndex;
        }
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[] { 2, 6, 8, 10, 1, 3, 5, 234, 12, 13 };
        HeapSortUtil.minheapHeapify(array);
        int num = array.length;
        while (num > 0) {
            num--;
            System.out.println(array[0]);
            array[0] = array[num];
            HeapSortUtil.minHeapSiftDown(array, 0, num);
        }

    }

}
