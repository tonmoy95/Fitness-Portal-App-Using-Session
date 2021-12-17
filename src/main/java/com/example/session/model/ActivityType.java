package com.example.session.model;

import java.util.Arrays;
import java.util.List;

/**
 * Types of activities that can be used.
 * 
 * @author Tonmoy
 *
 */
public enum ActivityType {

	Select, Running, Walking, Cycling, Swimming;

	/**
	 * 
	 * @return This returns the list of activities.
	 */
	public static List<ActivityType> activityType() {

		List<ActivityType> listOfActivities = Arrays.asList(ActivityType.Select, ActivityType.Running,
				ActivityType.Walking, ActivityType.Cycling, ActivityType.Swimming);
		return listOfActivities;
	}
}
