package graphsearch;
/**
 * A class which holds coordinates for a node - also overrides hash
 */
public class Coordinate {
	public int x, y;
	
	/**
	 * An override method to test whether coordinates are equal
	 * @return boolean Whether it matches or not
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * An override method to test whether coordinates are equal
	 * @return boolean Whether it matches or not
	 */
	@Override
	public boolean equals(Object o) {
		Coordinate c = (Coordinate) o;
		return x == c.x && y == c.y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	/**
	 * ToString method
	 * returns String The string of the coordinates
	 */
	public String toString(){
		return x + ", " + y;
	}
	
	@Override 
	public int hashCode() { 
		return x + y; 
	} 
}
