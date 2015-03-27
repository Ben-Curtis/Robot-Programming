package exercise2;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

public class PartTwoListener extends RobotProgrammingDemo {

	private final WheeledRobotSystem robot;
	public static TouchSensor ts1 = new TouchSensor(SensorPort.S1);
	public static TouchSensor ts2 = new TouchSensor(SensorPort.S2);
	public boolean t = false;

	public PartTwoListener(WheeledRobotSystem robot) {
		this.robot = robot;
	}

	@Override
	public void run() {

		DifferentialPilot p = robot.getPilot();
		p.setTravelSpeed(160.0);
		p.setRotateSpeed(90);
		
		SensorPort.S1.addSensorPortListener(new SensorPortListener() {

			@Override
			public void stateChanged(SensorPort aSource, int OldValue,
					int NewValue) {

				if (NewValue < 1000) {
					touched();
				}
			}
		});
		
		SensorPort.S2.addSensorPortListener(new SensorPortListener() {

			@Override
			public void stateChanged(SensorPort aSource, int OldValue,
					int NewValue) {

				if (NewValue < 1000) {
					touched();
				}
			}
		});

		while (m_run) {

			p.travel(100,true);

			if (t == true) {
				p.stop();
				p.travel(-100);
				p.rotate(180);
				t = false;
			}
		}
	}

	public void touched() {
		t = true;
	}

	public static void main(String[] args) {

		Button.waitForAnyPress();
		WheeledRobotSystem robot = new WheeledRobotSystem(
				RobotConfigs.EXPRESS_BOT);
		RobotProgrammingDemo demo = new PartTwoListener(robot);
		demo.run();
	}

}
