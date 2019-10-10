package id.ac.polinema.uts.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import id.ac.polinema.uts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BirthdayFragment extends Fragment {
    TextView curentDateDisplay, currentDateButton;

    Calendar calendar;
    DatePickerDialog dpd;

    public BirthdayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);
        curentDateDisplay = view.findViewById(R.id.current_date);
        currentDateButton = view.findViewById(R.id.display_today_date);

        currentDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        curentDateDisplay.setText(mDay + "/" + mMonth + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });
        return view;
    }
}
