package objects;

import javax.swing.ImageIcon;

public class MapObject {
	private ImageIcon icon;
	private int x, y;
	protected int objectState = ObjectState.NONE;
	
	public MapObject(int x, int y) {
		this.x = x;
		this.y = y;
		setIcon(new ImageIcon("data\\images\\nothing.png"));
	}
	
	protected void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public void progress() {
		
	}
	
	public void cancel() {
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getState() {
		return objectState;
	}
}
