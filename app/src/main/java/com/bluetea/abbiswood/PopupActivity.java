package com.bluetea.abbiswood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.flyco.animation.Attention.Flash;
import com.flyco.animation.Attention.ShakeHorizontal;
import com.flyco.animation.Attention.ShakeVertical;
import com.flyco.animation.FlipEnter.FlipHorizontalEnter;
import com.flyco.animation.FlipExit.FlipHorizontalExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import java.util.Random;

public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        String message = getIntent().getExtras().getString("message");
        if(message != null) {

            final NormalDialog dialog = new NormalDialog(this);
            dialog.content(message)//
                    .title("ABBISWOOD")
                    .style(NormalDialog.STYLE_TWO)//
                    .titleTextSize(23)//
                    .showAnim(new ShakeHorizontal())//
                    .dismissAnim(new ShakeVertical())//
                    .btnNum(1)
                    .btnText("Okay")
                    .show();

            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                            PopupActivity.this.finish();
                        }
                    });

        }
        else
            finish();

    }

    public void close(View view) {
        finish();
    }
}
