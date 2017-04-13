package com.travijuu.numberpicker.library.Listener;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.travijuu.numberpicker.library.NumberPicker;

/**
 * Created by travijuu on 13/04/17.
 */

public class DefaultOnEditorActionListener implements TextView.OnEditorActionListener {

    NumberPicker numberPicker;

    public DefaultOnEditorActionListener(NumberPicker numberPicker) {
        this.numberPicker = numberPicker;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            int value = numberPicker.getMin();

            try {
                value = Integer.parseInt(v.getText().toString());
            } catch (Exception e) {
                // pass
            }

            numberPicker.setValue(value);

            if (numberPicker.getValue() == value) {
                return false;
            }
        }
        return true;
    }
}
