package robotsearch;

import graphsearch.Coordinate;
import graphsearch.Holds;
import graphsearch.Node;
import graphsearch.depthFirst;
import ilist.IList;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;
import maybe.Maybe;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

public class PathFinding extends RobotProgrammingDemo {
	
	private DifferentialPilot pilot;
	private boolean d = false;
	
	public PathFinding(WheeledRobotSystem m_robot) {

		this.pilot = m_robot.getPilot();
	}
	
	public void left() {

		pilot.stop();
		pilot.setTravelSpeed(60.0);
		pilot.travel(80.0);
		pilot.rotate(90.0);
		d = false;
	}

	public void right() {

		pilot.stop();
		pilot.setTravelSpeed(60.0);
		pilot.travel(80.0);
		pilot.rotate(-90.0);
		d = false;
	}

	public void forwards() {

		pilot.setTravelSpeed(60.0);
		pilot.travel(80.0);
		d = false;
	}
	
	public void reverse() {
		
		pilot.setTravelSpeed(60.0);
		pilot.travel(-80.0);
		d = false;
	}

	public static void main(String[] args) {

		System.out.println("Hello World!");
		Button.waitForAnyPress();
		WheeledRobotSystem robot = new WheeledRobotSystem(
				RobotConfigs.EXPRESS_BOT);
		RobotProgrammingDemo demo = new PathFinding(robot);
		demo.run();
	}

	@Override
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
		

		System.out.println("startmapping");
		RPLineMap lineMap = MapUtils.create2015Map1();
		gridMap gridMap = new gridMap(8, 12, 15, 15, 30, lineMap);

		Search<Coordinate> graph = new Search<Coordinate>(lineMap, gridMap);
		ArrayList<Coordinate> coords = graph.createCoordinates();
		//System.out.println(coords.size());
		ArrayList<Node<Coordinate>> nodes = graph.createNodes(coords);
		
		System.out.println("nodes created");
		
		for (int i = 0; i < 82; i++) {
			System.out.println(i);
			Node<Coordinate> n = (Node<Coordinate>) nodes.get(i);
			Coordinate c = n.contents();
			int x1 = ((Coordinate) c).getX();
			int y1 = ((Coordinate) c).getY();
			//System.out.println(x1 +", " +y1);
			int x2 = x1 + 1;
			int y2 = y1 + 1;
			
			int x3 = x1 - 1;
			int y3 = y1 - 1;
			
			if (gridMap.isValidTransition(x1, y1, x1, y2) == true) {
				Coordinate p = new Coordinate (x1, y2);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}
			
			if (gridMap.isValidTransition(x1, y1, x2, y1) == true) {
				Coordinate p = new Coordinate (x2, y1);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}

			if (gridMap.isValidTransition(x1, y1, x1, y3) == true) {
				Coordinate p = new Coordinate (x1, y3);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}
			
			if (gridMap.isValidTransition(x1, y1, x3, y1) == true) {
				Coordinate p = new Coordinate (x3, y1);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}
			System.out.print(".  " + n.successors());
		}
		
		System.out.println("successors added");
		
		Node<Coordinate> startNode = graph.nodeWith(new Coordinate(0, 0));
		System.out.println(startNode.successors());
		Node<Coordinate> findNode = graph.nodeWith(new Coordinate(1, 0));
		Holds<Coordinate> h = new Holds<Coordinate>(findNode);

		depthFirst<Coordinate> depthfirst = new depthFirst<Coordinate>();
		
		Maybe<IList<Node<Coordinate>>> path = depthfirst.findPathFrom(startNode, h);
		
		System.out.println("Depth First Search: "
				+ path);

		IList<Node<Coordinate>> path2 = path.fromMaybe();
		System.out.println(path2);
		while (m_run) {
			if (d == false) {
				pilot.setTravelSpeed(60.0);
				pilot.forward();
			}
			
			if (d == true) {
				
				while (!path2.isEmpty()) {
					
					// head of path
					Node<Coordinate> b = path2.getHead().fromMaybe();
					Coordinate c1 = b.contents();
					int _x1 = c1.getX();
					int _y1 = c1.getY();
					System.out.print(c1 + " / ");
		
					if (path2.getTail().fromMaybe().isEmpty()) {
						break;
					}
					
					IList<Node<Coordinate>> tail = path2.getTail().fromMaybe();
					
					Node<Coordinate> t = tail.getHead().fromMaybe();
		
					Coordinate c2 = t.contents();
					System.out.print(c2);
		
					int _x2 = c2.getX();
					int _y2 = c2.getY();
					
					if ((_x2 == _x1) && (_y1 < _y2)) {
						right();
					}
					
					if ((_x2 == _x1) && (_y1 < _y2)) {
						left();
					}
					
					if ((_x2 < _x1) && (_y1 == _y2)) {
						reverse();
					}
					
					if ((_x2 > _x1)&&(_y1 == _y2)) {
						forwards();
					}
					
					System.out.println(" ");
		
					path2 = tail;
				}
			}
		}
	}	
}