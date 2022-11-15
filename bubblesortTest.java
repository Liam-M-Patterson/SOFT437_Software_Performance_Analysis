import java.util.Random;

public class bubblesortTest {
    
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


        int arrLength = 10000;
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
        
        ins.startTiming("BubbleSort - Instrumented Main");
        BubbleSort.bubbleSort(arr);
        ins.stopTiming("Done bubblesort - Instrumented Main");

        ins.startTiming("Bubblesort - Uninstrumented main");
        ins.activate(false);
        BubbleSort.bubbleSort(arr);
        ins.activate(true);
        ins.stopTiming("Done bubblesort - uninstrumented main");

        
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

        ins.dump("bubblesort-"+arrLength+".log");
    }
}

