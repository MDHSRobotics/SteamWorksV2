package org.usfirst.frc.team4141.MDRobotBase.sensors;

import java.nio.ByteBuffer;

import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

import edu.wpi.first.wpilibj.I2C;

public class DualDistanceSensor implements Sensor{
   private static final char LED_COMMAND = 'L';
   private static final char LED_ON = 'H';
   private static final char LED_OFF = 'L';
   private static final byte DATA_ADDRESS = 0x02;
   private static final byte MODE_ADDRESS = 0x06;
   private static final short DATA_SIZE = 4;  //in bytes
   private static final short MODE_COMMAND_SIZE = 7;

   
	SensorReading[] readings = new SensorReading[2];
	private String name;
	private I2C i2cDevice;
	private boolean observe;
	private MDSubsystem subsystem;


	public DualDistanceSensor(int address){
		this(null,address);
	}
	
	public DualDistanceSensor(MDSubsystem subsystem, int address){
		this(null,address, true);
	}
	
	public DualDistanceSensor(MDSubsystem subsystem, int address, boolean observe){
		this.observe = observe;
		this.subsystem = subsystem;
		i2cDevice=new I2C(I2C.Port.kOnboard, address);
		int i=0;
		readings[i++]=new AnalogSensorReading(this,"distance0", 0);
		readings[i++]=new AnalogSensorReading(this,"distance1", 0);
	}
	
	
   static byte [] charToByteArray( char c )
   {
      byte [] twoBytes = { (byte)(c >> 8 & 0xff), (byte)(c & 0xff) };
      return twoBytes;
   }


	public void setName(String name){
		this.name = name;
	}	

	public void refresh(){
		ByteBuffer data = ByteBuffer.allocateDirect(DATA_SIZE);//size of 2 char (e.g. 2 byte unsigned ints
		  
//		System.out.print("Reading i2c");
		if(!i2cDevice.read(DATA_ADDRESS, DATA_SIZE, data)){
			for(int i=0;i<DATA_SIZE/2;i++){
				((AnalogSensorReading)readings[i]).setValue(data.getChar());
//				if(i>0) System.out.print("\t");
//				System.out.printf("%d", (int)(((AnalogSensorReading)readings[i]).getValue()));
			}
		
		}else{
//			System.out.print("\tCould not read");
		}
//		System.out.println();
	}

	@Override
	public String getName() {
		return name;
	}

	public SensorReading[] getReadings() {
		return readings;
	}



public void setStatusLed(boolean state){
	   sendCommand(LED_COMMAND,new char[]{(state?LED_ON:LED_OFF),0x00});
   }

   	private void sendCommand(char command, char[] commandData) {
   		switch(command){
		case LED_COMMAND:
			//this is a mode command
			//device currently supports a 3 unsigned 2byte int e.g. char structure 
			//in addition, mode is currently defined at register address 0x06
			//create the command byte[]
			ByteBuffer data = ByteBuffer.allocateDirect(MODE_COMMAND_SIZE);
			data.put(MODE_ADDRESS);
			data.put(charToByteArray(command));
			for(char c : commandData){
				data.put(charToByteArray(c));
			}
			i2cDevice.writeBulk(data,MODE_COMMAND_SIZE);
			break;
		}
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
