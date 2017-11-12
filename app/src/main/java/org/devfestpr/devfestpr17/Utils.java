package org.devfestpr.devfestpr17;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;

/**
 * Palestra - Animacoes fluidas no Android
 * DevFestPR 2017 - 11/11/2017
 * @author Renato Peterman
 */

public class Utils {

    public static void launch(Context source, Class<? extends Activity> activity) {
        source.startActivity(new Intent(source, activity));
    }

    public static float dpToPx(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
