public class overhead {
    public static void main(String[] args) {

        Instrumentation ins = Instrumentation.Instance();
        
        ins.activate(true);

        // overhead of one timer call
        ins.startTiming("ins overhead test - 1 start/stop");
        ins.startTiming("inside timing");
        ins.stopTiming(null);
        ins.stopTiming(null);

        // overhead of 100 timer calls
        ins.startTiming("ins overhead test - 100 start/stops");
        for (int i = 0; i < 100; i ++) {
            ins.startTiming("inside timing");
            ins.stopTiming(null);
        }
        ins.stopTiming("END ins overhead test - 100 start/stops");


        // overhead of 100 startTiming calls 
        int numTimers = 200;
        ins.startTiming("ins overhead test - "+numTimers+" starts");
        
        for (int i = 0; i < numTimers; i ++) {
            ins.startTiming("inside timing");
        }

        long startOverhead = ins.stopFirstTimer("stopping the first timer");
        ins.comment("Overhead for starting "+numTimers+" timers: " +(startOverhead) +" ms", false);

        ins.comment("Beginning of stopping " + numTimers + " timers.", false);

        for (int i = 0; i < numTimers; i ++) {
            ins.stopTiming(null);
        }

        long stopOverhead = ins.stopTiming("end of stop overhead");

        ins.comment("Overhead for stopping "+numTimers+" timers: " +(stopOverhead-startOverhead) + " ms");

        ins.dump("overhead.log");
    }
}
