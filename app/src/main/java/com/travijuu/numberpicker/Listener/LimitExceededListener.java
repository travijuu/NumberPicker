package com.travijuu.numberpicker.Listener;

import android.util.Log;

import com.travijuu.numberpicker.Interface.ValueLimitExceededInterface;

/**
 * Created by travijuu on 26/05/16.
 */
public class LimitExceededListener implements ValueLimitExceededInterface {

    public void limitExceeded(int limit, int exceededValue) {

        Log.d(this.getClass().toString(), "limit: " + limit + " - value: " + exceededValue);
    }
}
