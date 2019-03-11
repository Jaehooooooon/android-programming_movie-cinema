package org.sajae.application4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieListFragment extends Fragment {
    static int preMovieId;
    ViewPager pager;
    MoviePagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_list_fragment, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.list_pager);
        pager.setOffscreenPageLimit(6);

        pagerAdapter = new MoviePagerAdapter(getChildFragmentManager());

        pager.setAdapter(pagerAdapter);

        return rootView;
    }

    private Bundle createBundle(int index, String image, String title, float reservation_rate, int grade) {
        Bundle bundle = new Bundle();

        bundle.putInt("index", index);
        bundle.putString("image", image);
        bundle.putString("title", title);
        bundle.putFloat("reservation_rate", reservation_rate);
        bundle.putInt("grade", grade);

        return bundle;
    }

    public MovieFragment1 createMovieFragment(int index, String image, String title, float reservation_rate, int grade) {
        MovieFragment1 moviePage = new MovieFragment1();
        moviePage.setArguments(createBundle(index, image, title, reservation_rate, grade));

        return moviePage;
    }

    public void addPagerItem(MovieFragment1 moviePage) {
        pagerAdapter.addItem(moviePage);
    }


}
