package spred;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {
	
	public static Font loadFont(String str) {
		try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "\\data\\fonts\\" + str));
        } catch (FontFormatException | IOException e) {
            return new Font("Georgia", Font.BOLD, 35);
        }
	}
	
	public static Font modify(Font font, float f, int style) {
		return font.deriveFont(style, f);
	}
}
