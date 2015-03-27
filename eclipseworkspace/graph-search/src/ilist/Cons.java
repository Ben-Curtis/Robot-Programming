package ilist;

import maybe.Just;
import maybe.Maybe;

/**
 * Implementation of a list that has a head and a tail
 * (using the "composite pattern").
 */

public class Cons<A> implements IList<A> {
    
  private final A head;
  private final IList<A> tail; // Reference to another list 
                              // (not a list itself).

  public Cons(A head,  IList<A> tail) {
    assert(tail != null);  // Tail should NOT be null. Use Nil instead.
    // See http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html

    this.head = head;      // The usual stuff now.
    this.tail = tail;
  }

  public boolean isEmpty() { 
   return false; 
  }

  public int size() {
    return 1 + tail.size(); // Is this a recursive call?
  }

  public String toString() {
    return "Cons("  +  head + "," + tail.toString()  +  ")";
  }
    
  public IList<A> append(IList<A> l) {
    return new Cons<A>(head, tail.append(l));
  }

  public IList<A> append(A e) {
    return append(new Cons<A>(e , new Nil<A>()));
  }

  public IList<A> reverse() {
    return tail.reverse().append(head);

    // // Equivalently:
    // IList <A> r = tail.reverse();
    // IList <A> s = r.append(head);
    // return s;
  }

  public boolean has(A e) {
    return head.equals(e) || tail.has(e); 
    // Short-circuit evaluation of "||" makes this more efficient.
    // Search for "short-circuit evaluation" in the internet.
  }

  // Higher-order functions:

  public IList<A> filter(Predicate<A> p) {
    if (p.holds(head)) {
      return new Cons<A>(head, tail.filter(p));
    }
    else {
      return tail.filter(p);
    }
  }

  public <B> IList<B> map(Function<A,B> f) {
    return new Cons<B>(f.apply(head), tail.map(f));
  }

  public <B> B fold(Function2<A,B,B> f, B b) {
    return f.apply(head, tail.fold(f, b));
  }

  public boolean all(Predicate<A> p) {
    return p.holds(head) && tail.all(p);
    // Short-circuit evaluation can speed up things here too.
  }

  public boolean some(Predicate<A> p) {
    return p.holds(head) || tail.some(p);
    // Short-circuit evaluation can speed up things here too.
  }

  public void forEach(Action<A> a) {
    a.apply(head);
    tail.forEach(a);
  }

@Override
public Maybe<A> getHead() {
	return new Just<A>(head);
}

public Maybe<IList<A>> getTail() {
	return new Just<IList<A>>(tail);
}

}
