package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;

public class AUTOPlaceGear extends MDCommandGroup{
	public AUTOPlaceGear(MDRobotBase robot){
		
		super(robot,"Auto2");
		addSequential(new MDPrintCommand(getRobot(),"auto2-1","[AUTO] Place Gear: Begin"));
		addSequential(new PlaceGearCommand(getRobot(), "Move To Gear Peg.",true));
		addSequential(new Pos1GearToggleCommand(getRobot(), "Open Arms"));
		addParallel(new Pos2GearToggleCommand(getRobot(), "Close Arms"));
		addSequential(new PlaceGearCommand(getRobot(), "Move Away From Gear Peg.",false));
		addSequential(new Pos1GearToggleCommand(getRobot(), "Open Arms"));
		addParallel(new Pos2GearToggleCommand(getRobot(), "Close Arms"));
		addSequential(new MDPrintCommand(getRobot(),"auto2-2","[AUTO] Place Gear: End"));
	}

}
