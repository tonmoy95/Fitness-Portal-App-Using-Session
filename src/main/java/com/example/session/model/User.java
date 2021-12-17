package com.example.session.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents an user registered in a fitness portal site.
 * 
 * @author Tonmoy
 *
 */

@Entity
@Table(name = "registration")
public class User {

	@Id
	private int regId;

	@NotNull(message = "Please fill the first name")
	@Size(min = 2, message = "{Size.Person.FirstName}")
	private String firstName;

	@NotNull(message = "Please fill the last name")
	@Size(min = 2, message = "{Size.Person.LastName}")
	private String lastName;

	@NotEmpty(message = "Please provide a valid email address")
	@Email
	private String emailId;

	@NotNull(message = "Please fill the password")
	@Size(min = 8, message = "{Size.Person.Password}")
	private String password;

	private String saltValue;

	public User() {
	}

	/**
	 * 
	 * @param regId     It is the user's registration id.
	 * @param firstName It is the user's first name.
	 * @param lastName  It is the user's last name.
	 * @param emailId   It is the user's email id.
	 * @param password  It is user's password.
	 * @param saltValue It is the randomly generated salt value.
	 */
	public User(int regId, String firstName, String lastName, String emailId, String password, String saltValue) {
		this.regId = regId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.saltValue = saltValue;
	}

	/**
	 * Gets the registration id of the user.
	 * 
	 * @return This returns the user's registration id.
	 */
	public int getRegId() {
		return regId;
	}

	/**
	 * Sets the registration id of the user. This is also the primary key.
	 * 
	 * @param regId It is the user's registration id.
	 */
	public void setRegId(int regId) {
		this.regId = regId;
	}

	/**
	 * Gets the user's first name.
	 * 
	 * @return This return the user's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the user's first name. Cannot be null and minimum length must be 2.
	 * 
	 * @param firstName It is the user's first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the user's last name.
	 * 
	 * @return This returns the user's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the user's last name. Cannot be null and minimum length must be 2.
	 * 
	 * @param lastName It is the user's last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the user's email id.
	 * 
	 * @return This returns the user's email id.
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * Sets the user's email id. This also acts as a unique key. Cannot be null.
	 * 
	 * @param emailId It is the user's email id.
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets the user's password.
	 * 
	 * @return This returns the user's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password. Cannot be null and minimum length must be 8.
	 * 
	 * @param password It is user's password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the salt value.
	 * 
	 * @return This returns the randomly generated salt value.
	 */
	public String getSaltValue() {
		return saltValue;
	}

	/**
	 * Sets the salt value. It is generated randomly.
	 * 
	 * @param saltValue It is the randomly generated salt value.
	 */
	public void setSaltValue(String saltValue) {
		this.saltValue = saltValue;
	}

}