import java.util.Random;

public class driver {
    
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

        int[] arr = populateArray(10000, 99999);
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

        ins.startTiming("QuickSort");
        QuickSort.quickSort(arr);
        ins.stopTiming("Done sorting");

        ins.startTiming("BubbleSort");
        BubbleSort.bubbleSort(arr);
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

        ins.dump("sort.log");
    }
}
