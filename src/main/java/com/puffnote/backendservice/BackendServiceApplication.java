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
		RoomServiceImpl roomService = context.getBean(RoomServiceImpl.class);
		NoteServiceImpl noteService = context.getBean(NoteServiceImpl.class);
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);

		List<String> notes = new ArrayList<String>();
		List<String> noteReferences = new ArrayList<String>();
		List<String> userReferences = new ArrayList<String>();

		//Delete All Data
		roomService.deleteAll();
		userService.deleteAll();
		noteService.deleteAll();

		//Room created first with zero users
		Room room = new Room("room2", userReferences);
		roomService.saveOrUpdate(room);//Add room to DB

		//User created next with zero notes
		User user = new User("user1", noteReferences);
		userService.saveOrUpdate(user);//Add user to DB
		//Add user reference to room
		roomService.addUserToRoom(room, user);

		//Note created by user
		notes.add("abcd");
		Note note = new Note("note1", notes, "text");
		noteService.saveOrUpdate(note);//Add note to DB
		//Add note reference to user
		userService.addNoteToUser(user, note);

		//List All Data
		roomService.listAll();
		userService.listAll();
		noteService.listAll();

		//Simulate note deletion
		Note noteToDelete = note;//Temporary assignment
		noteService.deleteAll();
		//Remove note reference from user
		userService.removeNoteFromUser(user, noteToDelete);

		//List Data
		userService.listAll();
		noteService.listAll();

		//Create new note
		Note newNote = new Note("note2", notes, "text");
		noteService.saveOrUpdate(newNote);
		//Add note reference to user by Id
		userService.addNoteToUserById(user.getId(), newNote.getId());

		//List Data
		userService.listAll();
		noteService.listAll();

		//Fetch Data from DB into Objects using our custom UUID
		Room temporaryRoom = roomService.findByUuid(room.getUUID());
		User temporaryUser = userService.findByUuid(user.getUUID());
		Note temporaryNote = noteService.findByUuid(newNote.getUUID());
	}

}
