package com.example.imageviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    ImageButton arrow;
    LinearLayout hiddenView;
    CardView cardView;
    LinearLayout imageContainer;
    Switch switchEnableDisable;
    SeekBar opacityBar;
    ImageView ImagePanel;
    Button originalButton,sepiaButton,invertButton,binairButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView = findViewById(R.id.base_cardview);
        arrow = findViewById(R.id.arrow_button);
        hiddenView = findViewById(R.id.hidden_view);
        imageContainer= findViewById(R.id.ImageContainer);
        switchEnableDisable=findViewById(R.id.switch1);
        opacityBar=findViewById(R.id.opacityBar);
        ImagePanel=findViewById(R.id.ImagePanel);
        originalButton=findViewById(R.id.originalButton);
        sepiaButton=findViewById(R.id.sepiaButton);
        invertButton=findViewById(R.id.invertButton);
        binairButton=findViewById(R.id.binairButton);

        opacityBar.setMax(100);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (hiddenView.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    hiddenView.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    hiddenView.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_baseline_expend_less_24);
                }
            }
        });


        originalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePanel.setColorFilter(null);
            }
        });
        sepiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePanel.setColorFilter(new ColorMatrixColorFilter( getColorMatrixSepia() ) );
            }
        });
        invertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePanel.setColorFilter(new ColorMatrixColorFilter( getColorMatrixInvert() ) );
            }
        });

        binairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePanel.setColorFilter(new ColorMatrixColorFilter( getColorMatrixBinary() ) );
            }
        });




        switchEnableDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
               {
                   ImagePanel.setBackgroundResource(R.drawable.border);
               }else
               {
                   ImagePanel.setBackgroundResource(0);
               }
            }
        });

        opacityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                //setAlpha (float alpha)
                //alpha between 0 and 1

                //ImagePanel.setAlpha((float)1);
                int j = 100 - progress;
                ImagePanel.setAlpha((float)j/100);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }});





    }


    private ColorMatrix getColorMatrixSepia() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrix colorScale = new ColorMatrix();
        colorScale.setScale(1, 1, 0.8f, 1);
// Convert to grayscale, then apply brown color
        colorMatrix.postConcat(colorScale);
        return colorMatrix;
    }
    private ColorMatrix getColorMatrixBinary() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        float m = 255f;
        float t = -255 * 128f;
        ColorMatrix threshold = new ColorMatrix(new float[]{
                m, 0, 0, 1, t,
                0, m, 0, 1, t,
                0, 0, m, 1, t,
                0, 0, 0, 1, 0
        });
// Convert to grayscale, then scale and clamp
        colorMatrix.postConcat(threshold);
        return colorMatrix;
    }
    private ColorMatrix getColorMatrixInvert() {
        return new ColorMatrix(new float[]{
                -1, 0, 0, 0, 255,
                0, -1, 0, 0, 255,
                0, 0, -1, 0, 255,
                0, 0, 0, 1, 0
        });
    }
}