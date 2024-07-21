package screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import objects.Infectible;
import objects.MapObject;
import objects.ObjectButton;
import objects.ObjectState;
import objects.infectious.Vaccine;
import objects.infectious.Virus;

import spred.FontLoader;
import spred.Level;
import spred.WindowGame;

public class Board extends Screen {

    private static final long serialVersionUID = 1L;
    private static final int MIN_BOARD_SIZE = 6;
    private static final int ZOOM_STEP = 3;

    private int boardSize = 0;
    private int maxBoardSize = 21;
    private int buttonSize = 0;  // Initial button size
    private int x = 0, y = 0;
    private ObjectButton[][] buttons;
    private JPanel boardPanel;
    private GridBagConstraints gbc = new GridBagConstraints();
    
    private JPanel zoomPanel = new JPanel(new GridLayout(2, 1, 0, 10)); 
    private JPanel movePanel = new JPanel(new GridLayout(2, 10, 0, 1)); 
    
    private List<Virus> viruses;
    private List<Infectible> infectibles;
    private List<Vaccine> vaccines;
    private int moveLeft = 0;
    private int maxMove;
    
    private Map<Integer, List<MapObject>> undoMap = new HashMap<>();
    
    private Font font1, font2;
    private BufferedImage keyIns;

    public Board(WindowGame window, Level level) {
        super(window);
        setLayout(new GridBagLayout());
        
        setImage("initial.jpg");
        
        initiate(level);
        
        font1 = FontLoader.loadFont("ShopeeDisplay-Medium.ttf");
		font1 = FontLoader.modify(font1, 35, Font.TRUETYPE_FONT);
		
		font2 = FontLoader.loadFont("ShopeeDisplay-Medium.ttf");
		font2 = FontLoader.modify(font2, 25, Font.TRUETYPE_FONT);
		
		setUpKeyBinds();
    }

	private void initiate(Level level) {
    	this.viruses = level.getViruses();
    	this.infectibles = level.getInfectibles();
    	this.vaccines = level.getVaccines();
    	this.moveLeft = level.getMoveLeft();
    	maxMove = moveLeft;
    	
    	boardSize = level.getBoardSize();
        maxBoardSize = boardSize;
        buttonSize = 630/boardSize;
        
        keyIns = ImageLoader.loadImage("keyins.png");

        // Create and set up the board panel
        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        boardPanel.setOpaque(false);
        boardPanel.setBorder(new LineBorder(Color.BLACK, 5));

        // Create the grid of buttons
        buttons = new ObjectButton[maxBoardSize][maxBoardSize];
        for (int i = 0; i < maxBoardSize; i++) {
            for (int j = 0; j < maxBoardSize; j++) {
                buttons[i][j] = createBoardButton(i, j);
                boardPanel.add(buttons[i][j]);
            }
        }
        
        //set up the viruses, infectibles and vaccines
        for(Virus virus : viruses) {
        	virus.linkBoard(this);
        	buttons[virus.getX()][virus.getY()].setObject(virus);
        }
        
        for(Infectible inf : infectibles) {
        	buttons[inf.getX()][inf.getY()].setObject(inf);
        }
        
        for(Vaccine vaccine : vaccines) {
        	//buttons[inf.getX()][inf.getY()].setObject(inf);
        }

        // Set up GridBagConstraints for centering the boardPanel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        add(boardPanel, gbc);

        // Create and add zoom buttons
        zoomPanel.setOpaque(false);

        JButton zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtonSize(ZOOM_STEP);
            }
        });
        zoomPanel.add(zoomOutButton);

        JButton zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeButtonSize(-ZOOM_STEP);
            }
        });
        zoomPanel.add(zoomInButton);
        
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        zoomPanel.add(undoButton);

        // Set up GridBagConstraints for the zoomPanel
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        add(zoomPanel, gbc);
        
        JButton goLeftButton = new JButton("Left");
        goLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setY(y - 1);
                updateBoard();
            }
        });
        movePanel.add(goLeftButton);

        JButton goRightButton = new JButton("Right");
        goRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setY(y + 1);
                updateBoard();
            }
        });
        movePanel.add(goRightButton);
        
        JButton goDownButton = new JButton("Down");
        goDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setX(x + 1);
                updateBoard();
            }
        });
        movePanel.add(goDownButton);

        JButton goUpButton = new JButton("Up");
        goUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setX(x - 1);
                updateBoard();
            }
        });
        movePanel.add(goUpButton);

        // Set up GridBagConstraints for the zoomPanel
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        add(movePanel, gbc);
	}
    
	private ObjectButton createBoardButton(int x, int y) {
		ObjectButton button = new ObjectButton(x, y);
        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
        button.setMinimumSize(new Dimension(buttonSize, buttonSize));
        button.setMaximumSize(new Dimension(buttonSize, buttonSize));
        return button;
    }

    private void changeButtonSize(int delta) {
        int newSize = boardSize + delta;
        if (newSize >= MIN_BOARD_SIZE && newSize <= maxBoardSize) {
            boardSize = newSize;
            buttonSize = 630/boardSize;
            setX(x);
            setY(y);
            updateBoard();
        }
    }

    private void updateBoard() {
    	remove(boardPanel);
    	boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        boardPanel.setOpaque(false);
        boardPanel.setBorder(new LineBorder(Color.BLACK, 5));  // Border around the board
        
        for (int i = x; i < x + boardSize; i++) {
            for (int j = y; j < y + boardSize; j++) {
            	ObjectButton button = buttons[i][j];
    	        button.setPreferredSize(new Dimension(buttonSize, buttonSize));
    	        button.setMinimumSize(new Dimension(buttonSize, buttonSize));
    	        button.setMaximumSize(new Dimension(buttonSize, buttonSize));
    	        boardPanel.add(button);
                boardPanel.add(buttons[i][j]);
            }
        }
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        add(boardPanel, gbc);
        
        revalidate();
        repaint();
    }
    
    @Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(Color.white);
		g.setFont(font2);
    	g.drawString("Moves left: " + moveLeft, 1000, 500);
    	
    	g.setFont(font1);
    	g.drawString("Level " + window.getCurrLevel(), 1000, 450);
    	
    	g.drawImage(keyIns, 950, 50, null);
    	
    	if(isVictory()) {
			window.winLevel();
		}
	}

	public void undo() {
		if(moveLeft >= maxMove) return;
		moveLeft++;
		List<MapObject> objList = undoMap.getOrDefault(moveLeft, new ArrayList<MapObject>());
		for(MapObject obj : objList) {
			buttons[obj.getX()][obj.getY()].setObject(obj);
		}
		objList.clear();
	}
	
	private boolean isVictory() {
		if(moveLeft >= 0) {
			for(int i = 0; i < maxBoardSize; i++) {
				for(int j = 0; j < maxBoardSize; j++) {
					if(buttons[i][j].getState() != ObjectState.VIRUS && buttons[i][j].getState() != ObjectState.NONE) {
						return false;
					}
				}
			}
		} else return false;
		return true;
	}
    
    public void decreaseMoves() {
    	moveLeft--;
    }
    
	public int getX() {
		return x;
	}

	public void setX(int x) {
		if(x >= 0 && x + boardSize <= maxBoardSize)
			this.x = x;
		else if(x < 0)
			this.x = 0;
		else this.x = maxBoardSize - boardSize;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y >= 0 && y + boardSize <= maxBoardSize)
			this.y = y;
		else if(y < 0)
			this.y = 0;
		else this.y = maxBoardSize - boardSize;
	}
    
	public ObjectButton[][] getBoard(){
		return buttons;
	}
	
	public int getMaxSize() {
		return maxBoardSize;
	}
	
	public List<Virus> getViruses(){
		return viruses;
	}
	
	public Map<Integer, List<MapObject>> getUndoMap(){
		return undoMap;
	}
	
	public int getMove() {
		return moveLeft;
	}

    private void setUpKeyBinds() {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false),
                "moveLeft");
		getActionMap().put("moveLeft",
				new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	setY(y - 1);
                updateBoard();
		    }
		});
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),
                "moveRight");
		getActionMap().put("moveRight",
				new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	System.out.print("working");
		    	setY(y + 1);
                updateBoard();
		    }
		});
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false),
                "moveUp");
		getActionMap().put("moveUp",
				new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	setX(x - 1);
                updateBoard();
		    }
		});
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false),
                "moveDown");
		getActionMap().put("moveDown",
				new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	setX(x + 1);
                updateBoard();
		    }
		});
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),
                "zoomOut");
		getActionMap().put("zoomOut",
				new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
                changeButtonSize(ZOOM_STEP);
            }
		});
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false),
                "zoomIn");
		getActionMap().put("zoomIn",
				new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
                changeButtonSize(-ZOOM_STEP);
            }
		});
		
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK, false),
                "undo");
		getActionMap().put("undo",
				new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
                undo();
            }
		});
		
    	setFocusable(true);
    	requestFocusInWindow();
	}
}
