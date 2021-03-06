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
/*global Ext, NX*/

/**
 * Authentication settings fields.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.AuthenticationSettings', {
  extend: 'Ext.form.FieldContainer',
  alias: 'widget.nx-coreui-authenticationsettings',
  requires: [
    'NX.I18n',
  ],

  namePrefix: undefined,

  defaults: {
    xtype: 'textfield'
  },

  /**
   * @override
   */
  initComponent: function () {
    var me = this,
        namePrefix = me.namePrefix ? me.namePrefix + 'A' : 'a';

    me.items = [
      {
        name: namePrefix + 'uthUsername',
        fieldLabel: NX.I18n.get('ADMIN_AUTHENTICATION_USERNAME'),
        allowBlank: false
      },
      {
        xtype: 'nx-password',
        name: namePrefix + 'uthPassword',
        fieldLabel: NX.I18n.get('ADMIN_AUTHENTICATION_PASSWORD')
      },
      {
        name: namePrefix + 'uthNtlmHost',
        fieldLabel: NX.I18n.get('ADMIN_AUTHENTICATION_HOST')
      },
      {
        name: namePrefix + 'uthNtlmDomain',
        fieldLabel: NX.I18n.get('ADMIN_AUTHENTICATION_DOMAIN')
      }
    ];

    me.callParent(arguments);
  }

});
