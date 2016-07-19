package com.bluetea.abbiswood.helper;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.FlipEnter.FlipHorizontalEnter;
import com.flyco.animation.FlipExit.FlipHorizontalExit;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

/**
 * Created by MEK on 6/26/2016.
 */
public class Commons {

    public static void showErrorAlert(Context mContext, String content){
        BaseAnimatorSet mBasIn = new BounceTopEnter();
        BaseAnimatorSet mBasOut = new SlideBottomExit();


        final NormalDialog dialog = new NormalDialog(mContext);
        dialog.content(content)//
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)
                .title(":(")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .btnNum(1)
                .btnText("Okay")
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                });
    }

    public static void showInfoAlert(Context mContext, String content, String title){

        final NormalDialog dialog = new NormalDialog(mContext);
        dialog.content(content)//
                .title(title)
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(23)//
                .showAnim(new FlipHorizontalEnter())//
                .dismissAnim(new FlipHorizontalExit())//
                .btnNum(1)
                .btnText("Okay")
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                });
    }

    public static String getDeviceCode(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
