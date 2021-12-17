//package com.example.session.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.time.LocalDate;
//
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
//import com.example.session.exception.AgeInputException;
//import com.example.session.exception.InvalidInputException;
//import com.example.session.model.Profile;
//import com.example.session.service.ProfileService;
//
///**
// * This is the test for profile controller layer where the service layer has
// * been mocked.
// * 
// * @author Tonmoy
// * 
// * 
// */
//public class ProfileControllerTest {
//	private static final int REG_ID = 107;
//	private static final int PROFILE_ID = 207;
//	private static final LocalDate DATE = LocalDate.of(1995, 06, 24);
//	private static final int G_IND = 1;
//	private static final Float HEIGHT = 5.6f;
//	private static final Float NEW_HEIGHT = 6.2f;
//	private static final Float WEIGHT = 67.5f;
//	private static final Float NEW_WEIGHT = 89.5f;
//	private static final int STEP_SIZE = 24;
//	private static final int NEW_STEP_SIZE = 27;
//
//	Profile profile;
//	Profile dbProfile;
//	ProfileService mockProfileService;
//	ProfileController profileController;
//	HttpServletRequest request = mock(HttpServletRequest.class);
//	HttpServletResponse response = mock(HttpServletResponse.class);
//	HttpSession session = mock(HttpSession.class);
//
//	@BeforeEach
//	public void setUp() {
//		profile = new Profile();
//		dbProfile = new Profile();
//		mockProfileService = mock(ProfileService.class);
//		profileController = new ProfileController(mockProfileService);
//	}
//
//	@AfterEach
//	public void tearDown() {
//		profile = null;
//		dbProfile = null;
//		mockProfileService = null;
//		profileController = null;
//	}
//
//	@Test
//	public void getProfilePage_DisplayingDashboardPage_IfProfileAlreadyAdded() throws IOException {
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		// profile = createDefaultProfile(PROFILE_ID, DATE, REG_ID, PROFILE_ID,
//		// NEW_STEP_SIZE, G_IND);
//		when(mockProfileService.getProfileByRegId(REG_ID)).thenReturn(profile);
//		ModelAndView actual = profileController.getProfilePage(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void getProfilePage_DisplayingProfilePage_IfProfileNotAdded() throws IOException {
//		ModelAndView expected = new ModelAndView("newProfile");
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockProfileService.getProfileByRegId(REG_ID)).thenReturn(null);
//		ModelAndView actual = profileController.getProfilePage(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addProfile_InvalidInputs_FailToAddProfile() throws InvalidInputException {
//		ModelAndView expected = new ModelAndView("newProfile");
//		expected.addObject("inputError", true);
//		String expectedStr = expected.toString();
//		profile.setHeight(12f);
//		BindingResult bindingResult = mock(BindingResult.class);
//		ModelAndView actual = profileController.addProfile(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addProfile_InvalidDate_FailToAddProfile() throws InvalidInputException {
//		ModelAndView expected = new ModelAndView("newProfile");
//		expected.addObject("dateError", true);
//		String expectedStr = expected.toString();
//		profile = createDefaultProfile(PROFILE_ID, LocalDate.now(), G_IND, HEIGHT, WEIGHT, STEP_SIZE);
//		BindingResult bindingResult = mock(BindingResult.class);
//		ModelAndView actual = profileController.addProfile(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addProfile_AddProfileSuccessful() throws InvalidInputException {
//		// session = request.getSession();
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		profile = createDefaultProfile(PROFILE_ID, DATE, G_IND, HEIGHT, WEIGHT, STEP_SIZE);
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(request.getSession(false)).thenReturn(session);
//		when(mockProfileService.saveProfile(profile)).thenReturn(true);
//		ModelAndView actual = profileController.addProfile(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void editProfilePage_DisplayingEditProfilePage_IfProfileAlreadyAdded() {
//		ModelAndView expected = new ModelAndView("editProfile");
//		expected.addObject("uProfile", profile);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockProfileService.getProfileByRegId(REG_ID)).thenReturn(profile);
//		ModelAndView actual = profileController.editProfilePage(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void editProfilePage_DisplayingDashboardPage_IfProfileNotAdded() {
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockProfileService.getProfileByRegId(REG_ID)).thenReturn(null);
//		ModelAndView actual = profileController.editProfilePage(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void updateProfile_InvalidInputs_FailToUpdateProfile() throws InvalidInputException, AgeInputException {
//		ModelAndView expected = new ModelAndView("editProfile");
//		expected.addObject("inputError", true);
//		String expectedStr = expected.toString();
//		profile = createDefaultProfile(PROFILE_ID, DATE, G_IND, 12f, WEIGHT, NEW_STEP_SIZE);
//		// profile.setHeight(12f);
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockProfileService.getProfileByRegId(REG_ID)).thenReturn(profile);
//		ModelAndView actual = profileController.updateProfile(profile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void updateProfile_UpdateProfileSuccessful() throws InvalidInputException, AgeInputException {
//		ModelAndView expected = new ModelAndView("editProfile");
//		expected.addObject("updated", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		Profile profile = createDefaultProfile(PROFILE_ID, DATE, G_IND, HEIGHT, WEIGHT, STEP_SIZE);
//		Profile updateProfile = createDefaultUpdateProfile(PROFILE_ID, NEW_HEIGHT, NEW_WEIGHT, NEW_STEP_SIZE);
//		when(mockProfileService.getProfileByRegId(REG_ID)).thenReturn(profile);
//		when(mockProfileService.updateByProfileId(PROFILE_ID, NEW_WEIGHT, NEW_HEIGHT, NEW_STEP_SIZE)).thenReturn(true);
//		ModelAndView actual = profileController.updateProfile(updateProfile, bindingResult, request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void displayProfile_NoProfileFound_FailToDisplay() {
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		when(mockProfileService.getProfileByRegId(REG_ID)).thenReturn(null);
//		ModelAndView actual = profileController.displayProfile(request, response);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	private Profile createDefaultProfile(int profileId, LocalDate date, int genId, float height, float weight,
//			int stepSize) {
//		Profile newProfile = new Profile();
//		newProfile.setGenderInd(genId);
//		newProfile.setProfileId(profileId);
//		newProfile.setBirthDate(date);
//		newProfile.setHeight(height);
//		newProfile.setWeight(weight);
//		newProfile.setStepSize(stepSize);
//		return newProfile;
//	}
//
//	private Profile createDefaultUpdateProfile(int profileId, float height, float weight, int stepSize) {
//		Profile updateProfile = new Profile();
//		updateProfile.setProfileId(profileId);
//		updateProfile.setHeight(height);
//		updateProfile.setWeight(weight);
//		updateProfile.setStepSize(stepSize);
//		return updateProfile;
//	}
//}
