package com.example.technotez.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.technotez.MainFragments.AddFragment;
import com.example.technotez.MainFragments.ProfileFragment;
import com.example.technotez.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout=findViewById(R.id.framelayout);
        meowBottomNavigation=findViewById(R.id.meowbnv);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.add));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.profile));

        meowBottomNavigation.show(1,true);
        replace(new AddFragment());

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch(model.getId()){
                    case 1:
                        replace(new AddFragment());
                        break;

                    case 2:
                        replace(new ProfileFragment());
                        break;
                }
                return null;
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }
}