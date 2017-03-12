//package org.usfirst.frc.team4141.robot.subsystems;
//
//import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
//import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
//import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
//import org.usfirst.frc.team4141.MDRobotBase.sensors.MDDigitalInput;
//
//import edu.wpi.first.wpilibj.PWM;
//import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.SolenoidBase;
//
//public class GearSubSystem extends MDSubsystem {
//
//	public enum SolenoidPosition{
//		left,
//		right
//	}
//	public enum SwitchPosition{
//		recessed,
//		extended
//	}
//	
//	public static String motorName="gearPushMotor";
//	
//	public MDSubsystem configure(){
//		super.configure();
//		//are we configured properly?
//		if(getSolenoids()==null 
//				|| !getSolenoids().containsKey(SolenoidPosition.left.toString()) 
//				|| !getSolenoids().containsKey(SolenoidPosition.right.toString()))
//			throw new IllegalArgumentException("Invalid solenoid configuration for gear system.");
//		// assume if more than one motor, use the first motor
//		if(getMotors()==null 
//				|| !getMotors().containsKey(motorName))
//			throw new IllegalArgumentException("Invalid motor configuration for gear system.");
//		if(getSensors()==null 
//				|| !getSensors().containsKey(SwitchPosition.recessed.toString()) 
//				|| !getSensors().containsKey(SwitchPosition.extended.toString()))
//			throw new IllegalArgumentException("Invalid switch configuration for gear system.");
//		return this;
//	}	
//	//let's assume the solenods start in a closed position
//	//assume for solenoids closed is  false
//	//assume for motor that positive is pushing gear out 
//	
//	public GearSubSystem(MDRobotBase robot, String name) {
//		super(robot, name);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	protected void setUp() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void settingChangeListener(ConfigSetting setting) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected void initDefaultCommand() {
//		// TODO Auto-generated method stub
//
//	}
//	
//	public void open(){
//		Solenoid leftSolenoid = (Solenoid)getSolenoids().get(SolenoidPosition.left);
//		Solenoid rightSolenoid = (Solenoid)getSolenoids().get(SolenoidPosition.right);
//		leftSolenoid.set(true);
//		rightSolenoid.set(true);
//	}
//	public void close(){
//		Solenoid leftSolenoid = (Solenoid)getSolenoids().get(SolenoidPosition.left);
//		Solenoid rightSolenoid = (Solenoid)getSolenoids().get(SolenoidPosition.right);
//		leftSolenoid.set(false);
//		rightSolenoid.set(false);
//	}
//	public void move(double speed){
//		getMotors().get(motorName).setPosition(speed);
//	}
//	
//	public boolean isRecessed(){
//		return ((MDDigitalInput)(getSensors().get(SwitchPosition.recessed.toString()))).get();
//	}
//	public boolean isExtended(){
//		return ((MDDigitalInput)(getSensors().get(SwitchPosition.extended.toString()))).get();
//	}
//
//}
