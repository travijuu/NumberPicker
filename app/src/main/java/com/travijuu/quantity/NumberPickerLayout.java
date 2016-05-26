package com.travijuu.quantity;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travijuu.quantity.Enums.ActionEnum;
import com.travijuu.quantity.Listener.ActionListener;
import com.travijuu.quantity.Listener.LimitExceededListener;

/**
 * Created by travijuu on 26/05/16.
 */
public class NumberPickerLayout extends LinearLayout {

    private final int DEFAULT_MIN = 0;
    private final int DEFAULT_MAX = 99999999;
    private final int DEFAULT_VALUE = 1;
    private final int DEFAULT_UNIT = 1;

    private int minValue;
    private int maxValue;
    private int unit;

    private Context mContext;
    private Button decrementButton;
    private Button incrementButton;
    private TextView display;

    private int currentValue;

    private LimitExceededListener limitExceededListener;


    public NumberPickerLayout(Context context) {
        super(context, null);
    }

    public NumberPickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.number_picker_layout, this, true);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberPickerLayout, 0, 0);

        this.minValue = attributes.getInteger(R.styleable.NumberPickerLayout_min, this.DEFAULT_MIN);
        this.maxValue = attributes.getInteger(R.styleable.NumberPickerLayout_max, this.DEFAULT_MAX);
        this.currentValue = attributes.getInteger(R.styleable.NumberPickerLayout_value, this.DEFAULT_VALUE);
        this.unit = attributes.getInteger(R.styleable.NumberPickerLayout_unit, this.DEFAULT_UNIT);

        this.mContext = context;

        this.decrementButton = (Button) findViewById(R.id.decrement);
        this.incrementButton = (Button) findViewById(R.id.increment);
        this.display = (TextView) findViewById(R.id.display);

        this.incrementButton.setOnClickListener(new ActionListener(this, ActionEnum.INCREMENT));
        this.decrementButton.setOnClickListener(new ActionListener(this, ActionEnum.DECREMENT));

        this.setLimitExceededListener(new LimitExceededListener());

        this.update();
    }

    public NumberPickerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMin(int value) {
        this.minValue = value;
    }

    public void setMax(int value) {
        this.maxValue = value;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return this.unit;
    }

    public int getMin() {
        return this.minValue;
    }

    public int getMax() {
        return this.maxValue;
    }

    public void setLimitExceededListener(LimitExceededListener limitExceededListener) {
        this.limitExceededListener = limitExceededListener;
    }

    public void setValue(int value) {
        if (value < this.minValue || value > this.maxValue) {
            //TODO: limitExceeded
            limitExceededListener.limitExceeded(value < this.minValue ? this.minValue : this.maxValue);
            return;
        }

        this.currentValue = value;
        this.update();
    }

    public void increment() {
        if (this.currentValue + this.unit > this.maxValue) {
            limitExceededListener.limitExceeded(this.maxValue);
            return;
        }

        this.currentValue += this.unit;
        this.update();
    }

    public void decrement() {
        if (this.currentValue - this.unit < this.minValue) {
            limitExceededListener.limitExceeded(this.minValue);
            return;
        }

        this.currentValue -= this.unit;
        this.update();
    }

    private void update() {
        this.display.setText(Integer.toString(this.currentValue));
    }

}


