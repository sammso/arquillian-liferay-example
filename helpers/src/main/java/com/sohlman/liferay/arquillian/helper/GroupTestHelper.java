package com.sohlman.liferay.arquillian.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(immediate=true, service=GroupTestHelper.class)
public class GroupTestHelper {	
	public Group getDefeaultCompanyGuestGroup() throws PortalException, SystemException {
		return getGuestGroup(_companyTestHelper.getCompanyId());
	}
	
	public long getDefeaultCompanyGuestGroupId() throws PortalException, SystemException {
		return getDefeaultCompanyGuestGroup().getGroupId();
	}

	public Group getGuestGroup(long companyId) throws PortalException, SystemException {
		return _groupLocalService.getGroup(companyId, GroupConstants.GUEST);
	}	
	
	public long getGuestGroupId(long companyId) throws PortalException, SystemException {
		return getGuestGroup(companyId).getGroupId();
	}

	@Reference(unbind="-")
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}
	
	@Reference(unbind="-")
	public void setCompanyTestHelper(CompanyTestHelper companyTestHelper) {
		_companyTestHelper = companyTestHelper;
	}
	
	private CompanyTestHelper _companyTestHelper;	

	private GroupLocalService _groupLocalService;
	
	private static Log _log = LogFactoryUtil.getLog(GroupTestHelper.class);
}