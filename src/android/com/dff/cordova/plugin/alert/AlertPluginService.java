package com.dff.cordova.plugin.alert;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.WindowManager;

import com.dff.cordova.plugin.log.CordovaPluginLog;

public class AlertPluginService extends Service {
    public class AlertBinder extends Binder {
        AlertPluginService getService() {
            return AlertPluginService.this;
        }
    }
    
    private final IBinder mBinder = new AlertBinder();
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private DialogInterface.OnClickListener dialogOnClickListener;

	public void onCreate() {
		super.onCreate();
	}
	
	public void showAlertDialog(CordovaInterface cordova, final CallbackContext callbackContext, JSONArray args) {
		String title;
		String message;
		String positiveButton;
		String negativeButton;
		String neutralButton;
		int type;
		boolean cancelable;
		builder = new AlertDialog.Builder(this);

		dialogOnClickListener = new DialogInterface.OnClickListener() {					
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String bs;
				
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					bs = "BUTTON_POSITIVE";
					break;
				case DialogInterface.BUTTON_NEUTRAL:
					bs = "BUTTON_NEUTRAL";
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					bs = "BUTTON_NEGATIVE";
					break;

				default:
					bs = null;
					break;
				}
				
				CordovaPluginLog.i(this.getClass().getName(), "AlertDialog clicked " + bs);		
				callbackContext.success(which);
			}
		};
		
		if (alertDialog != null) {
			alertDialog.dismiss();			
		}
		
		try {
			JSONObject jsonArgs = args.getJSONObject(0);
			
			if (jsonArgs == null) {
				callbackContext.error("no args given");
			}
			else if (!jsonArgs.has("message")) {
				callbackContext.error("message missing");
			}
			else if (!jsonArgs.has("title")) {
				callbackContext.error("title missing");
			}
			else if (!jsonArgs.has("type")) {
				callbackContext.error("type missing");
			}
			else {
				title = jsonArgs.getString("title");
				message = jsonArgs.getString("message");
				type = jsonArgs.optInt("type", WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);				
				cancelable = jsonArgs.optBoolean("cancelable", false);
				positiveButton = jsonArgs.optString("positiveButton", "OK");
				negativeButton = jsonArgs.optString("negativeButton", "Cancel");
				neutralButton = jsonArgs.optString("neutralButton", null);
				
				builder.setTitle(title);
				builder.setMessage(message);
				builder.setPositiveButton(positiveButton, dialogOnClickListener);
				
				if (neutralButton != null) {
					builder.setNeutralButton(neutralButton, dialogOnClickListener);
				}
				
				builder.setCancelable(cancelable);
				
				if (cancelable) {
					builder.setNegativeButton(negativeButton, dialogOnClickListener);
				}
				
				builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						CordovaPluginLog.i(this.getClass().getName(), "AlertDialog dismissed");
						
						if (!callbackContext.isFinished()) {
							callbackContext.success();
						}
					}
				});
				
				alertDialog = builder.create();
				alertDialog.getWindow().setType(type);
				alertDialog.show();
			}
		}
		catch(JSONException e) {
			CordovaPluginLog.e(this.getClass().getName(), e.getMessage(), e);
			callbackContext.error(e.getMessage());
		}
		catch(Exception ex) {
			CordovaPluginLog.e(this.getClass().getName(), ex.getMessage(), ex);
			callbackContext.error(ex.getMessage());
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		CordovaPluginLog.i(this.getClass().getName(), "starting width flags: " + flags + ", startId: "+ startId);
		
		return START_NOT_STICKY;
	};
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

}
