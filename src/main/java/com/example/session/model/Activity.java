package com.example.session.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Represents the activity that an user participates.
 * 
 * @author Tonmoy
 *
 */
@Entity
@Table(name = "activity")
public class Activity {

	@Id
	private int activityId;

	private int regId;

	@NotNull(message = "Please fill the title")
	@Size(min = 5, message = "{Size.Activity.TitleMin}")
	@Size(max = 150, message = "{Size.Activity.TitleMax}")
	private String title;

	@NotNull
	private String activityType;

	private float distance;

	private float durationHr;

	private float energyExpended;

	@NotNull(message = "Please fill valid date")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate activityDate;

	public Activity() {

	}

	/**
	 * 
	 * @param activityId     It is the user's activity id.
	 * @param regId          It is the user's registration id.
	 * @param title          It is the title given by the user.
	 * @param activityType   It is the type of activity.
	 * @param distance       It is the distance travelled by the user.
	 * @param durationHr     It is the duration taken by the user.
	 * @param energyExpended It is the energy burnt by the user.
	 * @param activityDate   It is the date of the performed activity.
	 */
	public Activity(int activityId, int regId, String title, String activityType, float distance, float durationHr,
			float energyExpended, LocalDate activityDate) {
		this.activityId = activityId;
		this.regId = regId;
		this.title = title;
		this.activityType = activityType;
		this.distance = distance;
		this.durationHr = durationHr;
		this.energyExpended = energyExpended;
		this.activityDate = activityDate;
	}

	/**
	 * Gets the user's activity id.
	 * 
	 * @return This returns the user's activity id.
	 */
	public int getActivityId() {
		return activityId;
	}

	/**
	 * Sets the user's actvitiy id. It is also the primary key.
	 * 
	 * @param activityId It is the user's activity id.
	 */
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	/**
	 * Gets the user's registration id.
	 * 
	 * @return This returns the user's registration id.
	 */
	public int getRegId() {
		return regId;
	}

	/**
	 * Sets the user's registration id. Cannot be null.
	 * 
	 * @param regId It is the user's registration id.
	 */
	public void setRegId(int regId) {
		this.regId = regId;
	}

	/**
	 * Gets the title from the user.
	 * 
	 * @return This returns the title given by the user.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title. Minimum length required is 5. Cannot be null.
	 * 
	 * @param title It is the title given by the user.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the activity type from the user.
	 * 
	 * @return This returns the type of activity.
	 */
	public String getActivityType() {
		return activityType;
	}

	/**
	 * Sets the activity type. Cannot be null.
	 * 
	 * @param activityType It is the type of activity.
	 */
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * Gets the distance travelled by the user.
	 * 
	 * @return This returns the distance travelled by the user.
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Sets the distance travelled by the user. Cannot be null.
	 * 
	 * @param distance It is the distance travelled by the user.
	 */
	public void setDistance(float distance) {
		this.distance = distance;
	}

	/**
	 * Gets the duration taken by the user.
	 * 
	 * @return This returns the duration taken by the user.
	 */
	public float getDurationHr() {
		return durationHr;
	}

	/**
	 * Sets the duration taken by the user. Cannot be null.
	 * 
	 * @param durationHr This returns the duration taken by the user.
	 */
	public void setDurationHr(float durationHr) {
		this.durationHr = durationHr;
	}

	/**
	 * Gets the energy burnt of the user.
	 * 
	 * @return This returns the energy burnt of the user.
	 */
	public float getEnergyExpended() {
		return energyExpended;
	}

	/**
	 * Sets the energy burnt of the user. Cannot be null.
	 * 
	 * @param energyExpended It is the energy burnt of the user.
	 */
	public void setEnergyExpended(float energyExpended) {
		this.energyExpended = energyExpended;
	}

	/**
	 * Gets the activity performed date from the user.
	 * 
	 * @return This returns the activity performed date from the user.
	 */
	public LocalDate getActivityDate() {
		return activityDate;
	}

	/**
	 * Sets the activity performed date from the user. Cannot be null.
	 * 
	 * @param activityDate It is the activity performed date from the user.
	 */
	public void setActivityDate(LocalDate activityDate) {
		this.activityDate = activityDate;
	}

}
