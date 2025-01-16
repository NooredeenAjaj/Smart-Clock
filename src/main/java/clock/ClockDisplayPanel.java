package clock;

import fetchData.OpenAIService;
import fetchData.WeatherService;

import javax.swing.*;
import java.awt.*;


public class ClockDisplayPanel extends JPanel {



    private int hours, minutes, seconds;
    private int alarmHours, alarmMinutes, alarmSeconds;
    private boolean alarmOn;
    private Font digitalFont;
    private boolean isEditMode= false;
    private String formattedDate = "";
    private String temperature= "";
    private String watherCond = "";
    private String frameworksInfo = "";

    private AlarmSounds[] alarmSounds = AlarmSounds.values();
    private int selectedSoundIndex = 0; // Index för valt alarm-ljud
    private boolean showAlarmSounds = false;

    public ClockDisplayPanel(Font digitalFont) {
        this.digitalFont = digitalFont;
        setPreferredSize(new Dimension(800, 300));
        setBackground(Color.BLACK);
        setForeground(Color.PINK);
        setFont(digitalFont);
        setFocusable(true);
        startWeatherUpdates();
        showJavaFrameworks();


    }

    public void updateTime(int hours, int minutes, int seconds) {
        if (!isEditMode){
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            repaint();
        }

    }




    public void setAlarmIndicator(boolean on) {
        this.alarmOn = on;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getForeground());
        int weatherInfoY = 300;
        int mainClockY = 150;
        int alarmClockY = 250;


        drawTime(g, hours, minutes, seconds, 50, mainClockY,120);
        drawTime(g, alarmHours, alarmMinutes, alarmSeconds, 50, alarmClockY,60);
        drawWeatherInfo(g,weatherInfoY);
        if (alarmOn) {
            g.setFont(digitalFont.deriveFont(Font.BOLD, 60)); // Font size for alarm indicator
            g.setFont(new Font("Monospaced", Font.PLAIN, 100));
            g.drawString("\u1003", 700, mainClockY); // Using Unicode escape sequence for alarm clock
        }
        if (showAlarmSounds){
            drawAlarmSounds(g);
        }




        drawFrameworksInfoSingleLine(g, 400);


    }




    private void drawWeatherInfo(Graphics g, int y) {
        g.setColor(new Color(253,251,118, 255));

        g.setFont(new Font("SansSerif", Font.PLAIN, 40));

        g.drawString( formattedDate, 50, y); // Display the date
        g.drawString(temperature + " "+ watherCond, 50, y + 50 ) ;


    }

    private void drawAlarmSounds(Graphics g) {
        int startX = 50; // Startposition för X
        int stepX = 10;  // Avståndet mellan varje alternativ
        int yPos = 50;  // Y-positionen för alla alternativ



        g.setFont(new Font("SansSerif", Font.PLAIN, 20)); // Teckensnitt och storlek för röstlägen
        g.setColor(Color.WHITE); // Färg för texten

        for (int i = 0; i < alarmSounds.length; i++) {
            String mode = alarmSounds[i].getDisplayName();
            if (i == selectedSoundIndex) {
                g.setColor(Color.RED);
                g.fillRect(startX, yPos - 30, g.getFontMetrics().stringWidth(mode), 40);
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }

            g.drawString(mode, startX, yPos);
            startX += g.getFontMetrics().stringWidth(mode) + 10;
        }
    }
    public void navigateAlarmSounds() {

        selectedSoundIndex = (selectedSoundIndex + 1) % alarmSounds.length;
        System.out.println(selectedSoundIndex);
        repaint();
    }

    private void drawTime(Graphics g, int hours, int minutes, int seconds, int x, int y, int size) {
        g.setColor(new Color(144,220,255, 255));
        g.setFont(digitalFont.deriveFont(Font.PLAIN, size));
        Graphics2D g2d = (Graphics2D) g;
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        g2d.drawString(time, x, y);
        // Shadow
        //g2d.setColor(new Color(248, 175, 175, 60));
        //g2d.drawString(time, x + 5, y + 2);


    }

    private void drawFrameworksInfoSingleLine(Graphics g, int y) {
        // Om du vill matcha alarmets färg
        g.setColor(new Color(144, 220, 255, 255));
        // Samma fontstorlek som alarmklockan: 60 (ändra vid behov)
        g.setFont(digitalFont.deriveFont(Font.PLAIN, 25));

        // Rita hela strängen i en rad
        g.drawString(frameworksInfo, 50, y);
    }



    public void setEditMode(boolean onOf) {
        isEditMode=onOf;
    }

    public void editModeUpdate(int hours, int minutes, int seconds, Choice choice) {
        switch (choice) {
            case SET_TIME:
                this.hours = hours;
                this.minutes = minutes;
                this.seconds = seconds;
                break;
            case SET_ALARM:
                this.alarmHours = hours;
                this.alarmMinutes = minutes;
                this.alarmSeconds = seconds;
                break;

            default:
                throw new IllegalArgumentException("Unexpected choice: " + choice);
        }
        repaint();
    }
    private void startWeatherUpdates() {
        WeatherService weatherService = new WeatherService();
        weatherService.fetchWeather(new WeatherService.WeatherCallback() {
            @Override
            public void onWeatherFetched(String date, String temp, String condition) {
                formattedDate = date;
                temperature = temp;
                watherCond=condition;
                repaint();
            }
        });
    }


    public void displayAlarmSounds(boolean onOf) {
        showAlarmSounds = onOf;
        repaint();
    }
    /**
     * Metod som instansierar OpenAIService och visar nya/populära
     * Java-frameworks på panelen.
     */
    public void showJavaFrameworks() {
        OpenAIService service = new OpenAIService();
        // Använder callback (lambda) för att ta emot svaret
        service.fetchNewJavaFrameworks(responseText -> {
            String singleLine = responseText.replaceAll("\\r?\\n", ", ");

            this.frameworksInfo = "New frameworks from Java:\n"
                    + singleLine
                    ;

            repaint();
        });
    }

    public String getSeletedSound() {
        return alarmSounds[selectedSoundIndex].getDisplayName();
    }
}
