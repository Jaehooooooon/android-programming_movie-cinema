package org.sajae.application4;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.sajae.application4.data.CommentItem;

import java.util.ArrayList;

public class MoviePagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
    ArrayList<MovieFragment1> items = new ArrayList<MovieFragment1>();

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(MovieFragment1 item) {
        items.add(item);
    }

    @Override
    public MovieFragment1 getItem(int position) {
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
