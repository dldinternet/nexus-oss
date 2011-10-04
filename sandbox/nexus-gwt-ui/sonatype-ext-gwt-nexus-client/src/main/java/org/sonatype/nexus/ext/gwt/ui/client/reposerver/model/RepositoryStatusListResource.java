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
package org.sonatype.nexus.ext.gwt.ui.client.reposerver.model;

import org.sonatype.nexus.ext.gwt.ui.client.data.Entity;
import org.sonatype.nexus.ext.gwt.ui.client.reposerver.RepoServerUtil;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;

public class RepositoryStatusListResource extends BaseModelData implements Entity {
    
    public RepositoryStatusListResource() {
    }

    public RepositoryStatusListResource(ModelData model) {
        setId(RepoServerUtil.getRepositoryId(model));
        setRepoType((String) model.get("repoType"));
        /*
        setLocalStatus((String) model.get("localStatus"));
        setRemoteStatus((String) model.get("remoteStatus"));
        setProxyMode((String) model.get("proxyMode"));
        */
    }

    public String getType() {
        return "org.sonatype.nexus.rest.model.RepositoryStatusListResource";
    }

    public Class getFieldType(String fieldName) {
        return "status".equals(fieldName) ? RepositoryStatusResource.class : String.class;
    }

    public Entity createEntity(String fieldName) {
        return "status".equals(fieldName) ? new RepositoryStatusResource() : null;
    }

    public String getResourceURI() {
        return get("resourceURI");
    }

    public void setResourceURI(String resourceURI) {
        set("resourceURI", resourceURI);
    }

    public String getId() {
        return get("id");
    }

    public void setId(String id) {
        set("id", id);
    }

    public String getName() {
        return get("name");
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getRepoType() {
        return get("repoType");
    }

    public void setRepoType(String repoType) {
        set("repoType", repoType);
    }

    public RepositoryStatusResource getStatus() {
        return get("status");
    }

    public void setStatus(RepositoryStatusResource status) {
        set("status", status);
    }

    /*
    public String getLocalStatus() {
        return get("localStatus");
    }

    public void setLocalStatus(String localStatus) {
        set("localStatus", localStatus);
    }
    
    public String getRemoteStatus() {
        return get("remoteStatus");
    }

    public void setRemoteStatus(String remoteStatus) {
        set("remoteStatus", remoteStatus);
    }

    public String getProxyMode() {
        return get("proxyMode");
    }

    public void setProxyMode(String proxyMode) {
        set("proxyMode", proxyMode);
    }
    */

}
