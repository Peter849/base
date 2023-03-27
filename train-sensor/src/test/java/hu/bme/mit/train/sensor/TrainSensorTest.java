package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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

    @Test
    public void testToTachograph(){
        String dateString = "2023-03-27";
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date rightTime = dateFormat.parse(dateString);
             int joystickPosition = 10;
            int referenceSpeed = 90;
            sensor.putToTachograph(rightTime, joystickPosition, referenceSpeed);
            int rF = sensor.getTachograph().get(rightTime, joystickPosition);
            Assert.assertEquals(90, rF);
        }catch (ParseException e) {
            e.printStackTrace();
        }    
    }

    @Test
    public void testRemoveFromTachograph(){
                String dateString = "2023-03-27";
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date rightTime = dateFormat.parse(dateString);
            int joystickPosition = 10;
            int referenceSpeed = 90;
            sensor.putToTachograph(rightTime, joystickPosition, referenceSpeed);
            sensor.removeFromTachograph(rightTime, joystickPosition);

            int rF = 0;
            if(sensor.getTachograph().get(rightTime, joystickPosition) == null){
                rF = Integer.MIN_VALUE;
            }else{
                rF = sensor.getTachograph().get(rightTime, joystickPosition);
            }
            Assert.assertEquals(Integer.MIN_VALUE, rF);
        }catch (ParseException e) {
            e.printStackTrace();
        }   
    }

}