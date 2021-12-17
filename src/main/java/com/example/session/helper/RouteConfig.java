package com.example.session.helper;

import org.springframework.stereotype.Component;

/**
 * Represents the routing configuration.
 * 
 * @author Tonmoy
 *
 */
@Component
public class RouteConfig {

	public static final String REDIRECT_DASHBOARD = "redirect:/user/dashboard";
	public static final String REDIRECT_LOGIN = "redirect:/user/login";
	public static final String REDIRECT_INDEX = "redirect:/";
	public static final String RUNNING_ACTIVITY = "redirect:/activity/displayActivity?activityType=Running";
	public static final String WALKING_ACTIVITY = "redirect:/activity/displayActivity?activityType=Walking";
	public static final String CYCLING_ACTIVITY = "redirect:/activity/displayActivity?activityType=Cycling";
	public static final String SWIMMING_ACTIVITY = "redirect:/activity/displayActivity?activityType=Swimming";
	public static final String SELECT_ACTIVITY = "redirect:/activity/displayActivity?activityType=Select";
	public static final String DISPLAY_ACTIVITY = "redirect:/activity/displayActivity";
}
