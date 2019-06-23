package player;

import java.awt.event.KeyEvent;

import helper.Helper;
import physics.PhysicsController;
import physics.Position;

public class Player {
	/** The player's physics */
	private PhysicsController physicsController;
	/** Keyboard State */
	private boolean[] keyboardState;
	
	public static final int PLAYER_WIDTH = 110;
	public static final int PLAYER_HEIGHT = 25;
	
	/* Change these to be modifiable later */
	public static final int LEFT_KEY = KeyEvent.VK_LEFT;
	public static final int RIGHT_KEY = KeyEvent.VK_RIGHT;
	public static final int JUMP_KEY = KeyEvent.VK_SPACE;
	
	public static final int LEFT_INDEX = 0;
	public static final int RIGHT_INDEX = 1;
	public static final int JUMP_INDEX = 2;
	
	public Player() {
		keyboardState = new boolean[]{false, false, false};
		physicsController = new PhysicsController();
	}

	public void handleKeyReleased(int keyCode) {
		switch(keyCode) {
		
			case LEFT_KEY:  keyboardState[LEFT_INDEX] =  false;
														 break;				
			case RIGHT_KEY: keyboardState[RIGHT_INDEX] = false;
														 break;
		}
	}

	public void handleKeyPressed(int keyCode) {
		switch(keyCode) {

			case LEFT_KEY:  keyboardState[LEFT_INDEX] =  true;
														 break;				
			case RIGHT_KEY: keyboardState[RIGHT_INDEX] = true;
														 break;
			case JUMP_KEY:  keyboardState[JUMP_INDEX] =  true;
														 break;
		}
	}
	
	public void onTick() {
		handlePhysics();
		
	//	System.out.println(Helper.boolArrayToString(keyboardState));
	//	System.out.println(physicsController.getDirection());
	//	System.out.println(physicsController.getJumpsRemaining());
	
	}
	
	private void handlePhysics() {
		
		physicsController.onTick();
		
		/* 
		 * Handle the X-Movement 
		 */
		
		int leftPressed = Helper.encodeBoolean(keyboardState[LEFT_INDEX]);
		int rightPressed = Helper.encodeBoolean(keyboardState[RIGHT_INDEX]);
		
		if(leftPressed + rightPressed != 1) { //T-T || F-F 
			physicsController.setDirection(0);
		}
		else if(leftPressed == 1) {
			physicsController.setDirection(-1); //T-F
		}
		else {
			physicsController.setDirection(1); //F-T
		}
		
		/* 
		 * Handle the Y-Movement 
		 */
		if(keyboardState[JUMP_INDEX]) {
			physicsController.jump();
			keyboardState[JUMP_INDEX] = false;
		}
		
	}
	
	public Position getPosition() {
		return physicsController.getPosition();
	}
	
	
	
	
	
	
	
	
	
}

