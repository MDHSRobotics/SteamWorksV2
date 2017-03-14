package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;

public class AutonomousSubsystem extends MDSubsystem {

	private double auto1Speed;
	private long auto1Duration;
	private double auto2Distance;
	
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
		if(getConfigSettings().containsKey("auto2Distance")) auto2Distance = getConfigSettings().get("auto2Distance").getDouble();
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		
		if(changedSetting.getName().equals("auto1Speed")) auto1Speed = changedSetting.getDouble();
		if(changedSetting.getName().equals("auto1Duration")) auto1Duration = (long) (changedSetting.getDouble()*1000);
		if(changedSetting.getName().equals("auto2Distance")) auto2Distance = changedSetting.getDouble();
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
	
	public double getAuto2Distance() {
		return auto2Distance;
	}

}
