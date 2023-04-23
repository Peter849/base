package hu.bme.mit.train.sensor;

import java.io.*;
import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;
import java.lang.Integer;
import java.util.Date;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;
	//pontos ido, joystick pozicio, referencia sebesseg:
	Table<Date, Integer, Integer> tachograph;

	public TrainSensorImpl(){
		this.tachograph = HashBasedTable.create();
	}

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
		this.tachograph = HashBasedTable.create();
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int _speedLimit) {
		if(checkAbsoluteLimit(_speedLimit) || checkRelativeLimit(_speedLimit)) 
			this.user.setAlarmState(true);
			
		this.speedLimit = _speedLimit;
		if(this.controller!=null)controller.setSpeedLimit(_speedLimit);
	}

	@Override
	boolean checkAbsoluteLimit(int _speedLimit) {
		return _speedLimit < 0 || _speedLimit > 500;
	}

	@Override
	boolean checkRelativeLimit(int _speedLimit) {
		return _speedLimit < controller.getReferenceSpeed() * 0.5;
	}

	@Override
	public Table<Date, Integer, Integer> getTachograph(){
		return this.tachograph;
	}

	@Override
	public void putToTachograph(Date rightTime, int joystickPosition, int referenceSpeed){
		tachograph.put(rightTime, joystickPosition, referenceSpeed);
	}

	@Override
	public void removeFromTachograph(Date rightTime, int joystickPosition){
		tachograph.remove(rightTime, joystickPosition);
	}
}
