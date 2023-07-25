package com.example.technotez.MainFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.technotez.R;
import com.example.technotez.ViewPageAdapter2;
import com.example.technotez.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ProfileFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        tabLayout=view.findViewById(R.id.tab_profile_layout);
        viewPager=view.findViewById(R.id.view_layout_pager);

        ViewPageAdapter2 viewPagerAdapter=new ViewPageAdapter2(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}