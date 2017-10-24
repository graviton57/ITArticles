package com.havryliuk.itarticles.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public class AppUtils {

    public static final SimpleDateFormat FORMAT_TO =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    public static final  SimpleDateFormat FORMAT_FROM_ITEM_LIST =
            new SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault());


    /**
     * Check for internet connection
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Hide keyboard
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (((Activity) context).getCurrentFocus() != null) {
            try {
                inputManager.hideSoftInputFromWindow(
                        ((AppCompatActivity) context).getCurrentFocus().getWindowToken(), 0);
            } catch (NullPointerException ex) {
                //
            }
        }
    }

     /*
     * Email validation
     */
    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*
    * Format html
    */
    @SuppressWarnings({"deprecation"})
    public static CharSequence fromHtml(@NonNull String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }


}