package com.puffnote.backendservice;

import com.puffnote.backendservice.model.Note;
import com.puffnote.backendservice.model.Room;
import com.puffnote.backendservice.model.User;
import com.puffnote.backendservice.service.NoteServiceImpl;
import com.puffnote.backendservice.service.RoomServiceImpl;
import com.puffnote.backendservice.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BackendServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(BackendServiceApplication.class);

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {

		//Test DB Code using following method call
		//testDBCode();
		SpringApplication.run(BackendServiceApplication.class, args);
	}

	/**
	 * Temporary method for testing DB Code
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static void testDBCode() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.puffnote.backendservice");
		context.refresh();

		//Services
		RoomServiceImpl rs = context.getBean(RoomServiceImpl.class);
		NoteServiceImpl ns = context.getBean(NoteServiceImpl.class);
		UserServiceImpl us = context.getBean(UserServiceImpl.class);

		List<String> notes = new ArrayList<String>();
		List<String> noteReferences = new ArrayList<String>();
		List<String> userReferences = new ArrayList<String>();

		//Delete All Data
		rs.deleteAll();
		us.deleteAll();
		ns.deleteAll();

		//Create Note
		notes.add("abcd");
		Note n = new Note("note1", notes, "text");
		ns.saveOrUpdate(n);
		//Get Note Reference
		noteReferences.add(n.getId());
		//TODO: Get reference on successful save


		//Create User
		User u = new User("user1", noteReferences);
		us.saveOrUpdate(u);
		//Get User Reference
		userReferences.add(u.getId());
		//TODO: Get reference on successful save

		Room r = new Room("room2", userReferences);
		rs.saveOrUpdate(r);

		//List All Data
		rs.listAll();
		us.listAll();
		ns.listAll();
	}

}
