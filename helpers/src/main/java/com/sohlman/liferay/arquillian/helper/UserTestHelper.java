package com.sohlman.liferay.arquillian.helper;

import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


/**
 * @author Sampsa Sohlman
 */
@Component(immediate=true, service=UserTestHelper.class)
public class UserTestHelper {
	public User getAdminUser() throws PortalException, SystemException {
		return getAdminUser(_companyTestHelper.getCompanyId());
	}
	
	public User getAdminUser(long companyId) throws PortalException, SystemException {
		Role role = _roleLocalService.getRole(companyId,
				RoleConstants.ADMINISTRATOR);

		List<User> users = _userLocalService.getRoleUsers(role.getRoleId(), 0, 2);

		if (!users.isEmpty()) {
			return users.get(0);
		}
		throw new NoSuchUserException();
	}
	
	@Reference(unbind="-")
	public void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}	

	@Reference(unbind="-")
	public void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}
	
	@Reference(unbind="-")
	public void setCompanyTestHelper(CompanyTestHelper companyTestHelper) {
		_companyTestHelper = companyTestHelper;
	}
	
	private CompanyTestHelper _companyTestHelper;
	
	private UserLocalService _userLocalService;
	
	private RoleLocalService _roleLocalService;
}