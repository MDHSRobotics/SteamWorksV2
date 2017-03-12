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
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
//import org.usfirst.frc.team4141.robot.commands.SpinShootMotorCommand;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
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

		// TankDrive with 2 motors example:
		
				add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
						.add(MotorPosition.right, new Victor(0))
						.add(MotorPosition.left, new Victor(1))
						.add("accelerometer", new MD_BuiltInAccelerometer())
						.add("IMU", new MD_IMU())
						.add("dualDistance", new DualDistanceSensor(0x41))
						.add("High Gear", new ShiftGearSensor())
						.add(MDDriveSubsystem.shiftSolenoid, new Solenoid(1))
						.add(MDDriveSubsystem.shiftSolenoid1, new Solenoid(2))
						.add("a", new DoubleConfigSetting(0.0, 1.0, 0.25))//high speed turn factor
				 	    .add("b", new DoubleConfigSetting(0.0, 1.0, 0.4))//slow speed turn factor
						.add("c", new DoubleConfigSetting(0.0, 1.0, 1.0))//speed governor
						.configure()
				);	
				
		// MecanumDrive example
		// ---------------------------------		
/*				add(new MDDriveSubsystem(this, "driveSystem", Type.MecanumDrive)
						.add(MotorPosition.frontLeft, new Victor(3))
						.add(MotorPosition.rearLeft, new Victor(2))
						.add(MotorPosition.frontRight, new Victor(1))
						.add(MotorPosition.rearRight, new Victor(0))
						.add("accelerometer", new MD_BuiltInAccelerometer())
						.add("IMU", new MD_IMU())
						.add("a", new DoubleConfigSetting(0.0, 1.0, 0.25))
						.add("b", new DoubleConfigSetting(0.0, 1.0, 0.4))
						.add("c", new DoubleConfigSetting(0.0, 1.0, 1.0))
						.configure()
				);	
*/	
				
		// TankDrive with 4 motors example:
		// ----------------------------------
/*				add(new MDDriveSubsystem(this, "driveSystem", Type.TankDrive)
						.add(MotorPosition.frontRight, new Victor(0))
						.add(MotorPosition.rearRight, new Victor(1))
						.add(MotorPosition.frontLeft, new Victor(2))
						.add(MotorPosition.rearLeft, new Victor(3))
						.add("accelerometer", new MD_BuiltInAccelerometer())
						.add("IMU", new MD_IMU())
						.configure()
				);	
			
		*/
				
		//A commands needs to be configured for the autonomous mode.
		//In some cases it is desirable to have more than 1 auto command and make a decision at game time which command to use
		
	
		
		// -----------------------------------------------------------------------
		// A robot is composed of subsystems
		// A robot will typically have 1 drive system and several other fit to purpose subsystems		
		// The Drive system is a special subsystem in that it has specific logic handle the speed controllers
		// We have 2 types of drive systems, tank drive and mecanum drive
		// ncomment the desired drive system and adjust the motor configuration as needed
		// -----------------------------------------------------------------------
	
		// Mecanum example :
		// ---------------------
/*		add(new MDDriveSubsystem(this, "driveSystem", Type.MecanumDrive)
				.add(MotorPosition.frontRight, new Victor(1))
				.add(MotorPosition.rearRight, new Victor(0))
				.add(MotorPosition.frontLeft, new Victor(3))
				.add(MotorPosition.rearLeft, new Victor(2))
				.add("accelerometer", new MD_BuiltInAccelerometer())
				.add("IMU", new MD_IMU())
				.configure()
		);	
*/
		



		add(new RopeSubsystem(this, "ropeSubsystem")
				.add(RopeSubsystem.motorName, new Victor(6))
				.add("liftSpeed", new DoubleConfigSetting(-1.0, 1.0, 0.2))
				.configure());
		
		//add(new TalonDriveSubsystem(this, "talonSubsystem")
				//.add(TalonDriveSubsystem.motorName, new CANTalon(1))
//				.add("Talon-Speed", new DoubleConfigSetting(-1.0, 1.0, 0.2))
//				.add("Talon-F", new DoubleConfigSetting(0.0, 1.0, 0.0))
//		 	    .add("Talon-P", new DoubleConfigSetting(0.0, 1.0, 0.2))
//				.add("Talon-I", new DoubleConfigSetting(0.0, 1.0, 1.0))
//				.add("Talon-D", new DoubleConfigSetting(0.0, 1.0, 0.2))
//				.add("Talon-RPM", new DoubleConfigSetting(0.0, 100.0, 10.0))
				//.configure());
		
		
		add(new RumbleSubsystem(this, "rumbleSubsystem")
				//.add("rumbleDuration",new DoubleConfigSetting(0.0, 0.5, 0.12))
				//.add("rumbleIntenisty",new DoubleConfigSetting(0.0, 1.0, 0.5))
				// Not using for 2017
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
		//The last 2 items are SetAutonomousCommand
		// and CoreSubSystem		
		setAutonomousCommand(new MDCommandGroup[]{
				//new AUTOMoveFromWall(this),
				//new AUTODistanceDetection(this)
			}, "Auto1"  //specify the default
		);
		//Subsystem to manage robot wide config settings
		add( new CoreSubsystem(this, "core")
				 .add("name",new StringConfigSetting("MaterBot"))					//go ahead name your robot
				 .configure()
		);	
	}
	
	@Override
	public void teleopInit() {
		super.teleopInit();   //ArcardeCommand started in super.teleopInit();
		
	} 
	@Override
	public void autonomousInit() { 
		super.autonomousInit();

	} 	

	@Override
	public void disabledPeriodic() {
		super.disabledPeriodic();
//		if(getSubsystems()!=null && getSubsystems().containsKey("driveSystem")){
//			MDDriveSubsystem sys = (MDDriveSubsystem)getSubsystems().get("driveSystem");
//			if(sys.getSensors()!= null && sys.getSensors().containsKey("IMU")){
//				MD_IMU imu = (MD_IMU)sys.getSensors().get("IMU");
//				debug("angles: x = "+imu.getAngleX()+", y = "+imu.getAngleY()+", z = "+imu.getAngleZ()+ ", angle="+imu.getAngle());
//			}
//		}
	}

}


/* Override lifecycle methods, as needed
	@Override
	public void teleopPeriodic() {
			super.teleopPeriodic();
			...
		}
		@Override
		public void autonomousPeriodic() {
			super.autonomousPeriodic();
			...
		}	
	
		
		Event manager WebSocket related methods
		Override as needed
		@Override
		public void onConnect(Session session) {
			super.onConnect(session);
			...
		}
		
 }
*/
