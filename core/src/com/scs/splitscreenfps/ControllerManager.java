package com.scs.splitscreenfps;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;

public class ControllerManager {

	public List<Controller> knownControllers = new ArrayList<Controller>();
	private List<Controller> controllersAdded = new ArrayList<Controller>();
	private List<Controller> controllersRemoved = new ArrayList<Controller>();

	private long lastCheckTime;
	
	public ControllerManager() {
	}


	public void checkForControllers() {
		if (lastCheckTime + 4000 > System.currentTimeMillis()) {
			return;
		}
		
		Array<Controller> controllers = Controllers.getControllers();
		for (Controller controller : controllers) {
			if (knownControllers.contains(controller) == false) {
				knownControllers.add(controller);
				if (controller.getName().toLowerCase().indexOf("keyboard") < 0) {
					controllersAdded.add(controller);
				}
				//p("Controller added: " + controller);
			}
		}

		// Removed controllers
		for (Controller knownController : this.knownControllers) {
			boolean found = false; 
			for (Controller controller : controllers) {
				if (controller == knownController) {
					found = true;
					break;
				}
			}
			if (found == false) {
				this.knownControllers.remove(knownController);
				this.controllersRemoved.add(knownController);
			}
		}
	}

}