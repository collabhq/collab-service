package com.puffnote.backendservice.repository;

import com.puffnote.backendservice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Data repository class used to implement data access on Room objects
 */
@RestResource(exported = false)
public interface RoomRepository extends MongoRepository<Room,String> {
    /**
     * Find room object by name
     * @param name
     * @return Room object found
     */
    public Room findByName(String name);

    /**
     * Find room object by uuid
     * @param uuid
     * @return Room object found
     */
    public Room findByUuid(String uuid);
}
