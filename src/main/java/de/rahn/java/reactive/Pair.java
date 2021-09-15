package de.rahn.java.reactive;

import java.util.Objects;

public class Pair<T1, T2> {
  private final T1 t1;
  private final T2 t2;

  public Pair(T1 t1, T2 t2) {
    this.t1 = t1;
    this.t2 = t2;
  }

  public static <T1, T2> Pair<T1, T2> of(T1 a, T2 b) {
    return new Pair<>(a, b);
  }

  public T1 getT1() {
    return t1;
  }

  public T2 getT2() {
    return t2;
  }

  @Override
  public String toString() {
    return "Pair {" + "t1=" + t1 + ", t2=" + t2 + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pair)) {
      return false;
    }

    final Pair<?, ?> pair = (Pair<?, ?>) o;

    if (!Objects.equals(t1, pair.t1)) {
      return false;
    }
    return Objects.equals(t2, pair.t2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(t1, t2);
  }
}
