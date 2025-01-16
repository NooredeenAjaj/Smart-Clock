package clock;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {
    public static Font loadDigitalFont() {
        try {
            Font digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/vtks chalk 79.ttf")).deriveFont(Font.PLAIN, 100);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(digitalFont);
            return digitalFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 100); // Fallback font
        }
    }
}
