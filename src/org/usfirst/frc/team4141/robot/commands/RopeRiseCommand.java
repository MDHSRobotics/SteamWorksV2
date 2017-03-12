package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.RopeSubsystem;

public class RopeRiseCommand extends MDCommand {
	
	private RopeSubsystem ropeSubsystem;
	
	public RopeRiseCommand(MDRobotBase robot, String name) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("ropeSubsystem")){
			log(Level.ERROR, "initialize()", "rope subsystem not found");
			throw new IllegalArgumentException("Rope Subsystem not found");
		}
		ropeSubsystem = (RopeSubsystem)getRobot().getSubsystems().get("ropeSubsystem"); 
		requires(ropeSubsystem);
	}

	protected void initialize() {
		}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void execute() {
		if (ropeSubsystem!=null)ropeSubsystem.move();
	}
	
	@Override
		protected void end() {
			
			ropeSubsystem.stop();
			
		}
}
