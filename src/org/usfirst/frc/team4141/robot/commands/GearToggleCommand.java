package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.BracketGearSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.BracketGearSubsystem.Element;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

import edu.wpi.first.wpilibj.command.Scheduler;

public class GearToggleCommand extends MDCommand {

	private BracketGearSubsystem bracketGearSubsystem;
	
	// ------------------------------------------------ //

	public GearToggleCommand(MDRobotBase robot, String name) {
		super(robot, name);
		if(!getRobot().getSubsystems().containsKey("bracketGearSubsystem")){
			log(Level.ERROR, "initialize()",  "Gear subsystem not found");
			throw new IllegalArgumentException("Gear subsystem not found");
		}
		bracketGearSubsystem = (BracketGearSubsystem)getRobot().getSubsystems().get("bracketGearSubsystem"); 
		requires(bracketGearSubsystem);
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
		bracketGearSubsystem.toggle(Element.gearSolenoid);
	}
	
	@Override
	protected void end() {

	}
	
}
