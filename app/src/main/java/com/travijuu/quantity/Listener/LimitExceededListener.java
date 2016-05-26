package com.travijuu.quantity.Listener;

import android.util.Log;

import com.travijuu.quantity.Interface.ValueLimitExceededInterface;

/**
 * Created by travijuu on 26/05/16.
 */
public class LimitExceededListener implements ValueLimitExceededInterface {

    public void limitExceeded(int limit) {

        Log.d(this.getClass().toString(), "limitExceeded: " + limit);
    }
}
