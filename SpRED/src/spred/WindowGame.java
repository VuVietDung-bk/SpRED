package spred;

import javax.swing.JFrame;

import objects.*;
import objects.infectious.viruses.*;
import screens.*;

public class WindowGame {
	public static final int WIDTH = 1280, HEIGHT = 720;
	
	private JFrame window;
	private Screen screen;
	private Level level;
	
	private int currLevel = 0;
	
	public WindowGame(){
		window = new JFrame("SpRED");
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        screen = new InitialWindow(this);
        window.add(screen);
        
        window.setVisible(true);
	}
	
	public static void main(String[] args) {
		new WindowGame();
	}

	public void startGame() {
		window.remove(screen);
		
		createLevel(currLevel);
		screen = new Board(this, level);
		
		window.add(screen);
		window.revalidate();
	}
	
	public void winLevel() {
		window.remove(screen);
		currLevel++;
		screen = new WinScreen(this);
		
		window.add(screen);
		window.revalidate();
	}
	
	public void backToMenu() {
		window.remove(screen);
		screen = new InitialWindow(this);
		
		window.add(screen);
		window.revalidate();
	}
	
	public void createLevel(int i) {
		level = LevelReader.scanLevel("level_" + i + ".txt");
	}
	
	public int getCurrLevel() {
		return currLevel;
	}
}
