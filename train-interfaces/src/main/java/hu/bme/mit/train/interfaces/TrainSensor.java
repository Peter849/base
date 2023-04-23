package hu.bme.mit.train.interfaces;

import java.util.Date;

import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;

public interface TrainSensor {

	int getSpeedLimit();

	void overrideSpeedLimit(int _speedLimit);

	Table<Date, Integer, Integer> getTachograph();

	void putToTachograph(Date rightTime, int joystickPosition, int referenceSpeed);

	void removeFromTachograph(Date rightTime, int joystickPosition);

	boolean checkAbsoluteLimit(int _speedLimit);

	boolean checkRelativeLimit(int _speedLimit);
}
