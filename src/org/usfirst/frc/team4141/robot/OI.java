package org.usfirst.frc.team4141.robot;


import org.usfirst.frc.team4141.MDRobotBase.ConsoleOI;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.OIBase;
import org.usfirst.frc.team4141.robot.commands.ClearSettingsCommand;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.commands.MoveFromWallCommand;
import org.usfirst.frc.team4141.robot.commands.NextGearStateCommand;
import org.usfirst.frc.team4141.robot.commands.Pos1GearToggleCommand;
import org.usfirst.frc.team4141.robot.commands.Pos2GearToggleCommand;
import org.usfirst.frc.team4141.robot.commands.PushGearToggleCommand;
import org.usfirst.frc.team4141.robot.commands.ResetGyroCommand;
import org.usfirst.frc.team4141.robot.commands.RopeLowerCommand;
//import org.usfirst.frc.team4141.robot.commands.OpenDoorCommand;
import org.usfirst.frc.team4141.robot.commands.RopeRiseCommand;
import org.usfirst.frc.team4141.robot.commands.RumbleCommand;
import org.usfirst.frc.team4141.robot.commands.SwitchChannelCommand;
import org.usfirst.frc.team4141.robot.commands.ShiftToggleCommand;
//import org.usfirst.frc.team4141.robot.commands.ShootCommand;
import org.usfirst.frc.team4141.robot.commands.ToggleOrientationCommand;

public class OI extends OIBase{

	//--------------------------------------------------------//
	
	public OI(MDRobotBase robot) {
		super(robot);
		robot.debug("OI created");
	}

	public void configureOI(){
		
/*		A robot should have 1 or more operator interfaces (OI)
		Typically, a robot will have at least 1 joystick and 1 console
		
		If using gamepad, the buttons are generally as follows:
		 a is button 1
		 b is button 2
		 x is button 3
		 y is button 4
		
		If using joystick, the buttons are generally as follows:
			trigger is button 1
			side is button 2
			3 is button 3
			4 is button 4
			5 is button 5
			6 is button 6
		
*/		
		
        // Configure the joystick(s) here
		// ------------------------------------------------ //		
		add(new MDJoystick(getRobot(), "driveJoystick", 0)
				
/*          The following commands are test move commands useful in testing drive configuration and set up
            comment out and replace as needed.
            -------------------------------------
		    .whenPressed("rightBumper",5,new MDPrintCommand(getRobot(),"Right Bumper Command","Right Bumper Command message"))
		    .whileHeld("leftBumper",6,new MDPrintCommand(getRobot(),"Left Bumper Command","Left Bumper Command message"))				
*/
				
/*
			// Controller Config: Xbox
			// -------------------------------------------------
			.whenPressed("A",1,new UnjamBallsystemCommand(getRobot(),"UnjamBallsystemCommand"))
			.whenPressed("B",2,new ShiftToggleCommand(getRobot(), "ShiftToggle"))
			.whenPressed("X",3,new ToggleOrientationCommand(getRobot(), "ToggleOrientationCommand"))
			.whenPressed("Y",4,new UnjamShootCommand(getRobot(),"UnjamShootCommand"))
			.whileHeld("LB",5,new RopeRiseCommand(getRobot(), "RopeRiseCommand"))
			.whileHeld("RB",6,new ShootCommand(getRobot(), "ShootCommand")
			.whenPressed("start",8,new StopShootSystemCommand(getRobot(),"StopShootSystemCommand"))
			.configure()
			);
*/
/*				
			//Controller Config: Logitech
			//--------------------------------------------------
			.whenPressed("A",2,new UnjamBallsystemCommand(getRobot(),"UnjamBallsystemCommand"))
			.whenPressed("B",3,new ShiftToggleCommand(getRobot(), "ShiftToggle"))
			.whenPressed("X",1,new ToggleOrientationCommand(getRobot(), "ToggleOrientationCommand"))
			.whenPressed("Y",4,new UnjamShootCommand(getRobot(),"UnjamShootCommand"))
			.whileHeld("LB",5,new RopeRiseCommand(getRobot(), "RopeRiseCommand"))
			.whileHeld("RB",6,new ShootCommand(getRobot(), "ShootCommand")
			.whenPressed("start",10,new StopShootSystemCommand(getRobot(),"StopShootSystemCommand"))
			.configure());
*/
			
			// Joystick Config: EXTREME 360 Pro
			//-------------------------------------------------
			.whileHeld("Button2",11,new RopeRiseCommand(getRobot(), "RopeRiseCommand"))
			.whileHeld("Button2",12,new RopeLowerCommand(getRobot(), "RopeLowerCommand"))
			.whenPressed("Button3",3,new ShiftToggleCommand(getRobot(), "ShiftToggle"))
			.whenPressed("Button5",5,new ToggleOrientationCommand(getRobot(), "ToggleOrientationCommand"))
/*			.whenPressed("Button12",12,new RumbleCommand(getRobot(), "RumbleCommand"))
			.whileHeld("Button4",4,new TalonDriveCommand(getRobot(), "TalonCommand"))
			.whenPressed("trigger",1,new StopBallsystemCommand(getRobot(), "StopBallsystemCommand"))			
		    .whenPressed("Button7",7,new StopBallsystemCommand(getRobot(), "CollectCommand"))			
			.whenPressed("Trigger",1,new ShootCommand(getRobot(), "ShootCommand"))
			.whenPressed("SideButton",2,new ToggleOrientationCommand(getRobot(), "ToggleOrientationCommand"))
			.whenPressed("Button3",3,new OpenDoorCommand(getRobot(), "OpenDoorCommand"))			
			.whenPressed("Button6",6,new MDMoveCommand(getRobot(),"forward command",Direction.forward))			
			.whenPressed("Button7",7,new ShootCommand(getRobot(),"ShootCommand"))	
*/			
				
			// Controller Config: Logitech G29 Driving Force
			// ------------------------------------------------ //
/*			.whenPressed("5",2,new RopeRiseCommand(getRobot(), "RopeRiseCommand"))
			.whenPressed(buttonName, buttonNumber, command)
*/				
			
			.configure());


		// NES Controller
		// ------------------------------------------------ //
		add(new MDJoystick(getRobot(), "buttonJoystick", 1)
			.whileHeld("A",2,new RopeRiseCommand(getRobot(), "RopeRiseCommand"))
			.configure());
		
		// Configure the RioHID here
		// Uncomment the following to attach a command to the user button on the RoboRio
		// ------------------------------------------------ //
/*		add(new RioHID(getRobot())
			.whileHeld(new MDPrintCommand(getRobot(),"ExampleCommand1","ExampleCommand1 message"))
			.configure()
		);
*/
		
		//Configure the MDConsole OI here
		// ------------------------------------------------ //
		add(new ConsoleOI(getRobot())
			//.whenPressed("Toggle Gear: Pos. 1",15, new Pos1GearToggleCommand(getRobot(), "Pos1GearToggleCommand"))
			//.whenPressed("Toggle Gear: Pos. 2",14, new Pos2GearToggleCommand(getRobot(), "Pos1GearToggleCommand"))
			//.whenPressed("Toggle Gear: Push",13, new PushGearToggleCommand(getRobot(), "Pos1GearToggleCommand"))
			.whenPressed("Reset Gyro",6,new ResetGyroCommand(getRobot(),"resetGyro"))
			.whenPressed("Switch Cameras",8,new SwitchChannelCommand(getRobot(), "SwitchChannelCommand"))
			.whenPressed("Clear Settings",10,new ClearSettingsCommand(getRobot(), "ClearSettingsCommand"))
			.whenPressed("Next State",115,new NextGearStateCommand(getRobot(), "NextGearStateCommand"))
			.whileHeld("Rope Rise",23,new RopeRiseCommand(getRobot(), "RopeRiseCommand"))
			.whileHeld("Rope Lower",24,new RopeLowerCommand(getRobot(), "RopeLowerCommand"))
			.whenPressed("Auto1", 11, new MoveFromWallCommand(getRobot(), "MoveFromWallCommand"))
			//.whenPressed("Auto2", 20, new PlaceGearCommand(getRobot(), "PlaceGearCommand"))
			
			// ------------------------------------------------------------------------------------------- //
			//.whenPressed("Toggle Light",11,new ToggleLightCommand(getRobot(), "ToggleLightCommand"))
			//.whileHeld("Talon Held",7,new TalonDriveCommand(getRobot(), "TalonDriveCommand"))
			//.whileHeld("Distance While Held",9,new DistanceDetectionCommand(getRobot(), "DistanceDetectionCommand"))
			//.whenPressed("Shift Gear",10,new ShiftToggleCommand(getRobot(), "ShiftToggle"))
			//.whenPressed("whenPressed",0,new MDPrintCommand(getRobot(),"whenPressed","whenPressed..."))
			//.whileHeld("whileHeld",3,new MDPrintCommand(getRobot(),"whileHeld","whileHeld..."))
			//.whenPressed("Auto: Move from Wall",1,new AUTOMoveFromWall(getRobot()))
			//.whenPressed("Auto 2: Distance Detection",2,new AUTODistanceDetection(getRobot()))
			//.whileHeld("Rope Rise",3,new RopeRiseCommand(getRobot(), "RopeRiseCommand"))
			//.whenPressed("Rumble Test",4,new RumbleCommand(getRobot(),"rumble"))
			//.whenPressed("Flip Orientation",5,new ToggleOrientationCommand(getRobot(),"consoleOrientationToggler"))
			.configure()
			);		
		
	}

}  


