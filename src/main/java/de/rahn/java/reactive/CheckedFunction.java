package de.rahn.java.reactive;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R> extends Function<T, Optional<R>> {

  @Override
  default Optional<R> apply(T t) {
    try {
      return Optional.of(applyWithException(t));
    } catch (Exception exception) {
      return Optional.empty();
    }
  }

  R applyWithException(T t) throws Exception;
}
