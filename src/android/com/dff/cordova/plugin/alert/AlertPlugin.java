package com.dff.cordova.plugin.alert;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.dff.cordova.plugin.alert.AlertPluginService.AlertBinder;
import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 
 *
 * @author dff solutions
 */
public class AlertPlugin extends CommonPlugin {
	private static final String LOG_TAG = "com.dff.cordova.plugin.alert.AlertPlugin";
	
	private Intent alertServiceIntent;
	private AlertPluginService alertPluginService;
	
	private ServiceConnection alertServiceConnection = new ServiceConnection() {		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			alertPluginService = null;			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			alertPluginService = ((AlertBinder)service).getService();			
		}
	};

   /**
	* Called after plugin construction and fields have been initialized.
	*/
    public void pluginInitialize() {
    	super.pluginInitialize();
    	
    	this.alertServiceIntent =  new Intent(cordova.getActivity(), AlertPluginService.class);

//    	cordova.getActivity().startService(alertServiceIntent);
    	cordova.getActivity().bindService(alertServiceIntent, alertServiceConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	cordova.getActivity().unbindService(alertServiceConnection);
    	this.logListener.onDestroy();
    }
    
    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode   The request code originally supplied to startActivityForResult(),
     *                      allowing you to identify who this result came from.
     * @param resultCode    The integer result code returned by the child activity through its setResult().
     * @param intent        An Intent, which can return result data to the caller (various data can be
     *                      attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	CordovaPluginLog.i(this.getClass().getName(), "onActivityResult - requestCode: " + requestCode + "; resultCode: " + resultCode + "; intent: " + intent.toString());
    }
    
    /**
    * Executes the request.
    *
    * This method is called from the WebView thread.
    * To do a non-trivial amount of work, use:
    * cordova.getThreadPool().execute(runnable);
    *
    * To run on the UI thread, use:
    * cordova.getActivity().runOnUiThread(runnable);
    *
    * @param action The action to execute.
    * @param args The exec() arguments.
    * @param callbackContext The callback context used when calling back into JavaScript.
    * @return Whether the action was valid.
    */
	@Override
    public boolean execute(String action
    		, final JSONArray args
    		, final CallbackContext callbackContext)
        throws JSONException {
    	
		CordovaPluginLog.i(LOG_TAG, "call for action: " + action + "; args: " + args);

		if (action.equals("alert")) {
    		if (this.alertPluginService != null) {
    			this.alertPluginService.showAlertDialog(cordova, callbackContext, args);    			
    		}
    		else {
    			callbackContext.error("AlertPluginService not initialized");
    		}    		
    		
    		return true;
    	}
    	
        return super.execute(action, args, callbackContext);
    }
}
