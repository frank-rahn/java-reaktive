package de.rahn.java.reactive;

import java.util.Optional;
import java.util.function.BiFunction;

@FunctionalInterface
public interface CheckedTriFunction<T1, T2, T3, R> extends TriFunction<T1, T2, T3, Optional<R>> {

  @Override
  default Optional<R> apply(T1 t1, T2 t2, T3 t3) {
    try {
      return Optional.of(applyWithException(t1, t2, t3));
    } catch (Exception exception) {
      return Optional.empty();
    }
  }

  R applyWithException(T1 t1, T2 t2, T3 t3) throws Exception;
}
