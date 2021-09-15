package de.rahn.java.reactive;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Observer<KEY, VALUE> {
  private final ConcurrentHashMap<KEY, Consumer<VALUE>> listener = new ConcurrentHashMap<>();

  interface Registration {
    void remove();
  }

  public Registration register(KEY key, Consumer<VALUE> consumer) {
    listener.put(key, consumer);
    return () -> listener.remove(key);
  }

  public void remove(KEY key) {
    listener.remove(key);
  }

  public void senEvent(VALUE value) {
    listener.values().forEach(c -> c.accept(value));
  }
}
