package physics;

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
	/** X Position of the Physics Object */
	private double xPosition;
	/** Y Position of the Physics Object */
	private double yPosition;
	/** X Speed of the Physics Object */
	private double xSpeed;
	/** X Direction of the Physics Object */
	private int xDirection;
	
	public static final double STANDARD_GRAVITY = 2.579;
	public static final double STANDARD_INITIAL_VELOCITY = 10.5;
	
	/** If we should be calculating the jumping velocity or not */
	private boolean isJumping;
	
	
	/**
	 * Constructs a new PhysicsController with given g0 and v0.
	 */
	public PhysicsController(double g0, double v0) {
		this.g0 = g0;
		this.v0 = v0;
		isJumping = false;
	}
	
	/**
	 * Constructs a new PhysicsController with default values.
	 */
	public PhysicsController() {
		this.g0 = STANDARD_GRAVITY;
		this.v0 = 0;
	}
	
	/**
	 * Updates the position of the Physics Object
	 */
	public void onTick() {
		
		if(isJumping) {
			++timeElapsed;
			yPosition += evaluateYPositionDelta();
		}
		
		/*********************
		 * CHANGE THIS LATER *
		 *********************/
		if(yPosition < 0) {
			yPosition = 0;
			stopFalling();
		}
		
		xPosition += evaluateXPositionDelta();
	}
	
	public void startJumping() {
		if(!(isJumping)) {
			isJumping = true;
			v0 = STANDARD_INITIAL_VELOCITY;
		}
	}
	
	public void startFalling() {
		isJumping = true;
		v0 = 0; //No initial velocity
	}
	
	public void stopFalling() {
		isJumping = false;
		resetTimeElapsed();
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
		return (g0 * timeElapsed) + v0;
	}
}