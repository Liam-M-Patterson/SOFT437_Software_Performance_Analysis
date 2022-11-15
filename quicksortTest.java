import java.util.Random;

public class quicksortTest {
    
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

        Instrumentation ins = Instrumentation.Instance();    
        ins.activate(true);
        
        ins.startTiming("Main");
        ins.startTiming("Generate Array");
        ins.startTiming("populateArray()");

        int arrLength = 100;
        int[] arr = populateArray(arrLength, 99999);
        int[] arr2 = arr.clone();   
        
        ins.stopTiming("Done populating");

        if (debug) {
 
            System.out.println("Random:");
            for (int num : arr2) {
                System.out.print(num + " ");
            }
            System.out.println("\n");
        }
        ins.stopTiming(null);

        ins.startTiming("QuickSort - Instrumented Main");
        QuickSort.quickSort(arr);
        ins.stopTiming("Done quicksort - Instrumented main");

        ins.startTiming("QuickSort - Unistrumented Main");
        ins.activate(false);
        QuickSort.quickSort(arr2);
        ins.activate(true);
        ins.stopTiming("Done quicksort - uninstrumented Main");
        
        ins.startTiming("Printing");        
        if (debug) {
            System.out.println("Sorted:");
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println("\n");
        }

        try {
            int i = 0;
            while (i < arrLength) {
    
                if (arr[i] != arr2[i]) throw(new Exception("quicksort and bubblesort produced different results"));
                i ++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        ins.stopTiming(null);

        ins.stopTiming("Main");

        ins.dump("quicksort"+arrLength+".log");
    }
}
