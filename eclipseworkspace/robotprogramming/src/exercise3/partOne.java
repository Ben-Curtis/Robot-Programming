package exercise3;

import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

public class partOne extends RobotProgrammingDemo {

	private DifferentialPilot pilot;

	private boolean d = false;

	public partOne(WheeledRobotSystem m_robot) {

		this.pilot = m_robot.getPilot();

	}

	public void run() {

		SensorPort.S1.addSensorPortListener(new SensorPortListener() {

			// @Override
			public void stateChanged(SensorPort aSource, int OldValue,
					int NewValue) {
				if (NewValue < OldValue) {
					follow();
					d = true;
				}
			}
		});

		SensorPort.S4.addSensorPortListener(new SensorPortListener() {
			// @Override

			public void stateChanged(SensorPort aSource, int OldValue,
					int NewValue) {
				if (NewValue < OldValue) {
					follow();
					d = true;
				}
			}
		});

		while (m_run) {

			follow();
			while (pilot.isMoving()) {
				speed();
			}
		}
	}

	public void follow() {

		while (d == true) {

			LightSensor ls = new LightSensor(SensorPort.S1);
			LightSensor ls2 = new LightSensor(SensorPort.S4);

			ls.readValue();
			ls2.readValue();

			System.out.println(ls.getLightValue() + "&" + ls2.getLightValue());

			if (ls.getLightValue() <= 35) {

				pilot.stop();
				pilot.rotate(7.0);
				d = false;
			}

			else if (ls2.getLightValue() <= 35) {

				pilot.stop();
				pilot.rotate(-7.0);
				d = false;
			}

			if (ls.getLightValue() > 36 && ls2.getLightValue() > 36) {

				pilot.forward();
				d = false;
			}

			else if (ls.getLightValue() <= 35 && ls2.getLightValue() <= 35) {

				Random randomGenerator = new Random();

				pilot.stop();

				int randomInt = randomGenerator.nextInt(10);

				if (randomInt <= 5) {

					pilot.travel(80.0);
					pilot.rotate(90);
					d = false;
				}

				else {

					pilot.travel(80.0);
					pilot.rotate(-90);
					d = false;
				}
			}
		}
	}

	public void speed() {

		while (m_run) {

			UltrasonicSensor us = new UltrasonicSensor(SensorPort.S3);
			us.continuous();

			double sp = us.getDistance() * 1.8;

			pilot.setTravelSpeed(sp);

		}
	}

	public static void main(String[] args) {

		System.out.println("Hello World!");
		System.out.println();
		Button.waitForAnyPress();

		WheeledRobotSystem m_robot = new WheeledRobotSystem(
				RobotConfigs.EXPRESS_BOT);

		RobotProgrammingDemo demo = new partOne(m_robot);

		demo.run();

	}
}
