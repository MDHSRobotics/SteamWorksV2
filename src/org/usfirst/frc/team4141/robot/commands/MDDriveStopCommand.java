package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;



import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

public class MDDriveStopCommand extends MDCommand {
	
	private MDDriveSubsystem driveSystem;
	
	// ------------------------------------------------ //

	public MDDriveStopCommand(MDRobotBase robot, String name) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("driveSystem")){
			log(Level.ERROR, "initialize()",  "Drive system not found");
			throw new IllegalArgumentException("Stop drive system not found");
		}
		driveSystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem"); 
		requires(driveSystem);
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
		driveSystem.stop();
	}

}
