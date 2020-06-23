package com.smk.siakad.ui.tabs;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smk.siakad.siswa.HariFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[] {
            "Sen", "Sel", "Rab", "Kam", "Jum", "Sab"  };
    private final Fragment[] PAGES = new Fragment[] {
            new HariFragment("Senin"), new HariFragment("Selasa"), new HariFragment("Rabu")
            , new HariFragment("Kamis"), new HariFragment("Jumat"), new HariFragment("Sabtu")};

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PAGES[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return PAGES.length;
    }
}