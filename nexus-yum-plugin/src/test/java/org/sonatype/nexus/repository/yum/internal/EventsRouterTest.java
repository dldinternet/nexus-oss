/**
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2012 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.yum.internal;

import static org.sonatype.nexus.repository.yum.internal.utils.RepositoryTestUtils.createItem;
import static org.sonatype.nexus.repository.yum.internal.utils.RepositoryTestUtils.createRepository;

import javax.inject.Inject;

import org.junit.Test;
import org.sonatype.nexus.proxy.events.RepositoryItemEventStoreCreate;
import org.sonatype.nexus.proxy.maven.MavenRepository;
import org.sonatype.nexus.proxy.repository.Repository;
import org.sonatype.nexus.repository.yum.YumRegistry;
import org.sonatype.nexus.repository.yum.internal.utils.AbstractRepositoryTester;

public class EventsRouterTest
    extends AbstractRepositoryTester
{

    @Inject
    private EventsRouter eventsRouter;

    @Inject
    private YumRegistry repositoryRegistry;

    @Test
    public void shouldNotCreateRepo()
    {
        Repository repo = createRepository( true );
        repositoryRegistry.unregister( repo.getId() );
        eventsRouter.on( new RepositoryItemEventStoreCreate( repo, createItem( "VERSION", "test-source.jar" ) ) );
    }

    @Test
    public void shouldNotCreateRepoForPom()
    {
        MavenRepository repo = createRepository( true );
        repositoryRegistry.register( repo );
        eventsRouter.on( new RepositoryItemEventStoreCreate( repo, createItem( "VERSION", "test.pom" ) ) );
    }

    @Test
    public void shouldCreateRepoForRpm()
    {
        MavenRepository repo = createRepository( true );
        repositoryRegistry.register( repo );
        eventsRouter.on( new RepositoryItemEventStoreCreate( repo, createItem( "VERSION", "test.rpm" ) ) );
    }

}
