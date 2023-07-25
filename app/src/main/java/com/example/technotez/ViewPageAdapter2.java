package com.example.technotez;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.technotez.UploadFragments.ImageFragment;
import com.example.technotez.UploadFragments.PdfFragment;
import com.example.technotez.UploadFragments.VideoFragment;
import com.example.technotez.ViewProfile.ViewImageFragment;
import com.example.technotez.ViewProfile.ViewPdfFragment;
import com.example.technotez.ViewProfile.ViewVideoFragment;

public class ViewPageAdapter2 extends FragmentPagerAdapter {
    public ViewPageAdapter2(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new ViewPdfFragment();
        }
        else if(position==1){
            return new ViewImageFragment();
        }
        else{
            return new ViewVideoFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Pdf's";
        }
        else if(position==1){
            return "Images";
        }
        else{
            return "Videos";
        }
    }
}
