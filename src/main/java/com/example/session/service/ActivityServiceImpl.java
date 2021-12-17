package com.example.session.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.session.model.Activity;
import com.example.session.repository.ActivityRepository;

/**
 * This is the activity service interface's implementation named
 * ActivityServiceImpl where the methods are defined. The business logics are
 * written here.
 * 
 * @author Tonmoy
 * 
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	ActivityRepository activityRepository;

	/**
	 * Stores the user's activity information.
	 * 
	 * @param activity Object of user's activity.
	 * @return true when the user's activity is saved.
	 */
	@Override
	public boolean saveActivity(Activity activity) {
		activityRepository.save(activity);
		return true;
	}

	/**
	 * Retrieves and checks the activity using user's registered id and activity
	 * type.
	 * 
	 * @param regId         The user's registered id.
	 * @param activity_type The type of activity the user plays.
	 * @return This returns the object of user's activity.
	 */
	@Override
	public List<Activity> specificActivity(int regId, String activity_type) {
		return activityRepository.specificActivity(regId, activity_type);
	}

	/**
	 * Updates the user's activity information using activity id.
	 * 
	 * @param activityId     The user's activity id.
	 * @param title          The title given by the user.
	 * @param distance       The distance that the user has covered.
	 * @param duration_hr    The duration that the user has covered.
	 * @param energyExpended The energy that the user has spent in performing the
	 *                       activities.
	 * @param activityDate   The date that the user has started the activty.
	 * @return true when the user's activity is updated.
	 */
	@Override
	public boolean updateByActivityId(int activityId, String title, float distance, float durationHr,
			float energyExpended, LocalDate activityDate) {
		activityRepository.updateByActivityId(activityId, title, distance, durationHr, energyExpended, activityDate);
		return true;
	}

	/**
	 * Searches the user's activity using user's registered id and title.
	 * 
	 * @param regId The user's registered id.
	 * @param title The title given by the user.
	 * @return This returns the list of activities of the user.
	 */
	@Override
	public List<Activity> searchActivity(int regId, String title) {
		return activityRepository.searchActivity(regId, title);

	}

	/**
	 * Retrieves all the activities of the user.
	 * 
	 * @param regId The user's registration id.
	 * @return This returns the list of activities of the user.
	 */
	@Override
	public List<Activity> allActivity(int regId) {
		List<Activity> allActivity = activityRepository.allActivity(regId);
		return allActivity;
	}

	/**
	 * Retrieves the user's activity using user's activity id.
	 * 
	 * @param activityId The user's activity id.
	 * @return This returns the user's activity object.
	 */
	@Override
	public Activity getActivityByActivityId(int activityId) {
		return activityRepository.getById(activityId);
	}

	/**
	 * Deletes the user's activity using activity id.
	 * 
	 * @param activityId The user's activity id.
	 * @return This returns true if the user's activity is deleted.
	 */
	@Override
	public boolean deleteById(int activityId) {
		activityRepository.deleteById(activityId);
		return true;
	}
}
