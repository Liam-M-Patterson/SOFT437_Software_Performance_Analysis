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

        // overhead of 1000 timer calls
        ins.startTiming("ins overhead test - 1000 start/stops");
        for (int i = 0; i < 1000; i ++) {
            ins.startTiming("inside timing");
            ins.stopTiming(null);
        }
        ins.stopTiming("END ins overhead test - 1000 start/stops");
        

        // overhead of 10000 timer calls
        ins.startTiming("ins overhead test - 10000 start/stops");
        for (int i = 0; i < 10000; i ++) {
            ins.startTiming("inside timing");
            ins.stopTiming(null);
        }
        ins.stopTiming("END ins overhead test - 10000 start/stops");

        // overhead of 100000 timer calls
        ins.startTiming("ins overhead test - 100000 start/stops");
        for (int i = 0; i < 100000; i ++) {
            ins.startTiming("inside timing");
            ins.stopTiming(null);
        }
        ins.stopTiming("END ins overhead test - 100000 start/stops");

        // overhead of 100 timer calls
        // ins.startTiming("ins overhead test - 100 activate on/off");
        // for (int i = 0; i < 100; i ++) {
        //     ins.activate(false);
        //     ins.activate(true);
        // }
        // ins.stopTiming("END ins overhead test - 100 activate");

        // // overhead of 100 startTiming calls 
        // int numTimers = 100;
        // ins.startTiming("ins overhead test - "+numTimers+" starts");
        
        // for (int i = 0; i < numTimers; i ++) {
        //     ins.startTiming("inside timing");
        // }

        // long startOverhead = ins.stopFirstTimer("stopping the first timer");
        // ins.comment("Overhead for starting "+numTimers+" timers: " +(startOverhead) +" ms", false);

        // // Overhead of 100 stopTiming calls
        // ins.comment("Beginning of stopping " + numTimers + " timers.", false);

        // for (int i = 0; i < numTimers; i ++) {
        //     ins.stopTiming(null);
        // }

        // long stopOverhead = ins.stopTiming("end of stop overhead");
        // ins.comment("Overhead for stopping "+numTimers+" timers: " +(stopOverhead-startOverhead) + " ms");

        ins.dump("overhead.log");
    }
}
