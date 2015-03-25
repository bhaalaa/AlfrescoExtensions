/*
 * Copyright 2015 Alfresco Software, Ltd.  All rights reserved.
 *
 * License rights for this program may be obtained from Alfresco Software, Ltd. 
 * pursuant to a written agreement and any use of this program without such an 
 * agreement is prohibited. 
 */
package org.alfresco.entities;

import java.io.IOException;
import java.util.Collection;

import org.alfresco.events.node.types.NodeEvent;
import org.alfresco.httpclient.AuthenticationException;
import org.alfresco.services.nlp.Entity;

/**
 * 
 * @author sglover
 *
 */
public interface EntitiesService
{
	Collection<Entity<String>> getNames(long nodeId, long nodeVersion);
	void getEntities(NodeEvent nodeEvent) throws AuthenticationException, IOException;
}
