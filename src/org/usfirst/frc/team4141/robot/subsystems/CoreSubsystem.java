package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;

public class CoreSubsystem extends MDSubsystem {

	public CoreSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
		setCore(true);
		//try to set the autocommand
		if(robot.getAutoCommand()!=null)
			add("autoCommand",new StringConfigSetting(robot.getAutoCommand().getName()));		//name of autoCommand you wish to start with
	}

	@Override
	protected void initDefaultCommand() {
	}

	@Override
	protected void setUp() {
		if(getConfigSettings().containsKey("name")){
			getRobot().setName(getConfigSettings().get("name").getString());
		}
		if(getConfigSettings().containsKey("autoCommand")){
			getRobot().setAutoCommand(getConfigSettings().get("autoCommand").getString());
		}
	}

	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(getConfigSettings()!=null && getConfigSettings().containsKey(changedSetting.getName()) && changedSetting.getName().equals("autoCommand")){
			if(getRobot().hasAutoCommand(changedSetting.getString())){
				getRobot().setAutoCommand(changedSetting.getString());
			}
			else{
				getConfigSettings().get(changedSetting.getName()).setValue(getRobot().getAutoCommand().getName());
			}
		}	
		else if(getConfigSettings()!=null && getConfigSettings().containsKey(changedSetting.getName()) && changedSetting.getName().equals("name")){
			getRobot().setName(changedSetting.getString());
		}	
	}
}
