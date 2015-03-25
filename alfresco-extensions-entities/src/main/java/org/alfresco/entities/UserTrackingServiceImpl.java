/*
 * Copyright 2015 Alfresco Software, Ltd.  All rights reserved.
 *
 * License rights for this program may be obtained from Alfresco Software, Ltd. 
 * pursuant to a written agreement and any use of this program without such an 
 * agreement is prohibited. 
 */
package org.alfresco.entities;

import org.alfresco.events.node.types.NodeContentGetEvent;

/**
 * 
 * @author sglover
 *
 */
public class UserTrackingServiceImpl implements UserTrackingService
{
	private UserTrackingDAO userTrackingDAO;

	public void setUserTrackingDAO(UserTrackingDAO userTrackingDAO)
	{
		this.userTrackingDAO = userTrackingDAO;
	}


	@Override
	public void handleContentGet(NodeContentGetEvent event)
	{
		String username = event.getUsername();
		long nodeInternalId = event.getNodeInternalId();
		long nodeVersion = event.getNodeVersion();
		long eventTimestamp = event.getTimestamp();
		userTrackingDAO.addUserNodeView(nodeInternalId, nodeVersion, username, eventTimestamp);
	}
}
