package com.shristi.timerv1_alpha_withDrawerBar;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by shristi on 6/22/2016.
 */
public class ReplaceFont {
    public static void replaceDefaultFont(Context context,String nameOfFontBeingReplaced,String nameOfFontInAssest){
        Typeface customFontTypeface=Typeface.createFromAsset(context.getAssets(),nameOfFontInAssest);
        replaceFont(nameOfFontBeingReplaced,customFontTypeface);
    }

    private static void replaceFont(String nameOfFontBeingReplaced, Typeface customFontTypeface) {
        try {
            Field myfield=Typeface.class.getDeclaredField(nameOfFontBeingReplaced);
            myfield.setAccessible(true);
            myfield.set(null,customFontTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
