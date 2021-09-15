package de.rahn.java.reactive;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface OptionalAsyncSymmetric<T> {

  // Methoden von Optional
  Boolean isPresent();

  OptionalAsyncSymmetric<T> ifPresent(Consumer<T> consumer);
  OptionalAsyncSymmetric<T> ifPresentAsync(Consumer<T> consumer);

  static <T> OptionalAsyncSymmetric<T> ofNullable(T value) {
    return ofNullable(value, "Object was null");
  }
  // ...

  // Neue Methoden
  default Boolean isAbsent() {
    return !isPresent();
  }

  default OptionalAsyncSymmetric<T> ifAbsent(Runnable action) {
    Objects.requireNonNull(action);
    if (isAbsent()) {
      action.run();
    }
    return this;
  }

  default OptionalAsyncSymmetric<T> ifAbsentAsync(Runnable action) {
    Objects.requireNonNull(action);
    if (isAbsent()) {
      CompletableFuture.runAsync(action);
    }
    return this;
  }

  static <T> OptionalAsyncSymmetric<T> ofNullable(T value, String failedMessage) {
    return Objects.nonNull(value) ? success(value) : failure(failedMessage);
  }

  static <T> OptionalAsyncSymmetric<T> success(T value) {
    Objects.requireNonNull(value);
    return new OptionalAsyncSymmetric.Success<>(value);
  }

  static <T> OptionalAsyncSymmetric<T> failure(String errorMessage) {
    Objects.requireNonNull(errorMessage);
    return new OptionalAsyncSymmetric.Failure<>(errorMessage);
  }

  OptionalAsyncSymmetric<T> ifFailed(Consumer<String> failed);

  // Implementierungen
  abstract class AbstractOptionalAsyncSymmetric<T> implements OptionalAsyncSymmetric<T> {}

  class Success<T> extends AbstractOptionalAsyncSymmetric<T> {
    protected final T value;

    public Success(T value) {
      this.value = value;
    }

    @Override
    public Boolean isPresent() {
      return Boolean.TRUE;
    }

    @Override
    public OptionalAsyncSymmetric<T> ifPresent(Consumer<T> consumer) {
      Objects.requireNonNull(consumer);
      consumer.accept(value);
      return this;
    }

    @Override
    public OptionalAsyncSymmetric<T> ifPresentAsync(Consumer<T> consumer) {
      Objects.requireNonNull(consumer);
      CompletableFuture.runAsync(() -> consumer.accept(value));
      return this;
    }

    @Override
    public OptionalAsyncSymmetric<T> ifFailed(Consumer<String> failed) {
      Objects.requireNonNull(failed);
      return this;
    }
  }

  class Failure<T> extends AbstractOptionalAsyncSymmetric<T> {

    private final String errorMessage;

    public Failure(String errorMessage) {
      this.errorMessage = errorMessage;
    }

    @Override
    public Boolean isPresent() {
      return Boolean.FALSE;
    }

    @Override
    public OptionalAsyncSymmetric<T> ifPresent(Consumer<T> consumer) {
      Objects.requireNonNull(consumer);
      return this;
    }

    @Override
    public OptionalAsyncSymmetric<T> ifPresentAsync(Consumer<T> consumer) {
      Objects.requireNonNull(consumer);
      return this;
    }

    @Override
    public OptionalAsyncSymmetric<T> ifFailed(Consumer<String> failed) {
      Objects.requireNonNull(failed);
      failed.accept(errorMessage);
      return this;
    }
  }
}
