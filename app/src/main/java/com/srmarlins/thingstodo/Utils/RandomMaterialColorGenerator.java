package com.srmarlins.thingstodo.Utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.srmarlins.thingstodo.R;

/**
 * Created by jfowler on 12/4/15.
 */
public class RandomMaterialColorGenerator {

    public int[] mColors;

    public RandomMaterialColorGenerator(Context context){
        if(context != null) {
            mColors = context.getResources().getIntArray(R.array.material_colors);
        }
    }

    public int getRandomColor(){
        int size = mColors.length;
        double rand = Math.random();
        rand = rand * 10000;
        long num = (int) rand;
        int index = (int)num % size;
        return mColors[index];
    }

    public int getNextColor(int pos){
        return mColors[pos + 1];
    }


}
