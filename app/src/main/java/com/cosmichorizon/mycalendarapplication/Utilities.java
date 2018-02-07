package com.cosmichorizon.mycalendarapplication;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jgracia on 22/06/2017.
 */

public class Utilities
{
    static void validateDate(Context context, Date D1)
    {
        Calendar c = Calendar.getInstance();
        c.get(Calendar.MONTH);
    }
}
