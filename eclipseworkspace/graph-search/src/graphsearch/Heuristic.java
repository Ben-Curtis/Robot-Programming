package graphsearch;


/**
 * A class to hold a Heuristic function object(s)
 */
public class Heuristic<A> {

	Coordinate start;
	Coordinate end;
	/**
	 * Constructor to create a new Heuristic Function
	 * @param origin The coordinates of the origin
	 * @param destination The coordinates of the node you want to get to
	 */
	public Heuristic(Node<A> origin, Node<A> destination){
		start = (Coordinate)origin.contents() ;
		end = (Coordinate)destination.contents();
		
	}
	/**
	 * A method to return a heuristic function
	 * @return The calculated heuristic function
	 */
	public int returnH(){
		return (int)Math.sqrt(Math.abs((start.x - end.x)*(start.x - end.x)) + Math.abs((start.y - end.y)*(start.y - end.y)));
	}
	
	

}
