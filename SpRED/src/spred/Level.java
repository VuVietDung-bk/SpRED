package spred;

import java.util.ArrayList;
import java.util.List;

import objects.Infectible;
import objects.infectious.Vaccine;
import objects.infectious.Virus;

public class Level {
	private int boardSize = 21;
	private List<Virus> viruses = new ArrayList<Virus>();
	private List<Infectible> infectibles = new ArrayList<Infectible>();
	private List<Vaccine> vaccines = new ArrayList<Vaccine>();
	private int moveLeft;
	
	public Level(int moveLeft, int boardSize) {
		this.moveLeft = moveLeft;
		this.boardSize = boardSize;
	}
	
	public Level() {
		
	}
	
	public void addVirus(Virus virus) {
		viruses.add(virus);
	}
	
	public void addInfectible(Infectible infectible) {
		infectibles.add(infectible);
	}
	
	public void addVaccine(Vaccine vaccine) {
		vaccines.add(vaccine);
	}
	
	public int getBoardSize() {
		return boardSize;
	}
	
	public List<Virus> getViruses(){
		return viruses;
	}
	
	public List<Infectible> getInfectibles(){
		return infectibles;
	}
	
	public List<Vaccine> getVaccines(){
		return vaccines;
	}

	public int getMoveLeft() {
		return moveLeft;
	}
}
