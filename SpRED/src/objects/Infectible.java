package objects;

import javax.swing.ImageIcon;

import screens.Board;

public class Infectible extends MapObject {
	
	public Infectible(int x, int y) {
		super(x, y);
		setIcon(new ImageIcon("data\\images\\normal.png"));
		objectState = ObjectState.INFECTIBLE;
	}
}
