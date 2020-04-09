package com.cnergee.service.util;

import android.app.Activity;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;

import com.cnergee.broadbandservice.R;

import com.dd.morphingbutton.MorphingButton;


public class MorphButtonUtility {

    private final Activity mActivity;

    public MorphButtonUtility(Activity activity) {
        mActivity = activity;
    }
    public int integer() {
        return mActivity.getResources().getInteger(R.integer.mb_animation);
    }

    private int dimen(@DimenRes int resId) {
        return (int) mActivity.getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return mActivity.getResources().getColor(resId);
    }

    /**
     * Converts morph button ot square shape
     *
     * @param btnMorph the button to be converted
     * @param duration time period of transition
     */
    public void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_328))
                .height(dimen(R.dimen.mb_height_48))
                .color(color(R.color.mb_blue))
                .colorPressed(color(R.color.mb_blue_dark))
                .text(mActivity.getString(R.string.mb_button));
        btnMorph.morph(square);
    }

    /**
     * Converts morph button into success shape
     *
     * @param btnMorph the button to be converted
     */
    public void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer())
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_green))
                .colorPressed(color(R.color.mb_green_dark))
                .icon(R.drawable.ic_done);
        btnMorph.morph(circle);
    }

    /**
     * Converts morph button ot square shape
     *
     * @param btnMorph the button to be converted
     * @param duration time period of transition
     */
    public void morphToGrey(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_328))
                .height(dimen(R.dimen.mb_height_48))
                .color(color(R.color.mb_gray))
                .colorPressed(color(R.color.mb_gray))
                .text(mActivity.getString(R.string.mb_button));
        btnMorph.morph(square);
    }
}
