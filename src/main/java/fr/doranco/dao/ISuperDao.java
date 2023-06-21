package fr.doranco.dao;

import java.util.List;

public interface ISuperDao<T> {

  void create(T t);

  T getById(Long id);
  
  T getByField(String field);
  
  void update(T t);
  
  void delete(T t);
  
  List<T> getAll();

}
