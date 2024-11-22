package com.fpl.Electroland.helper;

import java.text.DecimalFormat;

public class FormatUtils {

    // Method để format double thành String với định dạng #.###
    public static String formatDouble(Double value) {
        if (value == null) {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        return decimalFormat.format(value);
    }

    public static void main(String[] args) {
        // Test
        Double number1 = 123434556789.0;
        Double number2 = 123.0;
        Double number3 = null;

        System.out.println("Formatted: " + formatDouble(number1)); // Output: 1234.568
        System.out.println("Formatted: " + formatDouble(number2)); // Output: 123
        System.out.println("Formatted: " + formatDouble(number3)); // Output: 0
    }
}
