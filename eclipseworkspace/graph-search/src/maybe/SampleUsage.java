// Sample usage of the Maybe type implemented using the composite pattern.
// See first the interface file Maybe.

package maybe;

import java.lang.Integer;

public class SampleUsage {

  // We begin by doing something people often do, but that it is
  // unsafe and the cause of several programming errors, and hence
  // should be avoided. We use the suffix "_" for methods that rely on
  // bad practice, and remove it from their versions that apply better
  // practice.

  // https://en.wikipedia.org/wiki/Pointer_%28computer_programming%29#Null_pointer
  // https://en.wikipedia.org/wiki/Tony_Hoare#Apologies_and_retractions

  static <A> A find_(Predicate<A> p, A [] as) {
    // Find an element of the array as satisfying the predicate p,
    // if such an element exists, and null otherwise.
    for (A a : as) {
      if (p.holds(a))
        return a; 
    } 
    // If the for-loop ends, this means we haven't found anything.
    return null;
  }

  // Now this is very likely to cause a null-pointer exception.
  // In the following method, it doesn't.
  static <A> boolean some_(Predicate<A> p, A [] as) {
    return find(p, as) != null;
  }

  // A better-engineered solution is to use the Maybe type.
  static <A> Maybe<A> find(Predicate<A> p, A [] as) {
    // Find just an element of the array as satisfying the predicate p,
    // if such an element exists, and nothing otherwise.
    for (A a : as) {
      if (p.holds(a))
        return new Just<A>(a); 
    } 
    // If the for-loop ends, this means we haven't found anything.
    return new Nothing<A>();
  }
    
  static <A> boolean some(Predicate<A> p, A [] as) {
    return !find(p, as).isNothing();
  }

  // As another example, the unsafe head and tail operations in the
  // interface IList could have been given the following types:
  //
  //   public <A> Maybe<A> head();
  //   public <A> Maybe<IList<A>> tail();


  public static void main(String[] args) {
    
    Maybe<Integer> x   = new Nothing<Integer>();
    Maybe<Integer> y = new Just<Integer>(17);

    System.out.println(x + " has size " + x.size());
    System.out.println(y + " has size " + y.size());
    System.out.println(x + " has the element  3 is " + x.has(3));
    System.out.println(x + " has the element 17 is " + x.has(17));
    System.out.println(y + " has the element  3 is " + y.has(3));
    System.out.println(y + " has the element 17 is " + y.has(17));

    Predicate<Integer> even = n -> n % 2 == 0;
    Predicate<Integer> odd  = n -> !even.holds(n);

    System.out.println("Filter evens from " + x + " is " + x.filter(even));
    System.out.println("Filter odds from " + y + " is " + y.filter(odd));
    System.out.println("Filter evens from " + x + " is " + x.filter(even));
    System.out.println("Filter odds from " + y + " is " + y.filter(odd));

    Maybe<String> z = new Just<String>("John");
    Maybe<String> t = new Nothing<String>();

    System.out.println("Map length to " + z + " gives " + z.map(s -> s.length()));
    System.out.println("Map length to " + t + " gives " + t.map(s -> s.length()));

    // Add up all elements by folding:
    System.out.println("The sum of " + x + " is " + x.fold(a -> a, 0));     
    System.out.println("The sum of " + y + " is " + y.fold(a -> a, 0));     

    // Compute the length by folding:
    System.out.println("The length of " + x + " is " + x.fold(a -> 1, 0));     
    System.out.println("The length of " + y + " is " + y.fold(a -> 1, 0));     

    // All even:
    System.out.println("All elements of " + x + " are even is " + x.all(even));
    System.out.println("All elements of " + y + " are even is " + y.all(even));

    // Some even:
    System.out.println("Some element of " + x + " is even is " + x.some(even));
    System.out.println("Some element of " + y + " is even is " + y.some(even));

    // All smaller than 10:
    System.out.println("All elements of " + x + " are < 10 is " + x.all(a -> a < 10));
    System.out.println("All elements of " + y + " are < 10 is " + y.all(a -> a < 10));
    // Some smaller than 10:
    System.out.println("Some elements of " + x + " are < 10 is " + x.some(a -> a < 10));
    System.out.println("Some elements of " + y + " are < 10 is " + y.some(a -> a < 10));

    // Print all elements:
    System.out.println("Now we print each element of " + x);
    x.forEach(a -> System.out.println(a + " "));
    System.out.println("End of this printing"); 
    System.out.println("Now we print each element of " + y);
    y.forEach(a -> System.out.println(a + " "));
    System.out.println("End of this printing"); 

    // Fold does case analysis:
    String xdescription = 
      x.fold(s -> x + " is just " + s,
             x + " is Nothing");

    String ydescription = 
      y.fold(s -> y + " is just " + s,
             y + " is Nothing");

    System.out.println(xdescription);
    System.out.println(ydescription);

    boolean xIsNothing = x.fold(s -> false, true);
    boolean yIsNothing = y.fold(s -> false, true);

    System.out.println(x + " is nothing is " + xIsNothing); 
    System.out.println(y + " is nothing is " + yIsNothing); 

    /* This is what we get when we run the function main:

Nothing has size 0
Just(17) has size 1
Nothing has the element  3 is false
Nothing has the element 17 is false
Just(17) has the element  3 is false
Just(17) has the element 17 is true
Filter evens from Nothing is Nothing
Filter odds from Just(17) is Just(17)
Filter evens from Nothing is Nothing
Filter odds from Just(17) is Just(17)
Map length to Just(John) gives Just(4)
Map length to Nothing gives Nothing
The sum of Nothing is 0
The sum of Just(17) is 17
The length of Nothing is 0
The length of Just(17) is 1
All elements of Nothing are even is true
All elements of Just(17) are even is false
Some element of Nothing is even is false
Some element of Just(17) is even is false
All elements of Nothing are < 10 is true
All elements of Just(17) are < 10 is false
Some elements of Nothing are < 10 is false
Some elements of Just(17) are < 10 is false
Now we print each element of Nothing
End of this printing
Now we print each element of Just(17)
17 
End of this printing
Nothing is Nothing
Just(17) is just 17
Nothing is nothing is true
Just(17) is nothing is false

     */
  }
    
}
