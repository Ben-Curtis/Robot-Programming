package maybe;

// DO NOT MODIFY this file.
// Any modification will incurr into the mark zero for the whole exercise.

/**
 * Interface for the Maybe type using the "composite pattern". We include
 * high-order methods. We will use A,B,C for type variables.
 */

public interface Maybe<A> {
	public boolean isNothing();

	public int size();

	public boolean has(A a);

	// Higher-order methods:
	public Maybe<A> filter(Predicate<A> p);

	public <B> Maybe<B> map(Function<A, B> f);

	public <B> B fold(Function<A, B> f, B b);

	public boolean all(Predicate<A> p);

	public boolean some(Predicate<A> p);

	public void forEach(Action<A> a);

	// Unsafe operation, which should not be used (or even offered in this
	// interface).
	public A fromMaybe();
	// A method cases is not needed, because in this case it is the same as
	// fold.
}

/*
 * 
 * We have two implementations of the Maybe interface:
 * 
 * (1) The class Nothing. (2) The class Just.
 * 
 * There is only one object of type Nothing.
 * 
 * Look at SampleUsage.java.
 */
