package com.dff.cordova.plugin.alert.action;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;

import com.dff.cordova.plugin.alert.AlertPluginService;
import com.dff.cordova.plugin.common.action.CordovaAction;

public class AlertActionAlert extends CordovaAction {

	private AlertPluginService alertPluginService;
	
	public AlertActionAlert(String action, JSONArray args,
			CallbackContext callbackContext, CordovaInterface cordova, AlertPluginService alertPluginService) {
		super(action, args, callbackContext, cordova);
		
		this.alertPluginService = alertPluginService;
	}

	@Override
	public void run() {
		super.run();
		
	}
}
