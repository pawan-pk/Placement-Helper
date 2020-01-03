package in.eduhelp.placementhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    ImageView flash_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        flash_screen=(ImageView) findViewById(R.id.flash_screen);
        flash_screen.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                Intent I=new Intent(MainActivity.this,DashBoardActivity.class);
                startActivity(I);
                finish();
            }
        };
        handler.postDelayed(runnable,1000);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
}
