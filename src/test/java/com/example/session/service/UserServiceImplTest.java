package com.example.session.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.security.NoSuchProviderException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.session.helper.HashingHelper;
import com.example.session.model.User;
import com.example.session.repository.UserRepository;

/**
 * This is the test for service layer where the repository layer has been
 * mocked.
 * 
 * @author Tonmoy
 * 
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	private static final int regId = 107;
	private static final String emailId = "Java@project.com";
	private static final String password = "Spring";
	private static final String firstName = "Java";
	private static final String lastName = "Project";

	private HashingHelper hashingHelper = new HashingHelper();

	@Mock
	private UserRepository mockUserRepository;

	UserService userService;

	@BeforeEach
	void initUseCase() {
		userService = new UserServiceImpl(mockUserRepository);
	}

	@AfterEach
	public void tearDown() {
		userService = null;
	}

	@Test
	public void saveUser_RegisteringNewUser() throws NoSuchProviderException {
		User user = createDefaultUser(regId, firstName, lastName, emailId, password);
		when(mockUserRepository.save(user)).thenReturn(user);
		boolean actualUser = userService.saveUser(user);
		assertThat(actualUser == true);

	}

	@Test
	public void getUserByEmail_UserIsRetrievedbyEmail() throws NoSuchProviderException {
		User user = createDefaultUser(regId, firstName, lastName, emailId, password);
		when(mockUserRepository.getUserByEmail(emailId)).thenReturn(user);
		User actualUser = userService.getUserByEmail(emailId);
		assertEquals(emailId, actualUser.getEmailId());

	}

	private User createDefaultUser(int regId, String firstName, String lastName, String emailId, String password)
			throws NoSuchProviderException {
		byte[] byteSalt = null;
		byteSalt = hashingHelper.getSalt();
		String strPassword = hashingHelper.getSecurePassword(password, byteSalt);
		String strSalt = hashingHelper.toHex(byteSalt);
		final User user = new User(regId, firstName, lastName, emailId, strPassword, strSalt);
		return user;
	}
}
