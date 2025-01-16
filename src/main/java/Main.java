import clock.*;
import clock.Choice;
import com.google.api.client.util.DateTime;
import fetchData.GoogleCalendar;
import fetchData.OpenAIService;

import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws InterruptedException, GeneralSecurityException, IOException {






        Font digitalFont = FontLoader.loadDigitalFont();
        AlarmClockEmulator emulator = new AlarmClockEmulator(digitalFont);
        ClockInput in = emulator.getInput();
        ClockOutput out = emulator.getOutput();
        ClockMonitor clockMonitor = new ClockMonitor();
        Thread tickClock = new TimeThread(clockMonitor, out);
        tickClock.start();





        while (true) {
            int[] time = new int[3];
            in.getSemaphore().acquire();


            ClockInput.UserInput userInput = in.getUserInput();
            Choice c = userInput.choice();
            int h = userInput.hours();
            int m = userInput.minutes();
            int s = userInput.seconds();
            switch (c) {
                case SET_TIME:
                    clockMonitor.setClockTime(h, m, s);

                    break;
                case SET_ALARM:
                    clockMonitor.setAlarmTime(h, m, s);

                    break;
                case TOGGLE_ALARM:
                    clockMonitor.toggleAlarm();
                    out.setAlarmIndicator(clockMonitor.isAlarmOn());


                    break;


                case SET_ALARM_SOUND:
                    System.out.println(c);
                    System.out.println(userInput.sound());
                    break;
            }


            System.out.println("choice=" + c + " h=" + h + " m=" + m + " s=" + s);
        }



    }

}
