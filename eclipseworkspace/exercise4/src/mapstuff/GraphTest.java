package mapstuff;

import graphsearch.Coordinate;
import graphsearch.Holds;
import graphsearch.Node;
import graphsearch.depthFirst;
import ilist.IList;

import java.util.ArrayList;
import java.util.Random;

import maybe.Maybe;

import org.testng.Assert;
import org.testng.annotations.Test;

import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;

public class GraphTest {

	public GraphTest() {

	}

	@Test
	public void testPathFind() {

		RPLineMap lineMap = MapUtils.create2015Map1();
		gridMap gridMap = new gridMap(8, 12, 15, 15, 30, lineMap);

		Graph<Coordinate> graph = new Graph<Coordinate>(lineMap, gridMap);
		ArrayList<Coordinate> coords = graph.createCoordinates();
		// System.out.println(coords.size());
		ArrayList<Node<Coordinate>> nodes = graph.createNodes(coords);

		for (int i = 0; i < nodes.size(); i++) {
			Node<Coordinate> n = (Node<Coordinate>) nodes.get(i);
			Coordinate c = n.contents();
			int x1 = ((Coordinate) c).getX();
			int y1 = ((Coordinate) c).getY();
			// System.out.println(x1 +", " +y1);
			int x2 = x1 + 1;
			int y2 = y1 + 1;

			int x3 = x1 - 1;
			int y3 = y1 - 1;

			if (gridMap.isValidTransition(x1, y1, x1, y2) == true) {
				Coordinate p = new Coordinate(x1, y2);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}

			if (gridMap.isValidTransition(x1, y1, x2, y1) == true) {
				Coordinate p = new Coordinate(x2, y1);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}

			if (gridMap.isValidTransition(x1, y1, x1, y3) == true) {
				Coordinate p = new Coordinate(x1, y3);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}

			if (gridMap.isValidTransition(x1, y1, x3, y1) == true) {
				Coordinate p = new Coordinate(x3, y1);
				Node<Coordinate> s = graph.nodeWith(p);
				n.addSuccessor(s);
			}
		}

		for (int i = 0; i < 10; i++) {

			Random r = new Random();

			int x1 = r.nextInt(12);
			int y1 = r.nextInt(8);
			int x2 = r.nextInt(12);
			int y2 = r.nextInt(8);

			depthFirst<Coordinate> astar = new depthFirst<Coordinate>();

			Node<Coordinate> startNode = graph.nodeWith(new Coordinate(x1, y1));

			while (gridMap.isObstructed(x1, y1) == true) {
				x1 = r.nextInt(12);
				y1 = r.nextInt(8);
				startNode = graph.nodeWith(new Coordinate(x1, y1));
			}

			Node<Coordinate> findNode = graph.nodeWith(new Coordinate(x2, y2));

			while (gridMap.isObstructed(x2, y2) == true) {
				x2 = r.nextInt(12);
				y2 = r.nextInt(8);
				findNode = graph.nodeWith(new Coordinate(x2, y2));

			}

			
			Holds<Coordinate> h = new Holds<Coordinate>(findNode);

			depthFirst<Coordinate> aStar = new depthFirst<Coordinate>();

			System.out.println("Depth First:  "
					+ aStar.findPathFrom(startNode, h));

			Maybe<IList<Node<Coordinate>>> path = astar.findPathFrom(startNode, h);

			IList<Node<Coordinate>> path2 = path.fromMaybe();
			System.out.println(path2);

			Assert.assertTrue(gridMap.isValidTransition(x1, y1, x1, y1));

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
				
				System.out.println(" ");

				Assert.assertTrue(gridMap.isValidTransition(_x1, _y1, _x2, _y2));

				Assert.assertFalse(gridMap.isObstructed(x1, y1));
				Assert.assertFalse(gridMap.isObstructed(x2, y2));
				
				path2 = tail;

			}
		}
	}
}