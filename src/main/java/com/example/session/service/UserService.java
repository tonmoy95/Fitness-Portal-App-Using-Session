package com.example.session.service;

import com.example.session.model.User;

/**
 * This is the user service interface where the required methods are declared.
 * Service section is a bridge between controller section and repository
 * section.
 * 
 * @author Tonmoy
 * 
 */
public interface UserService {

	/**
	 * Stores the user's information.
	 * 
	 * @param user User object to be saved in the database.
	 *
	 */
	public boolean saveUser(User user);

	/**
	 * Retrieves the user by email id.
	 * 
	 * @param emailId It is the user's mail id.
	 * 
	 */
	public User getUserByEmail(String emailId);

}
