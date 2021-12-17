package com.example.session.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
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

import com.example.session.exception.DbServerDownException;
import com.example.session.helper.ActivityTypes;
import com.example.session.helper.HashingHelper;
import com.example.session.helper.RouteConfig;
import com.example.session.model.Activity;
import com.example.session.model.User;
import com.example.session.service.UserService;

/**
 * This is the user controller section where the mapping of pages and their
 * respective job is executed.
 * 
 * @author Tonmoy
 * 
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	HashingHelper hashingHelper;

	@Autowired
	ActivityTypes diffActivities;

	public UserController() {
	}

	public UserController(UserService userService, HashingHelper hashingHelper, ActivityTypes diffActivities) {
		this.userService = userService;
		this.hashingHelper = hashingHelper;
		this.diffActivities = diffActivities;
	}

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	public boolean addedNew = false;
	// public boolean deadSession = false;

	/**
	 * Opens the index page.
	 * 
	 * @return This returns the index page.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView viewHomePage(HttpServletRequest request) {
		ModelAndView mav;
		HttpSession session = request.getSession(false);
		if (session != null) {
			mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
			return mav;
		} else {
			mav = new ModelAndView("index");
			return mav;
		}
	}

	/**
	 * Opens the login page for the user.
	 * 
	 * @return This returns the login page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView viewLoginPage(HttpServletRequest request) {
		ModelAndView mav;
		HttpSession session = request.getSession(false);
		if (session != null) {
			mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
			return mav;
		} else {
			mav = new ModelAndView("login");
			if (addedNew == true) {
				mav.addObject("addedNew", true);
				addedNew = false;
			}
			return mav;
		}
	}

	/**
	 * User's credentials are verified here and then they are redirected to the
	 * dashboard page.
	 * 
	 * @param user Object of the user.
	 * @return This returns the dashboard page if login is successful and returns
	 *         the login page if login is unsuccessful.
	 * @throws DbServerDownException if the database server is down.
	 * 
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute(value = "user") User user, HttpServletRequest request,
			HttpServletResponse response) throws NoSuchAlgorithmException, ServletException, IOException {
		response.setContentType("text/html");
		ModelAndView mav;
		String password = user.getPassword();
		String emailId = user.getEmailId();
		if (emailId == "" || password == "") {
			mav = new ModelAndView("login");
			mav.addObject("userNotPresent", true);
			return mav;
		}
		try {
			User checkEmail = userService.getUserByEmail(emailId);
			if (checkEmail != null) {
				String strSalt = checkEmail.getSaltValue();
				byte[] byteSalt = hashingHelper.fromHex(strSalt);
				String loginPassword = hashingHelper.getSecurePassword(password, byteSalt);
				if (loginPassword.equals(checkEmail.getPassword())) {
					logger.info("User logged in at : " + LocalDateTime.now());
					HttpSession session = request.getSession();
					session.setAttribute("regId", checkEmail.getRegId());
					session.setAttribute("emailId", emailId);
					session.setMaxInactiveInterval(5 * 60);
					mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
					return mav;
				}
				mav = new ModelAndView("login");
				mav.addObject("invalidPassword", true);
				return mav;
			}
			mav = new ModelAndView("login");
			mav.addObject("userNotPresent", true);
			return mav;

		} catch (Exception e) {
			throw new DbServerDownException();
		}

	}

	/**
	 * Opens the dashboard page for the user.
	 * 
	 * @param activity      Object of the user's activity.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * 
	 * @return This returns the dashboard page.
	 * @throws IOException
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView viewDashboardPage(Activity activity, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		try {
			ModelAndView mav;
			HttpSession session = request.getSession(false);
			String emailId = "";
			if (session != null) {
				emailId = (String) session.getAttribute("emailId");
			} else {
				mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
				mav.addObject("deadSession", true);
				// deadSession = true;
				return mav;
			}
			User checkEmail = userService.getUserByEmail(emailId);
			mav = new ModelAndView("dashboard");
			mav.addObject("uDetails", checkEmail);
			mav.addObject("activityList", diffActivities.activityType());
			String addedProfile = (String) session.getAttribute("alreadyAddedProfile");
			String submittedProfile = (String) session.getAttribute("profileSubmitted");
			String updateProfile = (String) session.getAttribute("updateProfile");
			String profileDetails = (String) session.getAttribute("profileDetails");
			String displayByActivityType = (String) session.getAttribute("displayByActivityType");
			String displayBySearch = (String) session.getAttribute("displayBySearch");
			if (addedProfile != null) {
				if (addedProfile.equals("ProfileAdded")) {
					mav.addObject("alreadyAdded", true);
					session.setAttribute("alreadyAddedProfile", "");
				}
			}
			if (submittedProfile != null) {
				if (submittedProfile.equals("ProfileSubmitted")) {
					mav.addObject("submittedProfile", true);
					session.setAttribute("profileSubmitted", "");
				}
			}
			if (updateProfile != null) {
				if (updateProfile.equals("ProfileUpdate")) {
					mav.addObject("updateFirst", true);
					session.setAttribute("updateProfile", "");
				}
			}
			if (profileDetails != null) {
				if (profileDetails.equals("ProfileDetails")) {
					mav.addObject("profileDetails", true);
					session.setAttribute("profileDetails", "");
				}
			}
			if (displayByActivityType != null) {
				if (displayByActivityType.equals("DisplayByActivityType")) {
					mav.addObject("displayByActivityType", true);
					session.setAttribute("displayByActivityType", "");
				}
			}
			if (displayBySearch != null) {
				if (displayBySearch.equals("DisplayBySearch")) {
					mav.addObject("displayBySearch", true);
					session.setAttribute("displayBySearch", "");
				}
			}
			return mav;

		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}

	}

	/**
	 * Opens the registration form/page for the user.
	 * 
	 * @param user          object of the user.
	 * @param bindingResult spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the newRegister page.
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.GET)
	public String viewRegistrationPage(User user, BindingResult bindingResult) {
		return "newRegister";
	}

	/**
	 * Verifies and stores the user's credentials along with basic information.
	 * 
	 * @param user          Object of the user.
	 * @param bindingResult Spring's object that holds the result of the validation
	 *                      and binding.
	 * @return This returns the login page if registration is successful and returns
	 *         the newRegister page if registration is unsuccessful.
	 * @throws DbServerDownException if the database server is down.
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		ModelAndView mav;
		mav = new ModelAndView("newRegister");
		if (bindingResult.hasErrors()) {
			return mav;
		}
		String password = user.getPassword();
		String email = user.getEmailId();
		try {

			User checkEmail = userService.getUserByEmail(email);
			if (checkEmail == null) {
				String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
				Pattern p = Pattern.compile(regex);
				Matcher pass = p.matcher(password);
				boolean checkRegex = pass.matches();
				if (!checkRegex) {
					mav.addObject("invalidPassword", true);
					return mav;
				}
				byte[] byteSalt = null;
				byteSalt = hashingHelper.getSalt();
				String strPassword = hashingHelper.getSecurePassword(password, byteSalt);
				String strSalt = hashingHelper.toHex(byteSalt);
				user.setSaltValue(strSalt);
				user.setPassword(strPassword);
				boolean add = userService.saveUser(user);
				if (add) {
					mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
					addedNew = true;
					return mav;
				} else {
					mav.addObject("error", true);
					return mav;
				}
			}
		} catch (DbServerDownException e) {
			throw new DbServerDownException();
		}
		mav.addObject("emailAlreadyUsed", true);
		return mav;
	}

	/**
	 * Moves to the dashboard page.
	 * 
	 * @return This returns the dashboard page.
	 */
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public ModelAndView back(HttpServletRequest request) {
		ModelAndView mav;
		HttpSession session = request.getSession(false);
		if (session == null) {
			mav = new ModelAndView(RouteConfig.REDIRECT_LOGIN);
			mav.addObject("deadSession", true);
			return mav;
		}
		mav = new ModelAndView(RouteConfig.REDIRECT_DASHBOARD);
		return mav;
	}

	/**
	 * Logs out the user.
	 * 
	 * @return This returns the index page.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		logger.info("User logged out at : " + LocalDateTime.now());
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return RouteConfig.REDIRECT_LOGIN;
	}

}
