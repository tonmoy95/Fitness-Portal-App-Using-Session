package com.example.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.session.model.Profile;

/**
 * 
 * This is the profile repository section where user's profile data from the
 * database is fetched and pushed according to the user's needs.
 * 
 * @author Tonmoy
 *
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

	/**
	 * Retreives user's profile by registration id.
	 * 
	 * @param regId The registered Id.
	 */
	@Query(value = "select * from profile where reg_id = (:reg_id)", nativeQuery = true)
	public Profile getProfileByRegId(@Param("reg_id") int regId);

	/**
	 * Updates and stores user's profile.
	 * 
	 * @param profileId User's profile id.
	 * @param weight    User's weigth.
	 * @param height    User's height.
	 * @param stepSize  User's step size.
	 */
	@Transactional
	@Modifying
	@Query(value = "update profile p set weight = (:weight), height = (:height), step_size = (:step_size) where p.profile_id = (:profile_id)", nativeQuery = true)
	public void updateByProfileId(@Param("profile_id") int profileId, @Param("weight") float weight,
			@Param("height") float height, @Param("step_size") int stepSize);
}
