package com.scs.splitscreenfps;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ControllerManager implements ControllerListener {

	private List<Controller> inGameControllers = new ArrayList<Controller>();
	private Array<Controller> allControllers = new Array<Controller>();
	//private List<Controller> controllersAdded = new ArrayList<Controller>();
	//private List<Controller> controllersRemoved = new ArrayList<Controller>();

	//private IControllerListener listener;
	private long lastCheckTime;

	public ControllerManager(ControllerListener listener) {
		Controllers.addListener(this);
		if (listener != null) {
			Controllers.addListener(listener);
		}
	}


	public Array<Controller> getAllControllers() {
		return allControllers;
	}


	public boolean isControllerInGame(Controller c) {
		synchronized (inGameControllers) {
			return this.inGameControllers.contains(c);
		}
	}


	public List<Controller> getInGameControllers() {
		return inGameControllers;
	}


	public void checkForControllers() {
		if (lastCheckTime + 4000 > System.currentTimeMillis()) {
			return;
		}

		synchronized (allControllers) {
			allControllers = Controllers.getControllers();
		}
		/*Array<Controller> controllers = Controllers.getControllers();
		for (Controller controller : controllers) {
			if (knownControllers.contains(controller) == false) {
				knownControllers.add(controller);
				//if (controller.getName().toLowerCase().indexOf("keyboard") < 0) {
					controllersAdded.add(controller);
				//}
				//p("Controller added: " + controller);
			}
		}

		// Removed controllers
		/*for (Controller knownController : this.knownControllers) {
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
		}*/
	}


	@Override
	public void connected(Controller controller) {
	}


	@Override
	public void disconnected(Controller controller) {
		synchronized (allControllers) {
			this.allControllers.removeValue(controller, true);
			this.inGameControllers.remove(controller);
		}

	}


	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		if (buttonCode == 1) {
			synchronized (inGameControllers) {
				if (this.inGameControllers.contains(controller) == false) {
					this.inGameControllers.add(controller);
				}
			}
		}
		return true;
	}


	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		return false;
	}


	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		return false;
	}


	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		return false;
	}


	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}


	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}


	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		return false;
	}

}