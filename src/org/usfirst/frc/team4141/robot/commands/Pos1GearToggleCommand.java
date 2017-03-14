package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.GearSubsystem.Element;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

import edu.wpi.first.wpilibj.command.Scheduler;

public class Pos1GearToggleCommand extends MDCommand {

	private GearSubsystem gearSubsystem;
	
	// ------------------------------------------------ //

	public Pos1GearToggleCommand(MDRobotBase robot, String name) {
		super(robot, name);
		// TODO Auto-generated constructor stub
		if(!getRobot().getSubsystems().containsKey("gearSubsystem")){
			log(Level.ERROR, "initialize()",  "Gear subsystem not found");
			throw new IllegalArgumentException("Gear subsystem not found");
		}
		gearSubsystem = (GearSubsystem)getRobot().getSubsystems().get("gearSubsystem"); 
		requires(gearSubsystem);
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
		gearSubsystem.toggle(Element.pos1Solenoid);
	}
	
	@Override
	protected void end() {

	}
	
}
