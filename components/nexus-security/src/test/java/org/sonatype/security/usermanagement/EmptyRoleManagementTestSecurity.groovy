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
package org.sonatype.security.usermanagement

import org.sonatype.security.model.CPrivilege
import org.sonatype.security.model.CRole
import org.sonatype.security.model.CUser
import org.sonatype.security.model.CUserRoleMapping
import org.sonatype.security.model.Configuration

/**
 * @since 3.0
 */
class EmptyRoleManagementTestSecurity
{

  static Configuration securityModel() {
    return new Configuration(
        users: [
            new CUser(
                id: 'admin',
                password: 'f865b53623b121fd34ee5426c792e5c33af8c227',
                firstName: 'Administrator',
                status: 'active',
                email: 'changeme@yourcompany.com'
            ),
            new CUser(
                id: 'test-user',
                password: 'b2a0e378437817cebdf753d7dff3dd75483af9e0',
                firstName: 'Test User',
                status: 'active',
                email: 'changeme1@yourcompany.com'
            ),
            new CUser(
                id: 'test-user-with-empty-role',
                password: 'b2a0e378437817cebdf753d7dff3dd75483af9e0',
                firstName: 'Test User With Empty Role',
                status: 'active',
                email: 'empty-role@yourcompany.com'
            ),
            new CUser(
                id: 'anonymous',
                password: '0a92fab3230134cca6eadd9898325b9b2ae67998',
                firstName: 'Anonynmous User',
                status: 'active',
                email: 'changeme2@yourcompany.com'
            )
        ],
        userRoleMappings: [
            new CUserRoleMapping(
                userId: 'other-user',
                source: 'other-realm',
                roles: ['role2', 'role3']
            ),
            new CUserRoleMapping(
                userId: 'admin',
                source: 'default',
                roles: ['role1']
            ),
            new CUserRoleMapping(
                userId: 'test-user',
                source: 'default',
                roles: ['role1', 'role2']
            ),
            new CUserRoleMapping(
                userId: 'test-user-with-empty-role',
                source: 'default',
                roles: ['empty-role', 'role1', 'role2']
            ),
            new CUserRoleMapping(
                userId: 'anonymous',
                source: 'default',
                roles: ['role2']
            )
        ],
        privileges: [
            new CPrivilege(
                id: '1',
                type: 'method',
                name: '1',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path1/'
                ]
            ),
            new CPrivilege(
                id: '2',
                type: 'method',
                name: '2',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path2/'
                ]
            ),
            new CPrivilege(
                id: '3',
                type: 'method',
                name: '3',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path/'
                ]
            ),
            new CPrivilege(
                id: '4',
                type: 'method',
                name: '4',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path/'
                ]
            )
        ],
        roles: [
            new CRole(
                id: 'role1',
                name: 'RoleOne',
                description: 'Role One',
                privileges: ['1', '2']
            ),
            new CRole(
                id: 'role2',
                name: 'RoleTwo',
                description: 'Role Two',
                privileges: ['3', '4']
            ),
            new CRole(
                id: 'role3',
                name: 'RoleThree',
                description: 'Role Three',
                privileges: ['1', '4']
            ),
            new CRole(
                id: 'empty-role',
                name: 'Empty Role',
                description: 'Empty Role',
            )
        ]
    )
  }

}

