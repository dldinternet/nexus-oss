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
 * Task "Settings" form.
 *
 * @since 3.0
 */
Ext.define('NX.coreui.view.task.TaskSettingsForm', {
  extend: 'NX.view.SettingsForm',
  alias: 'widget.nx-coreui-task-settings-form',
  requires: [
    'NX.Conditions',
    'NX.I18n'
  ],

  items: [
    {
      xtype: 'hiddenfield',
      name: 'id'
    },
    {
      xtype: 'hiddenfield',
      name: 'typeId'
    },
    {
      xtype: 'checkbox',
      fieldLabel: NX.I18n.get('ADMIN_TASKS_SETTINGS_ENABLED'),
      name: 'enabled',
      allowBlank: false,
      checked: true,
      editable: true
    },
    {
      name: 'name',
      fieldLabel: NX.I18n.get('ADMIN_TASKS_SETTINGS_NAME')
    },
    {
      xtype: 'nx-email',
      name: 'alertEmail',
      fieldLabel: NX.I18n.get('ADMIN_TASKS_SETTINGS_EMAIL'),
      allowBlank: true
    },
    { xtype: 'nx-coreui-formfield-settingsfieldset' },
    { xtype: 'nx-coreui-task-schedulefieldset' }
  ],

  /**
   * @override
   */
  initComponent: function() {
    var me = this;

    me.editableCondition = me.editableCondition || NX.Conditions.and(
      NX.Conditions.isPermitted('nexus:tasks', 'update'),
      NX.Conditions.formHasRecord('nx-coreui-task-settings-form', function (model) {
        return model.get('schedule') !== 'internal';
      })
    );

    me.callParent(arguments);
  },

  /**
   * @override
   * Additionally, gets value of properties.
   */
  getValues: function() {
    var me = this,
      values = me.getForm().getFieldValues(),
      task = {
        id: values.id,
        typeId: values.typeId,
        enabled: values.enabled ? true : false,
        name: values.name,
        alertEmail: values.alertEmail,
        schedule: values.schedule
      };

    task.properties = me.down('nx-coreui-formfield-settingsfieldset').exportProperties(values);
    task.recurringDays = me.down('nx-coreui-task-schedulefieldset').getRecurringDays();
    task.startDate = me.down('nx-coreui-task-schedulefieldset').getStartDate();

    Ext.Array.each(Object.keys(values), function(key) {
      if (key.indexOf("property_") == 0) {
        task[key] = values[key];
      }
    });

    return task;
  },

  /**
   * @override
   * Additionally, sets properties values.
   */
  loadRecord: function(model) {
    var me = this,
        taskTypeModel = NX.getApplication().getStore('TaskType').getById(model.get('typeId')),
        settingsFieldSet = me.down('nx-coreui-formfield-settingsfieldset');

    this.callParent(arguments);

    if (taskTypeModel) {
      settingsFieldSet.importProperties(model.get('properties'), taskTypeModel.get('formFields'));
    }
  },

  /**
   * @override
   * Additionally, marks invalid properties.
   */
  markInvalid: function(errors) {
    var me = this;

    me.down('nx-coreui-formfield-settingsfieldset').markInvalid(errors);
  }

});
