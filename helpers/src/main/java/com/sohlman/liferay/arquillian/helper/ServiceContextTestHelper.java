/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.sohlman.liferay.arquillian.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


/**
 * @author Sampsa Sohlman
 */
@Component(immediate=true, service=ServiceContextTestHelper.class)
public class ServiceContextTestHelper {

	public ServiceContext getServiceContext(Group group, long userId) {
		return getServiceContext(group.getCompanyId(), group.getGroupId(),
				userId);
	}

	public ServiceContext getServiceContext(long groupId)
			throws PortalException, SystemException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = _userTestHelper.getAdminUser(group.getCompanyId());

		return getServiceContext(group, user.getUserId());
	}

	public ServiceContext getServiceContext(long groupId, long userId)
			throws PortalException, SystemException {
		Group group = _groupLocalService.getGroup(groupId);

		return getServiceContext(group.getCompanyId(), group.getGroupId(),
				userId);
	}

	public static ServiceContext getServiceContext(long companyId,
			long groupId, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	@Reference(unbind="-")
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind="-")
	public void setUserTestHelper(UserTestHelper userTestHelper) {
		_userTestHelper = userTestHelper;
	}


	private GroupLocalService _groupLocalService;
	
	private UserTestHelper _userTestHelper;
}