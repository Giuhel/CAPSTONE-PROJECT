package com.e.therapygame.actividades;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e.therapygame.R;
import com.e.therapygame.actividades.ui.main.SectionsPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ContendorInstruccionesActivity extends AppCompatActivity {

    private LinearLayout linearPuntos;
    private TextView[] puntosSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contendor_instrucciones);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        linearPuntos = findViewById(R.id.LinearPuntos);
        agregarIndicadorPuntos(0);
        viewPager.addOnPageChangeListener(viewListener);

    }

    private void agregarIndicadorPuntos(int pos) {
        puntosSlide = new TextView[8];
        linearPuntos.removeAllViews();

        for (int i=0;i<puntosSlide.length;i++){
            puntosSlide[i]=new TextView(this);
            puntosSlide[i].setText(Html.fromHtml("&#8226"));
            puntosSlide[i].setTextSize(35);
            puntosSlide[i].setTextColor(getResources().getColor(R.color.colorBlancoTrasparente));
            linearPuntos.addView(puntosSlide[i]);
        }

        if (puntosSlide.length>0){
            puntosSlide[pos].setTextColor(getResources().getColor(R.color.colorBlanco));
        }
    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            agregarIndicadorPuntos(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onBackPressed() {

    }
}