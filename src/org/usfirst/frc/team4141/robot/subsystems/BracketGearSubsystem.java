package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.sensors.AnalogSensorReading;
import org.usfirst.frc.team4141.MDRobotBase.sensors.DualDistanceSensor;
import edu.wpi.first.wpilibj.Solenoid;

public class BracketGearSubsystem extends MDSubsystem {

	//--------------------------------------------------------//
	
	public enum Element {
		gearSolenoid
	}
	
	public enum State {
		on,
		off
	}

	private Solenoid gearSolenoid;

	private boolean gearSolenoidState = false;
	
//	private double approachDistanceSetting = 0;
	private double deliveryDistanceSetting = 0;
	private double withdrawalDistanceSetting = 0;
	private double gearDetectSetting = 0;
	
	private DualDistanceSensor dualDistanceSensor;

	private State state;
	
	//--------------------------------------------------------//
	
	public MDSubsystem configure(){
		super.configure();
		
		//--------------------------------------------------------//

		if(getSolenoids()==null 
				|| !getSolenoids().containsKey(Element.gearSolenoid.toString()) || !(getSolenoids().get(Element.gearSolenoid.toString()) instanceof Solenoid)) {
				throw new IllegalArgumentException("Invalid Gear Subsystem configuraton, missing pos2 solenoid.");
		}	
		gearSolenoid=(Solenoid) getSolenoids().get(Element.gearSolenoid.toString());
		
	    if(getSensors()==null && !getSensors().containsKey("dualDistanceSensor")){
			throw new IllegalArgumentException("Invalid gear subsystem configuraton, missing Dual Distance Sensors.");
		}
	    dualDistanceSensor=(DualDistanceSensor) getSensors().get("dualDistanceSensor");
	    
		//--------------------------------------------------------//
	    
	    return this;
	}
	
	public BracketGearSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		state=State.on;
		
	}
	
	//--------------------------------------------------------//
	
	public void set(boolean position){
		gearSolenoidState= position;
		gearSolenoid.set(gearSolenoidState);
	}
	
	public void toggle(Element solenoid) {
			gearSolenoidState = !gearSolenoidState;
			gearSolenoid.set(gearSolenoidState);

		};
	
	//--------------------------------------------------------//
	
	public void nextState(){
		switch(state) {
		// ------- //
		case on:
			debug("on");
			setState(State.off);
			break;
		case off:
			debug("off");
			setState(State.on);
			break;
		}
		
	}
	
	public void setState(State newState){
		this.state = newState;
		switch(state) {
		// ------- //
		case on:
			gearSolenoid.set(true);
			break;
		case off:
			gearSolenoid.set(false);
			break;
		}
		
	}
	
	public boolean hasGear(){
		double gearDistance = ((AnalogSensorReading) (dualDistanceSensor.getReadings()[1])).getValue();
		return gearDistance>gearDetectSetting;
	}
	
	public State getState(){
		return state;
	};

	public double getDeliveryDistanceSetting(){
		return deliveryDistanceSetting;
	}
	
	public double getWithdrawalDistanceSetting(){
		return withdrawalDistanceSetting;
	}	
	
	public double getActualDistance(){
		return ((AnalogSensorReading) (dualDistanceSensor.getReadings()[0])).getValue();
	}
	
	//--------------------------------------------------------//
	
	@Override
	protected void setUp() {
		if(getConfigSettings().containsKey("deliveryDistance")) deliveryDistanceSetting = getConfigSettings().get("deliveryDistance").getDouble();   //adjust scaling factors
		if(getConfigSettings().containsKey("withdrawalDistance")) withdrawalDistanceSetting = getConfigSettings().get("withdrawalDistance").getDouble();
		if(getConfigSettings().containsKey("gearDetect")) gearDetectSetting = getConfigSettings().get("gearDetect").getDouble();
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("deliveryDistance")) deliveryDistanceSetting = changedSetting.getDouble();
		if(changedSetting.getName().equals("withdrawalDistance")) withdrawalDistanceSetting = changedSetting.getDouble();
		if(changedSetting.getName().equals("gearDetect")) gearDetectSetting = changedSetting.getDouble();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
}

