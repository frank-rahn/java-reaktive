package de.rahn.java.reactive;

import java.util.Optional;
import java.util.function.BiFunction;

@FunctionalInterface
public interface CheckedBiFunction<T1, T2, R> extends BiFunction<T1, T2, Optional<R>> {

  @Override
  default Optional<R> apply(T1 t1, T2 t2) {
    try {
      return Optional.of(applyWithException(t1, t2));
    } catch (Exception exception) {
      return Optional.empty();
    }
  }

  R applyWithException(T1 t1, T2 t2) throws Exception;
}
