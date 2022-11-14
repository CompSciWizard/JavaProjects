/**
 * @author James Coburn, QuonDarius Woody  
 * N#:n01469324, n01481589
 * @date 12/8/21
 *
 */
import java.awt.*; //Contains all of the classes for creating user interfaces and for painting graphics and images

import java.awt.event.*; //Provides interfaces and classes for dealing with different types of events fired by AWT components.

import javax.swing.*; //Provides a set of "lightweight" (all-Java language) components that, to the maximum degree possible, work the same on all platforms.

import java.util.Random; //Randomization and Timer 

import java.awt.event.ActionListener; //for specifics

public class GamePanel extends JPanel implements ActionListener{ //interface within used by the packages

	//value can not be changed when final, static allows you to set the value during instances
	//setting the requirements and necessities to establish an image
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE  = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	
	//
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	
	//snake starts off with 6 pieces 
	int bodyParts = 6;
	
	//keeps a count of the score 
	int applesEaten;
	
	//coordinates for the apple
	int appleX;
	int appleY;
	
	//starts at the top left side of the screen and goes right 'R'
	char direction = 'R';
	//If program is running 
	boolean running = false;
	
	Timer timer; //time constraints 
	Random random; //randomization
	
	
	GamePanel(){
		
		//randomizies the position everytime the game is ran 
		random = new Random(); 
		
		//this.something refers back to the package the constructor is in 
		//user Interface is assigned a preferred size by constructing a Dimension and passes in the values we established
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		//Sets the background color of the component
		this.setBackground(Color.PINK);
		// sets the focusable state of the component
		this.setFocusable(true);
		//Adds the specified key listener to receive key events from this component (refers back to MykeyAdapter class)
		this.addKeyListener(new MyKeyAdapter());
		//calls StartGame 
		startGame();
		
	}
	
public void startGame() {
	//calls the newapple method in order to establish the randomized coordinates of our apple
	newApple();
	
	//If the game starts our timer is started
	running = true;
	
	//Creates a timer 
	timer = new Timer(DELAY, this);
	
	//Once timer runs it starts to send action events to our listener. This indicates we can play the game
	timer.start();
	
	
	}
	
//Calls the UI delegate's paint method, if the UI delegate is non-null. 
//We pass the delegate a copy of the Graphics object to protect the
//rest of the paint code from irrevocable changes.
//Basically we can assign colors and manipulate them

//The Graphics class is the abstract base class for all graphics contexts that allow 
//an application to draw onto components.
//that are realized on various devices, as well as onto off-screen images. 
// Gives us the ability

	public void paintComponent(Graphics g) {
	super.paintComponent(g);	
	draw(g);

	//Produces the action of doing so by calling methods and pre-defined constructors
	}
	
	public void draw(Graphics g) {
	
	//If game is running which true then we go ahead and assign our colors, positions and other features
	if(running) {
		
		//Implies that Apple is red and is colored within a certain position which is specified by fillOval(oval created)
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
	
	// allows the snake to increment when it eats an apple at a given position Implies it is referring to bodyParts
		for(int i = 0; i < bodyParts; i++) {
			if(i == 0) {
				//The head beings as green at the start of the game and fills it to that color at a given position (makes rectangle)
				g.setColor(Color.GREEN);
				g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
				}
			else {
				//Otherwise it will asign it a random color at a given position
				g.setColor(new Color(45,180,0));
				g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
				g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
		
		//Sets the ScoreBoard of apples eaten while creating the graphical features of it
		g.setColor(Color.white);
		g.setFont(new Font("default",Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		}
	
	//Once game is not running anymore it will go to the post game features which is score and etc.
		else {
			gameOver(g);
		}
	}
	
	public void newApple() {
		//refers back to the instance of random so that the coordinates of the apple can be different everytime it is ran or eaten 
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() {
		
		//allows the movement of the snake to be a unit
		for(int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		//Implies the direction with the cases on your keyboard and executes the movement 
		//direction is manipulated through the KeyAdapter
		switch(direction) {
		//Up
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
		//Down
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
		//Left	
			x[0] = x[0] - UNIT_SIZE;
			break;
		//Right	
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	
	public void checkApple() {
		//if head of snake correlates with apple's coordinates it will increment the body of the snake and the number of apples eaten
		// Once it has done this the apple will spawn somewhere different
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			//selects a different position for the apple to spawn 
			newApple();
			
		}
		
		
	}
	
	public void checkCollisions() {
		//checks if head collides with body
		for(int i = bodyParts;i>0;i--) {
			if(x[0] == x[i]&& (y[0] == y[i])) {
				running = false;
			}
		}
	//check if head touches left border	
		if(x[0] < 0) {
			running = false;
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
	}
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//score
		g.setColor(Color.white);
		g.setFont(new Font("Futura",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		//draws a specified string
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		//GameOver text 
	
		g.setColor(Color.red);
		g.setFont(new Font("Futura",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		//draws a specified string
		g.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		}
	
	
	
	@Override
	//Invokes when an action occurs 
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(running) {
			//If game is running it executes the methods which are components of the game
			move();
			checkApple();
			checkCollisions();
			
		}
		//repaints the component 
		repaint();
		
	}
	
		//This allows the program to know when keys are pressed and what direction they go in and it assigns it that direction 
		//Recieves keyboard events
	public class MyKeyAdapter extends KeyAdapter{
			@Override
			
			//key Event is apart of the event package which indicates when a keystroke has occured
			public void keyPressed(KeyEvent e) {
				//passes in an integer keyCode associated with the key in this event
				switch(e.getKeyCode()) {
				//VK_LEFT is a constant for the non-numpad left arrow key 
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;
					//VK_RIGHT is a constant for the non-numpad right arrow key 
				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;
					//VK_LEFT is a constant for the non-numpad Up arrow key 
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
				//VK_LEFT is a constant for the non-numpad Down arrow key 
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
		}
}
	