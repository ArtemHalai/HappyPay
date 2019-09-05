package dao.intefaces;

import java.util.List;

public interface DAO<T>{

    T getById(int id);

    List<T> findAll();
}
