NumberPicker 
============

[![Build Status](https://travis-ci.org/travijuu/NumberPicker.svg?branch=master)](https://travis-ci.org/travijuu/NumberPicker) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.travijuu/numberpicker/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.travijuu/numberpicker)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-NumberPicker-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5838)

A simple customizable NumberPicker for Android.

<img src="https://raw.githubusercontent.com/travijuu/NumberPicker/master/images/numberpicker.png" width=450/>

Installation
--------

via Gradle:
```groovy
compile 'com.github.travijuu:numberpicker:1.0.7'
```
or Maven:
```xml
<dependency>
  <groupId>com.github.travijuu</groupId>
  <artifactId>numberpicker</artifactId>
  <version>1.0.7</version>
  <type>aar</type>
</dependency>
```

Usage
-----

Add NumberPicker component in your XML layout

*activity_main.xml*

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    xmlns:numberpicker="http://schemas.android.com/apk/res-auto"
    tools:context="com.travijuu.numberpicker.sample.MainActivity">

    <com.travijuu.numberpicker.library.NumberPicker
        android:id="@+id/number_picker"
        android:layout_width="130dp"
        android:layout_height="40dp"
        numberpicker:min="0"
        numberpicker:max="10"
        numberpicker:value="-5"
        numberpicker:unit="1"
        numberpicker:focusable="false"
        numberpicker:custom_layout="@layout/number_picker_custom_layout" />

</LinearLayout>

```

*MainActivity.java*

```java
import com.travijuu.numberpicker.library.NumberPicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setMax(15);
        numberPicker.setMin(5);
        numberPicker.setUnit(2);
        numberPicker.setValue(10);
    }
}

```

XML Attributes
--------------

| Name          | Type    | Default |
|---------------|---------|---------|
| min           | int     | 0       |
| max           | int     | 999999  |
| value         | int     | 1       |
| unit          | int     | 1       |
| focusable     | boolean | false   |
| custom_layout | layout  | @layout/number_picker_layout |


Layout Customization
--------------------

if you want to customize your NumberPicker layout you can create your own.

**IMPORTANT!** This layout should contains at least 3 items with given Ids:
- Button (@+id/increment)
- Button (@+id/decrement)
- TextView (@+id/display)

Note: You can see an example layout in both sample and library modules.

Example XML layout:

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="130dp"
    android:layout_height="40dp"
    android:orientation="horizontal"
    android:background="@android:color/white">

    <Button
        android:layout_width="30dp"
        android:layout_height="match_parent"
        android:padding="0dp"
        android:textColor="@android:color/black"
        android:background="@null"
        android:id="@+id/decrement"
        android:textStyle="bold"
        android:text="—"/>

    <TextView
        android:layout_width="70dp"
        android:background="@android:color/white"
        android:layout_height="match_parent"
        android:text="1"
        android:textColor="@android:color/black"
        android:inputType="number"
        android:id="@+id/display"
        android:gravity="center"
        />
    <Button
        android:layout_width="30dp"
        android:layout_height="match_parent"
        android:padding="0dp"
        android:textSize="25sp"
        android:textColor="@android:color/black"
        android:background="@null"
        android:id="@+id/increment"
        android:text="+"/>
</LinearLayout>
```

Methods
-------

Here is the list of methods with definitions.

### setMin(int value)
Sets minimum value allowed

### getMin()
Gets minimum value

### setMax(int value)
Sets maximum value allowed

### getMax()
Gets maximum value allowed

### setUnit(int value)
Sets unit value for increment/decrement operation

### getUnit()
Gets unit value 

### setValue(int value)
Sets NumberPicker current value 

### getValue()
Gets NumberPicker current value

### setActionEnabled(ActionEnum action, boolean enabled)
Enables or disables Increment/Decrement buttons

### setDisplayFocusable(boolean focusable)
Enables or disables NumberPicker editable via keyboard

### increment()
NumberPicker will be incremented by `defined` unit value

### increment(int unit)
NumberPicker will be incremented by `given` unit value

### decrement()
NumberPicker will be decremented by `defined` unit vale

### decrement(int unit)
NumberPicker will be decremented by `given` unit value

### refresh()
NumberPicker will be refreshed with already defined value

### clearFocus()
NumberPicker will lose the focus

### valueIsAllowed(int value)
Checks whether given value is acceptable or not

### setLimitExceededListener(LimitExceededListener limitExceededListener)

### setValueChangedListener(ValueChangedListener valueChangedListener)

### setOnEditorActionListener(OnEditorActionListener onEditorActionListener)

### setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener)

Listeners
---------

### LimitExceededListener

This is triggered when you try to set lower or higher than the given min/max limits

```java
public class DefaultLimitExceededListener implements LimitExceededListener {

    public void limitExceeded(int limit, int exceededValue) {

        String message = String.format("NumberPicker cannot set to %d because the limit is %d.", exceededValue, limit);
        Log.v(this.getClass().getSimpleName(), message);
    }
}
```

### ValueChangedListener

This is triggered when the NumberPicker is incremented or decremented.

*Note:* `setValue` method will not trigger this listener.

```java
public class DefaultValueChangedListener implements ValueChangedListener {

    public void valueChanged(int value, ActionEnum action) {

        String actionText = action == ActionEnum.MANUAL ? "manually set" : (action == ActionEnum.INCREMENT ? "incremented" : "decremented");
        String message = String.format("NumberPicker is %s to %d", actionText, value);
        Log.v(this.getClass().getSimpleName(), message);
    }
}
```

### OnEditorActionListener

This is triggered when you click "**done**" button on keyboard after you edit current value.

*Note:* "**done**" button can be changed on xml so this listener should be overrided according to new IME option. 

### OnFocusChangeListener
This is triggered when `clearFocus()` is called which helps to set new value when the focus lost
