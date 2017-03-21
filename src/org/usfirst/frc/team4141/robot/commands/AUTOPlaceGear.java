//package org.usfirst.frc.team4141.robot.commands;
//
//import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
//import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
//
//public class AUTOPlaceGear extends MDCommandGroup{
//	public AUTOPlaceGear(MDRobotBase robot){
//		
//		super(robot,"Auto2");
//		addSequential(new MDPrintCommand(getRobot(),"auto2-1","[AUTO] Move from Wall: Begin"));
//		addSequential(new MoveFromWallCommand(getRobot(),"Move From Wall."));
//		addSequential(new OpenArmsCommand(getRobot(), "Open Arms");
//		addSequential(new MoveBackToWall(getRobot(), "Move To Wall.");
//		addParallel(new CloseArms(getRobot(), "Close Arms."));
//		addSequential(new MDPrintCommand(getRobot(),"auto2-3","[AUTO] Move from Wall: End"));
//	}
//
//}
//