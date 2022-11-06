import java.util.Random;

public class driver {
    
    // A utility function to swap two elements
    static void swap(int[] arr, int i, int j)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
  
    /* This function takes last element as pivot, places
       the pivot element at its correct position in sorted
       array, and places all smaller (smaller than pivot)
       to left of pivot and all greater elements to right
       of pivot */
    static int partition(int[] arr, int low, int high)
    {
  
        // pivot
        int pivot = arr[high];
  
        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);
  
        for (int j = low; j <= high - 1; j++) {
  
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
  
                // Increment index of smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }
  

    static void quickqort(int[] arr) {
        quickSort(arr, 0, arr.length-1);
    }

    /* The main function that implements QuickSort
        arr[] --> Array to be sorted,
        low --> Starting index,
        high --> Ending index
    */
    static void quickSort(int[] arr, int low, int high)
    {
        if (low < high) {
  
            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);
  
            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int[] populateArray(int length, int max){
        Random rand = new Random();

        int[] arr = new int[length]; 

        for (int i = 0; i < length; i ++) {
            int num = rand.nextInt(max);
            arr[i] = num+1;
        }

        return arr;
    }


    public static void main(String[] args) {

        boolean debug = false;

        Instrumentation ins = Instrumentation.getInstance();    
        ins.activate(true);

        ins.startTiming("Generate Array");
        ins.startTiming("populateArray()");

        int[] arr = populateArray(1000000, 99999);

        ins.stopTiming("Done populating");

        if (debug) {

        
            System.out.println("Random:");
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println("\n");
        }
        ins.stopTiming(null);

        ins.startTiming("Sorting");
        quickqort(arr);
        ins.stopTiming("Done sorting");

        ins.startTiming("Printing");
        
        if (debug) {

        
            System.out.println("Sorted:");
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println("\n");
        }

        ins.stopTiming(null);
        ins.dump("quicksort.log");
    }
}
