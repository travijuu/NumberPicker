package com.travijuu.numberpicker.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.LimitExceededListener;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.Listener.ActionListener;
import com.travijuu.numberpicker.library.Listener.DefaultLimitExceededListener;
import com.travijuu.numberpicker.library.Listener.DefaultValueChangedListener;

/**
 * Created by travijuu on 26/05/16.
 */
public class NumberPicker extends LinearLayout {

    // default values
    private final int DEFAULT_MIN = 0;
    private final int DEFAULT_MAX = 99999999;
    private final int DEFAULT_VALUE = 1;
    private final int DEFAULT_UNIT = 1;
    private final int DEFAULT_LAYOUT = R.layout.number_picker_layout;

    // required variables
    private int minValue;
    private int maxValue;
    private int unit;
    private int currentValue;
    private int layout;

    // ui components
    private Context mContext;
    private Button decrementButton;
    private Button incrementButton;
    private TextView displayTextView;

    // listeners
    private LimitExceededListener limitExceededListener;
    private ValueChangedListener valueChangedListener;

    public NumberPicker(Context context) {
        super(context, null);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.initialize(context, attrs);
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberPicker, 0, 0);

        // set required variables with values of xml layout attributes or default ones
        this.minValue = attributes.getInteger(R.styleable.NumberPicker_min, this.DEFAULT_MIN);
        this.maxValue = attributes.getInteger(R.styleable.NumberPicker_max, this.DEFAULT_MAX);
        this.currentValue = attributes.getInteger(R.styleable.NumberPicker_value, this.DEFAULT_VALUE);
        this.unit = attributes.getInteger(R.styleable.NumberPicker_unit, this.DEFAULT_UNIT);
        this.layout = attributes.getResourceId(R.styleable.NumberPicker_custom_layout, this.DEFAULT_LAYOUT);
        this.mContext = context;

        // if current value is greater than the max. value, decrement it to the max. value
        this.currentValue = this.currentValue > this.maxValue ? maxValue : currentValue;

        // if current value is less than the min. value, decrement it to the min. value
        this.currentValue = this.currentValue < this.minValue ? minValue : currentValue;

        // set layout view
        LayoutInflater.from(this.mContext).inflate(layout, this, true);

        // init ui components
        this.decrementButton = (Button) findViewById(R.id.decrement);
        this.incrementButton = (Button) findViewById(R.id.increment);
        this.displayTextView = (TextView) findViewById(R.id.display);

        // register button click listeners
        this.incrementButton.setOnClickListener(new ActionListener(this, ActionEnum.INCREMENT));
        this.decrementButton.setOnClickListener(new ActionListener(this, ActionEnum.DECREMENT));

        // init listener for exceeding upper and lower limits
        this.setLimitExceededListener(new DefaultLimitExceededListener());
        // init listener for increment&decrement
        this.setValueChangedListener(new DefaultValueChangedListener());

        // update ui view
        this.updateView();
    }

    private void updateView() {
        this.displayTextView.setText(Integer.toString(this.currentValue));
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

    public void setValue(int value) {
        if (value < this.minValue || value > this.maxValue) {
            this.limitExceededListener.limitExceeded(value < this.minValue ? this.minValue : this.maxValue, value);
            return;
        }

        this.currentValue = value;
        this.updateView();
    }

    public int getValue() {
        return this.currentValue;
    }

    public void setLimitExceededListener(LimitExceededListener limitExceededListener) {
        this.limitExceededListener = limitExceededListener;
    }

    public void setValueChangedListener(ValueChangedListener valueChangedListener) {
        this.valueChangedListener = valueChangedListener;
    }

    public void setActionEnabled(ActionEnum action, boolean enabled) {
        if (action == ActionEnum.INCREMENT) {
            this.incrementButton.setEnabled(enabled);
        } else if (action == ActionEnum.DECREMENT) {
            this.decrementButton.setEnabled(enabled);
        }
    }

    public void increment() {
        this.changeValueBy(this.unit);
    }

    public void increment(int unit) {
        this.changeValueBy(unit);
    }

    public void decrement() {
        this.changeValueBy(-this.unit);
    }

    public void decrement(int unit) {
        this.changeValueBy(-unit);
    }

    private void changeValueBy(int unit) {
        int oldValue = this.getValue();

        this.setValue(this.currentValue + unit);

        if (oldValue != this.getValue()) {
            this.valueChangedListener.valueChanged(this.getValue(), unit > 0 ? ActionEnum.INCREMENT : ActionEnum.DECREMENT);
        }
    }
}
