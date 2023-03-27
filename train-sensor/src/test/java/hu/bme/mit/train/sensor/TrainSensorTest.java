package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainController controller;
	TrainSensor sensor;
	TrainUser user;

    @Before
    public void before() {
        // TODO Add initializations
        TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();
    }

    @Test
    public void testOverrideSpeedLimit() {
        sensor.overrideSpeedLimit(120);
        Assert.assertEquals(120, sensor.getSpeedLimit());
    }
}