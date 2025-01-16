package clock;

import fetchData.GoogleCalendar;
import fetchData.OpenAIService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;

public class ClockMonitor {
    private int hours, minutes, seconds;
    private int alarmHours, alarmMinutes, alarmSeconds;

    Timer lockaTime= new Timer();
    private String openAIResponse ;

    private boolean alarm;

    public ClockMonitor() {
        LocalTime now = LocalTime.now();  // Fetches the current local time
        this.hours = now.getHour();
        this.minutes = now.getMinute();
        this.seconds = now.getSecond();
    }
    public synchronized void setClockTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public synchronized void getClockTime(int[] time) {
        time[0] = this.hours;
        time[1] = this.minutes;
        time[2] = this.seconds;
    }

    public synchronized void setAlarmTime(int hours, int minutes, int seconds) {
        this.alarmHours = hours;
        this.alarmMinutes = minutes;
        this.alarmSeconds = seconds;
    }





    public synchronized boolean isAlarmOn() {

      return this.alarm;
    }

    // Method to increment the current time by one second
    public synchronized void tickClock() throws InterruptedException {
        // Wait while in edit mode

        // Tick the clock forward one second
        this.seconds++;
        if (this.seconds >= 60) {
            this.seconds = 0;
            this.minutes++;
        }
        if (this.minutes >= 60) {
            this.minutes = 0;
            this.hours++;
        }
        if (this.hours >= 24) {
            this.hours = 0;
        }



    }

    public synchronized boolean shouldLarm() {
        return (this.alarm &&
                this.hours == this.alarmHours &&
                this.minutes == this.alarmMinutes &&
                this.seconds == this.alarmSeconds);

    }
   public  synchronized  void toggleAlarm(){
       alarm = !alarm;
   }





}
