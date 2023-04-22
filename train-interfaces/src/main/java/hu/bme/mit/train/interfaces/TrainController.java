package hu.bme.mit.train.interfaces;

public interface TrainController {

	public void runSimulation() throws InterruptedException;

	public void startSimulation();

	public void stopSimulation();

	void followSpeed();

	int getReferenceSpeed();

	void setSpeedLimit(int speedLimit);

	void setJoystickPosition(int joystickPosition);

	void emergencyBreak();

}
