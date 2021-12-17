package com.example.session.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.session.model.Profile;
import com.example.session.repository.ProfileRepository;

/**
 * This is the profile service interface's implementation named
 * ProfileServiceImpl where the methods are defined. The business logics are
 * written here.
 * 
 * @author Tonmoy
 * 
 */
@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	ProfileRepository profileRepository;

	/**
	 * Stores the user's profile information.
	 * 
	 * @param profile Object of user's profile.
	 * @return true when the profile is saved.
	 */
	@Override
	public boolean saveProfile(Profile profile) {
		profileRepository.save(profile);
		return true;
	}

	/**
	 * Retrieves the user's profile using registration id.
	 * 
	 * @param regId The user's registration id.
	 * @return Object of user's profile.
	 */
	@Override
	public Profile getProfileByRegId(int regId) {
		return profileRepository.getProfileByRegId(regId);
	}

	/**
	 * Updates the user's profile information using profile id.
	 * 
	 * @param profileId User's profile id.
	 * @param weight    User's weigth.
	 * @param height    User's height.
	 * @param stepSize  User's step size.
	 * @return true when the profile is updated.
	 */
	@Override
	public boolean updateByProfileId(int profileId, float weight, float height, int stepSize) {
		profileRepository.updateByProfileId(profileId, weight, height, stepSize);
		return true;
	}
}
