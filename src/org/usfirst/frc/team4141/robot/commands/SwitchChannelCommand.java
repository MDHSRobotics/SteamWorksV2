package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.HolySeeSubsystem;

public class SwitchChannelCommand extends MDCommand {
	
	private HolySeeSubsystem holyseeSubsystem;
	
	// ------------------------------------------------ //
	
	public SwitchChannelCommand(MDRobotBase robot, String name) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("HolySeeSubsystem")){
			log(Level.ERROR, "initialize()",  "HolySeeSubsystem not found");
			throw new IllegalArgumentException("HolySeeSubsystem not found");
		}
		holyseeSubsystem = (HolySeeSubsystem)getRobot().getSubsystems().get("HolySeeSubsystem");
		
	}
	
	// ------------------------------------------------ //
	
	@Override
	protected void initialize() {
		//log(Level.DEBUG, "initialize()", "shift channel");
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void execute() {
		holyseeSubsystem.switchChannel();
	}
	
	@Override
	protected void end() {
		super.end();
	}
}
