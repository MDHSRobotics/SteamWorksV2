package org.usfirst.frc.team4141.MDRobotBase.sensors;

import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

public class SteamTargetSensor implements Sensor{
   
	private DigitalSensorReading reading;
	private String name;
	private boolean observe;
	private MDSubsystem subsystem;


	public SteamTargetSensor(){
		this(null);
	}
	
	public SteamTargetSensor(MDSubsystem subsystem){
		this(null, true);
	}
	
	public SteamTargetSensor(MDSubsystem subsystem, boolean observe){
		this.observe = observe;
		this.subsystem = subsystem;
		reading=new DigitalSensorReading(this,"Steam Target", false);
	}

	public void set(boolean targetAcquried){
		reading.setValue(targetAcquried);
	}
	
	public boolean get(){
		return reading.getValue();
	}
	
	public void setName(String name){
		this.name = name;
	}	

	public void refresh(){
	}

	@Override
	public String getName() {
		return name;
	}

	public SensorReading[] getReadings() {
		return new SensorReading[]{reading};
	}


	@Override
	public boolean observe() {
		return observe;
	}
	public void setObserve(boolean observe){
		this.observe = observe;
	}

	@Override
	public MDSubsystem getSubsystem() {
		return subsystem;
	}
	@Override
	public Sensor setSubsystem(MDSubsystem subsystem) {
		this.subsystem = subsystem;
		return this;
	}
}
