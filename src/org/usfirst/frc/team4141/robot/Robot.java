//error 404 not found
package org.usfirst.frc.team4141.robot;

import org.usfirst.frc.team4141.MDRobotBase.MDCommandGroup;
import org.usfirst.frc.team4141.MDRobotBase.sensors.ConsoleConnectionSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.DualDistanceSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.GearTargetSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_BuiltInAccelerometer;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.sensors.ShiftGearSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SteamTargetSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.TegraConnectionSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.VisionConnectedSensor;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
//import org.usfirst.frc.team4141.robot.commands.SpinShootMotorCommand;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.HolySeeSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;
//import org.usfirst.frc.team4141.robot.subsystems.GearSubSystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;
import org.usfirst.frc.team4141.robot.subsystems.RopeSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.RumbleSubsystem;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends MDRobotBase {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	@Override
	protected void configureRobot() {
		
		add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
				.add(MotorPosition.right, new Victor(0))
				.add(MotorPosition.left, new Victor(1))
				.add("accelerometer", new MD_BuiltInAccelerometer())
				.add("IMU", new MD_IMU())
				.add("High Gear", new ShiftGearSensor())
				.add(MDDriveSubsystem.rightShiftSolenoidName, new Solenoid(0))
				.add(MDDriveSubsystem.leftShiftSolenoidName, new Solenoid(1))
				.add("a", new DoubleConfigSetting(0.0, 1.0, 0.25)) //high speed turn factor
		 	    .add("b", new DoubleConfigSetting(0.0, 1.0, 0.4)) //slow speed turn factor
				.add("c", new DoubleConfigSetting(0.0, 1.0, 1.0)) //speed governor
				.configure()
		);	

		add(new GearSubsystem(this, "gearSubsystem")
				.add(GearSubsystem.Element.pos1Solenoid.toString(), new Solenoid(2))
				.add(GearSubsystem.Element.pos2Solenoid.toString(), new Solenoid(3))
				.add(GearSubsystem.Element.pushSolenoid.toString(), new Solenoid(4))
				.add("approachDistance", new DoubleConfigSetting(0.0, 24.0, 12.0))
				.add("deliveryDistance", new DoubleConfigSetting(0.0, 12.0, 6.0))
				.add("withdrawalDistance", new DoubleConfigSetting(24.0, 48.0, 36.0))
				.add("gearDetect", new DoubleConfigSetting(0.0, 12.0, 4.0))
				.add("dualDistanceSensor", new DualDistanceSensor(0x41))
				.configure());

		add(new RopeSubsystem(this, "ropeSubsystem")
				.add(RopeSubsystem.motorName, new Victor(6))
				.add("liftSpeed", new DoubleConfigSetting(-1.0, 1.0, 0.2))
				.configure());
		
		
		add(new RumbleSubsystem(this, "rumbleSubsystem")
				.configure());
		
		add(new AutonomousSubsystem(this, "autoSubsystem")
				.add("auto1Speed",new DoubleConfigSetting(-1.0, 1.0, -0.75))
				.add("auto1Duration",new DoubleConfigSetting(0.0, 15.0, 2.0))
				.add("auto2Distance",new DoubleConfigSetting(0.0, 18.0, 0.0))
				.configure());
		
		add(new HolySeeSubsystem(this, "HolySeeSubsystem")
				.add("visionConnected", new VisionConnectedSensor())
				.add("Steam Target Acquired", new SteamTargetSensor())
				.add("Gear Target Acquired", new GearTargetSensor())
		    	.add("console", new ConsoleConnectionSensor())
		    	.add("tegra", new TegraConnectionSensor())
				.configure());
		
		add( new CoreSubsystem(this, "core")
				 .add("name",new StringConfigSetting("MaterBot"))					//go ahead name your robot
				 .configure()
		);	
	
		setAutonomousCommand(new MDCommandGroup[]{
				//new AUTOMoveFromWall(this),
				//new AUTODistanceDetection(this)
			}, "Auto1"  //specify the default
		);
	}
	
	@Override
	public void teleopInit() {
		super.teleopInit();   //ArcadeCommand started in super.teleopInit();
		
	} 
	@Override
	public void autonomousInit() { 
		super.autonomousInit();

	} 	
	@Override
	public void disabledPeriodic() {
		super.disabledPeriodic();

	}

}
