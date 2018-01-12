package movies.flag.pt.moviesapp.screens;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import movies.flag.pt.moviesapp.Constants.PreferenceIds;
import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.helpers.SharedPreferencesHelper;

/**
 * Created by user on 28/10/2017.
 */

public class IntroScreen extends Screen{
    private ImageView tape;
    private TextView text;
    private TextView hide;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_screen);
        findViews();
        animation();
     //  verifyFirstUse();
    }

    private void animation() {
        hide.animate().translationXBy(700).setDuration(2000);
    tape.animate().rotation(360).translationXBy(700).setDuration(2000).
    setListener(new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            startActivity(new Intent(IntroScreen.this, MainScreen.class));
            finish();
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }).start();
    }
   /* private void verifyFirstUse() {
        boolean firstUse = false;
        if(SharedPreferencesHelper.
                getStringPreference(PreferenceIds.IS_FIRST_USE) == null){
            firstUse = true;
            SharedPreferencesHelper.savePreference
                    (PreferenceIds.IS_FIRST_USE, "");
        }

    }*/
    private void findViews() {
        tape = (ImageView) findViewById(R.id.intro_screen_tape);
        text = (TextView) findViewById(R.id.intro_screen_title);
        hide = (TextView) findViewById(R.id.intro_screen_hide_text);
    }

}
