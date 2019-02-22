package com.ramoncasares.paginatedtable.common.util;

import com.ramoncasares.paginatedtable.common.util.usertyperequest.AdminUserTypeRequest;
import com.ramoncasares.paginatedtable.common.util.usertyperequest.UnauthenticatedUserTypeRequest;

/**
 * A factory for creating UserTypeRequest objects.
 */
public class UserTypeRequestFactory {

	/**
	 * Instantiates a new user type request factory.
	 */
	private UserTypeRequestFactory() {
	}

	/**
	 * Gets the http request.
	 *
	 * @param userType the user type
	 * @return the http request
	 */
	public static IUserTypeRequest getHttpRequest(UserTypeEnum userType) {
		IUserTypeRequest userTypeRequest = null;
		switch (userType) {
		case ADMIN:
			userTypeRequest = AdminUserTypeRequest.getUserTypeRequestInstance();
			break;
		case ANONIMO:
			userTypeRequest = UnauthenticatedUserTypeRequest.getUserTypeRequestInstance();
			break;
		default:
			break;
		} 
		return userTypeRequest;
	}
	


}
