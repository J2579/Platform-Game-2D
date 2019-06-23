package physics;

import gui.GUI;
import player.Player;

/**
 * Controls a PhysicsObject's Physics
 * 
 * @author J2579
 * @version 1.0.0
 * @since 2019-06-23
 */
public class PhysicsController {
	/** Gravity of the Physics Object (m/s/s) */
	private double g0;
	/** Initial velocity of the Physics Object (m/s) */
	private double v0;
	/** Time elapsed since initial velocity */
	private double timeElapsed;
	
	/** X-Speed of the Physics Object */
	private double xSpeed;
	/** X Direction of the Physics Object */
	private int xDirection;
	
	
	/** X Position of the Physics Object */
	private double xPosition;
	/** Y Position of the Physics Object */
	private double yPosition;
	
	/** Number of jumps allowed */
	private int jumpsAllowed;
	/** Number of jumps taken before landing */
	private int jumpsRemaining;
	
	public static final double STANDARD_GRAVITY = 1.3;
	public static final double STANDARD_INITIAL_VELOCITY = 20;
	public static final double STANDARD_X_SPEED = 6.5;
	
	/*************************************
	 * Position is calculated at the     *
	 * BOTTOM-LEFT of the player hitbox. *
	 *************************************/
	
	public static final double LEFT_BORDER = 0.0;
	public static final double RIGHT_BORDER = (GUI.CANVAS_WIDTH - Player.PLAYER_WIDTH);
	public static final double BOTTOM_BORDER = 0.0;
	public static final double TOP_BORDER = GUI.CANVAS_HEIGHT; //Player can be above the screen (for transition animation)
	public static final int SPACER = 15;
	
	/** If we should be calculating the jumping velocity or not */
	private boolean isJumping;
	
	
	/**
	 * Constructs a new PhysicsController with given g0 and v0.
	 */
	public PhysicsController(double g0, double v0, double xSpeed, int jumpsAllowed) {
		this.g0 = g0;
		this.v0 = v0;
		isJumping = false;
		this.xSpeed = xSpeed;
		this.jumpsAllowed = jumpsAllowed;
		this.jumpsRemaining = jumpsAllowed;
	}
	
	/**
	 * Constructs a new PhysicsController with default values.
	 */
	public PhysicsController() {
		this.g0 = STANDARD_GRAVITY;
		this.v0 = 0;
		this.xSpeed = STANDARD_X_SPEED;
		this.jumpsAllowed = 2;
		this.jumpsRemaining = jumpsAllowed;
	}
	
	/**
	 * Updates the position of the Physics Object
	 */
	public void onTick() {
		
		//If we're in the air, calculate our movement per tick
		if(isJumping) {
			++timeElapsed;
			yPosition += evaluateYPositionDelta();
		}
		
		//Bind our Y-Movement to the constraints of the screen
		if(yPosition < BOTTOM_BORDER) {
			yPosition = 0;
			stopFalling();
		}
		else if(yPosition > TOP_BORDER) {
			yPosition = TOP_BORDER;
			startFalling(); //Remove any remaining upward momentum
		}
		
		
		xPosition += evaluateXPositionDelta();
		
		//Bind our X-Movement to the constraints of the screen
		if(xPosition < LEFT_BORDER) {
			xPosition = LEFT_BORDER;
		}
		else if(xPosition > RIGHT_BORDER) {
			xPosition = RIGHT_BORDER;
		}
	}
	
	public void jump() {
		if(jumpsRemaining > 0) {
			isJumping = true;
			v0 = STANDARD_INITIAL_VELOCITY;
			resetTimeElapsed();
			--jumpsRemaining;
		}
	}
	
	public void startFalling() {
		isJumping = true;
		v0 = 0; //No initial velocity
	}
	
	public void stopFalling() {
		isJumping = false;
		resetTimeElapsed();
		jumpsRemaining = jumpsAllowed;
		
	}
	
	protected void setPosition(double x, double y) {
		this.xPosition = x;
		this.yPosition = y;
	}
	
	/**
	 * Passes a Position object back to the GUI to draw
	 */
	public Position getPosition() {
		return new Position(xPosition, yPosition);
	}
	
	private void resetTimeElapsed() {
		timeElapsed = 0;
	}
	
	/**
	 * Sets the xDirection
	 */
	public void setDirection(int direction) {
		this.xDirection = direction;
	}
	
	/**
	 * Returns the differential of the X Position for a given tick.
	 * 
	 * @return The differential of the X Position for a given tick.
	 */
	private double evaluateXPositionDelta() {
		return xSpeed * xDirection;
	}

	/**
	 * Returns the derivative of the gravity function for given g0 and v0.
	 * We don't have to factor in h0, because the derivative of a constant
	 * is always 0!
	 * 
	 * @return The differential of the Y Position for a given tick.
	 */
	private double evaluateYPositionDelta() {
		return -1 * (g0 * timeElapsed) + v0;
	}
	
	
	/*******************************************
	 * BELOW HERE IS DEBUG STUFF: REMOVE LATER *
	 *******************************************/
	
	@Deprecated
	public int getDirection() {
		return xDirection;
	}
	
	@Deprecated
	public int getJumpsRemaining() {
		return jumpsRemaining; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}