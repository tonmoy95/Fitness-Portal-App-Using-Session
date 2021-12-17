package com.example.session.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Represents an user profile in a fitness portal site.
 * 
 * @author Tonmoy
 *
 */
@Entity
@Table(name = "profile")
public class Profile {

	@Id
	private int profileId;

	private int regId;

	@NotNull
	private int genderInd;

	@NotNull(message = "Please fill the date")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate birthDate;

	@NotNull
	private float weight;

	@NotNull
	private float height;

	@NotNull
	private int stepSize;

	public Profile() {
	}

	/**
	 * 
	 * @param profileId User's profile id.
	 * @param regId     User's registration id.
	 * @param genderInd User's gender.
	 * @param birthDate User's birth date.
	 * @param weight    User's weight.
	 * @param height    User's height.
	 * @param stepSize  User's step size.
	 */
	public Profile(int profileId, int regId, int genderInd, LocalDate birthDate, float weight, float height,
			int stepSize) {
		this.profileId = profileId;
		this.regId = regId;
		this.genderInd = genderInd;
		this.birthDate = birthDate;
		this.weight = weight;
		this.height = height;
		this.stepSize = stepSize;
	}

	/**
	 * Gets the profile id of the user.
	 * 
	 * @return This returns the profile id.
	 */
	public int getProfileId() {
		return profileId;
	}

	/**
	 * Sets the profile id of the user. Cannot be null. This is also the primary
	 * key.
	 * 
	 * @param profileId User's profile id.
	 */
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	/**
	 * Gets the user's registration id.
	 * 
	 * @return This returns the registration id.
	 */
	public int getRegId() {
		return regId;
	}

	/**
	 * Sets the registration id. Cannot be null.
	 * 
	 * @param regId User's registration id.
	 */
	public void setRegId(int regId) {
		this.regId = regId;
	}

	/**
	 * Gets the user's gender.
	 * 
	 * @return This returns the gender.
	 */
	public int getGenderInd() {
		return genderInd;
	}

	/**
	 * Sets the user's gender. Cannot be null.
	 * 
	 * @param genderInd User's gender.
	 */
	public void setGenderInd(int genderInd) {
		this.genderInd = genderInd;
	}

	/**
	 * Gets the user's birth date.
	 * 
	 * @return This returns the user's birth date.
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets the user's birth date. Cannot be null. Minimum age required is 18 years
	 * and maximum age is applicable upto 100 years.
	 * 
	 * @param birthDate User's birth date.
	 */
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets the user's weight.
	 * 
	 * @return This returns the user's weight.
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * Sets the user's weight. cannot be null. Minimum weight required is 10kg and
	 * maximum weight is applicable upto 150kg.
	 * 
	 * @param weight User's weight.
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * Gets the user's height.
	 * 
	 * @return This returns the user's height.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the user's height. Cannot be null. Minimum height required is 5ft. and
	 * maximum height is applicable upto 10ft.
	 * 
	 * @param height User's height.
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * 
	 * Gets the user's step size.
	 * 
	 * @return This returns the user's step size.
	 *
	 */
	public int getStepSize() {
		return stepSize;
	}

	/**
	 * Sets the user's step size. Cannot be null. Minimum stepSize required is 15cm
	 * and maximum stepSize is applicable upto 30cm.
	 * 
	 * @param stepSize User's step size.
	 */
	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}
}
