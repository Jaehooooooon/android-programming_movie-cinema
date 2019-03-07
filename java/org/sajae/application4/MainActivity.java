package org.sajae.application4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {
    MovieListFragment movieListFragment;
    MovieDetailsFragment movieDetailsFragment;

    Toolbar toolbar;

    MoviePagerAdapter nullAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        movieListFragment = new MovieListFragment();
        movieDetailsFragment = new MovieDetailsFragment();

        nullAdapter = new MoviePagerAdapter(getSupportFragmentManager());

        //영화 목록(뷰페이저 화면)을 첫 화면으로
        getSupportFragmentManager().beginTransaction().add(R.id.container, movieListFragment).commit();
    }

    //작성하기 화면
    public void showCommentWriteActivity() {
        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        startActivityForResult(intent, 101);
    }
    //모두보기 화면
    public void showCommentSeeActivity() {
        Intent intent = new Intent(getApplicationContext(), CommentSeeActivity.class);
        //모두보기 액티비티로 어레이리스트 넘겨주기
        intent.putParcelableArrayListExtra("items", movieDetailsFragment.adapter.items);

        startActivityForResult(intent, 201);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 101) {
            Toast.makeText(getApplicationContext(), "작성하기에서 돌아옴", Toast.LENGTH_SHORT).show();

            if (intent != null) {
                String contents = intent.getStringExtra("contents");
                float rating = intent.getFloatExtra("rating", 0.0f);

                Collections.reverse(movieDetailsFragment.adapter.items);
                movieDetailsFragment.adapter.addItem(new CommentItem(R.drawable.user1, "seo71**", "1분전",contents, rating));
                Collections.reverse(movieDetailsFragment.adapter.items);
                movieDetailsFragment.adapter.notifyDataSetChanged(); //어댑터 새로고침
            }
        } else if (requestCode == 201) {
            Toast.makeText(getApplicationContext(), "모두보기에서 돌아옴", Toast.LENGTH_SHORT).show();

            if (intent != null) {
                ArrayList<CommentItem> intentitem = intent.getParcelableArrayListExtra("items");
                movieDetailsFragment.adapter.items = intentitem;
                movieDetailsFragment.adapter.notifyDataSetChanged(); //어댑터 새로고침
            }
        }
    }

    public void onFragmentSelected(int position) {
        Fragment curFragment = null;

        if (position == 0) {
            curFragment = movieDetailsFragment;
            toolbar.setTitle("영화 상세");
            movieListFragment.pager.setAdapter(nullAdapter);    //기존의 adapter 해제용(계속 연결돼있으면 페이저가 처음말고는 안보임)
        } else if (position == 1) {
            curFragment = movieListFragment;
            toolbar.setTitle("영화 목록");
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie_list) {
            // 뷰페이저로 이동
            onFragmentSelected(1);
        } else if (id == R.id.nav_movie_api) {

        } else if (id == R.id.nav_book) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
