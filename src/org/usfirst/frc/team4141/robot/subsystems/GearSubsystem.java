package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.sensors.AnalogSensorReading;
import org.usfirst.frc.team4141.MDRobotBase.sensors.DualDistanceSensor;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class GearSubsystem extends MDSubsystem {

	//--------------------------------------------------------//
	
	public enum Element {
		pos1Solenoid,
		pos2Solenoid,
		pushSolenoid
	}
	
	public enum State {
		start,
		preCollect,
		driveCarry,
		deliverApproach,
		deliverGear
	}

	private Solenoid pos1Solenoid;
	private Solenoid pos2Solenoid;
	private Solenoid pushSolenoid;

	private boolean pos1SolenoidState = false;
	private boolean pos2SolenoidState = false;
	private boolean pushSolenoidState = false;
	private boolean isPrimed = false;
	
	private double approachDistanceSetting = 0;
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
				|| !getSolenoids().containsKey(Element.pos1Solenoid.toString()) || !(getSolenoids().get(Element.pos1Solenoid.toString()) instanceof Solenoid)) {
				throw new IllegalArgumentException("Invalid Gear Subsystem configuraton, missing pos1 solenoid.");
		}	
		pos1Solenoid=(Solenoid) getSolenoids().get(Element.pos1Solenoid.toString());

		if(getSolenoids()==null 
				|| !getSolenoids().containsKey(Element.pos2Solenoid.toString()) || !(getSolenoids().get(Element.pos2Solenoid.toString()) instanceof Solenoid)) {
				throw new IllegalArgumentException("Invalid Gear Subsystem configuraton, missing pos2 solenoid.");
		}	
		pos2Solenoid=(Solenoid) getSolenoids().get(Element.pos2Solenoid.toString());

		if(getSolenoids()==null 
				|| !getSolenoids().containsKey(Element.pushSolenoid.toString()) || !(getSolenoids().get(Element.pushSolenoid.toString()) instanceof Solenoid)) {
				throw new IllegalArgumentException("Invalid Gear Subsystem configuraton, missing push solenoid.");
		}
		pushSolenoid=(Solenoid) getSolenoids().get(Element.pushSolenoid.toString());
		//setCore(true);
		
	    if(getSensors()==null && !getSensors().containsKey("dualDistanceSensor")){
			throw new IllegalArgumentException("Invalid gear subsystem configuraton, missing Dual Distance Sensors.");
		}
	    dualDistanceSensor=(DualDistanceSensor) getSensors().get("dualDistanceSensor");
	    
		//--------------------------------------------------------//
	    
	    return this;
	}
	
	public GearSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		state=State.start;
		
	}
	
	//--------------------------------------------------------//
	
	public void set(Element solenoid, boolean position){
		switch(solenoid){
		case pos1Solenoid: 
			pos1SolenoidState = position;
			pos1Solenoid.set(pos1SolenoidState);
		break;
		case pos2Solenoid: 
			pos2SolenoidState = position;
			pos2Solenoid.set(pos2SolenoidState);
		break;
		case pushSolenoid: 
			pushSolenoidState = position;
			pushSolenoid.set(pushSolenoidState);
		break;
		};
	}
	
	public void toggle(Element solenoid) {
		switch(solenoid){
		case pos1Solenoid: 
			pos1SolenoidState = !pos1SolenoidState;
			pos1Solenoid.set(pos1SolenoidState);
		break;
		case pos2Solenoid: 
			pos2SolenoidState = !pos2SolenoidState;
			pos2Solenoid.set(pushSolenoidState);
		break;
		case pushSolenoid: 
			pushSolenoidState = !pushSolenoidState;
			pushSolenoid.set(pushSolenoidState);
		break;
		};
	}
	
	//--------------------------------------------------------//
	
	public void nextState(){
		switch(state) {
		// ------- //
		case start:
			if (hasGear()) {
				setState(State.driveCarry);
			} else {
				setState(State.preCollect);
			}
			break;
		case preCollect:
			setState(State.driveCarry);
			break;
		case driveCarry:
			setState(State.deliverApproach);
			break;
		case deliverApproach:
			setState(State.deliverGear);
			break;
		case deliverGear:
			setState(State.preCollect);
			break;
		}
		
	}
	
	public void setState(State newState){
		switch(newState) {
		// ------- //
		case start:
			pos1Solenoid.set(false);
			pos2Solenoid.set(false);
			pushSolenoid.set(false);
			break;
		case preCollect:
			pos1Solenoid.set(true);
			pos2Solenoid.set(false);
			pushSolenoid.set(false);
			break;
		case driveCarry:
			pos1Solenoid.set(false);
			pos2Solenoid.set(false);
			pushSolenoid.set(false);
			break;
		case deliverApproach:
			pos1Solenoid.set(false);
			pos2Solenoid.set(true);
			pushSolenoid.set(false);
			break;
		case deliverGear:
			pos1Solenoid.set(false);
			pos2Solenoid.set(true);
			pushSolenoid.set(true);
			break;
		}
		
	}
	
	public boolean hasGear(){
		double gearDistance = ((AnalogSensorReading) (dualDistanceSensor.getReadings()[1])).getValue()*12;
		return gearDistance>gearDetectSetting;
	}
	
	public State getState(){
		return state;
	};
	
	public boolean isPrimed() {
		return isPrimed;
	}
	
	public void setApproachPrime(boolean prime){
		isPrimed = prime;
	}
	
	public double getApproachDistanceSetting(){
		return approachDistanceSetting;
	}
	
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
		if(getConfigSettings().containsKey("approachDistance")) approachDistanceSetting = getConfigSettings().get("approachDistance").getDouble();
		if(getConfigSettings().containsKey("deliveryDistance")) deliveryDistanceSetting = getConfigSettings().get("deliveryDistance").getDouble();
		if(getConfigSettings().containsKey("deliveryDistance")) withdrawalDistanceSetting = getConfigSettings().get("withdrawalDistance").getDouble();
		if(getConfigSettings().containsKey("gearDetect")) gearDetectSetting = getConfigSettings().get("gearDetect").getDouble();

		
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("approachDistance")) approachDistanceSetting = changedSetting.getDouble();
		if(changedSetting.getName().equals("deliveryDistance")) deliveryDistanceSetting = changedSetting.getDouble();
		if(changedSetting.getName().equals("pushSolenoidState")) withdrawalDistanceSetting = changedSetting.getDouble();
		if(changedSetting.getName().equals("gearDetect")) gearDetectSetting = changedSetting.getDouble();

	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
	
}

