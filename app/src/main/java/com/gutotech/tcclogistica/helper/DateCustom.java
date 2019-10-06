package com.gutotech.tcclogistica.helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateCustom {

    public static String getData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(System.currentTimeMillis());
    }

    public static String getDia() {
        return getData().split("/")[0];
    }

    public static String getMes() {
        return getData().split("/")[1];
    }

    public static String getAno() {
        return getData().split("/")[2];
    }

    public static String getHorario() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return dateFormat.format(System.currentTimeMillis());
    }

    public static int getHora() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        return Integer.parseInt(dateFormat.format(System.currentTimeMillis()));
    }

    public static int getMinuto() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm", Locale.getDefault());
        return Integer.parseInt(dateFormat.format(System.currentTimeMillis()));
    }

    public static int getSegundo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ss", Locale.getDefault());
        return Integer.parseInt(dateFormat.format(System.currentTimeMillis()));
    }
}
