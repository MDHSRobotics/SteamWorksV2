package org.usfirst.frc.team4141.robot.subsystems;

import java.util.Date;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.MultiSpeedController;
import org.usfirst.frc.team4141.MDRobotBase.NotImplementedException;
import org.usfirst.frc.team4141.MDRobotBase.TankDriveInterpolator;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.sensors.AnalogSensorReading;
import org.usfirst.frc.team4141.MDRobotBase.sensors.DualDistanceSensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;
import org.usfirst.frc.team4141.MDRobotBase.sensors.ShiftGearSensor;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;


public class MDDriveSubsystem extends MDSubsystem {
	
	public enum Type{
		TankDrive,
		MecanumDrive
	}
	public enum MotorPosition{
		left,
		right,
		secondRight,
		secondLeft,
		frontLeft,
		rearLeft,
		frontRight,
		rearRight
	}
	
	// ------------------------------------------------ //
	
	private RobotDrive robotDrive;
	private Type type;
 // private boolean isHighGear = false;
	private boolean isFlipped = false;
	private boolean resettingGyro = false;
	private long gyroResetStart;
	private long gyroResetDuration = 150;
	private double speed = 0;
	private double c = 1.0;
	public static String rightShiftSolenoidName = "rightShiftSolenoid";
//	public static String leftShiftSolenoidName = "leftShiftSolenoid";
	private Solenoid rightShiftSolenoid;
//	private Solenoid leftShiftSolenoid;
	private MD_IMU imu;
	private ShiftGearSensor shiftGearSensor; 
	private TankDriveInterpolator interpolator = new TankDriveInterpolator();
	
//	private double F=0.0;
//	private double P=0.0;
//	private double I=0.1;
//	private double D=0.0;
//	private double rpm=1.0;
//	
//	private CANTalon rearLeftTalon;
//	private CANTalon rearRightTalon;
//	private CANTalon frontLeftTalon;
//	private CANTalon frontRightTalon;
	
	// ------------------------------------------------ //
	
	public MDDriveSubsystem(MDRobotBase robot, String name, Type type) {
		super(robot, name);
		this.type = type;
	}
	
	public MDDriveSubsystem add(MotorPosition position,SpeedController speedController){
		if(speedController instanceof PWM || speedController instanceof CANTalon){
			super.add(position.toString(),(SpeedController)speedController);
		}
		else
		{
			throw new NotImplementedException("Input is not a PWM");
		}
		return this;
	}

	public MDDriveSubsystem add(String name,Sensor sensor){
		super.add(name,sensor);
		return this;
	}
	
	// ------------------------------------------------ //
	
	public SpeedController get(MotorPosition position){
		SpeedController motor = getMotors().get(position.toString());
		if(motor instanceof SpeedController){
			return (SpeedController) motor;
		}
		return null;
	}
	
	public MDSubsystem configure(){
		super.configure();
		switch(type){
		case TankDrive:
			if(getMotors()==null){
				throw new IllegalArgumentException("Invalid motor configuration for TankDrive system.");
			}				
			if(getMotors().size()==2){
				if(!getMotors().containsKey(MotorPosition.left.toString()) || !getMotors().containsKey(MotorPosition.right.toString())){
					throw new IllegalArgumentException("Invalid MDDriveSubsystem TankDrive configuraton, missing motors.");
				}
				robotDrive = new RobotDrive(get(MotorPosition.left), get(MotorPosition.right));
			}
			else if(getMotors().size()==4){
				if(!getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
						  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
					throw new IllegalArgumentException("Invalid MDDriveSubsystem TankDrive configuraton, missing motors.");
				}
				robotDrive = new RobotDrive(new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearLeft), get(MotorPosition.frontLeft)}),
						new MultiSpeedController(new SpeedController[]{get(MotorPosition.rearRight), get(MotorPosition.frontRight)}));
			}
			
			if(getSolenoids()==null 
					|| !getSolenoids().containsKey(rightShiftSolenoidName) || !(getSolenoids().get(rightShiftSolenoidName) instanceof Solenoid)) {
					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid.");
			}	
			rightShiftSolenoid=(Solenoid) getSolenoids().get(rightShiftSolenoidName);

//			if(getSolenoids()==null 
//					|| !getSolenoids().containsKey(leftShiftSolenoidName) || !(getSolenoids().get(leftShiftSolenoidName) instanceof Solenoid)) {
//					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid1.");
//			}	
//			leftShiftSolenoid=(Solenoid) getSolenoids().get(leftShiftSolenoidName);
//			
			if(getSensors()==null && !getSensors().containsKey("IMU")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing IMU.");
			}
		    imu=(MD_IMU) getSensors().get("IMU");
		    gyroReset();
		    if(getSensors()==null && !getSensors().containsKey("High Gear")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing Gear Shift Sensors.");
			}
		    shiftGearSensor=(ShiftGearSensor) getSensors().get("High Gear");
//		    rearLeftTalon = (CANTalon)(getMotors().get(MotorPosition.rearLeft));
//		    rearRightTalon = (CANTalon)(getMotors().get(MotorPosition.rearRight));
//		    frontLeftTalon = (CANTalon)(getMotors().get(MotorPosition.frontLeft));
//		    frontRightTalon = (CANTalon)(getMotors().get(MotorPosition.frontRight));
			
			
			break;
		case MecanumDrive:
			if(getMotors()==null || !getMotors().containsKey(MotorPosition.rearLeft.toString()) || !getMotors().containsKey(MotorPosition.frontLeft.toString())
									  || !getMotors().containsKey(MotorPosition.rearRight.toString()) || !getMotors().containsKey(MotorPosition.frontRight.toString())){
				throw new IllegalArgumentException("Invalid motor configuration for MecanumDrive system.");
			}	
			robotDrive = new RobotDrive(get(MotorPosition.rearLeft), get(MotorPosition.frontLeft),
					get(MotorPosition.rearRight), get(MotorPosition.frontRight));
			break;
		default:
			throw new NotImplementedException("drive of type "+type.toString()+" is not supported.");
		}
		robotDrive.stopMotor();
		return this;
	}
	
	public Type getType() { 
		return type;
	}

	@Override
	protected void initDefaultCommand() {
		robotDrive.stopMotor();
		//set up default command, as needed
		//setDefaultCommand(new ArcadeDriveCommand(getRobot()));
	}
	
	// ------------------------------------------------ //\

	private double calculateMagnitude(double x,double y){
		//joystick will give x & y in a range of -1 <= 0 <= 1
		// the magnitude indicates how fast the robot shoudl be driving
		// use the distance formula:  s = sqrt(x^2 = y^2)
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	private double calculateDirection(double x, double y){
			//joystick will give x & y in a range of -1 <= 0 <= 1
			// the direction indicates in what angle the robot should move. this is not rotation.
			// 0 degrees means go straight
			// 180 degrees means back up
			// 90 degrees means go to the right
			// use trigonometry.  Tangent (angle) = opposite (in our case x) / adjacent (in our case y)
			// we have x & y, solve for angle by taking the inverse tangent
			// angle = tangent^-1(x/y)
			// since this includes a division we need logic to handle things when x & y are 0
			double angle = 0;
			if(y==0){
				if(x>0) angle = 90;
				if (x<0) angle = -90;
			}
			else if (x==0){
				if(y<0) angle = 180;
			}
			else{
				angle = Math.atan2(x, y)*180/Math.PI;
			}
			return angle;  
	}
	
	public void arcadeDrive(Joystick joystick) {
		switch(type){
		case MecanumDrive:
			double magnitude= calculateMagnitude(joystick.getRawAxis(0),joystick.getRawAxis(1));
			double direction = calculateDirection(-joystick.getRawAxis(0),-joystick.getRawAxis(1));
			double rotation = joystick.getRawAxis(4);
			robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
			break;
		default:
		 // double rightTriggerValue = joystick.getRawAxis(3);
		 //	double leftTriggerValue = -joystick.getRawAxis(2);
			double forwardAxisValue = -joystick.getRawAxis(1);
			double forward = (forwardAxisValue)*(1.0-(1.0-c));
		  	double rotate = -joystick.getRawAxis(4); //(Changed to accompass shifting w/controller and deadzoned)
	  	  debug("forward = " + forward + ", rotate = " + rotate);
		  	double[] speeds = interpolator.calculate(forward, rotate, isFlipped);
		    //debug("left: "+speeds[0]+", right: "+speeds[1]);
			robotDrive.tankDrive(-speeds[0], -speeds[1]);
		}
	}
	
	// ------------------------------------------------ //

	public void stop(){
		debug("motors stopped");
		robotDrive.stopMotor();
		speed = 0;
	}	
	
	@Override
	protected void setUp() {
		//called after configuration is completed
		if(getConfigSettings().containsKey("c")) c = getConfigSettings().get("c").getDouble();
		if(getConfigSettings().containsKey("a")) interpolator.setA(getConfigSettings().get("a").getDouble());
		if(getConfigSettings().containsKey("b")) interpolator.setB(getConfigSettings().get("b").getDouble());
//		if(getConfigSettings().containsKey("F")) F = getConfigSettings().get("F").getDouble();
//		if(getConfigSettings().containsKey("P")) P = getConfigSettings().get("P").getDouble();
//		if(getConfigSettings().containsKey("I")) I = getConfigSettings().get("I").getDouble();
//		if(getConfigSettings().containsKey("D")) D = getConfigSettings().get("D").getDouble();
//		if(getConfigSettings().containsKey("RPM")) rpm = getConfigSettings().get("RPM").getDouble();//*1000;
	}
	
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("c")) c = changedSetting.getDouble();
		if(changedSetting.getName().equals("a")) interpolator.setA(changedSetting.getDouble());
		if(changedSetting.getName().equals("b")) interpolator.setB(changedSetting.getDouble());
//		if(changedSetting.getName().equals("F")) F = changedSetting.getDouble();
//		if(changedSetting.getName().equals("P")) P = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("I")) I = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("D")) D = changedSetting.getDouble()*pidFactor;
//		if(changedSetting.getName().equals("RPM")) rpm = changedSetting.getDouble();//*1000;
		//method to listen to setting changes
	}

	// ------------------------------------------------ //

	public void right(double speed) {
		this.speed = speed;
		
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("right");
		double direction = -90;
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			robotDrive.tankDrive(this.speed, this.speed/10);
		}
	}

	public void left(double speed) {
		this.speed = speed;
		
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("left");
		double direction = 90;
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			robotDrive.tankDrive(this.speed/10, this.speed);
		}
	}

	public void reverse(double speed) {
		this.speed = speed;
		if (isFlipped) {
			this.speed = -this.speed;
		}
		debug("reverse");
		double direction = 180;
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			robotDrive.tankDrive(-this.speed, this.speed);
		}
	}

	public void forward(double speed) {
	//	debug("forward"); 	
		this.speed = speed;
		if (isFlipped) {
			this.speed = -this.speed;
		}
		
		double direction = 0;
		
		switch(type){
		case MecanumDrive:
			robotDrive.mecanumDrive_Polar(speed, direction, 0);
			break;
		default:
			debug("speed =" + speed);
			robotDrive.tankDrive(this.speed, this.speed);
		}
	}
	
	// ------------------------------------------------ //

	public void flip() {
		
		if (speed != 0) return;
		isFlipped = !isFlipped;
		debug("flip. isFlipped now sent to " + isFlipped + ". speed = " + speed);
	}
	
	public void shift() {
		stop();
		shiftGearSensor.set(!shiftGearSensor.get());
		rightShiftSolenoid.set(shiftGearSensor.get());
//		leftShiftSolenoid.set(shiftGearSensor.get());
		debug("shifted to " + (shiftGearSensor.get()?"high gear" : "low gear"));
		
	}
	
	public double getAngle() {
		if (resettingGyro) { 
			long now = (new Date()).getTime();
			if (now - gyroResetStart <= gyroResetDuration) return 0;
			else resettingGyro = false;
		}

		return imu.getAngleZ();
//		return imu.getAngleX();
	}

	// ------------------------------------------------ //

	public void gyroReset() {
		imu.reset();
		resettingGyro = true;
	    gyroResetStart = (new Date()).getTime();
	}

	public void move(double speed, double angle) {
		if(speed == 0) {stop();return;}
		double correction = angle/180.00;
  	  	debug("speed = " + speed + ", angle = " + angle+ ", correction = "+correction+", isFlipped = "+ isFlipped);
	  	//double[] speeds = interpolator.calculate(speed, correction, isFlipped);
		double[] speeds = new double[2];
		if(angle>=0){
			if(angle>10) angle = 10;
			speeds[1]=speed;
			speeds[0]=speed*(1.0 - angle/10.0);
		}
		else{
			if(angle<-10) angle = -10;
			speeds[1]=speed*(1.0 + angle/10.0);
			speeds[0]=speed;
		}
		robotDrive.tankDrive(speeds[0], speeds[1]);
	}
	//TODO 
	public boolean isLowGear(){
		if(shiftGearSensor.equals(false)){
			return true;
		} else {
			return false;
		}
	}

	// boolean isOn = false; // Why is this here? What is this? It doesn't link to anything.;
			

}






