package exercise2;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

public class PartThree extends RobotProgrammingDemo {

	private DifferentialPilot pilot;

	public PartThree(WheeledRobotSystem m_robot) {
		this.pilot = m_robot.getPilot();
		
	}

	public void run() {
		while(m_run) {
			TouchSensor touch = new TouchSensor(SensorPort.S1);
			TouchSensor touch2 = new TouchSensor(SensorPort.S2);
			UltrasonicSensor rf = new UltrasonicSensor(SensorPort.S3);

			if(!touch.isPressed() && !touch2.isPressed()) {
			pilot.setTravelSpeed(150.0);
			pilot.forward();
			}
			else if (Button.ESCAPE.isDown()) {
				break;
			}
			else if(rf.getDistance() > 15.0) {
				turnRight();
			}
			else {
				turnLeft();
			}
		}
	}
	
	public void turnLeft() {
		pilot.stop();
		pilot.backward();
		Delay.msDelay(200);
		pilot.stop();
		pilot.rotate(45.0);
	}
	
	public void turnRight() {
		pilot.stop();
		pilot.backward();
		Delay.msDelay(200);
		pilot.stop();
		pilot.rotate(-45.0);
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		System.out.println();
		Button.waitForAnyPress();
		WheeledRobotSystem robot = new WheeledRobotSystem(
			RobotConfigs.EXPRESS_BOT);
		RobotProgrammingDemo demo = new PartThree(robot);
		demo.run();
	}
}