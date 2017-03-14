package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigPreferenceManager;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

import edu.wpi.first.wpilibj.command.Scheduler;

public class ClearSettingsCommand extends MDCommand {

	public ClearSettingsCommand(MDRobotBase robot, String name) {
		super(robot, name);
	}

	// ------------------------------------------------ //
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void execute() {
		log(Level.DEBUG,"execute", "resetting preferences");
    	ConfigPreferenceManager.clearPreferences();
	}
	
	@Override
	protected void end() {
	}

}
