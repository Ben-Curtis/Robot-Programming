package exercise2;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

public class PartOne extends RobotProgrammingDemo {

	private DifferentialPilot pilot;
	private static boolean start = true;

	public PartOne(WheeledRobotSystem m_robot) {
		this.pilot = m_robot.getPilot();
	}

	@Override
	public void run() {
		pilot.setTravelSpeed(100.0);

		Button.ENTER.addButtonListener(new ButtonListener() {
			public void buttonPressed(Button b) {
				if (start == true) {
					start = false;
				} else if (start == false) {
					start = true;
				}
			}

			public void buttonReleased(Button b) {
			}
		});

		while (m_run) {
			patternOne();
			patternTwo();

			if (Button.ESCAPE.isDown()) {
				break;
			}
		}
	}

	public void patternOne() {
		if (start == true) {
			pilot.setTravelSpeed(200.0);
			pilot.travel(500.0, false);
			pilot.stop();
			pilot.rotate(180.0, false);
		}
	}

	public void patternTwo() {
		if (start == false) {
			pilot.setTravelSpeed(150.0);
			pilot.travel(500.0, false);
			pilot.stop();
			pilot.rotate(540.0, false);
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		System.out.println();
		Button.waitForAnyPress();
		WheeledRobotSystem robot = new WheeledRobotSystem(
				RobotConfigs.EXPRESS_BOT);
		RobotProgrammingDemo demo = new PartOne(robot);
		demo.run();
	}
}