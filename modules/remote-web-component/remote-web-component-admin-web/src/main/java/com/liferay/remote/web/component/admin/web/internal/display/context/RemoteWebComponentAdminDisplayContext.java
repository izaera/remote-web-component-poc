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

package com.liferay.remote.web.component.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
//import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.remote.web.component.service.RemoteWebComponentEntryLocalService;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteWebComponentAdminDisplayContext {

	public RemoteWebComponentAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		RemoteWebComponentEntryLocalService
			remoteWebComponentEntryLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CreationMenu getCreationMenu() {
		// TODO
//		return CreationMenuBuilder.addDropdownItem(
//			dropdownItem -> {
//				PortletURL addRemoteWebComponentEntryURL =
//					_renderResponse.createRenderURL();
//
//				addRemoteWebComponentEntryURL.setParameter(
//					"mvcRenderCommandName", "/edit_remote_web_component_entry");
//				addRemoteWebComponentEntryURL.setParameter(
//					"redirect", _getRedirect());
//
//				dropdownItem.setHref(addRemoteWebComponentEntryURL);
//
//				dropdownItem.setLabel(_getLabel("add-remote-web-component"));
//			}
//		).build();
		return null;
	}

	public PortletURL getCurrentPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_renderRequest);
	}

	private String _getLabel(String label) {
		return LanguageUtil.get(_getHttpServletRequest(), label);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}