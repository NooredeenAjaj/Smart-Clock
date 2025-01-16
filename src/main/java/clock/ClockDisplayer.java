package clock;

public class ClockDisplayer  implements ClockOutput{
    private   ClockDisplayPanel cd;

public ClockDisplayer( ClockDisplayPanel cd){
    this.cd = cd;
}
    @Override
    public void displayTime(int hours, int mins, int secs) {
    cd.updateTime(hours,mins,secs);

    }

    @Override
    public void setAlarmIndicator(boolean on) {
        cd.setAlarmIndicator(on);
    }

    @Override
    public void alarm() {
        System.out.println("Alarm! Wake up!");
    }
}
