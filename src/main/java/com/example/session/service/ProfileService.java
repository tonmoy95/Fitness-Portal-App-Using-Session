package com.example.session.service;

import com.example.session.model.Profile;

/**
 * This is the profile service interface where the required methods are
 * declared. Service section is a bridge between controller section and
 * repository section.
 * 
 * @author Tonmoy
 * 
 */
public interface ProfileService {

	/**
	 * Stores the user's profile information.
	 * 
	 * @param profile object of user's profile.
	 * 
	 */
	public boolean saveProfile(Profile profile);

	/**
	 * Retrieves the user's profile using registration id.
	 * 
	 * @param regId The user's registered id.
	 */
	public Profile getProfileByRegId(int regId);

	/**
	 * Updates the user's profile information using profile id.
	 * 
	 * @param profileId User's profile id.
	 * @param weight    User's weigth.
	 * @param height    User's height.
	 * @param stepSize  User's step size.
	 */
	public boolean updateByProfileId(int profileId, float weight, float height, int stepSize);
}
