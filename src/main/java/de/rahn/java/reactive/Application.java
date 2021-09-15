package de.rahn.java.reactive;

import de.rahn.java.reactive.Observer.Registration;
import java.util.Optional;
import java.util.stream.Stream;

public class Application {

  public static void main(String[] args) {
    var ups =
        Stream.of("1", "2", "3", "ups", "4")
            .map((CheckedFunction<String, Integer>) Integer::parseInt)
            .filter(Optional::isPresent)
            .mapToInt(Optional::get)
            .sum();
    System.out.println("Summe: " + ups);

    String helloWorld =
        Currying.<String, String, String>curryBiFunction()
            .apply((s1, s2) -> s1 + " " + s2)
            .apply("Hello")
            .apply("World");
    System.out.println("HelloWorld: " + helloWorld);

    class Legacy {
      public String doWork(Integer input) {
        return input.toString() + '-' + System.nanoTime();
      }
    }

    Legacy legacy = new Legacy();
    var memoize = Memoizer.memoize(legacy::doWork);

    System.out.println(memoize.apply(1));
    System.out.println(memoize.apply(1));
    System.out.println(memoize.apply(2));
    System.out.println(memoize.apply(2));

    String result =
        Case.match(
            Case.matchCase(() -> "Default value"),
            Case.matchCase(() -> false, () -> "Value false"),
            Case.matchCase(() -> true, () -> "Value true")
        );
    System.out.println("Case: " + result);

    Observer<String, Integer> observer = new Observer<>();
    Registration registration = observer.register("Nummer", System.out::println);
    observer.senEvent(2);
    registration.remove();
    observer.senEvent(1);
  }
}
