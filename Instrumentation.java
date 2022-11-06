import java.text.SimpleDateFormat;
import java.util.Date;

public class Instrumentation {

    // Use Singleton Design Pattern
    private static Instrumentation instance = new Instrumentation();

    private Instrumentation() {}

    public static Instrumentation getInstance() {return instance;}

    private boolean active = false;
    
    public void activate(boolean onOff) {
        active = onOff;
    }


    public void startTiming(String comment) {
        if (!active) {return;}

    }

    public void stopTiming(String comment) {
        if (!active) {return;}

    }

    public void comment(String comment) {
        if (!active) {return;}
        System.out.println("LOG COMMENT: "+comment);
    }

    public void dump(String filename) {
        if (!active) {return;}

        System.out.println("taking dump");
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

    }

    public static void main(String[] args) {


        Instrumentation ins = Instrumentation.getInstance();    
        ins.activate(true);

        String var = "first test";
        ins.comment(var);
        
        ins.dump();

        return;
    }
}