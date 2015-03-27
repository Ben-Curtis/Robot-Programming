package maybe;

/**
 * Implementation of a list that has a head and a tail (using the
 * "composite pattern").
 */

public class Just<A> implements Maybe<A> {

	private final A something;

	public Just(A something) {
		this.something = something;
	}

	public boolean isNothing() {
		return false;
	}

	public int size() {
		return 1;
	}

	public String toString() {
		return "Just(" + something + ")";
	}

	public boolean has(A e) {
		return something.equals(e);
	}

	// Higher-order functions:

	public Maybe<A> filter(Predicate<A> p) {
		if (p.holds(something)) {
			return this;
		} else {
			return new Nothing<A>();
		}
	}

	public <B> Maybe<B> map(Function<A, B> f) {
		return new Just<B>(f.apply(something));
	}

	public <B> B fold(Function<A, B> f, B b) {
		return f.apply(something);
	}

	public boolean all(Predicate<A> p) {
		return p.holds(something);
	}

	public boolean some(Predicate<A> p) {
		return p.holds(something);
	}

	public void forEach(Action<A> a) {
		a.apply(something);
	}

	// Potentially unsafe operation. In the case of this class Just,
	// it is just the get method, and is safe:

	public A fromMaybe() {
		return something;
	}
}
