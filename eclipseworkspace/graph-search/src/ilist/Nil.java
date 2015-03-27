package ilist;

import maybe.Maybe;
import maybe.Nothing;

/**
 * Implementation of the empty list
 * (using the "composite pattern").
 */

public class Nil<A> implements IList<A> {
    
  public Nil() { // Nothing to do in the constructor!
  }              // Could simply remove it.

  public boolean isEmpty() { 
    return true; 
  }

  public int size() {
    return 0;
  }

  public String toString() { 
    return "Nil"; 
  }

  public IList<A> append(IList<A> l) {
    return l;
  }

  public IList<A> append(A a) {
    return new Cons<A>(a , this);
  }

  public IList<A> reverse() {
    return this;
  }

  public boolean has(A a) {
    return false;
  }

  // Higher-order functions:

  public IList<A> filter(Predicate<A> p) {
    return this; // new Nil<A>() also works.
  }

  public <B> IList<B> map(Function<A,B> f) {
    return new Nil<B>();
  }

  public <B> B fold(Function2<A,B,B> f, B b) {
    return b;
  }

  public boolean all(Predicate<A> p) {
    return true;
  }

  public boolean some(Predicate<A> p) {
    return false;
  }

  public void forEach(Action<A> f) {
    // Nothing to do, but we have to include the method. (Why?)
  }

@Override
public Maybe<A> getHead() {
	// TODO Auto-generated method stub
	return new Nothing<A>();
}

public Maybe<IList<A>> getTail() {
	return new Nothing<IList<A>>();
}
}
