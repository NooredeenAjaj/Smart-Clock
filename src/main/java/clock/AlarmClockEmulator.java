package clock;

import javax.swing.*;
import java.awt.*;

public class AlarmClockEmulator {
    private final JFrame frame;
    private final ClockDisplayPanel displayPanel;
    private final ClockDisplayer out;
    private final ClockInputHandler in;

    public AlarmClockEmulator(Font digitalFont) {
        this.frame = new JFrame("Alarm Clock");
        this.displayPanel = new ClockDisplayPanel(digitalFont);
        this.out = new ClockDisplayer(displayPanel);
        this.in = new ClockInputHandler(displayPanel);

        setupGUI();
    }

    private void setupGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(displayPanel, BorderLayout.CENTER);
        frame.setSize(1200, 500);
        frame.setVisible(true);

        displayPanel.requestFocusInWindow();
    }

    public ClockOutput getOutput() {
        return this.out;
    }
    public ClockInput getInput() {
        return this.in;
    }



}
