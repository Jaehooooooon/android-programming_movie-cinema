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
    ViewPager pager;
    MoviePagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_list_fragment, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.list_pager);
        pager.setOffscreenPageLimit(6);

        pagerAdapter = new MoviePagerAdapter(getFragmentManager());

        //간략하게 구현
        pagerAdapter.addItem(createMovieFragment(1, R.drawable.image1, "1. 군 도"));
        pagerAdapter.addItem(createMovieFragment(2, R.drawable.image2, "2. 공 조"));
        pagerAdapter.addItem(createMovieFragment(3, R.drawable.image3, "3. 더 킹"));
        pagerAdapter.addItem(createMovieFragment(4, R.drawable.image4, "4. 레지던트 이블"));
        pagerAdapter.addItem(createMovieFragment(5, R.drawable.image5, "5. 럭 키"));
        pagerAdapter.addItem(createMovieFragment(6, R.drawable.image6, "6. 아수라"));

        pager.setAdapter(pagerAdapter);

        return rootView;
    }

    private Bundle createBundle(int index, int imgId, String title) {
        Bundle bundle = new Bundle(3 /* capacity */);

        bundle.putInt("index", index);
        bundle.putInt("imgId", imgId);
        bundle.putString("title", title);

        return bundle;
    }

    public MovieFragment1 createMovieFragment(int index, int imgId, String title) {
        MovieFragment1 moviePage = new MovieFragment1();
        moviePage.setArguments(createBundle(index, imgId, title));

        return moviePage;
    }

}
