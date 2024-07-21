package objects.infectious;

import java.awt.Color;

import javax.swing.border.LineBorder;

import objects.MapObject;
import objects.ObjectState;
import screens.Board;

public abstract class Virus extends MapObject implements Infectious {
	
	public static final int IDLE = 0, SHOWING = 1;
	private int level = 1;
	protected int[][] infectionRange;
	protected Color color;
	protected Board board;
	private int state = IDLE;
	protected int virusID = 0;
	
	public Virus(int x, int y) {
		super(x, y);
		objectState = ObjectState.VIRUS;
	}

	@Override
	public void spread() {
		// TODO Auto-generated method stub
		for(int i = 0; i < infectionRange.length; i++) {
			int xPos = getX() + infectionRange[i][0];
			int yPos = getY() + infectionRange[i][1];
			if(xPos >= 0 && xPos < board.getMaxSize() && yPos >= 0 && yPos < board.getMaxSize()) {
				addVirus(xPos, yPos);
			}
		}
		board.decreaseMoves();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		board.getBoard()[getX()][getY()].setObject(new MapObject(getX(), getY()));
	}
	
	@Override
	public void progress() {
		// TODO Auto-generated method stub
		if(!canSpread()) {
			return;
		}
		if(state == IDLE) {
			state = SHOWING;
			for(int i = 0; i < board.getMaxSize(); i++) {
				for(int j = 0; j < board.getMaxSize(); j++) {
					if(i == getX() && j == getY()) continue;
					if(board.getBoard()[i][j].getState() == ObjectState.VIRUS) {
						board.getBoard()[i][j].getObject().cancel();
					}
				}
			}
			displayZone();
		} else {
			spread();
			state = IDLE;
			displayZone();
		}
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		if(state == IDLE) return;
		state = IDLE;
		displayZone();
	}

	@Override
	public void displayZone() {
		// TODO Auto-generated method stub
		if(state == IDLE) {
			for(int i = 0; i < infectionRange.length; i++) {
				int xPos = getX() + infectionRange[i][0];
				int yPos = getY() + infectionRange[i][1];
				if(xPos >= 0 && xPos < board.getMaxSize() && yPos >= 0 && yPos < board.getMaxSize()) {
					board.getBoard()[xPos][yPos].setBorder(new LineBorder(Color.GRAY));
				}
			}
		} else if(state == SHOWING) {
			for(int i = 0; i < infectionRange.length; i++) {
				int xPos = getX() + infectionRange[i][0];
				int yPos = getY() + infectionRange[i][1];
				if(xPos >= 0 && xPos < board.getMaxSize() && yPos >= 0 && yPos < board.getMaxSize()) {
					if(board.getBoard()[xPos][yPos].getState() != ObjectState.VIRUS &&
							board.getBoard()[xPos][yPos].getState() != ObjectState.NONE)
					board.getBoard()[xPos][yPos].setBorder(new LineBorder(color));
				}
			}
		}
	}	
	
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		for(int i = 0; i < infectionRange.length; i++) {
			int xPos = getX() + infectionRange[i][0];
			int yPos = getY() + infectionRange[i][1];
			if(xPos >= 0 && xPos < board.getMaxSize() && yPos >= 0 && yPos < board.getMaxSize()) {
				board.getBoard()[xPos][yPos].setIcon(null);
			}
		}
	}
	
	private boolean canSpread() {
		for(int i = 0; i < infectionRange.length; i++) {
			int xPos = getX() + infectionRange[i][0];
			int yPos = getY() + infectionRange[i][1];
			if(xPos >= 0 && xPos < board.getMaxSize() && yPos >= 0 && yPos < board.getMaxSize()) {
				if(board.getBoard()[xPos][yPos].getState() != ObjectState.VIRUS && board.getBoard()[xPos][yPos].getState() != ObjectState.NONE) {
					return true;
				}
			}
		}
		return false;
	}

	public void levelUp() {
		level++;
	}
	
	public void levelDown() {
		level--;
	}
	
	public void linkBoard(Board board) {
		this.board = board;
	}

	public int getVirusID() {
		return virusID;
	}

	protected abstract void addVirus(int x, int y);
}
