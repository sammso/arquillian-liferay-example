package com.sohlman.liferay.arquillian.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.sohlman.liferay.arquillian.util.ServiceTestUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(immediate=true, service=CompanyTestHelper.class)
public class CompanyTestHelper {
	public Company getCompany() throws PortalException, SystemException {
		String companyWebId = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID);
		return _companyLocalService.getCompanyByWebId(companyWebId);
	}
	
	public long getCompanyId() throws PortalException, SystemException {
		return getCompany().getCompanyId();
	}
	
	public Company addCompany() throws Exception {
		return addCompany(ServiceTestUtil.randomString());
	}

	public Company addCompany(String name) throws Exception {
		String virtualHostname = name + "." +  ServiceTestUtil.randomString(3);

		return _companyLocalService.addCompany(
			name, virtualHostname, virtualHostname, false, 0, true);
	}
	
	@Reference(unbind="-")
	public void setCompanyLocalService(CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
	}
	
	private CompanyLocalService _companyLocalService;
}
