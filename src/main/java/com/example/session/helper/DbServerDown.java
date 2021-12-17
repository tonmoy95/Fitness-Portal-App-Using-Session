package com.example.session.helper;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.session.exception.DbServerDownException;

/**
 * The database server down exception is handled.
 * 
 * @author Tonmoy
 *
 */
public class DbServerDown {

	/**
	 * Handles the database server.
	 * 
	 * @param exception Contains the error that needs to be handled.
	 * @return This returns the error page.
	 */
	@ExceptionHandler(value = DbServerDownException.class)
	public ModelAndView exception(DbServerDownException exception) {
		ModelAndView mav;
		mav = new ModelAndView("error");
		return mav;
	}
}
