package com.travijuu.numberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travijuu.numberpicker.Enums.ActionEnum;
import com.travijuu.numberpicker.Listener.ActionListener;
import com.travijuu.numberpicker.Listener.LimitExceededListener;

/**
 * Created by travijuu on 26/05/16.
 */
public class NumberPicker extends LinearLayout {

    private final int DEFAULT_MIN = 0;
    private final int DEFAULT_MAX = 99999999;
    private final int DEFAULT_VALUE = 1;
    private final int DEFAULT_UNIT = 1;
    private final int DEFAULT_LAYOUT = R.layout.number_picker_layout;

    private int minValue;
    private int maxValue;
    private int unit;
    private int currentValue;
    private int layout;

    private Context mContext;
    private Button decrementButton;
    private Button incrementButton;
    private TextView displayTextView;

    private LimitExceededListener limitExceededListener;

    public NumberPicker(Context context) {
        super(context, null);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberPicker, 0, 0);

        this.minValue = attributes.getInteger(R.styleable.NumberPicker_min, this.DEFAULT_MIN);
        this.maxValue = attributes.getInteger(R.styleable.NumberPicker_max, this.DEFAULT_MAX);
        this.currentValue = attributes.getInteger(R.styleable.NumberPicker_value, this.DEFAULT_VALUE);
        this.unit = attributes.getInteger(R.styleable.NumberPicker_unit, this.DEFAULT_UNIT);
        this.layout = attributes.getResourceId(R.styleable.NumberPicker_custom_layout, this.DEFAULT_LAYOUT);
        this.mContext = context;

        LayoutInflater.from(this.mContext).inflate(layout, this, true);

        this.decrementButton = (Button) findViewById(R.id.decrement);
        this.incrementButton = (Button) findViewById(R.id.increment);
        this.displayTextView = (TextView) findViewById(R.id.display);

        this.incrementButton.setOnClickListener(new ActionListener(this, ActionEnum.INCREMENT));
        this.decrementButton.setOnClickListener(new ActionListener(this, ActionEnum.DECREMENT));

        this.setLimitExceededListener(new LimitExceededListener());

        this.update();
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
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
            limitExceededListener.limitExceeded(value < this.minValue ? this.minValue : this.maxValue, value);
            return;
        }

        this.currentValue = value;
        this.update();
    }

    public void increment() {
        if (this.currentValue + this.unit > this.maxValue) {
            limitExceededListener.limitExceeded(this.maxValue, this.currentValue + this.unit);
            return;
        }

        this.currentValue += this.unit;
        this.update();
    }

    public void decrement() {
        if (this.currentValue - this.unit < this.minValue) {
            limitExceededListener.limitExceeded(this.minValue, this.currentValue - this.unit);
            return;
        }

        this.currentValue -= this.unit;
        this.update();
    }

    private void update() {
        this.displayTextView.setText(Integer.toString(this.currentValue));
    }
}


