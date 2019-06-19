package com.travijuu.numberpicker.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.LimitExceededListener;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.Listener.ActionListener;
import com.travijuu.numberpicker.library.Listener.DefaultLimitExceededListener;
import com.travijuu.numberpicker.library.Listener.DefaultOnFocusChangeListener;
import com.travijuu.numberpicker.library.Listener.DefaultValueChangedListener;
import com.travijuu.numberpicker.library.Listener.DefaultOnEditorActionListener;

/**
 * Created by travijuu on 26/05/16.
 */
public class NumberPicker extends LinearLayout {

    // default values
    private final int DEFAULT_MIN = 0;
    private final int DEFAULT_MAX = 999999;
    private final int DEFAULT_VALUE = 1;
    private final int DEFAULT_UNIT = 1;
    private final int DEFAULT_LAYOUT = R.layout.number_picker_layout;
    private final boolean DEFAULT_FOCUSABLE = false;

    // required variables
    private int minValue;
    private int maxValue;
    private int unit;
    private int currentValue;
    private int layout;
    private boolean focusable;

    // ui components
    private Context mContext;
    private View decrementButton;
    private View incrementButton;
    private EditText displayEditText;

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
        this.focusable = attributes.getBoolean(R.styleable.NumberPicker_focusable, this.DEFAULT_FOCUSABLE);
        this.mContext = context;

        // if current value is greater than the max. value, decrement it to the max. value
        this.currentValue = this.currentValue > this.maxValue ? maxValue : currentValue;

        // if current value is less than the min. value, decrement it to the min. value
        this.currentValue = this.currentValue < this.minValue ? minValue : currentValue;

        // set layout view
        LayoutInflater.from(this.mContext).inflate(layout, this, true);

        // init ui components
        this.decrementButton = findViewById(R.id.decrement);
        this.incrementButton = findViewById(R.id.increment);
        this.displayEditText = (EditText) findViewById(R.id.display);

        // register button click and action listeners
        this.incrementButton.setOnClickListener(new ActionListener(this, this.displayEditText, ActionEnum.INCREMENT));
        this.decrementButton.setOnClickListener(new ActionListener(this, this.displayEditText, ActionEnum.DECREMENT));

        // init listener for exceeding upper and lower limits
        this.setLimitExceededListener(new DefaultLimitExceededListener());
        // init listener for increment&decrement
        this.setValueChangedListener(new DefaultValueChangedListener());
        // init listener for focus change
        this.setOnFocusChangeListener(new DefaultOnFocusChangeListener(this));
        // init listener for done action in keyboard
        this.setOnEditorActionListener(new DefaultOnEditorActionListener(this));

        // set default display mode
        this.setDisplayFocusable(this.focusable);

        // update ui view
        this.refresh();
    }

    public void refresh() {
        this.displayEditText.setText(Integer.toString(this.currentValue));
    }

    public void clearFocus() {
        this.displayEditText.clearFocus();
    }

    public boolean valueIsAllowed(int value) {
        return (value >= this.minValue && value <= this.maxValue);
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
        if (!this.valueIsAllowed(value)) {
            this.limitExceededListener.limitExceeded(value < this.minValue ? this.minValue : this.maxValue, value);
            return;
        }

        this.currentValue = value;
        this.refresh();
    }

    public int getValue() {
        return this.currentValue;
    }

    public void setLimitExceededListener(LimitExceededListener limitExceededListener) {
        this.limitExceededListener = limitExceededListener;
    }

    public LimitExceededListener getLimitExceededListener() {
        return this.limitExceededListener;
    }

    public void setValueChangedListener(ValueChangedListener valueChangedListener) {
        this.valueChangedListener = valueChangedListener;
    }

    public ValueChangedListener getValueChangedListener() {
        return this.valueChangedListener;
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
        this.displayEditText.setOnEditorActionListener(onEditorActionListener);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.displayEditText.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void setActionEnabled(ActionEnum action, boolean enabled) {
        if (action == ActionEnum.INCREMENT) {
            this.incrementButton.setEnabled(enabled);
        } else if (action == ActionEnum.DECREMENT) {
            this.decrementButton.setEnabled(enabled);
        }
    }

    public void setDisplayFocusable(boolean focusable) {
        this.displayEditText.setFocusable(focusable);

        // required for making EditText focusable
        if (focusable) {
            this.displayEditText.setFocusableInTouchMode(true);
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
