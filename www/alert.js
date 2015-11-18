/**
 * @module com/dff/cordova/plugins/alert
 */

'use strict';

var cordova = require('cordova');
var feature = "Alert";
var self = {};

self.alert = function (success, error, args) {
    cordova.exec(success, error, feature, "alert", [args]);
};

self.onLog = function (success, error) {
    cordova.exec(success, error, feature, "onLog", []);
};


self.TYPE_APPLICATION = 2; // (0x00000002)
self.TYPE_APPLICATION_ATTACHED_DIALOG = 1003; // (0x000003eb)
self.TYPE_APPLICATION_MEDIA = 1001; // (0x000003e9)
self.TYPE_APPLICATION_PANEL = 1000; // (0x000003e8)
self.TYPE_APPLICATION_STARTING = 3; // (0x00000003)
self.TYPE_APPLICATION_SUB_PANEL = 1002; // (0x000003ea)
self.TYPE_BASE_APPLICATION = 1; // (0x00000001)
self.TYPE_CHANGED = 2011; // (0x000007db)
self.TYPE_INPUT_METHOD_DIALOG = 2012; // (0x000007dc)
self.TYPE_KEYGUARD_DIALOG = 2009; // (0x000007d9)
self.TYPE_PHONE = 2002; // (0x000007d2)
self.TYPE_PRIORITY_PHONE = 2007; // (0x000007d7)
self.TYPE_PRIVATE_PRESENTATION = 2030; // (0x000007ee)
self.TYPE_SEARCH_BAR = 2001; // (0x000007d1)
self.TYPE_STATUS_BAR = 2000; // (0x000007d0)
self.TYPE_STATUS_BAR_PANEL = 2014; // (0x000007de)
self.TYPE_SYSTEM_ALERT = 2003; // (0x000007d3)
self.TYPE_SYSTEM_DIALOG = 2008; // (0x000007d8)
self.TYPE_SYSTEM_ERROR = 2010; // (0x000007da)
self.TYPE_SYSTEM_OVERLAY = 2006; // (0x000007d6)
self.TYPE_TOAST = 2005; // (0x000007d5)
self.TYPE_WALLPAPER = 2013; // (0x000007dd)

self.BUTTON_NEGATIVE = -2; // (0xfffffffe)
self.BUTTON_NEUTRAL = -3; // (0xfffffffd)
self.BUTTON_POSITIVE = -1; // (0xffffffff)

module.exports = self;
