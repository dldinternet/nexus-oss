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
package org.sonatype.security.realms.ldap.internal;

import java.util.SortedSet;
import java.util.TreeSet;

import org.sonatype.ldaptestsuite.LdapServer;
import org.sonatype.security.authentication.AuthenticationException;
import org.sonatype.security.realms.ldap.internal.connector.dao.LdapDAOException;
import org.sonatype.security.realms.ldap.internal.connector.dao.LdapUser;
import org.sonatype.security.realms.ldap.internal.connector.dao.NoLdapUserRolesFoundException;
import org.sonatype.security.realms.ldap.internal.connector.dao.NoSuchLdapGroupException;
import org.sonatype.security.realms.ldap.internal.connector.dao.NoSuchLdapUserException;
import org.sonatype.security.realms.ldap.internal.persist.entity.LdapConfiguration;
import org.sonatype.security.realms.ldap.internal.realms.LdapManager;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionBlackListTest
    extends LdapTestSupport
{
  @Override
  protected LdapConfiguration createLdapClientConfigurationForServer(final String name, final int order,
                                                                     final LdapServer ldapServer)
  {
    final LdapConfiguration ldapConfiguration = super.createLdapClientConfigurationForServer(name, order, ldapServer);
    ldapConfiguration.getConnection().setConnectionTimeout(1);
    ldapConfiguration.getConnection().setConnectionRetryDelay(7);
    // important: maxIncidentCount = 1
    ldapConfiguration.getConnection().setMaxIncidentsCount(1);
    return ldapConfiguration;
  }

  @Test
  public void testAuthenticate()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    Assert.assertNotNull(ldapManager.authenticateUser("brianf", "brianf123"));

    stopLdapServers();

    try {
      ldapManager.authenticateUser("brianf", "brianf123");
      Assert.fail("Expected AuthenticationException");
    }
    catch (AuthenticationException e) {
      // expected
    }

    startLdapServers();

    try {
      ldapManager.authenticateUser("brianf", "brianf123");
      Assert.fail("Expected AuthenticationException");
    }
    catch (AuthenticationException e) {
      // expected
    }

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);
    Assert.assertNotNull(ldapManager.authenticateUser("brianf", "brianf123"));
  }

  @Test
  public void testGetAllGroups()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    SortedSet<String> expectedGroups = new TreeSet<String>();
    // from the default
    expectedGroups.add("public");
    expectedGroups.add("releases");
    expectedGroups.add("snapshots");

    SortedSet<String> actualGroups = ldapManager.getAllGroups();
    Assert.assertEquals(expectedGroups, actualGroups);

    stopLdapServers();

    Assert.assertEquals(0, ldapManager.getAllGroups().size());

    startLdapServers();

    Assert.assertEquals(0, ldapManager.getAllGroups().size());

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);

    actualGroups = ldapManager.getAllGroups();
    Assert.assertEquals(expectedGroups, actualGroups);
  }

  @Test
  public void testGetAllUsers()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    Assert.assertEquals(3, ldapManager.getAllUsers().size());

    stopLdapServers();
    Assert.assertEquals(0, ldapManager.getAllUsers().size());

    startLdapServers();
    Assert.assertEquals(0, ldapManager.getAllUsers().size());

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);
    Assert.assertEquals(3, ldapManager.getAllUsers().size());
  }

  @Test
  public void testGetGroupName()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    Assert.assertEquals("releases", ldapManager.getGroupName("releases"));

    stopLdapServers();

    try {
      ldapManager.getGroupName("releases");
      Assert.fail("Expected LdapDAOException");
    }
    catch (NoSuchLdapGroupException e) {
      // expected
    }

    startLdapServers();

    try {
      ldapManager.getGroupName("releases");
      Assert.fail("Expected LdapDAOException");
    }
    catch (NoSuchLdapGroupException e) {
      // expected
    }

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);
    Assert.assertEquals("releases", ldapManager.getGroupName("releases"));
  }

  @Test
  public void testGetUser()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    LdapUser brianf = ldapManager.getUser("brianf");
    Assert.assertNotNull(brianf);
    Assert.assertEquals("brianf", brianf.getUsername());
    Assert.assertEquals(brianf.getUsername() + "123", brianf.getPassword());
    Assert.assertEquals("Brian Fox", brianf.getRealName());
    Assert.assertEquals(2, brianf.getMembership().size());

    stopLdapServers();
    try {
      ldapManager.getUser("brianf");
      Assert.fail("Expected NoSuchLdapUserException");
    }
    catch (NoSuchLdapUserException e) {
      // expected: this was thrown before NX3
    }
    catch (LdapDAOException e) {
      // expected: this is thrown today after merge of OSS/Pro
    }

    startLdapServers();
    try {
      ldapManager.getUser("brianf");
      Assert.fail("Expected NoSuchLdapUserException");
    }
    catch (NoSuchLdapUserException e) {
      // expected: this was thrown before NX3
    }
    catch (LdapDAOException e) {
      // expected: this is thrown today after merge of OSS/Pro
    }

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);
    Assert.assertNotNull(ldapManager.getUser("brianf"));
  }

  @Test
  public void testGetUserRoles()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    Assert.assertEquals(2, ldapManager.getUserRoles("brianf").size());

    stopLdapServers();
    try {
      ldapManager.getUserRoles("brianf");
      Assert.fail("Expected LdapDAOException");
    }
    catch (NoLdapUserRolesFoundException e) {
      // expected
    }

    startLdapServers();
    try {
      ldapManager.getUserRoles("brianf");
      Assert.fail("Expected LdapDAOException");
    }
    catch (NoLdapUserRolesFoundException e) {
      // expected
    }

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);
    Assert.assertEquals(2, ldapManager.getUserRoles("brianf").size());
  }

  @Test
  public void testGetUsers()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    // exact number
    Assert.assertEquals(1, ldapManager.getUsers(1).size());


    stopLdapServers();
    Assert.assertEquals(0, ldapManager.getUsers(1).size());
    Assert.assertEquals(0, ldapManager.getUsers(1).size());
    Assert.assertEquals(0, ldapManager.getUsers(1).size());

    startLdapServers();
    Assert.assertEquals(0, ldapManager.getUsers(1).size());

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);
    Assert.assertEquals(1, ldapManager.getUsers(1).size());
  }

  @Test
  public void testSearchUsers()
      throws Exception
  {
    LdapManager ldapManager = lookup(LdapManager.class);

    Assert.assertEquals(3, ldapManager.searchUsers("", null).size());

    stopLdapServers();
    Assert.assertEquals(0, ldapManager.searchUsers("", null).size());

    startLdapServers();
    Assert.assertEquals(0, ldapManager.searchUsers("", null).size());

    // wait 3 more sec, then we should be good
    Thread.sleep(7 * 1000);
    Assert.assertEquals(3, ldapManager.searchUsers("", null).size());
  }
}
