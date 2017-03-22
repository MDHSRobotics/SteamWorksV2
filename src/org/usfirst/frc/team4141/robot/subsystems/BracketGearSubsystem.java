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
		leftSolenoid,
		rightSolenoid
	}
	
	public enum State {
//		start,
//		empty,
		driveCarry,
//		deliverApproach,
		deliverGear
	}

	private Solenoid leftSolenoid;
	private Solenoid rightSolenoid;

	private boolean leftSolenoidState = false;
	private boolean rightSolenoidState = false;
	
//	private double approachDistanceSetting = 0;
	private double deliveryDistanceSetting = 0;
	private double withdrawalDistanceSetting = 0;
	private double gearDetectSetting = 0;
	
	private DualDistanceSensor dualDistanceSensor;

	private State state;
	
	//--------------------------------------------------------//
	// Mr. Smith doesn't know C++. Lmao
	
	public MDSubsystem configure(){
		super.configure();
		
		//--------------------------------------------------------//
		
		if(getSolenoids()==null 
				|| !getSolenoids().containsKey(Element.leftSolenoid.toString()) || !(getSolenoids().get(Element.leftSolenoid.toString()) instanceof Solenoid)) {
				throw new IllegalArgumentException("Invalid Gear Subsystem configuraton, missing pos1 solenoid.");
		}	
		leftSolenoid=(Solenoid) getSolenoids().get(Element.leftSolenoid.toString());

		if(getSolenoids()==null 
				|| !getSolenoids().containsKey(Element.rightSolenoid.toString()) || !(getSolenoids().get(Element.rightSolenoid.toString()) instanceof Solenoid)) {
				throw new IllegalArgumentException("Invalid Gear Subsystem configuraton, missing pos2 solenoid.");
		}	
		rightSolenoid=(Solenoid) getSolenoids().get(Element.rightSolenoid.toString());

		//setCore(true);
		
	    if(getSensors()==null && !getSensors().containsKey("dualDistanceSensor")){
			throw new IllegalArgumentException("Invalid gear subsystem configuraton, missing Dual Distance Sensors.");
		}
	    dualDistanceSensor=(DualDistanceSensor) getSensors().get("dualDistanceSensor");
	    
		//--------------------------------------------------------//
	    
	    return this;
	}
	
	public BracketGearSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		state=State.driveCarry;
		
	}
	
	//--------------------------------------------------------//
	
	public void set(boolean position){
		leftSolenoidState = position;
		rightSolenoidState = position;
		leftSolenoid.set(leftSolenoidState);
		rightSolenoid.set(rightSolenoidState);
	}
	
	public void toggle(Element solenoid) {
		switch(solenoid){
		case leftSolenoid: 
			leftSolenoidState = !leftSolenoidState;
			leftSolenoid.set(leftSolenoidState);
		break;
		case rightSolenoid: 
			rightSolenoidState = !rightSolenoidState;
			rightSolenoid.set(rightSolenoidState);
		break;
		};
	}
	
	//--------------------------------------------------------//
	
	public void nextState(){
		switch(state) {
		// ------- //
//		case start:
//			debug("start");
//			if (hasGear()) {
//				setState(State.driveCarry);
//			} else {
//				setState(State.empty);
//			}
//			break;
//		case empty:
//			debug("empty");
//			setState(State.driveCarry);
//			break;
		case driveCarry:
			debug("driveCarry");
			setState(State.deliverGear);
			break;
//		case deliverApproach:
//			debug("deliverApproach");
//			setState(State.deliverGear);
//			break;
		case deliverGear:
			debug("deliverGear");
			setState(State.driveCarry);
			break;
		}
		
	}
	
	public void setState(State newState){
		this.state = newState;
		switch(state) {
		// ------- //
//		case start:
//			leftSolenoid.set(false);
//			rightSolenoid.set(false);
//			break;
//		case empty:
//			leftSolenoid.set(false);
//			rightSolenoid.set(false);
//			break;
		case driveCarry:
			leftSolenoid.set(false);
			rightSolenoid.set(false);
			break;
//		case deliverApproach:
//			pos1Solenoid.set(false);
//			pos2Solenoid.set(true);
//			pushSolenoid.set(false);
//			break;
		case deliverGear:
			leftSolenoid.set(true);
			rightSolenoid.set(true);
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

