package id.ac.polinema.uts.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.ac.polinema.uts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BirthdayFragment extends Fragment {
    TextView currentDate, dateBirthday,
            currentBirthdayDay, currentBirthdayMonth, currentBirthdayYear,
            nextBirthdayDay, nextBirthdayMonth, nextBirthdayYear;
    Button calculateButton, clearButton;

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

        currentDate = view.findViewById(R.id.current_date);
        dateBirthday = view.findViewById(R.id.birthday_date);
        calculateButton = view.findViewById(R.id.button_calculate);
        currentBirthdayDay = view.findViewById(R.id.days_in_age);
        currentBirthdayMonth = view.findViewById(R.id.months_in_age);
        currentBirthdayYear = view.findViewById(R.id.years_in_age);
        nextBirthdayDay = view.findViewById(R.id.days_in_birthday);
        nextBirthdayMonth = view.findViewById(R.id.months_in_birthday);
        nextBirthdayYear = view.findViewById(R.id.years_in_birthday);
        clearButton = view.findViewById(R.id.button_clear);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        currentDate.setText(formattedDate);
        dateBirthday.setText(formattedDate);

        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        currentDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        dateBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dateBirthday.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTodayDate = currentDate.getText().toString();
                String getBirthdayDate = dateBirthday.getText().toString();
                DateTime todayDateTime = convertToDateTime(getTodayDate);
                DateTime birthdayDateTime = convertToDateTime(getBirthdayDate);
                if (todayDateTime.compareTo(birthdayDateTime) < 0) {
                    Toast.makeText(getActivity(), "Anda belum lahir!", Toast.LENGTH_SHORT).show();
                } else {
                    displayCurrentBirthday(todayDateTime, birthdayDateTime);
                    displayNextBirthday(todayDateTime, birthdayDateTime);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBirthdayDay.setText("");
                currentBirthdayMonth.setText("");
                currentBirthdayYear.setText("");
                nextBirthdayDay.setText("");
                nextBirthdayMonth.setText("");
                nextBirthdayYear.setText("");
            }
        });
        return view;
    }

    private DateTime convertToDateTime(String stringToConvert) {
        String[] newStringArray = convertStringToArray(stringToConvert);
        int year = Integer.parseInt(newStringArray[2].trim());
        int day = Integer.parseInt(newStringArray[0].trim());
        int month = Integer.parseInt(newStringArray[1].trim());
        LocalDate mLocalDate = new LocalDate(year, month, day);
        DateTime firstDateTime = mLocalDate.toDateTime(LocalTime.fromDateFields(mLocalDate.toDate()));
        return firstDateTime;
    }

    private String[] convertStringToArray(String stringToConvert){
        String[] newStringArray = stringToConvert.split("/");
        return newStringArray;
    }

    private void displayCurrentBirthday(DateTime dateToday, DateTime birthdayDate){
        Period dateDifferencePeriod = displayBirthdayResult(dateToday, birthdayDate);
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        currentBirthdayDay.setText(Html.fromHtml("<h4>Days</h4>" + getDateInDays));
        currentBirthdayMonth.setText(Html.fromHtml("<h4>Months</h4>" + getDateInMonths));
        currentBirthdayYear.setText(Html.fromHtml("<h4>Years</h4>" + getDateInYears));
    }

    private void displayNextBirthday(DateTime dateToday, DateTime birthdayDate){
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        DateTime nextBirthday = birthdayDate.withYear(year);
        DateTime dateToday2 = dateToday.withYear(year);
        Toast.makeText(getActivity(), "Birthday " + nextBirthday.getYear(), Toast.LENGTH_LONG).show();
        Period dateDifferencePeriod;
        if (dateToday2.compareTo(nextBirthday) < 0) {
            dateDifferencePeriod = displayBirthdayResult(nextBirthday, dateToday2);
        } else {
            dateDifferencePeriod = displayBirthdayResult(nextBirthday.plusYears(1), dateToday2);
        }
        int getDateInDays = dateDifferencePeriod.getDays();
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        nextBirthdayDay.setText(Html.fromHtml("<h4>Days</h4>" + getDateInDays));
        nextBirthdayMonth.setText(Html.fromHtml("<h4>Months</h4>" + getDateInMonths));
        nextBirthdayYear.setText(Html.fromHtml("<h4>Years</h4>" + getDateInYears));
    }

    private Period displayBirthdayResult(DateTime dateToday, DateTime birthdayDate){
        Period dateDifferencePeriod = new Period(birthdayDate, dateToday, PeriodType.yearMonthDayTime());
        return dateDifferencePeriod;
    }

}
