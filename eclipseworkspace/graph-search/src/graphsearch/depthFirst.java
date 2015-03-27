package graphsearch;

import ilist.Cons;
import ilist.IList;
import ilist.Nil;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import maybe.Just;
import maybe.Maybe;
import maybe.Nothing;
/** 
 * Class to perform a Depth-first search and return whether a node can be found from a set start node.
 */
public class depthFirst<A> {
	
	/**
	 * A method to return whether you can find a node from a set start node or not 
	 * @param x node The node you want to start from
	 * @param p predicate Holds a node - tests whether equal to node you want to find
	 * @return Maybe Type - Just or Nothing
	 */
	public Maybe<Node<A>> findNodeFrom(Node<A> x, Holds<A> p) {
		Stack<Node<A>> path = new Stack<Node<A>>();
		Set<Node<A>> visited = new HashSet<Node<A>>();
		path.add(x);
		while (!path.isEmpty()) {
			Node<A> y = path.pop();
			if (!visited.contains(y)) {
				if (p.holds(y.contents())) {
					return new Just<Node<A>>(y);
				} else {
					visited.add(y);
					path.addAll(y.successors());

				}
			}
		}
		return new Nothing<Node<A>>();
	}
	/**
	 * A method to return a path from a set start node  
	 * @param x The set start node
	 * @param p predicate Holds a node - tests whether equal to node you want to find
	 * @return Maybe Type - Just or Nothing containing an iList of the path
	 */
	public Maybe<IList<Node<A>>> findPathFrom(Node<A> x, Holds<A> p){
		Stack<Node<A>> path = new Stack<Node<A>>();
		Set<Node<A>> visited = new HashSet<Node<A>>();
		Map<Node<A>,Node<A>> traceable = new LinkedHashMap<Node<A>,Node<A>>(); 
		path.add(x);
		while (!path.isEmpty()) {
			Node<A> y = path.pop();
			for (Node<A> node : y.successors())	{
				if(!traceable.containsKey(node)){
					traceable.put(node, y);
				}
			}
			if (!visited.contains(y)) {
				if (p.holds(y.contents())) {
					return new Just<IList<Node<A>>>((mapToList(traceable,y,x)));
				} else {
					visited.add(y);
					path.addAll(y.successors());

				}
			}
		}
		return new Nothing<IList<Node<A>>>();
	
	}
	/**
	 * Method to take a map of all the explored nodes on the graph and their predecessors and add the path into the list by working backwards from the endNode.
	 * @param traceable The map of all the explored nodes on the graph and they're parents
	 * @param endNode The node we have been looking for
	 * @param startNode The node that we started from
	 * @return route The list containing the path from the endNode to the startNode 
	 */
	public IList<Node<A>> mapToList(Map<Node<A>,Node<A>> traceable, Node<A> endNode, Node<A> startNode){
		IList<Node<A>> route = new Cons<Node<A>>(endNode, new Nil<Node<A>>());
		while(startNode != endNode){
			route = route.append(traceable.get(endNode));
			endNode = traceable.get(endNode);
		}
		return route.reverse();
		
	}


}		