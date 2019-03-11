package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by karthik on 2019-03-11
 */
public interface RoomRepository extends MongoRepository<Room,String> {
    public Room findByName(String name);
}
