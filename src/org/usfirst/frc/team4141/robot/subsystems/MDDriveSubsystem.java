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
		frontLeft,
		rearLeft,
		frontRight,
		rearRight
	}
	
	private RobotDrive robotDrive;
	private Type type;
	private boolean isFlipped = false;
	private boolean resettingGyro = false;
	private long gyroResetStart;
	private long gyroResetDuration = 150;
	private double speed = 0;
	//private boolean isHighGear = false;
	public static String shiftSolenoid = "shiftSolenoid";
	public static String shiftSolenoid1 = "shiftSolenoid1";
	private Solenoid shifter;
	private Solenoid shifter1;
	private MD_IMU imu;
	private DualDistanceSensor distanceSensor;
	private ShiftGearSensor shiftGearSensor; 
	
	
	
	public MDDriveSubsystem(MDRobotBase robot, String name, Type type) {
		super(robot, name);
		this.type = type;
	}
	
	public MDDriveSubsystem add(MotorPosition position,SpeedController speedController){
		if(speedController instanceof PWM){
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
					|| !getSolenoids().containsKey(shiftSolenoid) || !(getSolenoids().get(shiftSolenoid) instanceof Solenoid)) {
					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid.");
			}	
			shifter=(Solenoid) getSolenoids().get(shiftSolenoid);

			if(getSolenoids()==null 
					|| !getSolenoids().containsKey(shiftSolenoid1) || !(getSolenoids().get(shiftSolenoid1) instanceof Solenoid)) {
					throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing shift solenoid1.");
			}	
			shifter1=(Solenoid) getSolenoids().get(shiftSolenoid1);
			
			if(getSensors()==null && !getSensors().containsKey("IMU")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing IMU.");
			}
		    imu=(MD_IMU) getSensors().get("IMU");
		    gyroReset();
		    
		    if(getSensors()==null && !getSensors().containsKey("dualDistance")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing Dual Distance Sensors.");
			}
		    distanceSensor=(DualDistanceSensor) getSensors().get("dualDistance");
		    if(getSensors()==null && !getSensors().containsKey("High Gear")){
				throw new IllegalArgumentException("Invalid MDDriveSubsystem configuraton, missing Gear Shift Sensors.");
			}
		    shiftGearSensor=(ShiftGearSensor) getSensors().get("High Gear");
			
			
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
	public Type getType(){ return type;}

	@Override
	protected void initDefaultCommand() {
		robotDrive.stopMotor();
		//set up default command, as needed
		//setDefaultCommand(new ArcadeDriveCommand(getRobot()));
	}
	
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
//			System.out.println("mecanum...");
			double magnitude= calculateMagnitude(joystick.getRawAxis(0),joystick.getRawAxis(1));
			double direction = calculateDirection(-joystick.getRawAxis(0),-joystick.getRawAxis(1));
			double rotation = joystick.getRawAxis(4);
			robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
			break;
		default:
//			  double rightTriggerValue = joystick.getRawAxis(3);
//			  double leftTriggerValue = -joystick.getRawAxis(2);
			  double forwardAxisValue = -joystick.getRawAxis(1);
			  double forward = (forwardAxisValue)*(1.0-(1.0-c));
		  	  double rotate = joystick.getRawAxis(2); //(Changed to accompass shifting w/controller and deadzoned)
//	  	  debug("forward = " + forward + ", rotate = " + rotate);
		  	  double[] speeds = interpolator.calculate(forward, rotate, isFlipped);
		  	 // debug("left: "+speeds[0]+", right: "+speeds[1]);
			  robotDrive.tankDrive(-speeds[0], -speeds[1]);
		}
	}
	
	public void stop(){
		debug("motors stopped");
		robotDrive.stopMotor();
		speed = 0;
	}	
	
	private double c = 1.0;
	private TankDriveInterpolator interpolator = new TankDriveInterpolator();
	@Override
	protected void setUp() {
		//called after configuration is completed
		if(getConfigSettings().containsKey("c")) c = getConfigSettings().get("c").getDouble();
		if(getConfigSettings().containsKey("a")) interpolator.setA(getConfigSettings().get("a").getDouble());
		if(getConfigSettings().containsKey("b")) interpolator.setB(getConfigSettings().get("b").getDouble());
	}
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("c")) c = changedSetting.getDouble();
		if(changedSetting.getName().equals("a")) interpolator.setA(changedSetting.getDouble());
		if(changedSetting.getName().equals("b")) interpolator.setB(changedSetting.getDouble());
		//method to listen to setting changes
	}

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
	
	public void flip() {
		
		if (speed != 0) return;
		isFlipped = !isFlipped;
		debug("flip. isFlipped now sent to " + isFlipped + ". speed = " + speed);
	}
	
	public void shift() {
		stop();
		shiftGearSensor.set(!shiftGearSensor.get());
		shifter.set(shiftGearSensor.get());
		shifter1.set(shiftGearSensor.get());
		debug("shifted to " + (shiftGearSensor.get()?"high gear" : "low gear"));
		
	}
	public double getAngle() {
		if (resettingGyro) { 
			long now = (new Date()).getTime();
			if (now - gyroResetStart <= gyroResetDuration) return 0;
			else resettingGyro = false;
		}
		
		return imu.getAngleZ();
	}
	
	public double getDistance() {
		SensorReading[] readings = distanceSensor.getReadings();
		String s = new String();
		for (int i = 0; i < readings.length; i++){ //initiation, change, and condition
			if (i!= 0){
				s += "\t";
			}
			s += ("Distance["+i+"]="+((AnalogSensorReading)(readings[i])).getValue());
		}
		debug(s);
		return ((AnalogSensorReading)(readings[0])).getValue();
	}

	public void gyroReset() {
		imu.reset();
		resettingGyro = true;
	    gyroResetStart = (new Date()).getTime();
	}
	
	public void gyroRefresh() {
		imu.refresh();
	}
	public void distanceRefresh() {
		distanceSensor.refresh();
	}
	
	boolean isOn = false;
	public void toggleLight(){
		if(getSensors()==null && !getSensors().containsKey("dualDistance")) return;
		isOn=!isOn;
		distanceSensor.setStatusLed(isOn);
	}
	public void setLight(boolean lightState){
		if(getSensors()==null && !getSensors().containsKey("dualDistance")) return;
		isOn=lightState;
		distanceSensor.setStatusLed(isOn);
	}	
	
	public void move(double speed, double angle) {
		if(speed == 0) {stop();return;}
//		double correction = angle/180.00;
//  	  		debug("speed = " + speed + ", angle = " + angle+ ", correction = "+correction+", isFlipped = "+ isFlipped);
//	  	  double[] speeds = interpolator.calculate(speed, correction, isFlipped);
		double[] speeds = new double[2];
		if(angle>=0){
			if(angle>20) angle = 20;
			speeds[1]=speed;
			speeds[0]=speed*(1.0 - angle/10.0);
		}
		else{
			if(angle<-20) angle = -20;
			speeds[1]=speed*(1.0 + angle/10.0);
			speeds[0]=speed;
		}
		robotDrive.tankDrive(speeds[0], speeds[1]);
	}

}






