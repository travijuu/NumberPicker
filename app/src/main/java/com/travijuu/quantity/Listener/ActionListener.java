package com.travijuu.quantity.Listener;

import android.view.View;

import com.travijuu.quantity.Enums.ActionEnum;
import com.travijuu.quantity.NumberPickerLayout;

/**
 * Created by travijuu on 26/05/16.
 */
public class ActionListener implements View.OnClickListener {

    NumberPickerLayout layout;
    ActionEnum action;

    public ActionListener(NumberPickerLayout layout, ActionEnum action) {
        this.layout = layout;
        this.action = action;
    }

    @Override
    public void onClick(View v) {
        if (this.action == ActionEnum.INCREMENT)
            this.layout.increment();
        else
            this.layout.decrement();
    }
}