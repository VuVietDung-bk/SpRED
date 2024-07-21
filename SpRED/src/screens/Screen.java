package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import spred.WindowGame;


public abstract class Screen extends JPanel{

	private static final long serialVersionUID = 1L;

	public WindowGame window;
	private Timer timer;
	protected BufferedImage img;
	
	public Screen(WindowGame window){
        timer = new Timer(1000/10, new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		repaint();
			}
        });
        timer.start();
        this.window = window;
	}
	
	protected void setImage(String path) {
		img = ImageLoader.loadImage(path);
	};

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(img != null) {
			g.drawImage(img, 0, 0, null);
		}
	}
}
