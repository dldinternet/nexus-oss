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
// =================== DO NOT EDIT THIS FILE ====================
// Generated by Modello 1.7,
// any modifications will be overwritten.
// ==============================================================

package org.sonatype.security.rest.model;

/**
 * REST Response object to list the privileges a users has.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
@com.thoughtworks.xstream.annotations.XStreamAlias( "authentication-login" )
@javax.xml.bind.annotation.XmlRootElement( name = "authentication-login" )
@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class AuthenticationLoginResourceResponse
    implements java.io.Serializable
{

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Login details.
     */
    private AuthenticationLoginResource data;


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Get login details.
     * 
     * @return AuthenticationLoginResource
     */
    public AuthenticationLoginResource getData()
    {
        return this.data;
    } //-- AuthenticationLoginResource getData()

    /**
     * Set login details.
     * 
     * @param data
     */
    public void setData( AuthenticationLoginResource data )
    {
        this.data = data;
    } //-- void setData( AuthenticationLoginResource )

}
