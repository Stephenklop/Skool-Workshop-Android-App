package com.example.skoolworkshop2.logic.converters;

public class DateConverter {
    public static String datePickerConverter(int year, int month, int dayOfMonth, int hours, int minutes, int seconds, String timezone) {
        StringBuilder result = new StringBuilder();

        // Year
        result.append(year).append("-");

        // Month
        if (month < 10) {
            result.append("0").append(month).append("-");
        } else {
            result.append(month).append("-");
        }

        // Day of month
        if (dayOfMonth < 9) {
            result.append("0").append(dayOfMonth).append("T");
        } else {
            result.append(dayOfMonth).append("T");
        }

        // Hours
        if (hours < 9) {
            result.append("0").append(hours);
        } else {
            result.append(hours);
        }

        result.append(":");

        // Minutes
        if (minutes < 9) {
            result.append("0").append(minutes);
        } else {
            result.append(minutes);
        }

        result.append(":");

        // Seconds
        if (seconds < 9) {
            result.append("0").append(seconds);
        } else {
            result.append(seconds);
        }

        // Timezone
        result.append("+").append(timezone);

        return result.toString();
    }
}
