// Sample usage of immutable lists implemented using the composite pattern.
// See first the interface file IList.java.

package ilist;

import java.lang.Integer;

public class SampleUsage {
    
  public static void main(String[] args) {
    // The list [1,2,3] = Cons(1, Cons(2, Cons(3, Nil)))

    IList<Integer> l1 = 
      new Cons<Integer>(1,new Cons<Integer>(2,new Cons<Integer>(3,new Nil<Integer>())));

    IList<Integer> l1again = 
      new Cons<Integer>(1,new Cons<Integer>(2,new Cons<Integer>(3,new Nil<Integer>())));

    System.out.println(l1 == l1again); // Prints false. Why?
                                       // Because "==" compares references (memory addresses), 
                                       // not (memory) content. 

    IList<Integer> l2 = new Cons<Integer> (7, 
                       new Cons<Integer> (8,
   		       new Cons<Integer> (9, 
                       new Nil<Integer>  () )));

    IList<Integer> l3 = l1.append(l2); // Doesn't change l1.
    IList<Integer> l4 = l3.reverse();  // Doesn't change l3.

    System.out.println(l1 + " has size " + l1.size());
    System.out.println(l3 + " has size " + l3.size());
    System.out.println(l4 + " has size " + l4.size());

    System.out.println(l4 + " has the element 3 is " + l4.has(3));
    System.out.println(l4 + " has the element 17 is " + l4.has(17));

    // Take the even elements of l4:
    Predicate<Integer> even = n -> n % 2 == 0;
    IList<Integer> l5 = l4.filter(even);  // Doesn't change l4.

    System.out.println("The even elements of " + l4 + " are " + l5);

    // Take the even elements of l4:
    System.out.println("The even elements of " + l4 + " are " + l4.filter(n -> n % 2 == 0));

    // Strings for a change:
    IList<String> l6 = new Cons<String>("John", 
                      new Cons<String>("Martin", 
                      new Cons<String>("Giannis", 
                      new Nil<String>())));

    System.out.println("The lengths of " + l6 + " are " + l6.map(s -> s.length()));

    // Add up all elements by folding:
    System.out.println("The sum of " + l4 + " is " + l4.fold((a,b)  -> a + b, 0));     

    // Compute the length by folding:
    System.out.println("The length of " + l4 + " is " + l4.fold((a,b)  -> 1 + b, 0));     

    // All even:
    System.out.println("All elements of " + l4 + " are even is " + l4.all(even));

    // Some even:
    System.out.println("Some element of " + l4 + " is even is " + l4.some(even));

    // All smaller than 10:
    System.out.println("All elements of " + l4 + " are < 10 is " + l4.all(a -> a < 10));

    // Print all elements of a list:
    System.out.println("Now we print each element of the list " + l4 + ", separated by spaces, and with a new line at the end :");
    l4.forEach(a -> System.out.print(a + " "));
    System.out.println(); // The new line at the end.
  }
    
}
