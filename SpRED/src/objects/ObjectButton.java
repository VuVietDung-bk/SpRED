package objects;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class ObjectButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MapObject obj;
	private int objectState = ObjectState.NONE;
	private MouseAdapter mouseAdapter;
	
	public ObjectButton(int x, int y) {
		obj = new MapObject(x, y);
		setFocusPainted(false);
        setContentAreaFilled(false); // Transparent background
        setOpaque(true);
        setBorder(new LineBorder(Color.GRAY));  // Add a border to each button
        setBackground(Color.BLACK);
        
		setIcon(obj.getIcon());
		
		setUpMouseListener();
		
		addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refresh(); // Call refresh when the component is resized
            }
        });
	}
	
	private void refresh() {
		Image img = obj.getIcon().getImage();
        Image resizedImg = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        setIcon(resizedIcon);
        repaint();
	}
	
	private void setUpMouseListener() {
		mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (objectState == ObjectState.VIRUS && SwingUtilities.isLeftMouseButton(e)) {
                    obj.progress();
                } else if (objectState == ObjectState.VIRUS && SwingUtilities.isRightMouseButton(e)) {
                    obj.cancel();
                }
            }
        };
		addMouseListener(mouseAdapter);
	}
	
	public void setObject(MapObject obj) {
		this.obj = obj;
		objectState = obj.getState();
		removeMouseListener(mouseAdapter);
		setUpMouseListener();
		if (getWidth() > 0 && getHeight() > 0) {
            refresh();
        }
		repaint();
	}

	public MapObject getObject() {
		return obj;
	}
	
	public int getState() {
		return objectState;
	}
}
