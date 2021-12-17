//package com.devcenter.fitness.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.devcenter.fitness.exception.InvalidSelectException;
//import com.devcenter.fitness.helper.ActivityTypes;
//import com.devcenter.fitness.model.Activity;
//import com.devcenter.fitness.model.ActivityType;
//import com.devcenter.fitness.service.ActivityService;
//
///**
// * This is the test for activity controller layer where the service layer has
// * been mocked.
// * 
// * @author Tonmoy
// * 
// * 
// */
//public class ActivityControllerTest {
//	private static final int REG_ID = 107;
//	private static final int ACTIVITY_ID = 807;
//	private static final String TITLE = "Running on Monday.";
//	private static final String INVALID_TITLE = "";
//	private static final String ACTIVITY_SELECT = "Select";
//	private static final String ACTIVITY_TYPE = "Running";
//	private static final float DISTANCE = 2.4f;
//	private static final float INVALID_DISTANCE = -2.2f;
//	private static final float DURATION_HR = 1.2f;
//	private static final float ENERGY_EXPENDED = 500f;
//	private static final LocalDate ACTIVITY_DATE = LocalDate.of(2021, 10, 18);
//	private static final LocalDate INVALID_DATE = LocalDate.of(2021, 12, 18);
//
//	Activity activity;
//	ActivityService mockActivityService;
//	ActivityController activityController;
//	ActivityTypes mockDiffActivities;
//	List<Activity> list;
//
//	@BeforeEach
//	public void setUp() {
//		activity = new Activity();
//		list = new ArrayList<Activity>();
//		mockActivityService = mock(ActivityService.class);
//		mockDiffActivities = mock(ActivityTypes.class);
//		activityController = new ActivityController(mockActivityService, REG_ID, ACTIVITY_ID, mockDiffActivities);
//	}
//
//	@AfterEach
//	public void tearDown() {
//		activity = null;
//		list = null;
//		mockActivityService = null;
//		activityController = null;
//	}
//
//	@Test
//	public void viewSaveActivityPage_DisplayingAddActivityPage() {
//		ModelAndView expected = new ModelAndView("newActivity");
//		expected.addObject("activityList", createDefaultListOfActivities());
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockDiffActivities.activityType()).thenReturn(createDefaultListOfActivities());
//		ModelAndView actual = activityController.viewSaveActivityPage(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addActivity_InvalidInputs_FailToAddActivity() {
//		ModelAndView expected = new ModelAndView("newActivity");
//		expected.addObject("activityList", createDefaultListOfActivities());
//		expected.addObject("inputError", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_SELECT, INVALID_DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		when(mockDiffActivities.activityType()).thenReturn(createDefaultListOfActivities());
//		ModelAndView actual = activityController.addActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addActivity_InvalidActivityType_FailToAddActivity() {
//		ModelAndView expected = new ModelAndView("newActivity");
//		expected.addObject("activityList", createDefaultListOfActivities());
//		expected.addObject("selectError", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_SELECT, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		when(mockDiffActivities.activityType()).thenReturn(createDefaultListOfActivities());
//		ModelAndView actual = activityController.addActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addActivity_InvalidDate_FailToAddActivity() {
//		ModelAndView expected = new ModelAndView("newActivity");
//		expected.addObject("activityList", createDefaultListOfActivities());
//		expected.addObject("dateError", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_TYPE, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, INVALID_DATE);
//		when(mockDiffActivities.activityType()).thenReturn(createDefaultListOfActivities());
//		ModelAndView actual = activityController.addActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void addActivity_ActivityAddSuccessful() {
//		ModelAndView expected = new ModelAndView("newActivity");
//		expected.addObject("activityList", createDefaultListOfActivities());
//		expected.addObject("submitted", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_TYPE, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		when(mockDiffActivities.activityType()).thenReturn(createDefaultListOfActivities());
//		when(mockActivityService.saveActivity(activity)).thenReturn(true);
//		ModelAndView actual = activityController.addActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void viewUpdateActivityPage_DisplayingUpdateActivityPage() {
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_TYPE, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		ModelAndView expected = new ModelAndView("editActivity");
//		expected.addObject("uActivity", activity);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockActivityService.getActivityByActivityId(ACTIVITY_ID)).thenReturn(activity);
//		ModelAndView actual = activityController.viewUpdateActivityPage(ACTIVITY_ID, activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void updateActivity_InvalidInputs_FailToUpdateActivity() throws InvalidSelectException {
//		activity = createDefaultUpdateActivity(TITLE, INVALID_DISTANCE, DURATION_HR, ENERGY_EXPENDED, ACTIVITY_DATE);
//		ModelAndView expected = new ModelAndView("editActivity");
//		expected.addObject("inputError", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		ModelAndView actual = activityController.updateActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void updateActivity_InvalidDate_FailToUpdateActivity() throws InvalidSelectException {
//		activity = createDefaultUpdateActivity(TITLE, DISTANCE, DURATION_HR, ENERGY_EXPENDED, INVALID_DATE);
//		ModelAndView expected = new ModelAndView("editActivity");
//		expected.addObject("dateError", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		ModelAndView actual = activityController.updateActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void updateActivity_ActivityUpdateSuccessful() throws InvalidSelectException {
//		ModelAndView expected = new ModelAndView("editActivity");
//		expected.addObject("updated", true);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		activity = createDefaultUpdateActivity(TITLE, DISTANCE, DURATION_HR, ENERGY_EXPENDED, ACTIVITY_DATE);
//		when(mockActivityService.updateByActivityId(ACTIVITY_ID, TITLE, DISTANCE, DURATION_HR, ENERGY_EXPENDED,
//				ACTIVITY_DATE)).thenReturn(true);
//		ModelAndView actual = activityController.updateActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void displayActivity_DisplayingDashboardPage_WhenNoActivitiesFound() {
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_SELECT, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockActivityService.allActivity(REG_ID)).thenReturn(list);
//		ModelAndView actual = activityController.displayActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void displayActivity_DisplayingDisplayActivityPage_WhenAllActivitiesFound() {
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_SELECT, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		list.add(activity);
//		ModelAndView expected = new ModelAndView("displayActivity");
//		expected.addObject("activityDetails", list);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockActivityService.allActivity(REG_ID)).thenReturn(list);
//		ModelAndView actual = activityController.displayActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void displayActivity_DisplayingDashboardPage_WhenNoActivitiesOfSpecificActivityTypeIsFound() {
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_TYPE, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockActivityService.specificActivity(REG_ID, ACTIVITY_TYPE)).thenReturn(list);
//		ModelAndView actual = activityController.displayActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void displayActivity_DisplayingDisplayActivityPagePage_WhenActivitiesOfSpecificActivityTypeIsFound() {
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_TYPE, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		list.add(activity);
//		ModelAndView expected = new ModelAndView("displayActivity");
//		expected.addObject("activityDetails", list);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockActivityService.specificActivity(REG_ID, ACTIVITY_TYPE)).thenReturn(list);
//		ModelAndView actual = activityController.displayActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void searchActivity_DisplayingDashboardPage_IfTitleIsUnmatched() {
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, INVALID_TITLE, ACTIVITY_TYPE, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		ModelAndView actual = activityController.searchActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void searchActivity_DisplayingDashboardPage_IfTitleNotFoundInDb() {
//		ModelAndView expected = new ModelAndView("redirect:/user/dashboard");
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockActivityService.searchActivity(REG_ID, TITLE)).thenReturn(list);
//		ModelAndView actual = activityController.searchActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	@Test
//	public void searchActivity_DisplayingDashboardPage_IfTitleFoundInDb() {
//		activity = createDefaultActivity(REG_ID, ACTIVITY_ID, TITLE, ACTIVITY_TYPE, DISTANCE, DURATION_HR,
//				ENERGY_EXPENDED, ACTIVITY_DATE);
//		list.add(activity);
//		ModelAndView expected = new ModelAndView("searchActivity");
//		expected.addObject("searchDetails", list);
//		String expectedStr = expected.toString();
//		BindingResult bindingResult = mock(BindingResult.class);
//		when(mockActivityService.searchActivity(REG_ID, TITLE)).thenReturn(list);
//		ModelAndView actual = activityController.searchActivity(activity, bindingResult);
//		String actualStr = actual.toString();
//		assertEquals(expectedStr, actualStr);
//	}
//
//	private Activity createDefaultActivity(int regId, int activityId, String title, String activityType, float distance,
//			float durationHr, float energyExpended, LocalDate activityDate) {
//		Activity newActivity = new Activity();
//		newActivity.setRegId(regId);
//		newActivity.setActivityId(activityId);
//		newActivity.setTitle(title);
//		newActivity.setActivityType(activityType);
//		newActivity.setDistance(distance);
//		newActivity.setDurationHr(durationHr);
//		newActivity.setEnergyExpended(energyExpended);
//		newActivity.setActivityDate(activityDate);
//		return newActivity;
//	}
//
//	private Activity createDefaultUpdateActivity(String title, float distance, float durationHr, float energyExpended,
//			LocalDate activityDate) {
//		Activity updateActivity = new Activity();
//		updateActivity.setTitle(title);
//		updateActivity.setDistance(distance);
//		updateActivity.setDurationHr(durationHr);
//		updateActivity.setEnergyExpended(energyExpended);
//		updateActivity.setActivityDate(activityDate);
//		return updateActivity;
//	}
//
//	private List<ActivityType> createDefaultListOfActivities() {
//		List<ActivityType> listOfActivities = Arrays.asList(ActivityType.Select, ActivityType.Running,
//				ActivityType.Walking, ActivityType.Cycling, ActivityType.Swimming);
//		return listOfActivities;
//	}
//
//}
