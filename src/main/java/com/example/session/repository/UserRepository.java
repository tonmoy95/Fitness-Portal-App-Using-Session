package com.example.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.session.model.User;

/**
 * 
 * This is the user repository section where the data from the database is
 * fetched and pushed according to the user's needs.
 * 
 * @author Tonmoy
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * Retrieves user by email.
	 * 
	 * @param emailId Finds the email id in the database.
	 * 
	 */
	@Query(value = "select * from registration where email_Id = (:email_Id)", nativeQuery = true)
	public User getUserByEmail(@Param("email_Id") String emailId);

}
