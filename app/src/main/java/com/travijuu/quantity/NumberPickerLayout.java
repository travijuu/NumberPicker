package com.travijuu.quantity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travijuu.quantity.Enums.ActionEnum;
import com.travijuu.quantity.Listener.ActionListener;

/**
 * Created by travijuu on 26/05/16.
 */
public class NumberPickerLayout extends LinearLayout {

    private int MIN = 0;
    private int MAX = 999999;
    private int RATIO = 1;

    private Context mContext;
    private Button decrementButton;
    private Button incrementButton;
    private TextView display;

    private int currentValue = 1;


    public NumberPickerLayout(Context context) {
        super(context);
    }

    public NumberPickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.number_picker_layout, this, true);
        this.mContext = context;

        this.decrementButton = (Button) findViewById(R.id.decrement);
        this.incrementButton = (Button) findViewById(R.id.increment);
        this.display = (TextView) findViewById(R.id.display);

        this.incrementButton.setOnClickListener(new ActionListener(this, ActionEnum.INCREMENT));
        this.decrementButton.setOnClickListener(new ActionListener(this, ActionEnum.DECREMENT));
    }

    public NumberPickerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setValue(int value) {
        if (value < this.MIN || value > this.MAX) {
            return;
        }

        this.currentValue = value;
        this.update();
    }

    public void increment() {
        if (this.currentValue + this.RATIO > this.MAX) {
            return;
        }

        this.currentValue += this.RATIO;
        this.update();
    }

    public void decrement() {
        if (this.currentValue - this.RATIO < this.MIN) {
            return;
        }

        this.currentValue -= this.RATIO;
        this.update();
    }

    private void update() {
        this.display.setText(Integer.toString(this.currentValue));
    }

    public void setMin(int value) {
        this.MIN = value;
    }

    public void setMax(int value) {
        this.MAX = value;
    }

    public void setRatio(int ratio) {
        this.RATIO = ratio;
    }

}


