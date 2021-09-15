package de.rahn.java.reactive;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Case<T> extends Pair<Supplier<Boolean>, Supplier<T>> {

  public Case(Supplier<Boolean> condition, Supplier<T> value) {
    super(condition, value);
  }

  public static <T> Case<T> matchCase(Supplier<Boolean> condition, Supplier<T> value) {
    return new Case<>(condition, value);
  }

  public static <T> DefaultCase<T> matchCase(Supplier<T> value) {
    return new DefaultCase<>(value);
  }

  private boolean isMatching() {
    return getT1().get();
  }

  public T value() {
    return getT2().get();
  }

  @SafeVarargs
  public static <T> T match(DefaultCase<T> defaultCase, Case<T>... matchers) {
    return Stream.of(matchers)
        .filter(Case::isMatching)
        .map(Case::value)
        .findFirst()
        .orElseGet(defaultCase::value);
  }

  public static class DefaultCase<T> extends Case<T> {

    public DefaultCase(Supplier<T> value) {
      super(() -> true, value);
    }
  }
}
