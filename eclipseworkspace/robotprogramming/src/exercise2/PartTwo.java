package exercise2;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

public class PartTwo extends RobotProgrammingDemo {

	private DifferentialPilot pilot;

	public PartTwo(WheeledRobotSystem m_robot) {
		this.pilot = m_robot.getPilot();
	}

	public void run() {
		while (m_run) {
			TouchSensor touch = new TouchSensor(SensorPort.S1);
			TouchSensor touch2 = new TouchSensor(SensorPort.S2);
			if (!touch.isPressed() && !touch2.isPressed()) {
				pilot.setTravelSpeed(160.0);
				pilot.forward();
			} else if (Button.ESCAPE.isDown()) {
				break;
			} else {
				pilot.stop();
				pilot.travel(-100);
				pilot.stop();
				pilot.rotate(180.0);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		System.out.println();
		Button.waitForAnyPress();
		WheeledRobotSystem robot = new WheeledRobotSystem(
				RobotConfigs.EXPRESS_BOT);
		RobotProgrammingDemo demo = new PartTwo(robot);
		demo.run();
	}
}