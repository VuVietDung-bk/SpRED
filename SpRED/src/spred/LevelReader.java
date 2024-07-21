package spred;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import objects.Infectible;
import objects.infectious.vaccines.VaccineFactory;
import objects.infectious.viruses.*;

public class LevelReader {
	
	public static Level scanLevel(String s) {
		File file = new File(System.getProperty("user.dir") + "\\data\\levels\\mainlevels\\" + s);
		Level level = new Level();
		try (Scanner sc = new Scanner(file)) {
			int moveLeft = sc.nextInt();
			int boardSize = sc.nextInt();
			level = new Level(moveLeft, boardSize);
			while(sc.hasNextLine()) {
				String str = sc.nextLine();
				if (str.isEmpty()) {
	                continue;
	            }
				if(str.equals("virus")){
					break;
				}
				String[] inf = str.split(",");
				int[] info = new int[3];
				for(int i = 0; i < 3; i++) {
					info[i] = Integer.parseInt(inf[i]);
				}
				for(int i = info[1]; i < info[2]; i++) {
					level.getInfectibles().add(new Infectible(info[0], i));
				}
			}
			while(sc.hasNextLine()) {
				String str = sc.nextLine();
				if (str.isEmpty()) {
					continue;
	            }
				if(str.equals("virus")) {
					continue;
				}
				if(str.equals("vaccine")){
					break;
				}
				String[] inf = str.split(",");
				int[] info = new int[3];
				for(int i = 0; i < 3; i++) {
					info[i] = Integer.parseInt(inf[i]);
				}
				level.getViruses().add(VirusFactory.createVirus(info[0], info[1], info[2]));
			}
			while(sc.hasNextLine()) {
				String str = sc.nextLine();
				if (str.isEmpty()) {
					break;
	            }
				if(str.equals("vaccine")) {
					continue;
				}
				String[] inf = str.split(",");
				int[] info = new int[3];
				for(int i = 0; i < 3; i++) {
					info[i] = Integer.parseInt(inf[i]);
				}
				level.getVaccines().add(VaccineFactory.createVaccine(info[0], info[1], info[2]));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			return null;
        } catch (IllegalArgumentException e) {
        	return null;
        }
		return level;
	}
	
	private void readCustomLevel(String str) {
		//file = new File(System.getProperty("user.dir") + "\\data\\levels\\customlevels\\" + str + ".txt");
	}
	
	public Level readLevel(int n){
		Level level = new Level();
		//read("level_" + Integer.toString(n) + ".txt");
		//level = scanLevel(level);
		return level;
	}
	
//	public void readData(WindowGame window) {
//		read("userdata.txt");
//		try {
//			if(file.createNewFile()) {
//				FileWriter writer = new FileWriter(file, true);
//				writer.write("0 0 0");
//				writer.close();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		int[] data = new int[3];
//		try (Scanner sc = new Scanner(file)){
//			for(int i = 0; i < 3; i++) {
//				data[i] = sc.nextInt();
//			}
//			window.setCurrLevel(data[0]);
//			window.setFlags(data[1]);
//			window.setTotalPoint(data[2]);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void updateData(int currLevel, int flags, int totalPoint) {
//		read("userdata.txt");
//		try {
//            FileWriter writer
//                = new FileWriter(file);
//            writer.write("");
//            writer.write(Integer.toString(currLevel) + " " + Integer.toString(flags) + " " + Integer.toString(totalPoint));
//            writer.close();
//        }
//        catch (IOException e) {
//        	//nothing
//        }
//	}
//	
//	public int[] readFlagPos(int num) {
//		read("flagpos.txt");
//		int[] arr = new int[2];
//		try (Scanner sc = new Scanner(file)){
//			while(num --> 0) {
//				sc.nextLine();
//			}
//			arr[0] = sc.nextInt();
//			arr[1] = sc.nextInt();
//		} catch (Exception e) {
//			return new int[] {0, 0};
//		}
//		return arr;
//	}
//	
//	public void saveCustomLevel(StringBuilder levelName, CustomLevel customLevel){
//		readCustomLevel(levelName.toString());
//		try {
//			//if the name exists
//			if(!file.createNewFile()) {
//				customLevel.setRepeatedName(true);
//				return;
//			} else customLevel.setRepeatedName(false);
//			
//			
//			try {
//	            FileWriter writer
//	                = new FileWriter(file);
//	            int sumBoard = -1;
//	    		for(int i = 0; i < customLevel.getBoard().length; i++) {
//	    			for(int j = 0; j < customLevel.getBoard()[0].length; j++) {
//	    				if(customLevel.getBoard()[i][j] > 0)
//	    					sumBoard += customLevel.getBoard()[i][j];
//	    			}
//	    		}
//	    		
//	    		int steps = sumBoard / 3;
//				int teleSteps = sumBoard % 3;
//	            writer.write(Integer.toString(customLevel.getX()) + " " + Integer.toString(customLevel.getY()) + " " + Integer.toString(steps) + " " + Integer.toString(teleSteps) + "\n");
//	            
//	            int num = 0;
//	            for(int i = 0; i < customLevel.getBoard().length; i++) {
//	            	int start = 0, end = 0;
//	    			for(int j = 0; j < customLevel.getBoard()[0].length; j++) {
//	    				if(customLevel.getBoard()[i][j] > 0) {
//	    					num = customLevel.getBoard()[i][j];
//	    					start = j;
//	    					while(++j < customLevel.getBoard()[0].length) {
//	    						if(customLevel.getBoard()[i][j] != num) {
//	    							end = j;
//	    							break;
//	    						}
//	    					}
//	    					writer.write(Integer.toString(i) + "," + Integer.toString(start) + "," + Integer.toString(end) + "," + Integer.toString(num) + "\n");
//	    					j--;
//	    				}
//	    			}
//	    		}
//	            
//	            if(customLevel.teleporters.size() != 0) {
//	            	writer.write("tele\n");
//	            	for(Teleporter t : customLevel.teleporters) {
//	            		writer.write(Integer.toString(t.startX) + "," + Integer.toString(t.startY) + "," + Integer.toString(t.endX) + "," + Integer.toString(t.endY) + "\n");
//	            	}
//	            }
//	            
//	            writer.close();
//	        }
//	        catch (IOException e) {
//	        	//nothing
//	        }
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
