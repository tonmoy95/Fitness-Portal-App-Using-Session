package com.example.session.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.session.model.User;
import com.example.session.repository.UserRepository;

/**
 * This is the user service interface's implementation named UserServiceImpl
 * where the methods are defined. The business logics are written here.
 * 
 * @author Tonmoy
 * 
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Stores the user's information.
	 * 
	 * @param user It is the user object to be saved in the database.
	 * @return true when the user is saved.
	 */
	@Override
	public boolean saveUser(User user) {
		userRepository.save(user);
		return true;
	}

	/**
	 * Retrieves the user by email id.
	 * 
	 * @param email_Id It is the user's mail id.
	 * @return object of the user.
	 */
	@Override
	public User getUserByEmail(String email_Id) {

		return userRepository.getUserByEmail(email_Id);
	}

}
