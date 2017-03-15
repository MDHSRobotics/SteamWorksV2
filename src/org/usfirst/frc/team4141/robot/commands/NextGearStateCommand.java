package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.BracketGearSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.GearSubsystem.Element;

public class NextGearStateCommand extends MDCommand {

	public NextGearStateCommand(MDRobotBase robot, String name) {
		super(robot, name);
//		if(!getRobot().getSubsystems().containsKey("gearSubsystem")){
		if(!getRobot().getSubsystems().containsKey("bracketGearSubsystem")){
			log(Level.ERROR, "initialize()",  "Gear subsystem not found");
			throw new IllegalArgumentException("Gear subsystem not found");
		}
		gearSubsystem = (BracketGearSubsystem)getRobot().getSubsystems().get("bracketGearSubsystem"); 
//		gearSubsystem = (GearSubsystem)getRobot().getSubsystems().get("gearSubsystem"); 
		requires(gearSubsystem);
	}
	
	// ------------------------------------------------ //

//	private GearSubsystem gearSubsystem;
	private BracketGearSubsystem gearSubsystem;
	
	// ------------------------------------------------ //
	
	@Override
	protected void initialize() {

	}
	
	@Override
	protected boolean isFinished() {
		log(Level.DEBUG,"isFinished()","Gear system now set to "+gearSubsystem.getState().toString());
		return true;
	}
	
	@Override
	protected void execute() {
		gearSubsystem.nextState();
	}
	
	@Override
	protected void end() {

	}
	
}