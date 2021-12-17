package com.example.session.helper;

import java.time.LocalDate;
import java.time.Period;

import com.example.session.exception.AgeInputException;
import com.example.session.exception.InvalidDateException;
import com.example.session.exception.InvalidInputException;
import com.example.session.exception.InvalidSelectException;
import com.example.session.model.Activity;
import com.example.session.model.ActivityType;
import com.example.session.model.Profile;

public class Validation {

	/**
	 * Checks the validation of the input given by the user.
	 * 
	 * @param profile Object of the user's profile.
	 * @throws InvalidInputException If the inputs are not in correct format.
	 * @throws AgeInputException     If the age is invalid.
	 */
	public static void inputCheckForProfile(Profile profile, boolean newProfile)
			throws InvalidInputException, AgeInputException {
		if (newProfile == true) {
			if (profile.getWeight() < 10 || profile.getWeight() > 150 || profile.getHeight() < 5
					|| profile.getHeight() > 10 || profile.getStepSize() < 15 || profile.getStepSize() > 30
					|| profile.getBirthDate() == null)
				throw new InvalidInputException();
			LocalDate getDate = profile.getBirthDate();
			LocalDate currentDate = LocalDate.now();
			Period period = Period.between(getDate, currentDate);
			int age = period.getYears();
			if (age < 18 || age > 100)
				throw new AgeInputException();
		} else {
			if (profile.getWeight() < 10 || profile.getWeight() > 150 || profile.getHeight() < 5
					|| profile.getHeight() > 10 || profile.getStepSize() < 15 || profile.getStepSize() > 30)
				throw new InvalidInputException();
		}

	}

	/**
	 * Checks the validation of the input given by the user.
	 * 
	 * @param activity Object of the user's activity.
	 * @throws InvalidInputException  If the inputs are not in correct format.
	 * @throws InvalidSelectException If the activity type selected is invalid.
	 * @throws InvalidDateException   If the selected date is invalid.
	 */
	public static void inputCheckForActivity(Activity activity, boolean newActivity)
			throws InvalidInputException, InvalidSelectException, InvalidDateException {
		if (newActivity == true) {
			if (activity.getActivityDate() == null || activity.getDistance() < 0 || activity.getDurationHr() < 0
					|| activity.getEnergyExpended() < 0 || activity.getTitle().equals(null)) {
				throw new InvalidInputException();
			}
			if (activity.getActivityType().equals((ActivityType.Select).toString())) {
				throw new InvalidSelectException();
			}
			LocalDate getDate = activity.getActivityDate();
			LocalDate currentDate1 = LocalDate.now();
			Period period = Period.between(getDate, currentDate1);
			float checkYears = period.getYears();
			float checkMonths = period.getMonths();
			float checkDays = period.getDays();
			if (checkDays < 0 || checkMonths < 0 || checkYears < 0) {
				throw new InvalidDateException();
			}
		} else {
			if (activity.getActivityDate() == null || activity.getDistance() < 0 || activity.getDurationHr() < 0
					|| activity.getEnergyExpended() < 0 || activity.getTitle().equals(null)) {
				throw new InvalidInputException();
			}
			LocalDate getDate = activity.getActivityDate();
			LocalDate currentDate1 = LocalDate.now();
			Period period = Period.between(getDate, currentDate1);
			float checkYears = period.getYears();
			float checkMonths = period.getMonths();
			float checkDays = period.getDays();
			if (checkDays < 0 || checkMonths < 0 || checkYears < 0) {
				throw new InvalidDateException();
			}
		}
	}

}
