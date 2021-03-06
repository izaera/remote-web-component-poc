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

package com.liferay.remote.web.component.service.base;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;
import com.liferay.remote.web.component.service.RemoteWebComponentEntryLocalService;
import com.liferay.remote.web.component.service.persistence.RemoteWebComponentEntryPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the remote web component entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.remote.web.component.service.impl.RemoteWebComponentEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.remote.web.component.service.impl.RemoteWebComponentEntryLocalServiceImpl
 * @generated
 */
public abstract class RemoteWebComponentEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, IdentifiableOSGiService,
			   RemoteWebComponentEntryLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>RemoteWebComponentEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.remote.web.component.service.RemoteWebComponentEntryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the remote web component entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteWebComponentEntry addRemoteWebComponentEntry(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		remoteWebComponentEntry.setNew(true);

		return remoteWebComponentEntryPersistence.update(
			remoteWebComponentEntry);
	}

	/**
	 * Creates a new remote web component entry with the primary key. Does not add the remote web component entry to the database.
	 *
	 * @param remoteWebComponentEntryId the primary key for the new remote web component entry
	 * @return the new remote web component entry
	 */
	@Override
	@Transactional(enabled = false)
	public RemoteWebComponentEntry createRemoteWebComponentEntry(
		long remoteWebComponentEntryId) {

		return remoteWebComponentEntryPersistence.create(
			remoteWebComponentEntryId);
	}

	/**
	 * Deletes the remote web component entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry that was removed
	 * @throws PortalException if a remote web component entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public RemoteWebComponentEntry deleteRemoteWebComponentEntry(
			long remoteWebComponentEntryId)
		throws PortalException {

		return remoteWebComponentEntryPersistence.remove(
			remoteWebComponentEntryId);
	}

	/**
	 * Deletes the remote web component entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public RemoteWebComponentEntry deleteRemoteWebComponentEntry(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		return remoteWebComponentEntryPersistence.remove(
			remoteWebComponentEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			RemoteWebComponentEntry.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return remoteWebComponentEntryPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return remoteWebComponentEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return remoteWebComponentEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return remoteWebComponentEntryPersistence.countWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return remoteWebComponentEntryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public RemoteWebComponentEntry fetchRemoteWebComponentEntry(
		long remoteWebComponentEntryId) {

		return remoteWebComponentEntryPersistence.fetchByPrimaryKey(
			remoteWebComponentEntryId);
	}

	/**
	 * Returns the remote web component entry with the matching UUID and company.
	 *
	 * @param uuid the remote web component entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry
		fetchRemoteWebComponentEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return remoteWebComponentEntryPersistence.fetchByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns the remote web component entry with the primary key.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry
	 * @throws PortalException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public RemoteWebComponentEntry getRemoteWebComponentEntry(
			long remoteWebComponentEntryId)
		throws PortalException {

		return remoteWebComponentEntryPersistence.findByPrimaryKey(
			remoteWebComponentEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			remoteWebComponentEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(RemoteWebComponentEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"remoteWebComponentEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			remoteWebComponentEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			RemoteWebComponentEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"remoteWebComponentEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			remoteWebComponentEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(RemoteWebComponentEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"remoteWebComponentEntryId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<RemoteWebComponentEntry>() {

				@Override
				public void performAction(
						RemoteWebComponentEntry remoteWebComponentEntry)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, remoteWebComponentEntry);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					RemoteWebComponentEntry.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return remoteWebComponentEntryLocalService.
			deleteRemoteWebComponentEntry(
				(RemoteWebComponentEntry)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return remoteWebComponentEntryPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns the remote web component entry with the matching UUID and company.
	 *
	 * @param uuid the remote web component entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web component entry
	 * @throws PortalException if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry getRemoteWebComponentEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return remoteWebComponentEntryPersistence.findByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns a range of all the remote web component entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @return the range of remote web component entries
	 */
	@Override
	public List<RemoteWebComponentEntry> getRemoteWebComponentEntries(
		int start, int end) {

		return remoteWebComponentEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of remote web component entries.
	 *
	 * @return the number of remote web component entries
	 */
	@Override
	public int getRemoteWebComponentEntriesCount() {
		return remoteWebComponentEntryPersistence.countAll();
	}

	/**
	 * Updates the remote web component entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteWebComponentEntry updateRemoteWebComponentEntry(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		return remoteWebComponentEntryPersistence.update(
			remoteWebComponentEntry);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			RemoteWebComponentEntryLocalService.class,
			IdentifiableOSGiService.class, PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		remoteWebComponentEntryLocalService =
			(RemoteWebComponentEntryLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return RemoteWebComponentEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return RemoteWebComponentEntry.class;
	}

	protected String getModelClassName() {
		return RemoteWebComponentEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				remoteWebComponentEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected RemoteWebComponentEntryLocalService
		remoteWebComponentEntryLocalService;

	@Reference
	protected RemoteWebComponentEntryPersistence
		remoteWebComponentEntryPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

}