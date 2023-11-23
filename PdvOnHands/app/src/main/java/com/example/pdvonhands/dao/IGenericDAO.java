package com.example.pdvonhands.dao;

import java.util.ArrayList;

public interface IGenericDAO<Object> {

    long insert (Object obj);
    long update (Object obj);
    long delete (Object obj);
    ArrayList<?> getAll();
    Object getById(int id);

}
