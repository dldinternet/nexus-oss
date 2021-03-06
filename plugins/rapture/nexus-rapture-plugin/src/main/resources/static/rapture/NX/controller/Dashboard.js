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
/*global Ext*/

/**
 * Dashboard controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.Dashboard', {
  extend: 'Ext.app.Controller',
  requires: [
    'NX.I18n'
  ],

  views: [
    'dashboard.Welcome'
  ],

  init: function () {
    var me = this;

    me.getApplication().getFeaturesController().registerFeature({
      path: '/Welcome',
      mode: 'browse',
      view: 'NX.view.dashboard.Welcome',
      text: NX.I18n.get('BROWSE_WELCOME_TITLE'),
      description: NX.I18n.get('BROWSE_WELCOME_SUBTITLE'),
      iconConfig: {
        file: 'house.png',
        variants: ['x16', 'x32']
      },
      weight: 10,
      authenticationRequired: false
    });
  }

});
