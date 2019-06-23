package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import physics.Position;
import player.Player;

/**
* This class acts as a driver for the 2D Game
*
* @author J2579
* @version 1.1.0
* @since 2019-06-23
*/
public class GUI {
	private GameWindow gameWindow;
	private Timer tick;
	private Player player;
	
	//TODO: Change to class constants
	private int height = 600;
	private int width = 600;
	private int playerWidth = 110;
	private int playerHeight = 25;
	
	/**
	 * Initializes the GUI
	 * 
	 * @param args Unused
	 */
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.initialize();
	}
	
	/**
	* This method acts to initialize all of the relevant components
	* of the Game Window.
	*/
	public void initialize() {
		//Create the graphical window.
		gameWindow = new GameWindow(width,height); 
		gameWindow.setSize(width,height);
		gameWindow.setVisible(true);
		
		//Create the panel, containing the window.
		JPanel panel = new JPanel(new GridLayout(1,1)); //Make the panel
		panel.add(gameWindow);
		
		//Create the application frame
		
		//Initialize the frame, and add all required components (Panel, KeyListener)
		JFrame frame = new JFrame("2D Game"); //Make the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.addKeyListener(new GetKey());
		frame.pack();
		
		//Make the frame visible.
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setAlwaysOnTop(true);
		
		gameWindow.createAndSetBuffer();

		//Initialize our game components here
		
		//Create and start the game clock
		EventListener t = new EventListener();
		tick = new Timer(17,t); //1 Tick / 0.017 Seconds ~ 60 Ticks / 1 Second 
		tick.setRepeats(true);
		tick.start();
		
		player = new Player();
	}
	
	
	/**
	* This class acts as an implementation of ActionListener, which
	* fires whenever an (action) is performed in the GUI.
	*
	* @author William DeStaffan
	* @version 1.0.0
	* @since 2019-06-23
	*/
	private class EventListener implements ActionListener
	{
		/**
		* This method will be called whenever an action is performed 
		* on the GUI the ButtonListener is added to - the internal 
		* game clock, as well as the test button.
		*
		* @param event An event that occurs within the GUI
		*/
		
		public void actionPerformed(ActionEvent event) {
			//Get the event pointer once, then store it for access.
			Object ev = event.getSource(); 
			
			//On every 'tick' of the internal timer:
			if(ev == tick) {	
				player.onTick();
				gameWindow.update();
			}
		}	
	}	
	
	/**
	* This class acts as an implementation of KeyListener, which
	* fires whenever a key is pressed in the GUI.
	*
	* @author William DeStaffan
	* @version 1.0.0
	* @since 2019-04-17
	*/
	private class GetKey implements KeyListener
	{
		/**
		* This method activates whenever a key is pressed.
		* When fired, pass the corresponding direction back 
		* to the game engine.
		*
		* @param KeyEvent e Keyboard key pressed.
		*/
		public void keyPressed(KeyEvent e) {			
			player.handleKeyPressed(e.getKeyCode());
		}
		
		/**
		* This method activates whenever a key is released
		* When fired, pass the corresponding direction back
		* to the game engine.
		*
		* @param KeyEvent e Keyboard key released.
		*/
		
		public void keyReleased(KeyEvent e) {
			player.handleKeyReleased(e.getKeyCode());
		}
		
		/**
		* This method activates whenever a key is pressed,
		* and then released. While not implemented, the presence
		* of this method is required by the KeyListener interface.
		*
		* @param e Unused.
		*/
		
		public void keyTyped(KeyEvent e) {}
	}
	
	/**
	* This class creates a double-buffered AWT Canvas, able to be 
	* redrawn through an inherited abstract method 'draw'.
	*
	* @author J2579
	* @version 1.0.0
	* @since 2019-04-17
	*/
	private class GameWindow extends DoubleBufferedCanvas {
		
		/** Serial UID */
		private static final long serialVersionUID = 1L;

		/**
		* This method constructs the window, creating it from a width 
		* and height through the parent constructor.
		*
		* @param width This is the width of the Canvas.
		* @param height This is the height of the Canvas.
		*/
		public GameWindow(int width, int height) {
			super(width,height);
		}
		
		/**
		* This method draws the Graphics to the Canvas.
		* By iterating through every object on the screen, 
		*
		* @param g The double-buffer's graphics to draw on.
		*/
		public void draw(Graphics g) {
			g.setColor(Color.RED);
			Position position = player.getPosition();
			g.fillRect((int)position.getX(), (int)(height - position.getY() - playerHeight), playerWidth, playerHeight);
		}
	}
}
