package com.example.technotez;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.technotez.UploadFragments.ImageFragment;
import com.example.technotez.UploadFragments.PdfFragment;
import com.example.technotez.UploadFragments.VideoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new PdfFragment();
        }
        else if(position==1){
            return new ImageFragment();
        }
        else{
            return new VideoFragment();
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
