import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Instrumentation {

    // Use Singleton Design Pattern
    private static Instrumentation instance = new Instrumentation();
    private Instrumentation() {}
    public static Instrumentation getInstance() {return instance;}

    private int numTimers = 0;
    private boolean active = false;
    private Stack<Long> stack = new Stack<Long>();
    private ArrayList<String> logs = new ArrayList<String>();
    
    
    public void activate(boolean onOff) {
        active = onOff;
    }

    public void startTiming(String comment) {
        if (!active) {return;}

        addLog("STARTTIMING: " + comment);
        numTimers++;
        long startTime = System.nanoTime();
        stack.push(startTime);
    }

    public void stopTiming(String comment) {
        if (!active) {return;}

        if (numTimers > 0) {

            long stopTime = System.nanoTime();
            long elapsedTime = (stopTime-stack.pop()) / 1000000;

            numTimers --;
            addLog("STOPTIMING: " + comment + " " + elapsedTime+"ms");
            
            System.out.println("Time: " + elapsedTime+"ms");
        }
    }

    public void comment(String comment) {
        if (!active) {return;}
        addLog("COMMENT: "+comment);
    }

    public void dump(String filename) {
        if (!active) {return;}

        dumpOutput(filename);
    } 

    public void dump() {
        if (!active) {return;}

        SimpleDateFormat sdf = new SimpleDateFormat("ddyyMMHHmmss");
        String date = sdf.format(new Date());
        String filename = "instrumentation" + date +".log";
        dumpOutput(filename);
    }   

    private void dumpOutput(String filename) {

        try {
            FileWriter myWriter = new FileWriter("logs/"+filename);
            for (String log : logs) {
                myWriter.write(log+"\n");
            }
            
            myWriter.close();
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    private void addLog(String log) {

        // repeat spacing characters, based on number of active timers
        String repeated = new String(new char[numTimers]).replace("\0", "|\t");

        logs.add(repeated+log);
    }

    public static void main(String[] args) {

        Instrumentation ins = Instrumentation.getInstance();    
        ins.activate(true);
        
        ins.startTiming("First timing test");
        ins.startTiming("Second timing test");

        for (int i =0; i < 1000; i ++) {
            System.out.println(i);
        }

        ins.stopTiming("Stopped");
        
        for (int i =0; i < 100; i ++) {
            System.out.println(i);
        }


        ins.stopTiming("lol");
        ins.comment("First comment test");
        ins.dump("test.log");

        return;
    }
}