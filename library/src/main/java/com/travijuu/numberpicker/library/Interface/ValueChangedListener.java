package com.travijuu.numberpicker.library.Interface;

import com.travijuu.numberpicker.library.Enums.ActionEnum;

/**
 * Created by travijuu on 19/12/16.
 */

public interface ValueChangedListener {

    void valueChanged(int value, ActionEnum action);
}
