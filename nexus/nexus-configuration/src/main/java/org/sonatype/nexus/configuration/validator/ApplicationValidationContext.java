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
package org.sonatype.nexus.configuration.validator;

import java.util.ArrayList;
import java.util.List;

import org.sonatype.configuration.validation.ValidationContext;

public class ApplicationValidationContext
    implements ValidationContext
{
    private List<String> existingRepositoryIds;

    private List<String> existingRepositoryShadowIds;

    private List<String> existingRepositoryGroupIds;

    private List<String> existingPathMappingIds;

    private List<String> existingRealms;

    private List<String> existingRepositoryTargetIds;

    public void addExistingRepositoryIds()
    {
        if ( this.existingRepositoryIds == null )
        {
            this.existingRepositoryIds = new ArrayList<String>();
        }
    }

    public void addExistingRepositoryShadowIds()
    {
        if ( this.existingRepositoryShadowIds == null )
        {
            this.existingRepositoryShadowIds = new ArrayList<String>();
        }
    }

    public void addExistingRepositoryGroupIds()
    {
        if ( this.existingRepositoryGroupIds == null )
        {
            this.existingRepositoryGroupIds = new ArrayList<String>();
        }
    }

    public void addExistingPathMappingIds()
    {
        if ( this.existingPathMappingIds == null )
        {
            this.existingPathMappingIds = new ArrayList<String>();
        }
    }

    public void addExistingRealms()
    {
        if ( this.existingRealms == null )
        {
            this.existingRealms = new ArrayList<String>();
        }
    }

    public void addExistingRepositoryTargetIds()
    {
        if ( this.existingRepositoryTargetIds == null )
        {
            this.existingRepositoryTargetIds = new ArrayList<String>();
        }
    }

    public List<String> getExistingRepositoryIds()
    {
        return existingRepositoryIds;
    }

    public List<String> getExistingRepositoryShadowIds()
    {
        return existingRepositoryShadowIds;
    }

    public List<String> getExistingRepositoryGroupIds()
    {
        return existingRepositoryGroupIds;
    }

    public List<String> getExistingPathMappingIds()
    {
        return existingPathMappingIds;
    }

    public List<String> getExistingRealms()
    {
        return existingRealms;
    }

    public List<String> getExistingRepositoryTargetIds()
    {
        return existingRepositoryTargetIds;
    }

}
