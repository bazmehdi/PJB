package com.bazmehdi.pjb.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.bazmehdi.pjb.R;
import com.bazmehdi.pjb.model.ItemModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ResourceType")
public class Constant {

    public static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "Error retrieving API version" + e.getMessage());
        }

        return f.floatValue();
    }
}
