package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.bme.mit.train.sensor.TrainSensorImpl;

public class TrainSensorTest {

    TrainSensorImpl sensor;

    @Before
    public void before() {
		sensor = new TrainSensorImpl();
    }

    @Test
    public void testDefaultSpeedLimitValue(){
        Assert.assertEquals(5, sensor.getSpeedLimit());
    }

    @Test
    public void testOverrideSpeedLimitValue() {
        sensor.overrideSpeedLimit(120);
        Assert.assertEquals(120, sensor.getSpeedLimit());
    }
}