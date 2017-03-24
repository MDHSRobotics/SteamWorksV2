package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

public class AutonomousSubsystem extends MDSubsystem {

	private double auto1Speed;
	private long auto1Duration;
	private double auto1Distance;
	private double gearStopDistance;
	private double gearBackupDistance;
	
	//--------------------------------------------------------//
	
	public AutonomousSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
	}
	
	//--------------------------------------------------------//

	
	@Override
	protected void setUp() {
		
		if(getConfigSettings().containsKey("auto1Speed")) auto1Speed = getConfigSettings().get("auto1Speed").getDouble();
		if(getConfigSettings().containsKey("auto1Duration")) auto1Duration = (long) (getConfigSettings().get("auto1Duration").getDouble()*1000);
		if(getConfigSettings().containsKey("auto1Distance")) auto1Distance = getConfigSettings().get("auto1Distance").getDouble();
		if(getConfigSettings().containsKey("gearStopDistance")) gearStopDistance = getConfigSettings().get("gearStopDistance").getDouble();
		if(getConfigSettings().containsKey("gearBackupDistance")) gearBackupDistance = getConfigSettings().get("gearBackupDistance").getDouble();
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		
		if(changedSetting.getName().equals("auto1Speed")) auto1Speed = changedSetting.getDouble();
		if(changedSetting.getName().equals("auto1Duration")) auto1Duration = (long) (changedSetting.getDouble()*1000);
		if(changedSetting.getName().equals("auto1Distance")) auto1Distance = changedSetting.getDouble();
		if(changedSetting.getName().equals("gearStopDistance")) gearStopDistance = changedSetting.getDouble();
		if(changedSetting.getName().equals("gearBackupDistance")) gearBackupDistance = changedSetting.getDouble();
	}
	
	@Override
	protected void initDefaultCommand() {

	}
	
	//--------------------------------------------------------//
	
	public long getAuto1Duration() {
		return auto1Duration;
	}
	
	public double getAuto1Speed() {
		return auto1Speed;
	}
	
	public double getAuto1Distance() {
		return auto1Distance;
	}
	
	public double getgearStopDistance() {
		return gearStopDistance;
	}
	
	public double getgearBackupDistance() {
		return gearBackupDistance;
	}

}
