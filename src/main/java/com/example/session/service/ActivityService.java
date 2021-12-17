package com.example.session.service;

import java.time.LocalDate;
import java.util.List;

import com.example.session.model.Activity;

/**
 * This is the activity service interface where the required methods are
 * declared. Service section is a bridge between controller section and
 * repository section.
 * 
 * @author Tonmoy
 * 
 */
public interface ActivityService {

	/**
	 * Stores the user's activity information.
	 * 
	 * @param activity Object of user's activity.
	 */
	public boolean saveActivity(Activity activity);

	/**
	 * Retrieves the activity id of the user.
	 * 
	 * @param regId        The user's registered id.
	 * @param activityType The type of activity the user plays.
	 */
	public List<Activity> specificActivity(int regId, String activityType);

	/**
	 * Updates the activity of the user.
	 * 
	 * @param activityId     The user's activity id.
	 * @param title          The title given by the user.
	 * @param distance       The distance that the user has covered.
	 * @param durationHr     The duration that the user has covered.
	 * @param energyExpended The energy that the user has spent in performing the
	 *                       activities.
	 * @param activityDate   The date that the user has started the activty.
	 */
	public boolean updateByActivityId(int activityId, String title, float distance, float durationHr,
			float energyExpended, LocalDate activityDate);

	/**
	 * Searches the activity of the user.
	 * 
	 * @param regId the user's registration id.
	 * @param title The title given by the user.
	 */
	public List<Activity> searchActivity(int regId, String title);

	/**
	 * Lists all the activities of the user.
	 * 
	 * @param regId the user's registration id.
	 * 
	 */
	public List<Activity> allActivity(int regId);

	/**
	 * Retrieves the user's activity using user's activity id.
	 * 
	 * @param activityId The user's activity id.
	 *
	 */
	public Activity getActivityByActivityId(int activityId);

	/**
	 * Deletes the activity of the user.
	 * 
	 * @param activityId The user's activity id.
	 * 
	 */
	public boolean deleteById(int activityId);
}
