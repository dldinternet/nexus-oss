/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-2015 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.wonderland.internal;

import javax.inject.Named;

import org.sonatype.nexus.guice.FilterChainModule;
import org.sonatype.nexus.plugins.siesta.SiestaModule;
import org.sonatype.nexus.wonderland.rest.SessionResource;

import com.google.inject.AbstractModule;

/**
 * Wonderland Guice module.
 *
 * @since 3.0
 */
@Named
public class WonderlandModule
  extends AbstractModule
{
  // TODO: How do we configure the CookieFilter (servlet-filter)?

  @Override
  protected void configure() {
    install(new FilterChainModule()
    {
      @Override
      protected void configure() {
        // Override the default Shiro filters for the session resource, we want session creation here
        addFilterChain(SiestaModule.MOUNT_POINT + SessionResource.RESOURCE_URI, "authcBasic");
      }
    });
  }
}
