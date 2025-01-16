package clock;

public class TimeThread  extends Thread {
    private ClockMonitor clockMonitor;
    private ClockOutput out;
    private volatile boolean inEditMode = false;
    public TimeThread(ClockMonitor clockMonitor,  ClockOutput out){
        this.clockMonitor = clockMonitor;
        this.out = out;

    }
    @Override
    public void run() {
        int i = 0;

        long t0 = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted()) {
            long now = System.currentTimeMillis();
            long t = t0 + (i + 1) * 1000;
            long sleepTime = t - now;

            if (sleepTime > 0 ) {
                try {
                    clockMonitor.tickClock();
                    int[] time = new int[3];
                    clockMonitor.getClockTime(time);

                    //time could potentially be changed by another thread (if any)
                    // before you fetch it with getClockTime()
                    out.displayTime(time[0], time[1],time[2]);
                    if (clockMonitor.shouldLarm()){
                        out.alarm();

                    }

                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new Error(e);
                }
            }
            i++;
        }
    }
    public void setEditMode(boolean mode) {
        this.inEditMode = mode;
    }

}
