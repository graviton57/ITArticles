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

import com.havryliuk.itarticles.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

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

    /**
     * Retrieve the quartile value from an array
     * .
     * @param values The array of data
     * @param lowerPercent The percent cut off. For the lower quartile use 25,
     *      50 for the median, for the upper-quartile use 75
     * @return The quartile value
     */
    public static double quartile(double[] values, double lowerPercent) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("The data array either is null" +
                    " or does not contain any data.");
        }
        double[] v = new double[values.length];
        System.arraycopy(values, 0, v, 0, values.length);
        Arrays.sort(v);
        int n = (int) Math.round(v.length * lowerPercent / 100);
        return v[n];
    }


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

    public static String getCategoryName(Context context, int categoryId){
        String categoryName = context.getResources()
                .getStringArray(R.array.articles_categories_values)[categoryId];
        Timber.d("categoryIndex=%d, categoryName=%s", categoryId, categoryName);
        if (categoryName.equals(context.getString(R.string.item_filter_all_values))){
            return null;
        }
        return categoryName;
    }


}