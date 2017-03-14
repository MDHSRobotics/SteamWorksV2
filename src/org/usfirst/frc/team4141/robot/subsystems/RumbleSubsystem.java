package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class RumbleSubsystem extends MDSubsystem {
	
	private long rumbleDuration = 500;
	private double rumbleIntenisty = 0.5;
	
	// ------------------------------------------------ //
	
/*	public MDSubsystem configure() {
		super.configure();
		if(getMotors()==null 
				|| !getMotors().containsKey(motorCollect)  || !(getMotors().get(motorCollect) instanceof SpeedController))
			throw new IllegalArgumentException("Invalid motor configuration for ball Pickup system.");
		motorController = (SpeedController)(getMotors().get(motorCollect));
		return this;
	}
*/
	
	public RumbleSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
	}
	
	// ------------------------------------------------ //
	
	@Override
	protected void setUp() {
		if(getConfigSettings().containsKey("rumbleIntenisty")){
			rumbleIntenisty = getConfigSettings().get("rumbleIntenisty").getDouble();
		}
		if(getConfigSettings().containsKey("rumbleDuration")){
			rumbleDuration = (long)(getConfigSettings().get("rumbleDuration").getDouble()*1000);
		}
	}

	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
	//	if(changedSetting.getName().equals("rumbleIntenisty")) rumbleIntenisty = changedSetting.getDouble();
		if(changedSetting.getName().equals("rumbleDuration")) rumbleDuration = (long)(changedSetting.getDouble()*1000);
	}

	@Override
	protected void initDefaultCommand() {	
	}
	
	// ------------------------------------------------ //
	
	public void rumble(){
		debug("rumbling...");
		getRobot().getOi().getJoysticks().get("driveJoystick").setRumble(RumbleType.kLeftRumble, (float) this.rumbleIntenisty);
		getRobot().getOi().getJoysticks().get("driveJoystick").setRumble(RumbleType.kRightRumble, (float) this.rumbleIntenisty);
	
	}
	
	public double getRumbleDuration(){
		return  rumbleDuration;
	}

	public void stop() {
		debug("Stopped Rumbling");
		getRobot().getOi().getJoysticks().get("driveJoystick").setRumble(RumbleType.kLeftRumble, 0);
		getRobot().getOi().getJoysticks().get("driveJoystick").setRumble(RumbleType.kRightRumble, 0);

	}
	
	
}
