package com.example.session.controller;

import java.io.IOException;

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
import org.springframework.web.servlet.ModelAndView;

import com.example.session.exception.AgeInputException;
import com.example.session.exception.DbServerDownException;
import com.example.session.exception.InvalidInputException;
import com.example.session.helper.RouteConfig;
import com.example.session.helper.Validation;
import com.example.session.model.GenderEnum;
import com.example.session.model.Profile;
import com.example.session.service.ProfileService;

/**
 * This is the profile controller section where the mapping of pages and their
 * respective job is executed.
 * 
 * @author Tonmoy
 * 
 * 
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	ProfileService profileService;

	public ProfileController() {
	}

	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

	/**
	 * Opens the profile page/form where the user can add their basic informations.
	 * 
	 * @param profile       Object of the user's profile.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the newProfile page if the user did not create the
	 *         profile and returns the dashboard page if the user has already
	 *         created a profile.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/saveProfile", method = RequestMethod.GET)
	public ModelAndView getProfilePage(Profile profile, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		ModelAndView mav;
		try {
			HttpSession session = request.getSession(false);
			int regId = 0;
			if (session != null) {
				regId = (Integer) session.getAttribute("regId");
			} else {
				mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
				mav.addObject("deadSession", true);
				return mav;
			}
			Profile checkProfile = profileService.getProfileByRegId(regId);
			if (checkProfile != null) {
				session.setAttribute("alreadyAddedProfile", "ProfileAdded");
				mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
				return mav;
			} else {
				mav = new ModelAndView("newProfile");
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Verifies and stores the user's profile.
	 * 
	 * @param profile       The user's profile object.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the newProfile page.
	 * @throws DbServerDownException if the database server is down.
	 * 
	 * 
	 */
	@RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
	public ModelAndView addProfile(@Valid @ModelAttribute("profile") Profile profile, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		try {
			Validation.inputCheckForProfile(profile, true);
		} catch (InvalidInputException e) {
			logger.error("Error in inputs : " + e);
			mav = new ModelAndView("newProfile");
			mav.addObject("inputError", true);
			return mav;
		} catch (AgeInputException e) {
			mav = new ModelAndView("newProfile");
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
		profile.setRegId(regId);
		try {
			boolean add = profileService.saveProfile(profile);
			if (add) {
				mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
				session.setAttribute("profileSubmitted", "ProfileSubmitted");
				return mav;
			} else {
				mav = new ModelAndView("newProfile");
				mav.addObject("error", true);
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Opens the page for editing user's profile.
	 * 
	 * @param profile       Object of user's profile.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns editProfile page if the profile has already been added
	 *         and returns dashboard page if the user did not made the profile.
	 * @throws DbServerDownException if the database server is down.
	 */

	@RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
	public ModelAndView editProfilePage(@Valid @ModelAttribute("profile") Profile profile, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		try {
			HttpSession session = request.getSession(false);
			int regId = 0;
			if (session != null) {
				regId = (Integer) session.getAttribute("regId");
			} else {
				mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
				mav.addObject("deadSession", true);
				return mav;
			}
			Profile checkProfile = profileService.getProfileByRegId(regId);
			if (checkProfile == null) {
				session.setAttribute("updateProfile", "ProfileUpdate");
				mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
				return mav;
			} else {
				mav = new ModelAndView("editProfile");
				mav.addObject("uProfile", checkProfile);
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Verifies and edits the user's profile information and stores it.
	 * 
	 * @param profile This is the object of user's profile.
	 * @return This returns the editProfile page.
	 * @throws DbServerDownException if the database server is down.
	 * 
	 */
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public ModelAndView updateProfile(@Valid @ModelAttribute("uProfile") Profile profile, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) throws AgeInputException {
		try {
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
			Profile checkProfile = profileService.getProfileByRegId(regId);
			try {
				Validation.inputCheckForProfile(checkProfile, false);
			} catch (InvalidInputException e) {
				logger.error("Error in inputs : " + e);
				mav = new ModelAndView("editProfile");
				mav.addObject("inputError", true);
				return mav;
			}
			int profileId = checkProfile.getProfileId();
			float weight = profile.getWeight();
			float height = profile.getHeight();
			int stepSize = profile.getStepSize();
			boolean updateProfile = profileService.updateByProfileId(profileId, weight, height, stepSize);
			if (updateProfile) {
				mav = new ModelAndView("editProfile");
				mav.addObject("updated", true);
				return mav;
			} else {
				mav = new ModelAndView("editProfile");
				mav.addObject("error", true);
				return mav;
			}

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}

	/**
	 * Displaying the user's profile information.
	 * 
	 * @return This returns the displayProfile page if there are informations
	 *         available and returns the dashboard page if there are no informations
	 *         available.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/displayProfile", method = RequestMethod.GET)
	public ModelAndView displayProfile(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav;
		GenderEnum gen;
		Profile profile = new Profile();
		try {
			HttpSession session = request.getSession(false);
			int regId = 0;
			if (session != null) {
				regId = (Integer) session.getAttribute("regId");
			} else {
				mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
				mav.addObject("deadSession", true);
				return mav;
			}
			Profile checkProfile = profileService.getProfileByRegId(regId);
			if (checkProfile == null) {
				session.setAttribute("profileDetails", "ProfileDetails");
				mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
				return mav;
			}
			int gInd = checkProfile.getGenderInd();
			if (gInd == 1) {
				gen = GenderEnum.Male;
			} else {
				gen = GenderEnum.Female;
			}
			profile.setBirthDate(checkProfile.getBirthDate());
			profile.setWeight(checkProfile.getWeight());
			profile.setHeight(checkProfile.getHeight());
			profile.setStepSize(checkProfile.getStepSize());
			mav = new ModelAndView("displayProfile");
			mav.addObject("birth", gen);
			mav.addObject("pDetails", profile);
			return mav;

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
	}
}
