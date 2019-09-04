package dao.intefaces;

import java.util.List;

public interface DAO<T> extends AutoCloseable {

    T getById(int id);

    List<T> findAll();

    @Override
    void close();
}
