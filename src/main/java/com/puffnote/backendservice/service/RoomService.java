package com.puffnote.backendservice.service;

import com.puffnote.backendservice.model.Room;

/**
 * Created by karthik on 2019-03-11
 */
public interface RoomService {
    Iterable listAll();
    Room findById(String id);
    Room findByName(String name);
    Room saveOrUpdate(Room room);
    void delete(Room room);
    void deleteById(String id);
    boolean existsById(String id);
    void deleteAll();
}
