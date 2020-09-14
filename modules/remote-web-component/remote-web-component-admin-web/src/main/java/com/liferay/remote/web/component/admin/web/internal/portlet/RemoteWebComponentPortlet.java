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

package com.liferay.remote.web.component.admin.web.internal.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteWebComponentPortlet extends MVCPortlet {

	public RemoteWebComponentPortlet(String name, String tagName, String url) {
		System.out.println("constructed: " + tagName);

		_name = name;
		_tagName = tagName;
		_url = url;
	}

	public synchronized void register(BundleContext bundleContext) {
		if (_serviceRegistration != null) {
			throw new IllegalStateException("Portlet is already registered");
		}

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
				"com.liferay.portlet.css-class-wrapper",
				"portlet-remote-web-component");
		properties.put(
				"com.liferay.portlet.display-category", "category.sample");
		properties.put(
				"com.liferay.portlet.header-portlet-css", "/display/css/main.css");
		properties.put("com.liferay.portlet.instanceable", true);
		properties.put("javax.portlet.name", _getPortletName());
		properties.put("javax.portlet.security-role-ref", "power-user,user");
		properties.put(
				"javax.portlet.resource-bundle", _getResourceBundleName());

		_serviceRegistration = bundleContext.registerService(
			Portlet.class, this, properties);

		properties = new Hashtable<>();

		properties.put("resource.bundle.base.name", _getResourceBundleName());
		properties.put(
			"servlet.context.name", "remote-web-component-admin-web");

		_resourceBundleLoaderServiceRegistration =
			bundleContext.registerService(
				ResourceBundleLoader.class,
				locale -> _getResourceBundle(locale), properties);

		System.out.println("registered: " + _tagName);
	}

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			printWriter.print(
				"<script src=\"" + _url + "\"></script>");

			printWriter.print(
				StringBundler.concat(
					StringPool.LESS_THAN, _name, StringPool.GREATER_THAN, "</",
					_name, StringPool.GREATER_THAN));

			printWriter.flush();
		} catch (IOException ioException) {
			_log.error("Unable to render HTML output", ioException);
		}
	}

	public synchronized void unregister() {
		if (_serviceRegistration == null) {
			throw new IllegalStateException("Portlet is not registered");
		}

		_resourceBundleLoaderServiceRegistration.unregister();
		_serviceRegistration.unregister();

		_resourceBundleLoaderServiceRegistration = null;
		_serviceRegistration = null;

		System.out.println("unregistered: " + _tagName);
	}

	private String _getPortletName() {
		return
			"remote_web_component_" + _tagName.replaceAll("[^A-Za-z0-9]", "_");
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(_labels.keySet());
			}

			@Override
			protected Object handleGetObject(String key) {
				return _labels.get(key);
			}

			private final Map<String, String> _labels = HashMapBuilder.put(
				"javax.portlet.title." + _getPortletName(), _name
			).build();

		};
	}

	private String _getResourceBundleName() {
		return _getPortletName() + ".Language";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebComponentPortlet.class);

	private final String _name;
	private ServiceRegistration<ResourceBundleLoader>
		_resourceBundleLoaderServiceRegistration;
	private ServiceRegistration<Portlet> _serviceRegistration;
	private final String _tagName;
	private final String _url;

}