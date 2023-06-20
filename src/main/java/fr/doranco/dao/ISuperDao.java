package fr.doranco.dao;

import java.util.List;

public interface ISuperDao<T> {

  void create(T t);

  T getById(int id);
  
  T getByField(String field);
  
  void update(T t);
  
  void delete(T t);
  
  List<T> getAll(boolean onlyPositiveSolde);

}
