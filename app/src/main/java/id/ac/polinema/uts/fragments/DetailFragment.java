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

import androidx.fragment.app.Fragment;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Weeks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.ac.polinema.uts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView dateSelected, totalDayResult, totalWeekResult, totalMonthResult, totalYearResult,
            totalHourResult, totalMinuteResult, totalSecondResult, zodiacResult;
    Button calculateButton, clearButton;
    Calendar calendar;
    DatePickerDialog dpd;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        calculateButton = view.findViewById(R.id.button_calculate_detail);
        dateSelected = view.findViewById(R.id.birthday_date);
        totalYearResult = view.findViewById(R.id.total_years_result);
        totalMonthResult = view.findViewById(R.id.total_months_result);
        totalWeekResult = view.findViewById(R.id.total_weeks_result);
        totalDayResult = view.findViewById(R.id.total_days_result);
        totalHourResult = view.findViewById(R.id.total_hours_result);
        totalMinuteResult = view.findViewById(R.id.total_minutes_result);
        totalSecondResult = view.findViewById(R.id.total_seconds_result);
        zodiacResult = view.findViewById(R.id.zodiac_result);
        clearButton = view.findViewById(R.id.button_clear);

        dateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dateSelected.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTodayDate = dateSelected.getText().toString();
                DateTime birthdayDate = convertToDateTime(getTodayDate);
                displayAgeAnalysis(birthdayDate);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalDayResult.setText("0");
                totalWeekResult.setText("0");
                totalMonthResult.setText("0");
                totalYearResult.setText("0");
                totalHourResult.setText("0");
                totalMinuteResult.setText("0");
                totalSecondResult.setText("0");
                zodiacResult.setText("Pig");
            }
        });
        return view;
    }

    private void displayAgeAnalysis(DateTime birthdayDate) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateToday = df.format(c);
        DateTime todayDateTime = convertToDateTime(dateToday);
        Period dateDifferencePeriod = displayBirthdayResult(todayDateTime, birthdayDate);
        int getDateInDays = dateDifferencePeriod.getDays();
        float getDateInWeeks = (float) getDateInDays / 7 + Weeks.weeksBetween(new DateTime(birthdayDate), new DateTime(todayDateTime)).getWeeks();
        int getDateInMonths = dateDifferencePeriod.getMonths();
        int getDateInYears = dateDifferencePeriod.getYears();
        int mDay = getDateInDays;
        int mMonth = getDateInMonths + (getDateInYears * 12);
        int hours = mDay * 24;
        int minutes = mDay * 24 * 60;
        int seconds = mDay * 24 * 60 * 60;
        totalYearResult.setText(Html.fromHtml(String.valueOf(getDateInYears)));
        totalMonthResult.setText(Html.fromHtml(String.valueOf(mMonth)));
        totalWeekResult.setText(Html.fromHtml(String.valueOf(getDateInWeeks)));
        totalDayResult.setText(Html.fromHtml(String.valueOf(mDay)));
        totalHourResult.setText("±" + Html.fromHtml(String.valueOf(hours)));
        totalMinuteResult.setText("±" + Html.fromHtml(String.valueOf(minutes)));
        totalSecondResult.setText("±" + Html.fromHtml(String.valueOf(seconds)));
        zodiacResult.setText(Html.fromHtml(displayZodiacResult(birthdayDate)));
    }

    private String[] convertStringToArray(String stringToConvert){
        String[] newStringArray = stringToConvert.split("/");
        return newStringArray;
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

    private Period displayBirthdayResult(DateTime dateToday, DateTime birthdayDate){
        Period dateDifferencePeriod = new Period(birthdayDate, dateToday, PeriodType.yearMonthDayTime());
        return dateDifferencePeriod;
    }

    private String displayZodiacResult(DateTime birthdayDate){
        String astro_sign="";
        int month = birthdayDate.getMonthOfYear();
        int day = birthdayDate.getDayOfMonth();
        // checks month and date within the
        // valid range of a specified zodiac
        if (month == 12){
            if (day < 22)
                astro_sign = "Sagittarius";
            else
                astro_sign ="capricorn";
        }

        else if (month == 1){
            if (day < 20)
                astro_sign = "Capricorn";
            else
                astro_sign = "aquarius";
        }

        else if (month == 2){
            if (day < 19)
                astro_sign = "Aquarius";
            else
                astro_sign = "pisces";
        }

        else if(month == 3){
            if (day < 21)
                astro_sign = "Pisces";
            else
                astro_sign = "aries";
        }
        else if (month == 4){
            if (day < 20)
                astro_sign = "Aries";
            else
                astro_sign = "taurus";
        }

        else if (month == 5){
            if (day < 21)
                astro_sign = "Taurus";
            else
                astro_sign = "gemini";
        }

        else if( month == 6){
            if (day < 21)
                astro_sign = "Gemini";
            else
                astro_sign = "cancer";
        }

        else if (month == 7){
            if (day < 23)
                astro_sign = "Cancer";
            else
                astro_sign = "leo";
        }

        else if( month == 8){
            if (day < 23)
                astro_sign = "Leo";
            else
                astro_sign = "virgo";
        }

        else if (month == 9){
            if (day < 23)
                astro_sign = "Virgo";
            else
                astro_sign = "libra";
        }

        else if (month == 10){
            if (day < 23)
                astro_sign = "Libra";
            else
                astro_sign = "scorpio";
        }

        else if (month == 11){
            if (day < 22)
                astro_sign = "scorpio";
            else
                astro_sign = "sagittarius";
        }
        return astro_sign;
    }
}