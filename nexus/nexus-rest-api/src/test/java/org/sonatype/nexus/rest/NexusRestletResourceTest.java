/**
 * Copyright (c) 2008-2011 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions
 *
 * This program is free software: you can redistribute it and/or modify it only under the terms of the GNU Affero General
 * Public License Version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License Version 3
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License Version 3 along with this program.  If not, see
 * http://www.gnu.org/licenses.
 *
 * Sonatype Nexus (TM) Open Source Version is available from Sonatype, Inc. Sonatype and Sonatype Nexus are trademarks of
 * Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation. M2Eclipse is a trademark of the Eclipse Foundation.
 * All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.rest;

import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;
import org.sonatype.plexus.rest.resource.PlexusResource;

/**
 *
 * @author plynch
 */
@RunWith(MockitoJUnitRunner.class)
public class NexusRestletResourceTest {

    @Mock
    private Request request;
    @Mock
    private Response response;
    @Mock
    private Context context;
    @Mock
    private PlexusResource plexusResource;
    @Mock
    private Variant variant;
    private NexusRestletResource instance;

    @Before
    public void setup() {
        // prevent NPE only caused by mocking
        when( context.getAttributes() ).thenReturn( new ConcurrentHashMap<String, Object>() );
        this.instance = spy( new NexusRestletResource( context, request, response, plexusResource ) );
    }

    @Test
    public void representResourceExceptionWithStatusNull() throws ResourceException {

        // possible that status is null in ResourceException, make sure our status checking does not throw NPE checking status       
        // make the call to super.represent throw ResourceException
        // fyi there is no way to directly mock a super call, so we have to mock the underlying bits
        ResourceException expected = new ResourceException( ( Status ) null, new Throwable() );
        doThrow( expected ).when( plexusResource ).get( context, request, response, variant );

        try {
            instance.represent( variant );
            fail("ResourceException expected.");
        } catch ( ResourceException actual ) {
            assertThat( actual, equalTo( expected ) );
        }

        // verify underlying bits - this verifies our assumption that the super implementation calls plexuResource.get() 
        // to protect against changes in super hiding a change in test prepare
        verify( plexusResource ).get( context, request, response, variant );
        //verify no apr since we don't have a status code
        verify( instance ).handleError( expected );
    }

    @Test
    public void representResourceExceptionWithStatus5xx() throws ResourceException {
        // brute force 5xx errors 
        for ( int i = 500; i < 600; i++ ) {
            ResourceException expected = new ResourceException( i );
            doThrow( expected ).when( plexusResource ).get( context, request, response, variant );

            try {
                instance.represent( variant );
                fail("ResourceException expected.");
            } catch ( ResourceException actual ) {
                assertThat( actual, equalTo( expected ) );
            }

            // verify when APR is called
            if ( 503 == i ) {
                verify( instance, never() ).handleError( expected );
            } else {
                verify( instance ).handleError( expected );
            }


        }


    }
}
