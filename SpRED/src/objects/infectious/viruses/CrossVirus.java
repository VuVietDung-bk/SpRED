package objects.infectious.viruses;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import objects.MapObject;
import objects.ObjectButton;
import objects.ObjectState;
import objects.infectious.Virus;

public class CrossVirus extends Virus {

	public CrossVirus(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		setIcon(new ImageIcon("data\\images\\virus1.png"));
		virusID = 1;
		color = Color.RED;
		infectionRange = new int[][] {
			{-1, 0},
			{0, -1},
			{1, 0},
			{0, 1}
		};
	}

	@Override
	protected void addVirus(int x, int y) {
		// TODO Auto-generated method stub
		ObjectButton ob = board.getBoard()[x][y];
		if(ob.getState() == ObjectState.INFECTIBLE) {
			Virus newVirus = new CrossVirus(x, y);
			List<MapObject> objList = board.getUndoMap().getOrDefault(board.getMove(), new ArrayList<MapObject>());
			objList.add(ob.getObject());
			board.getUndoMap().put(board.getMove(), objList);
			ob.setObject(newVirus);
			newVirus.linkBoard(board);
		}
	}
}
