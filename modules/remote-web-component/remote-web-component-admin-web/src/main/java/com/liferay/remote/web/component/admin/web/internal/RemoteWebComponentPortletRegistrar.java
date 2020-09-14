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

package com.liferay.remote.web.component.admin.web.internal;

import aQute.bnd.annotation.metatype.Configurable;
import com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration;
import com.liferay.remote.web.component.admin.web.internal.portlet.RemoteWebComponentPortlet;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	configurationPid = "com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration",
	immediate = true,
	service = RemoteWebComponentPortletRegistrar.class
)
public class RemoteWebComponentPortletRegistrar {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		System.out.println("activate");

		_bundleContext = bundleContext;
		_remoteWebComponentConfiguration =
			Configurable.createConfigurable(
				RemoteWebComponentConfiguration.class, properties);

		_updatePortlets();
	}

	@Modified
	protected void updated(Map<String, Object> properties) {
		System.out.println("updated");

		_remoteWebComponentConfiguration =
			Configurable.createConfigurable(
				RemoteWebComponentConfiguration.class, properties);

		_updatePortlets();
	}

	@Deactivate
	protected void deactivate() {
		System.out.println("deactivate");

		synchronized (_remoteWebComponentPortlets) {
			for (RemoteWebComponentPortlet remoteWebComponentPortlet :
					_remoteWebComponentPortlets.values()) {

				remoteWebComponentPortlet.unregister();
			}

			_remoteWebComponentPortlets.clear();
		}
	}

	private void _updatePortlets() {
		String components = _remoteWebComponentConfiguration.components();

		if(components == null) {
			return;
		}

		String[] entries = components.split(",");

		synchronized (_remoteWebComponentPortlets) {
			HashSet<String> keysToRemove =
				new HashSet<>(_remoteWebComponentPortlets.keySet());

			for (String entry : entries) {
				String[] entryParts = entry.split("=");

				if(entryParts.length != 2) {
					System.out.println(
						"ignored bad web component configuration entry: " +
							entry);

					continue;
				}

				String tagName = entryParts[0].trim();

				keysToRemove.remove(tagName);

				RemoteWebComponentPortlet remoteWebComponentPortlet =
					_remoteWebComponentPortlets.get(tagName);

				if(remoteWebComponentPortlet != null) {
					remoteWebComponentPortlet.unregister();
				}

				String url = entryParts[1].trim();

				remoteWebComponentPortlet =
					new RemoteWebComponentPortlet(tagName, tagName, url);

				remoteWebComponentPortlet.register(_bundleContext);

				_remoteWebComponentPortlets.put(
					tagName, remoteWebComponentPortlet);
			}

			for (String tagName : keysToRemove) {
				RemoteWebComponentPortlet remoteWebComponentPortlet =
					_remoteWebComponentPortlets.remove(tagName);

				remoteWebComponentPortlet.unregister();
			}
		}
	}

	private BundleContext _bundleContext;
	private volatile RemoteWebComponentConfiguration
		_remoteWebComponentConfiguration;
	private final ConcurrentMap<String, RemoteWebComponentPortlet>
		_remoteWebComponentPortlets = new ConcurrentHashMap<>();

}