package exercise3;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

public class partTwo extends RobotProgrammingDemo {

	private DifferentialPilot pilot;
	private boolean d = false;
	private int i = 1;

	public partTwo(WheeledRobotSystem m_robot) {

		this.pilot = m_robot.getPilot();
	}

	public void run() {

		SensorPort.S1.addSensorPortListener(new SensorPortListener() {
			// @Override
			public void stateChanged(SensorPort aSource, int OldValue,
					int NewValue) {
				if (NewValue < OldValue) {

					d = true;
				}
			}
		});

		SensorPort.S4.addSensorPortListener(new SensorPortListener() {
			// @Override
			public void stateChanged(SensorPort aSource, int OldValue,
					int NewValue) {

				if (NewValue < OldValue) {
					d = true;
				}
			}
		});

		while (m_run) {
			follow();
		}
	}

	public void follow() {

		while (d == false) {

			pilot.setTravelSpeed(60.0);
			pilot.forward();
		}

		while (d == true) {

			LightSensor ls = new LightSensor(SensorPort.S1);
			LightSensor ls2 = new LightSensor(SensorPort.S4);

			if (ls.getLightValue() <= 37 && ls2.getLightValue() <= 37) {

				// sets path for robot, i represents each square passed
				if (i == 2 || i == 5 || i == 6) {
					forwards();
				}

				else if (i == 1 || i == 4 || i == 7) {
					right();
				} else if (i == 3 || i > 7) {
					left();
				}
			}
		}
	}

	public void left() {

		pilot.stop();
		pilot.setTravelSpeed(60.0);
		pilot.travel(80.0);
		pilot.rotate(90.0);
		i = i + 1;
		d = false;
	}

	public void right() {

		pilot.stop();
		pilot.setTravelSpeed(60.0);
		pilot.travel(80.0);
		pilot.rotate(-90.0);
		i = i + 1;
		d = false;
	}

	public void forwards() {

		pilot.setTravelSpeed(60.0);
		pilot.travel(80.0);
		i = i + 1;
		d = false;
	}

	public static void main(String[] args) {

		System.out.println("Hello World!");
		Button.waitForAnyPress();
		WheeledRobotSystem robot = new WheeledRobotSystem(
				RobotConfigs.EXPRESS_BOT);
		RobotProgrammingDemo demo = new partTwo(robot);
		demo.run();
	}
}