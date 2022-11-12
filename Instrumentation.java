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
    public static Instrumentation Instance() {return instance;}

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

    public long stopTiming(String comment) {
        if (!active) {return 0;}
        
        if (numTimers > 0) {

            long stopTime = System.nanoTime();
            long elapsedTime = (stopTime-stack.pop()) / 1000000;
            
            numTimers --;
            addLog("STOPTIMING: " + comment + " " + elapsedTime+"ms");
            
            return elapsedTime;
        }
        return 0;
    }


    // This method will allow the first timer element on the stack to be used to calculate the elapsed time.
    // This is used to calculate the overhead of the startTiming method, since we can repeatedly call that
    // and then take the elapsed time from the first outer call, disregarding the calls inbetween
    public long stopFirstTimer(String comment) {
        if (!active) {return 0;}

        if (numTimers > 0) {

            long stopTime = System.nanoTime();
            long first = stack.firstElement();
        
            long elapsedTime = (stopTime-first) / 1000000;

            addLog("STOPTIMING: " + comment + " " + elapsedTime+"ms");            
            return elapsedTime;
        }
        return 0;
    }

    public void comment(String comment) {
        if (!active) {return;}
        addLog("COMMENT: "+comment);
    }

    // disable automatic comment indenting
    public void comment(String comment, boolean indent) {
        if (!active) {return;}
        if (!indent){
            logs.add("COMMENT: "+comment);
        }
        else comment(comment);
    }

    private void addLog(String log) {

        // repeat spacing characters, based on number of active timers
        String repeated = new String(new char[numTimers]).replace("\0", "|\t");

        logs.add(repeated+log);
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


    public static void main(String[] args) {

        Instrumentation ins = Instrumentation.Instance();    
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