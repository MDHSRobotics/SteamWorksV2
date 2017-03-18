package org.usfirst.frc.team4141.robot.commands;

import java.util.Date;

//REMEMBER TO TEST CODE

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.MDRobotBase.sensors.ShiftGearSensor;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;

import edu.wpi.first.wpilibj.command.Scheduler;

public class ShiftHighGearCommand extends MDCommand {
	
	private MDDriveSubsystem driveSystem;
	private long start;
	private long shiftToggleDuration = 250;
	
	// ------------------------------------------------ //
	
	public ShiftHighGearCommand(MDRobotBase robot, String name) {
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
	
	@Override
	protected void initialize() {

		log(Level.DEBUG, "initialize()", "Shift High Gears");
		
		start =(new Date()).getTime();
	}
	
	@Override
	protected boolean isFinished() {
		if (driveSystem.isLowGear() == true){
			
		long now = (new Date()).getTime();
		return  (now >=(start+shiftToggleDuration));
		
		}else{
			return false;
		}
	}
	
	@Override
	protected void execute() {
		driveSystem.stop();
	}
	
	@Override
	protected void end() {
		driveSystem.stop();
		driveSystem.shift();
		//TODO:  run collect command
		Scheduler.getInstance().add(new ArcadeDriveCommand(getRobot()));
	}

	
}
