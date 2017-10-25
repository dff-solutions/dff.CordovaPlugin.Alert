package com.dff.cordova.plugin.alert;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.dff.cordova.plugin.alert.AlertPluginService.AlertBinder;
import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author dff solutions
 */
public class AlertPlugin extends CommonPlugin {
    private static final String LOG_TAG = "com.dff.cordova.plugin.alert.AlertPlugin";
    private static final String PERMISSION = Manifest.permission.SYSTEM_ALERT_WINDOW;

    private Intent alertServiceIntent;
    private AlertPluginService alertPluginService;
    private boolean alertServiceBound = false;

    public AlertPlugin() {
        super(LOG_TAG);
    }

    private ServiceConnection alertServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            CordovaPluginLog.i(LOG_TAG, "AlertPluginService disconnected");
            alertPluginService = null;
            alertServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CordovaPluginLog.i(LOG_TAG, "AlertPluginService connected");

            alertPluginService = ((AlertBinder) service).getService();
            alertServiceBound = true;
        }
    };

    private void requestPermission() {
        CommonPlugin.addPermission(PERMISSION);
    }


    /**
     * Called after plugin construction and fields have been initialized.
     */
    @Override
    public void pluginInitialize() {
        super.pluginInitialize();
        requestPermission();

        this.alertServiceIntent = new Intent(cordova.getActivity(), AlertPluginService.class);

//    	cordova.getActivity().startService(alertServiceIntent);
        cordova.getActivity().bindService(alertServiceIntent, alertServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onReset() {
        super.onReset();

        CordovaPluginLog.d(LOG_TAG, "AlertPluginService bound: " + alertServiceBound);
    }

    ;

    @Override
    public void onDestroy() {
        super.onDestroy();

        CordovaPluginLog.d(LOG_TAG, "AlertPluginService bound: " + alertServiceBound);
        cordova.getActivity().unbindService(alertServiceConnection);
    }

    /**
     * Executes the request.
     * <p>
     * This method is called from the WebView thread.
     * To do a non-trivial amount of work, use:
     * cordova.getThreadPool().execute(runnable);
     * <p>
     * To run on the UI thread, use:
     * cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
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
            } else {
                callbackContext.error("AlertPluginService not initialized");
            }

            return true;
        }

        return super.execute(action, args, callbackContext);
    }
}
