package com.travijuu.numberpicker.Listener;

import android.view.View;

import com.travijuu.numberpicker.Enums.ActionEnum;
import com.travijuu.numberpicker.NumberPicker;

/**
 * Created by travijuu on 26/05/16.
 */
public class ActionListener implements View.OnClickListener {

    NumberPicker layout;
    ActionEnum action;

    public ActionListener(NumberPicker layout, ActionEnum action) {
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