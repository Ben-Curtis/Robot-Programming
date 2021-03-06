package graphsearch;

import maybe.Predicate;
/**
 * A class to implement the predicate
 */
public class Holds<A> implements Predicate<A> {

	public Node<A> node;
	/**
	 * Constructor which creates a new node to test against. 
	 * @param nNode The node used to test against
	 */
	public Holds(Node<A> nNode) {
		this.node = nNode;
	}
	/**
	 * Tests whether the node currently looking at matches the one we are looking for
	 * @return boolean Returns whether the node currently looking at matches the one we are looking for
	 */
	public boolean holds(A a) {
		return a.equals(node.contents());

	}
}
