package com.mywalletapp.core.entity;

public interface ObjectDao<T extends ObjectEntity> {

  T findById(long id);

  boolean save(T entity);

  boolean delete(T entity);

}
