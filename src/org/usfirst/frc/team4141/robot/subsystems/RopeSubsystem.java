package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

import edu.wpi.first.wpilibj.SpeedController;

public class RopeSubsystem extends MDSubsystem {
	
	private double liftSpeed=0.2;
	
	private SpeedController ropeController;
	
	public static String motorName="ropeMotor";

	public MDSubsystem configure(){
		super.configure();

		if(getMotors()==null 
				|| !getMotors().containsKey(motorName))
			throw new IllegalArgumentException("Invalid motor configuration for rope system.");
		ropeController = (SpeedController)(getMotors().get(motorName));
		//setCore(true);
		return this;
	}
	
	public RopeSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		// TODO Auto-generated constructor stub
	}
	
	public void move(){
		//positive speed=wind
		//negative speed=unwind
		ropeController.set(liftSpeed);
	}
		
	public void stop(){
		ropeController.stopMotor();
		
	}
	
	@Override
	protected void setUp() {
		
		if(getConfigSettings().containsKey("liftSpeed")) liftSpeed = getConfigSettings().get("liftSpeed").getDouble();
		
	}

	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		
		if(changedSetting.getName().equals("liftSpeed")) liftSpeed = changedSetting.getDouble();

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
