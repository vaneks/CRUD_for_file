package com.vaneks.crud.repository;

import java.io.IOException;
import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll();
    T getById(ID id);
    T save(T t) throws IOException;
    T update(T t) throws IOException;
    void deleteById(ID id) throws IOException;
}
