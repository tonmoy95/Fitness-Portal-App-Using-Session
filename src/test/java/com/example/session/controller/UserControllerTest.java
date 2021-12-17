//package com.example.session.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.example.session.helper.ActivityTypes;
//import com.example.session.helper.HashingHelper;
//import com.example.session.model.Activity;
//import com.example.session.model.ActivityType;
//import com.example.session.model.User;
//import com.example.session.service.UserService;
//
///**
// * This is the test for user controller layer where the service layer has been
// * mocked.
// * 
// * @author Tonmoy
// * 
// * 
// */
//public class UserControllerTest {
//
//	private static final int REG_ID = 107;
//	private static final String EMAIL_SUCCESS = "java@java.com";
//	private static final String EMAIL_FAILURE = "java@ja.com";
//	private static final String PASSWORD_SUCCESS = "Java@1234";
//	private static final String PASSWORD_FAILURE = "Java@4321";
//	private static final String PASSWORD_INVALID = "Ja12345678";
//	private static final String FIRST_NAME = "Spring";
//	private static final String LAST_NAME = "Thymeleaf";
//
//	private HashingHelper hashingHelper = new HashingHelper();
//
//	User user;
//	User dbUser;
//	UserService mockUserService;
//	ActivityTypes mockDiffActivities;
//	UserController userController;
//
//	@BeforeEach
//	public void setUp() {
//		user = new User();
//		dbUser = new User();
//		mockUserService = mock(UserService.class);
//		mockDiffActivities = mock(ActivityTypes.class);
//		userController = new UserController(mockUserService, hashingHelper, mockDiffActivities);
//	}
//
//	@AfterEach
//	public void tearDown() {
//		user = null;
//		dbUser = null;
//		mockUserService = null;
//		userController = null;
//	}
//
//	@Test
//	public void viewLoginPage_DisplayingLoginPage() {
//		userController = new UserController();
//		ModelAndView expected = new ModelAndView("login");
//		String expectedStr = expected.toString();
//		ModelAndView actual = userController.viewLoginPage();
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void viewHomePage_DisplayingHomePage() {
//		userController = new UserController();
//		String expected = "index";
//		String actual = userController.viewHomePage();
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void logout_DisplayingHomePage() {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		String expected = "redirect:/";
//		String actual = userController.logout(request, response);
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void back_DisplayingDashboardPage() {
//		String expected = "redirect:/user/dashboard";
//		String actual = userController.back();
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void viewDashboardPage_DisplayingDashboardPage() throws IOException {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		List<ActivityType> listOfActivities = Arrays.asList(ActivityType.Select, ActivityType.Running,
//				ActivityType.Walking, ActivityType.Cycling, ActivityType.Swimming);
//		user = createDefaultUser(EMAIL_SUCCESS, PASSWORD_SUCCESS);
//		Activity activity = new Activity();
//		// BindingResult mockBindingResult = mock(BindingResult.class);
//		ModelAndView expected = new ModelAndView("dashboard");
//		expected.addObject("uDetails", user);
//		expected.addObject("activityList", listOfActivities);
//		String expectedStr = expected.toString();
//		when(mockDiffActivities.activityType()).thenReturn(listOfActivities);
//		when(mockUserService.getUserByEmail(EMAIL_SUCCESS)).thenReturn(user);
//		ModelAndView actual = userController.viewDashboardPage(activity, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void login_BlankInputs_FailToLogin() throws NoSuchAlgorithmException, ServletException, IOException {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		userController = new UserController();
//		// HttpServletRequest request = mock(HttpServletRequest.class);
//		ModelAndView expected = new ModelAndView("login");
//		expected.addObject("userNotPresent", true);
//		String expectedStr = expected.toString();
//		user.setEmailId("");
//		user.setPassword("");
//		ModelAndView actual = userController.login(user, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//
//	}
//
//	@Test
//	public void login_ValidCredentials_LoginSuccessful()
//			throws NoSuchAlgorithmException, NoSuchProviderException, ServletException, IOException {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		HttpSession session = request.getSession();
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		dbUser = createDbUser();
//		user = createDefaultUser(EMAIL_SUCCESS, PASSWORD_SUCCESS);
//		when(mockUserService.getUserByEmail(EMAIL_SUCCESS)).thenReturn(dbUser);
//		when(request.getSession()).thenReturn(session);
//		ModelAndView actual = userController.login(user, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//
//	}
//
//	@Test
//	public void login_InvalidPassword_FailToLogin()
//			throws NoSuchAlgorithmException, NoSuchProviderException, ServletException, IOException {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		ModelAndView expected = new ModelAndView("login");
//		expected.addObject("invalidPassword", true);
//		String expectedStr = expected.toString();
//		dbUser = createDbUser();
//		user = createDefaultUser(EMAIL_SUCCESS, PASSWORD_FAILURE);
//		when(mockUserService.getUserByEmail(EMAIL_SUCCESS)).thenReturn(dbUser);
//		ModelAndView actual = userController.login(user, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//
//	}
//
//	@Test
//	public void login_WrongCredentials_FailToLogin() throws NoSuchAlgorithmException, ServletException, IOException {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		ModelAndView expected = new ModelAndView("login");
//		expected.addObject("userNotPresent", true);
//		String expectedStr = expected.toString();
//		user = createDefaultUser(EMAIL_FAILURE, PASSWORD_FAILURE);
//		when(mockUserService.getUserByEmail(EMAIL_FAILURE)).thenReturn(null);
//		ModelAndView actual = userController.login(user, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//
//	}
//
//	@Test
//	public void viewRegistrationPage_DisplayingRegistrationPage() {
//		userController = new UserController();
//		String expected = "newRegister";
//		BindingResult bindingResult = null;
//		String actual = userController.viewRegistrationPage(user, bindingResult);
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void addUser_RegisteringNewUser_RegistrationSuccessful()
//			throws NoSuchAlgorithmException, NoSuchProviderException {
//		ModelAndView expected = new ModelAndView("redirect:/user/login");
//		String expectedStr = expected.toString();
//		BindingResult mockBindingResult = mock(BindingResult.class);
//		user = createDefaultUser(EMAIL_SUCCESS, PASSWORD_SUCCESS);
//		when(mockUserService.getUserByEmail(EMAIL_SUCCESS)).thenReturn(null);
//		when(mockUserService.saveUser(user)).thenReturn(true);
//		ModelAndView actual = userController.addUser(user, mockBindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addUser_EmailAlreadyPresent_FailToRegister() throws NoSuchAlgorithmException, NoSuchProviderException {
//		ModelAndView expected = new ModelAndView("newRegister");
//		expected.addObject("emailAlreadyUsed", true);
//		String expectedStr = expected.toString();
//		BindingResult mockBindingResult = mock(BindingResult.class);
//		user = createDefaultUser(EMAIL_FAILURE, PASSWORD_SUCCESS);
//		when(mockUserService.getUserByEmail(EMAIL_FAILURE)).thenReturn(user);
//		ModelAndView actual = userController.addUser(user, mockBindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addUser_InvalidPassword_FailToRegister() throws NoSuchAlgorithmException, NoSuchProviderException {
//		ModelAndView expected = new ModelAndView("newRegister");
//		expected.addObject("invalidPassword", true);
//		String expectedStr = expected.toString();
//		BindingResult mockBindingResult = mock(BindingResult.class);
//		user = createDefaultUser(EMAIL_SUCCESS, PASSWORD_INVALID);
//		when(mockUserService.getUserByEmail(EMAIL_SUCCESS)).thenReturn(null);
//		ModelAndView actual = userController.addUser(user, mockBindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	private User createDefaultUser(String emailStatus, String passwordStatus) {
//		User newUser = new User();
//		newUser.setRegId(REG_ID);
//		newUser.setFirstName(FIRST_NAME);
//		newUser.setLastName(LAST_NAME);
//		newUser.setEmailId(emailStatus);
//		newUser.setPassword(passwordStatus);
//		return newUser;
//	}
//
//	private User createDbUser() throws NoSuchProviderException {
//		User newDbUser = new User();
//		byte[] byteSalt = null;
//		byteSalt = hashingHelper.getSalt();
//		String strPassword = hashingHelper.getSecurePassword(PASSWORD_SUCCESS, byteSalt);
//		String strSalt = hashingHelper.toHex(byteSalt);
//		newDbUser.setRegId(REG_ID);
//		newDbUser.setEmailId(EMAIL_SUCCESS);
//		newDbUser.setSaltValue(strSalt);
//		newDbUser.setPassword(strPassword);
//		return newDbUser;
//	}
//
//}
