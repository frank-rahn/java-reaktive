package de.rahn.java.reactive;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface Currying {

  static <A, B, R> Function<BiFunction<A, B, R>, Function<A, Function<B, R>>> curryBiFunction() {
    return (func) -> a -> b -> func.apply(a, b);
  }

  static <A, B, R>
      Function<CheckedBiFunction<A, B, R>, Function<A, CheckedFunction<B, R>>>
          curryCheckedBiFunction() {
    return (func) -> a -> b -> func.applyWithException(a, b);
  }
}
