/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) 2005-2011, Nitobi Software Inc.
 * Copyright (c) 2010-2011, IBM Corporation
 */
package com.phonegap.plugins.mmAd;

import java.lang.reflect.Method;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

public class mmAd extends Plugin {
	private boolean expand = false;
    protected static final String LOG_TAG = "mmAd";
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action        The action to execute.
     * @param args          JSONArry of arguments for the plugin.
     * @param callbackId    The callback id used when calling back into JavaScript.
     * @return              A PluginResult object with a status and message.
     */
    public PluginResult execute(String action, JSONArray args, String callbackId) {
        PluginResult.Status status = PluginResult.Status.OK;
        String result = "";

        try {
        	if (action.equals("init"))
        	{
        		this.init();
        	}
            if (action.equals("resizeAd")) {
            	this.resizeAd();
            }
        }
        catch (Exception e) {
        	return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
        }
        return new PluginResult(status, result);
    }

    @SuppressLint("NewApi")
	private void init()
    {
    	webView.setBackgroundColor(Color.TRANSPARENT);
        //mainView.setBackgroundDrawable(null);
        if (Build.VERSION.SDK_INT >= 11) // Android v3.0+
        {
            try {
             Method method = View.class.getMethod("setLayerType", int.class,  Paint.class);
             method.invoke(this, 1, new Paint());  // 1 = LAYER_TYPE_SOFTWARE (API11)
            } catch (Exception e) {}
        }
    }
    public int oldHeight = 0;
    private void resizeAd()
    {
    	this.webView.post(new Runnable() {
    	     public void run() {

    	android.view.ViewGroup.LayoutParams params = webView.getLayoutParams();
    	if (params.height != LayoutParams.MATCH_PARENT)
    	{
    		oldHeight = webView.getMeasuredHeight();
    		params.height = LayoutParams.MATCH_PARENT;
    	}
    	else
    	{
    		params.height = oldHeight;
    	}

    	webView.setLayoutParams(params);
    	webView.forceLayout();

    	//stuff that updates ui

    	    }
    	});
    }
 
}
