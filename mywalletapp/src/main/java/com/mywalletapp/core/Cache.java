package com.mywalletapp.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<K, V> {

  private final Map<K, V> map;

  public Cache() {
    map = new ConcurrentHashMap<K, V>();
  }

  public V get(K key) {
    return map.get(key);
  }

  public void put(K key, V value) {
    map.put(key, value);
  }

  public V putIfAbsent(K key, V value) {
    return map.putIfAbsent(key, value);
  }
}
