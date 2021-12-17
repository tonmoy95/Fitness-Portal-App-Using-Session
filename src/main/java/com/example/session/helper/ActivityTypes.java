package com.example.session.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.session.model.ActivityType;

/**
 * Represents the type of activities in the form of list.
 * 
 * @author Tonmoy
 *
 */
@Component
public class ActivityTypes {

	/**
	 * 
	 * @return This returns the list of activities.
	 */
	public List<ActivityType> activityType() {
		return ActivityType.activityType();
	}

}
