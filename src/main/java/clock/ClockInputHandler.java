package clock;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class ClockInputHandler implements ClockInput {
    ClockDisplayPanel displayPanel;
    Choice choice;
    Semaphore sem = new Semaphore(0);

    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private int currentDigit = 0;
    private String sound = "";

    private Timer exitEditModeTimer = new Timer();

    @Override
    public Semaphore getSemaphore() {
        return sem;
    }

    public ClockInputHandler(ClockDisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
        setupKeyListener();
    }

    private void setupKeyListener() {
        displayPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_B:
                        displayPanel.displayAlarmSounds(true);
                        displayPanel.navigateAlarmSounds();


                        sound = displayPanel.getSeletedSound();
                        resetEditModeExitTimer();
                        choice = Choice.SET_ALARM_SOUND;
                        break;
                    case KeyEvent.VK_V:




                        break;
                    case KeyEvent.VK_CONTROL:
                        choice = Choice.SET_TIME;
                        displayPanel.setEditMode(true);

                        resetEditModeExitTimer();
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                        //TODO get some error if choice = null, error comes from editModeUpdate swithc
                        handleArrowKeys(e);
                        resetEditModeExitTimer();
                        break;
                    case KeyEvent.VK_LEFT:
                        currentDigit = (currentDigit + 2) % 3;
                        resetEditModeExitTimer();
                        break;
                    case KeyEvent.VK_RIGHT:
                        currentDigit = (currentDigit + 1) % 3;
                        resetEditModeExitTimer();
                        break;
                    case KeyEvent.VK_META:
                        choice = Choice.SET_ALARM;

                        break;
                    case KeyEvent.VK_A:
                        choice = Choice.TOGGLE_ALARM;
                        sem.release();


                        break;
                    default:
                        // Other keys ignored
                }
            }
        });
        displayPanel.setFocusable(true);
        displayPanel.requestFocusInWindow();
    }

    private void resetEditModeExitTimer() {
        exitEditModeTimer.cancel();
        exitEditModeTimer = new Timer();
        exitEditModeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayPanel.setEditMode(false);
                displayPanel.displayAlarmSounds(false );
                sem.release();
            }
        }, 4000);
    }

    private void handleArrowKeys(KeyEvent e) {
        int increment = e.getKeyCode() == KeyEvent.VK_UP ? 1 : -1;
        adjustTimeField(currentDigit, increment);
        displayPanel.editModeUpdate(hours, minutes, seconds, choice);





    }

    private void adjustTimeField(int currentDigit, int increment) {
        switch (currentDigit) {
            case 0:
                hours = normalizeTime(hours + increment, 24);
                break;
            case 1:
                minutes = normalizeTime(minutes + increment, 60);
                break;
            case 2:
                seconds = normalizeTime(seconds + increment, 60);
                break;
        }
    }
    private int normalizeTime(int value, int max) {
        return (value + max) % max;
    }
    @Override
    public UserInput getUserInput() {
        return new MockUserInput();
    }

    @Override
    public void CurrentTime(int h, int m, int s) {
        hours = h;
        minutes = m;
        seconds = s;
    }

    class MockUserInput implements UserInput {

        @Override
        public Choice choice() {
            return choice;
        }

        @Override
        public int hours() {
            return hours;
        }

        @Override
        public int minutes() {
            return minutes;
        }

        @Override
        public int seconds() {
            return seconds;
        }
        @Override
        public String sound(){
            return sound;
        }
    }


}
