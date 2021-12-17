package com.example.session.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.session.exception.DbServerDownException;
import com.example.session.exception.InvalidDateException;
import com.example.session.exception.InvalidInputException;
import com.example.session.exception.InvalidSelectException;
import com.example.session.helper.ActivityTypes;
import com.example.session.helper.RouteConfig;
import com.example.session.helper.Validation;
import com.example.session.model.Activity;
import com.example.session.model.ActivityType;
import com.example.session.service.ActivityService;

/**
 * This is the activity controller section where the mapping of pages and their
 * respective job is executed.
 * 
 * @author Tonmoy
 * 
 * 
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	ActivityService activityService;

	@Autowired
	ActivityTypes diffActivities;

	public ActivityController() {
	}

	public ActivityController(ActivityService activityService, ActivityTypes diffActivities) {
		this.activityService = activityService;
		this.diffActivities = diffActivities;
	}

	private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

	/**
	 * Opens the user's activity page.
	 * 
	 * @param activity      The user's activity object.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the newActivity page.
	 */
	@RequestMapping(value = "/saveActivity", method = RequestMethod.GET)
	public ModelAndView viewSaveActivityPage(Activity activity, BindingResult bindingResult,
			HttpServletRequest request) {
		ModelAndView mav;
		HttpSession session = request.getSession(false);
		if (session == null) {
			mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
			mav.addObject("deadSession", true);
			return mav;
		}
		mav = new ModelAndView("newActivity");
		mav.addObject("activityList", diffActivities.activityType());
		return mav;
	}

	/**
	 * Verifies and stores the user's activity information.
	 * 
	 * @param activity      The user's activity object.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the newActivity page.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/saveActivity", method = RequestMethod.POST)
	public ModelAndView addActivity(@Valid @ModelAttribute("activity") Activity activity, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		if (bindingResult.hasErrors()) {
			mav = new ModelAndView("newActivity");
			mav.addObject("activityList", diffActivities.activityType());
			return mav;
		}
		try {
			Validation.inputCheckForActivity(activity, true);
		} catch (InvalidInputException e) {
			logger.error("Error in inputs : " + e);
			mav = new ModelAndView("newActivity");
			mav.addObject("activityList", diffActivities.activityType());
			mav.addObject("inputError", true);
			return mav;
		} catch (InvalidSelectException e) {
			mav = new ModelAndView("newActivity");
			mav.addObject("activityList", diffActivities.activityType());
			mav.addObject("selectError", true);
			return mav;
		} catch (InvalidDateException e) {
			mav = new ModelAndView("newActivity");
			mav.addObject("activityList", diffActivities.activityType());
			mav.addObject("dateError", true);
			return mav;
		}
		HttpSession session = request.getSession(false);
		int regId = 0;
		if (session != null) {
			regId = (Integer) session.getAttribute("regId");
		} else {
			mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
			mav.addObject("deadSession", true);
			return mav;
		}
		activity.setRegId(regId);
		try {
			boolean add = activityService.saveActivity(activity);
			if (add) {
				logger.info("User added " + activity.getActivityType() + " at : " + LocalDateTime.now());
				mav = new ModelAndView("newActivity");
				mav.addObject("activityList", diffActivities.activityType());
				mav.addObject("submitted", true);
				return mav;
			} else {
				mav = new ModelAndView("newActivity");
				mav.addObject("activityList", diffActivities.activityType());
				mav.addObject("error", true);
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Opens the editing page for user's activity.
	 * 
	 * @param activityId    The user's activity Id.
	 * @param activity      Object of user's activity.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the editActivity page.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/updateActivity", method = RequestMethod.GET)
	public ModelAndView viewUpdateActivityPage(@RequestParam int activityId,
			@Valid @ModelAttribute("activity") Activity activity, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("activityId", activityId);
		} else {
			mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
			mav.addObject("deadSession", true);
			return mav;
		}
		try {
			Activity retrievedActivity = activityService.getActivityByActivityId(activityId);
			mav = new ModelAndView("editActivity");
			mav.addObject("uActivity", retrievedActivity);
			return mav;

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Verifies edits and stores the user's activity information.
	 * 
	 * @param activity      The user's activity object.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the editActivity page.
	 * @throws DbServerDownException if the database server is down.
	 */

	@RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
	public ModelAndView updateActivity(@Valid @ModelAttribute("uActivity") Activity activity,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response)
			throws InvalidSelectException {
		ModelAndView mav;
		if (bindingResult.hasErrors()) {
			mav = new ModelAndView("editActivity");
			return mav;
		}
		try {
			Validation.inputCheckForActivity(activity, false);
		} catch (InvalidInputException e) {
			logger.error("Error in inputs : " + e);
			mav = new ModelAndView("editActivity");
			mav.addObject("inputError", true);
			return mav;
		} catch (InvalidDateException e) {
			mav = new ModelAndView("editActivity");
			mav.addObject("dateError", true);
			return mav;
		}
		String title = activity.getTitle();
		float distance = activity.getDistance();
		float durationHr = activity.getDurationHr();
		float energyExpended = activity.getEnergyExpended();
		LocalDate activityDate = activity.getActivityDate();
		try {
			HttpSession session = request.getSession(false);
			int aId = 0;
			if (session != null) {
				aId = (Integer) session.getAttribute("activityId");
			} else {
				mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
				mav.addObject("deadSession", true);
				return mav;
			}
			boolean updateActivity = activityService.updateByActivityId(aId, title, distance, durationHr,
					energyExpended, activityDate);
			if (updateActivity) {
				logger.info("User updated " + activity.getActivityType() + " at : " + LocalDateTime.now());
				mav = new ModelAndView("editActivity");
				mav.addObject("updated", true);
				return mav;
			} else {
				mav = new ModelAndView("editActivity");
				mav.addObject("error", true);
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Displaying the user's activity information.
	 * 
	 * @param activity      Object of the user's activity.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the displayActivity page if the informations are
	 *         available and returns the dashboard page if there are no informations
	 *         available.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/displayActivity", method = RequestMethod.GET)
	public ModelAndView displayActivity(@Valid @ModelAttribute("activity") Activity activity,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		try {
			HttpSession session = request.getSession(false);
			int regId = 0;
			if (session != null) {
				regId = (Integer) session.getAttribute("regId");
				session.setAttribute("activityType", activity.getActivityType());
			} else {
				mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
				mav.addObject("deadSession", true);
				return mav;
			}
			String activityType = (String) session.getAttribute("activityType");
			if (activityType.equals((ActivityType.Select).toString())) {
				List<Activity> allActivity = activityService.allActivity(regId);
				if (allActivity.size() == 0) {
					session.setAttribute("displayByActivityType", "DisplayByActivityType");
					mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
					return mav;
				} else {
					mav = new ModelAndView("displayActivity");
					mav.addObject("activityDetails", allActivity);
					return mav;
				}
			}
			List<Activity> specificActivity = activityService.specificActivity(regId, activityType);
			if (specificActivity.size() == 0) {
				session.setAttribute("displayByActivityType", "DisplayByActivityType");
				mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
				return mav;
			} else {
				mav = new ModelAndView("displayActivity");
				mav.addObject("activityDetails", specificActivity);
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Searches the user's activity information.
	 * 
	 * @param activity      Object of the user's activity.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the searchActivity page if the informations are
	 *         available and returns the dashboard page if there are no informations
	 *         available.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/searchActivity", method = RequestMethod.GET)
	public ModelAndView searchActivity(@Valid @ModelAttribute("activity") Activity activity,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		HttpSession session = request.getSession(false);
		int regId = 0;
		if (session != null) {
			regId = (Integer) session.getAttribute("regId");
		} else {
			mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
			mav.addObject("deadSession", true);
			return mav;
		}
		String title = activity.getTitle();
		if (title == "") {
			session.setAttribute("displayBySearch", "DisplayBySearch");
			mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
			return mav;
		}
		try {
			List<Activity> searchActivity = activityService.searchActivity(regId, title);
			if (searchActivity.size() == 0) {
				session.setAttribute("displayBySearch", "DisplayBySearch");
				mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
				return mav;
			} else {
				mav = new ModelAndView("searchActivity");
				mav.addObject("searchDetails", searchActivity);
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Deletes the user's activity information.
	 * 
	 * @param activityId The user's activity id.
	 * @return This returns the displayActivity page.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/deleteActivity", method = RequestMethod.POST)
	public ModelAndView deleteActivity(HttpServletRequest request) {
		ModelAndView mav;
		String typeOfActivity = "";
		HttpSession session = request.getSession(false);
		if (session != null) {
			typeOfActivity = (String) session.getAttribute("activityType");
		} else {
			mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
			mav.addObject("deadSession", true);
			return mav;
		}
		boolean deleteActivity = false;
		try {
			for (String actId : request.getParameterValues("activId")) {
				int activityId = Integer.parseInt(actId);
				try {
					deleteActivity = activityService.deleteById(activityId);
				} catch (DbServerDownException e) {
					throw new DbServerDownException();
				}
			}
			if (deleteActivity) {
				switch (typeOfActivity) {
				case "Running":
					mav = new ModelAndView(RouteConfig.RUNNING_ACTIVITY);
					break;
				case "Walking":
					mav = new ModelAndView(RouteConfig.WALKING_ACTIVITY);
					break;
				case "Cycling":
					mav = new ModelAndView(RouteConfig.CYCLING_ACTIVITY);
					break;
				case "Swimming":
					mav = new ModelAndView(RouteConfig.SWIMMING_ACTIVITY);
					break;
				default:
					mav = new ModelAndView(RouteConfig.SELECT_ACTIVITY);
				}
				mav.addObject("deleted", true);
				return mav;

			} else {
				mav = new ModelAndView(RouteConfig.DISPLAY_ACTIVITY);
				mav.addObject("error", true);
				return mav;
			}

		} catch (NullPointerException e) {
			switch (typeOfActivity) {
			case "Running":
				mav = new ModelAndView(RouteConfig.RUNNING_ACTIVITY);
				break;
			case "Walking":
				mav = new ModelAndView(RouteConfig.WALKING_ACTIVITY);
				break;
			case "Cycling":
				mav = new ModelAndView(RouteConfig.CYCLING_ACTIVITY);
				break;
			case "Swimming":
				mav = new ModelAndView(RouteConfig.SWIMMING_ACTIVITY);
				break;
			default:
				mav = new ModelAndView(RouteConfig.SELECT_ACTIVITY);
			}
			return mav;
		}
	}

	/**
	 * Moves to the display page.
	 * 
	 * @return This returns the displayActivity page.
	 */
	@RequestMapping(value = "/backToDisplay", method = RequestMethod.GET)
	public ModelAndView backToDisplay(HttpServletRequest request) {
		ModelAndView mav;
		String typeOfActivity = "";
		HttpSession session = request.getSession(false);
		if (session != null) {
			typeOfActivity = (String) session.getAttribute("activityType");
		} else {
			mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
			mav.addObject("deadSession", true);
			return mav;
		}
		switch (typeOfActivity) {
		case "Running":
			mav = new ModelAndView(RouteConfig.RUNNING_ACTIVITY);
			break;
		case "Walking":
			mav = new ModelAndView(RouteConfig.WALKING_ACTIVITY);
			break;
		case "Cycling":
			mav = new ModelAndView(RouteConfig.CYCLING_ACTIVITY);
			break;
		case "Swimming":
			mav = new ModelAndView(RouteConfig.SWIMMING_ACTIVITY);
			break;
		default:
			mav = new ModelAndView(RouteConfig.SELECT_ACTIVITY);
		}
		return mav;
	}
}
