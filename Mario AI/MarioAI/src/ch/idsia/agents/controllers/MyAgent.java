
package ch.idsia.agents.controllers;

import ch.idsia.agents.Agent;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;


public class MyAgent extends BasicMarioAIAgent implements Agent
{

	public MyAgent()
	{
		super("MyAgent");
		reset();
	}

	// Does (row, col) contain an enemy?   
	public boolean hasEnemy(int row, int col) {
		return enemies[row][col] != 0;
	}

	// Is (row, col) empty?   
	public boolean isEmpty(int row, int col) {
		return (levelScene[row][col] == 0);
	}




	// Display Mario's view of the world
	public void printObservation() {
		System.out.println("**********OBSERVATIONS**************");
		for (int i = 0; i < mergedObservation.length; i++) {
			for (int j = 0; j < mergedObservation[0].length; j++) {
				if (i == mergedObservation.length / 2 && j == mergedObservation.length / 2) {
					System.out.print("M ");
				}
				else if (hasEnemy(i, j)) {

					System.out.print("E" + i + " " + j);
				}
				else if (!isEmpty(i, j)) {
					System.out.print("B "+ i + " " + j);
				}
				else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println("************************");
	}

	// Actually perform an action by setting a slot in the action array to be true
	public boolean[] getAction()
	{


// if (!isEmpty(9, 10)) {

// 	action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
// }

//  if ((hasEnemy(9,11))||(hasEnemy(10,11))||(hasEnemy(12,11))||(hasEnemy(9,13))||(hasEnemy(9,12))){
 	


// action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;

//  }

//  if (hasEnemy(9,10)) {
//  	action[Mario.KEY_RIGHT] = false;
//  		action[Mario.KEY_LEFT] = true;
//  	action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
//  }else {
//  	action[Mario.KEY_RIGHT] = true;
//  	action[Mario.KEY_LEFT] = false;
//  }

//what states do you need?

 //if dude is above mario --> go left

 if ((hasEnemy(8,12))||(hasEnemy(8,11))||(hasEnemy(7,12))||(hasEnemy(7,11))||(hasEnemy(6,11))||(hasEnemy(6,12))||(hasEnemy(7,10))||(hasEnemy(6,10))||(hasEnemy(8,10))) {
 	action[Mario.KEY_RIGHT] = false;
 	action[Mario.KEY_LEFT] = true;
 }

  //if dude is below mario --> go left

 else if ((hasEnemy(10,12))||(hasEnemy(10,13))||(hasEnemy(10,10))||(hasEnemy(10,11))&&(isEmpty(9, 10))) {
 	action[Mario.KEY_RIGHT] = false;
 	action[Mario.KEY_LEFT] = true;
 	action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;

 }
else if ((hasEnemy(11,12))||(hasEnemy(11,13))||(hasEnemy(12,10))||(hasEnemy(12,11))&&(isEmpty(11, 10))) {
 	action[Mario.KEY_RIGHT] = false;
 	action[Mario.KEY_LEFT] = true;
 	

 }

 //if dude is right of mario --> jump

else if ((hasEnemy(9,11))||(hasEnemy(9,12))||(hasEnemy(9,9))||(hasEnemy(9,10))||(hasEnemy(9,13))){
 	
action[Mario.KEY_LEFT] = false;
action[Mario.KEY_RIGHT] = true;
action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;

 }
 //if block is right of mario -- > jump

 else if (!isEmpty(9, 10)||!isEmpty(9, 11)) {

 	if ( isEmpty(10, 10)) {
    action[Mario.KEY_RIGHT] = true;
 	action[Mario.KEY_LEFT] = false;
}else  {
	
	action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;
	action[Mario.KEY_LEFT] = false;
	action[Mario.KEY_RIGHT] = true;
}
}
//if pit jump over
// else if ((isEmpty(10,11))&&(isEmpty(10,12))){
 	


// action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = isMarioAbleToJump || !isMarioOnGround;

//  }

 //otherwise right
		
 	

else{
	action[Mario.KEY_RIGHT] = true;
 	action[Mario.KEY_LEFT] = false;

}


		
        printObservation();
		return action;
	}

	// Do the processing necessary to make decisions in getAction
	public void integrateObservation(Environment environment)
	{
		super.integrateObservation(environment);
    	levelScene = environment.getLevelSceneObservationZ(2);
	}

	// Clear out old actions by creating a new action array
	public void reset()
	{
		action = new boolean[Environment.numberOfKeys];
	}
}
