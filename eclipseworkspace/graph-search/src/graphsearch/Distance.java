package graphsearch;

/**
 * Class to hold a distance measurement object
 */
public class Distance<A> {

	Coordinate start;
	Coordinate end;
/**
 * Constructor to create a distance measurement object
 * @param origin The node we are starting from
 * @param destination The node we want to get to
 */
	public Distance(Node<A> origin, Node<A> destination) {
		start = (Coordinate) origin.contents();
		end = (Coordinate) destination.contents();
	}
	
	/**
	 * A method to return a distance function
	 * @return The calculated distance function
	 */
	public int returnD() {
		//return Math.abs((start.x - end.x)) + Math.abs((start.y - end.y)); 
		return (int)Math.sqrt(Math.abs((start.x - end.x)*(start.x - end.x)) + Math.abs((start.y - end.y)*(start.y - end.y)));
	}

}
