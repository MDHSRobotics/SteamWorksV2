package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

public class ResetGyroCommand extends MDCommand {
	
	public ResetGyroCommand(MDRobotBase robot, String name) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("driveSystem")){
			log(Level.ERROR, "initialize()",  "drive system not found");
			throw new IllegalArgumentException("Shift system not found");
		}
		driveSystem = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem");
		requires(driveSystem);
		// TODO Auto-generated constructor stub
	}
	
	// ------------------------------------------------ //
	
	private MDDriveSubsystem driveSystem;

	// ------------------------------------------------ //
	
	@Override
	protected void initialize() {
		log(Level.DEBUG, "initialize()", "resetting gyro");
	}
	
	@Override
	protected boolean isFinished() {
		return  true;
	}
	
	@Override
	protected void execute() {
		driveSystem.gyroReset();
	}
	
	@Override
	protected void end() {
	}
}
