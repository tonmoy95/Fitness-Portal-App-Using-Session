package com.example.session.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.session.model.Activity;

/**
 * 
 * This is the activity repository section where user's activity data from the
 * database is fetched and pushed according to the user's needs.
 * 
 * @author Tonmoy
 *
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	/**
	 * Retrieves and checks the activity using user's registered id and activity
	 * type.
	 * 
	 * @param reg_id       The user's registration Id.
	 * @param activityType The user's activity type.
	 */
	@Query(value = "select * from activity where reg_id = (:reg_id) and activity_type = (:activity_type)", nativeQuery = true)
	public List<Activity> specificActivity(@Param("reg_id") int reg_id, @Param("activity_type") String activity_type);

	/**
	 * Updates the user's activity information using activity id.
	 * 
	 * @param activityId     The user's activity id.
	 * @param title          The title given by the user.
	 * @param distance       The distance that the user has covered.
	 * @param durationHr     The duration that the user has covered.
	 * @param energyExpended The energy that the user has spent in performing the
	 *                       activities.
	 * @param activityDate   The date that the user has started the activty.
	 */
	@Transactional
	@Modifying
	@Query(value = "update activity a set title = (:title), distance = (:distance), duration_hr = (:duration_hr),energy_expended = (:energy_expended),activity_date = (:activity_date) where a.activity_id = (:activity_id)", nativeQuery = true)
	public void updateByActivityId(@Param("activity_id") int activityId, @Param("title") String title,
			@Param("distance") float distance, @Param("duration_hr") float durationHr,
			@Param("energy_expended") float energyExpended, @Param("activity_date") LocalDate activityDate);

	/**
	 * Searches the user's activity using user's registered id and title.
	 * 
	 * @param regId The user's registration id.
	 * @param title The title given by the user.
	 */
	@Query(value = "select * from activity where reg_id = (:reg_id) and title like (%:title%)", nativeQuery = true)
	public List<Activity> searchActivity(@Param("reg_id") int regId, @Param("title") String title);

	/**
	 * Retrieves all the activities of the user.
	 * 
	 * @param regId The user's registration id.
	 *
	 */
	@Query(value = "select * from activity where reg_id=(:reg_id)", nativeQuery = true)
	public List<Activity> allActivity(@Param("reg_id") int regId);

}
