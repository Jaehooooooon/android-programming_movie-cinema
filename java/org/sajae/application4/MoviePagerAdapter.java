package org.sajae.application4;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MoviePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> items = new ArrayList<Fragment>();

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment item) {
        items.add(item);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "페이지 " + position;
    }
}
