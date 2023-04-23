package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.sensor.TrainSensorImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.File;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    TrainSensorImpl sensor;

    //Member variables for unit tests of the alarm with mockito:
    TrainController mockitoController;
    TrainUser mockitoUser;

    @Before
    public void before() {
        mockitoController = mock(TrainController.class);
		mockitoUser = mock(TrainUser.class);
        
        sensor = new TrainSensorImpl();
        sensor = new TrainSensorImpl(mockitoController, mockitoUser);
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


    //Testcases of the alarm feature:
    /*In this testcase I set the new speed limit to -1, which is less then 0, so the alarm must be raised.*/
    @Test
    public void overrideSpeedLimit_AbsoluteSpeedLimitToLow_AlarmRaised() {
        when(mockitoController.getReferenceSpeed()).thenReturn(100);
        sensor.overrideSpeedLimit(-1);
        verify(mockitoUser,times(1)).setAlarmState(true);
    }

    /*In this testcase I set the new speed limit to 501, which is more then 500, so the alarm must be raised.*/
    @Test
    public void overrideSpeedLimit_AbsoluteSpeedLimitToHigh_AlarmRaised() {
        when(mockitoController.getReferenceSpeed()).thenReturn(100);
        sensor.overrideSpeedLimit(501);
        verify(mockitoUser,times(1)).setAlarmState(true);
    }

    /*In this testcase I set the new speed limit to 10, which is less between 0 and 500, so the alarm must NOT be raised.*/
    @Test
    public void overrideSpeedLimit_AbsoluteSpeedLimitIsValid_AlarmNOTRaised() {
        when(mockitoController.getReferenceSpeed()).thenReturn(20);
        sensor.overrideSpeedLimit(10);
        verify(mockitoUser,times(0)).setAlarmState(true);
    }

    /*In this testcase I set the new speed limit to 249, and the reference speed is 500
    currently. In this situation the new speed limit is less than the half of the reference speed,
    so the alarm must be raised. */
    @Test
    public void overrideSpeedLimit_RelativeSpeedLimitToLow_AlarmRaised() {
        when(mockitoController.getReferenceSpeed()).thenReturn(500);
        sensor.overrideSpeedLimit(249);
        verify(mockitoUser,times(1)).setAlarmState(true);
    }

    /*In this testcase I set the new speed limit to 250, and the reference speed is 500
    currently. In this situation the new speed limit is NOT less than the half of the reference speed,
    so the alarm must NOT be raised. */
    @Test
    public void overrideSpeedLimit_RelativeSpeedLimitIsValid_AlarmNOTRaised() {
        when(mockitoController.getReferenceSpeed()).thenReturn(500);
        sensor.overrideSpeedLimit(250);
        verify(mockitoUser,times(0)).setAlarmState(true);
    }

    /*In this testcase both limit value check is invalid, so the alarm must be raised. */
    @Test
    public void overrideSpeedLimit_AbsoluteANDRelativeSpeedLimitIsNOTValid_AlarmRaised() {
        when(mockitoController.getReferenceSpeed()).thenReturn(501);
        sensor.overrideSpeedLimit(10);
        verify(mockitoUser,times(1)).setAlarmState(true);
    }
}   