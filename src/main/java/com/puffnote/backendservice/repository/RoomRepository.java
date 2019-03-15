package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-03-11
 */
@RestResource(exported = false)
public interface RoomRepository extends MongoRepository<Room,String> {
    public Room findByName(String name);
    public Room findByUuid(String uuid);
}
