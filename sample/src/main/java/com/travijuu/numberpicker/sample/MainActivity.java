package com.travijuu.numberpicker.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.travijuu.numberpicker.library.NumberPicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker_default);
        numberPicker.setMax(15);
        numberPicker.setMin(5);
        numberPicker.setUnit(2);
        numberPicker.setValue(10);

    }
}
