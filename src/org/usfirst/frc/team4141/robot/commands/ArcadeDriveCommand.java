package org.usfirst.frc.team4141.robot.commands;


import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;


public class ArcadeDriveCommand extends MDCommand {
	
	private MDJoystick joystick = null;
	MDDriveSubsystem driveSys;
	
	// ------------------------------------------------ //

	public ArcadeDriveCommand(MDRobotBase robot) {
		super(robot,"ArcadeDriveCommand");
		if(!getRobot().getSubsystems().containsKey("driveSystem")){
			log(Level.ERROR, "initialize()",  "Arcade Drive system not found");
			throw new IllegalArgumentException("Arcade Drive system not found");
		}
		driveSys = (MDDriveSubsystem)getRobot().getSubsystems().get("driveSystem"); 
		requires(driveSys);
    }
	
	// ------------------------------------------------ //
	
	@Override
	protected void initialize() {
		super.initialize();
		joystick = getRobot().getOi().getJoysticks().get("driveJoystick");
	}

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	driveSys.arcadeDrive(joystick);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	super.end();
    	driveSys.stop();
    }

}