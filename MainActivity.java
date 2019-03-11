package org.sajae.application4;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.sajae.application4.data.CommentItem;
import org.sajae.application4.data.CommentList;
import org.sajae.application4.data.MovieInfo;
import org.sajae.application4.data.MovieList;
import org.sajae.application4.data.ResponseInfo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {

    MovieListFragment movieListFragment;
    MovieDetailsFragment movieDetailsFragment;

    Toolbar toolbar;

    MoviePagerAdapter nullAdapter;

    MovieList movieList;
    MovieList movieDetail;

    private static final int MAIN_WRITE_REQUESTCODE = 101;
    private static final int MAIN_SEE_REQUESTCODE = 201;
    static final int DETAIL_FRAG_POSITION = 1;
    static final int LIST_FRAG_POSITION = 0;
    private static int curPosition;

    // 뒤로가기 버튼 입력시간이 담길 long 객체
    private long pressedTime = 0;
    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    @Override
    public void onBackPressed() {
        if(curPosition == DETAIL_FRAG_POSITION) {
            onFragmentSelected(LIST_FRAG_POSITION);
        } else {
            if ( pressedTime == 0 ) {
                Snackbar.make(findViewById(R.id.drawer_layout),
                        " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    Snackbar.make(findViewById(R.id.drawer_layout),
                            " 한 번 더 누르면 종료됩니다." , Snackbar.LENGTH_LONG).show();
                    pressedTime = 0 ;
                }
                else {
                    super.onBackPressed();
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList = new MovieList();
        movieDetail = new MovieList();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        movieListFragment = new MovieListFragment();
        movieDetailsFragment = new MovieDetailsFragment();

        nullAdapter = new MoviePagerAdapter(getSupportFragmentManager());

        requestMovieList(LIST_FRAG_POSITION);

        //프래그먼트들을 container에 추가해놓기
        getSupportFragmentManager().beginTransaction().add(R.id.container, movieDetailsFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, movieListFragment).commit();
    }

    public void requestMovieDetails(final int index) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + AppHelper.readMovie;
        url += "?" + "id=" + toString().valueOf(index);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,    //GET 방식은 요청 path가 필요
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response, index);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
                    }
                }
        );  //POST 방식인 경우(파라미터 있는 경우) 중괄호 추가 후 getParams 메서드를 override

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void requestMovieList(final int index) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + AppHelper.readMovieList;
        url += "?" + "type=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,    //GET 방식은 요청 path가 필요
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response, index);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
                    }
                }
        );  //POST 방식인 경우(파라미터 있는 경우) 중괄호 추가 후 getParams 메서드를 override

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void processResponse(String response, int index) {
        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if (info.code == 200) {
            if (index == LIST_FRAG_POSITION) {
                movieList = gson.fromJson(response, MovieList.class);

                setMovieList();
            } else {
                movieDetail = gson.fromJson(response, MovieList.class);

                setMovieDetails();
            }
        }
    }

    public void setMovieDetails() {
        MovieInfo movieInfo = movieDetail.result.get(0);
        Log.e("Detail", "setDeails");
        movieDetailsFragment.setDetails(movieInfo.id, movieInfo.thumb, movieInfo.title, movieInfo.grade, movieInfo.date, movieInfo.genre, movieInfo.duration, movieInfo.like, movieInfo.dislike, movieInfo.reservation_grade,
                movieInfo.reservation_rate, movieInfo.audience_rating, movieInfo.audience, movieInfo.synopsis, movieInfo.director, movieInfo.actor, movieInfo.reviewer_rating);
    }

    public void setMovieList() {
        if (movieListFragment.pagerAdapter.items.size() == 0) {
            for (int i = 0; i < movieList.result.size(); i++) {
                MovieInfo movieInfo = movieList.result.get(i);
                movieListFragment.addPagerItem(movieListFragment.createMovieFragment(movieInfo.id, movieInfo.image, movieInfo.id + ". " + movieInfo.title, movieInfo.reservation_rate, movieInfo.grade));

                Toast.makeText(this, "어댑터의 아이탬 로딩 중 : " + (i+1), Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "로딩 완료", Toast.LENGTH_SHORT).show();
            movieListFragment.pagerAdapter.notifyDataSetChanged();
        }
    }

    //작성하기 화면
    public void showCommentWriteActivity() {
        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        intent.putExtra("movieId", movieDetailsFragment.movieId);
        intent.putExtra("title", movieDetailsFragment.movieTitle);
        intent.putExtra("grade", movieDetailsFragment.movieGrade);
        startActivityForResult(intent, MAIN_WRITE_REQUESTCODE);
    }
    //모두보기 화면
    public void showCommentSeeActivity() {
        Intent intent = new Intent(getApplicationContext(), CommentSeeActivity.class);
        intent.putExtra("movieId", movieDetailsFragment.movieId);
        intent.putExtra("title", movieDetailsFragment.movieTitle);
        intent.putExtra("grade", movieDetailsFragment.movieGrade);
        intent.putExtra("reviewerRating", movieDetailsFragment.reviewerRating);
        startActivityForResult(intent, MAIN_SEE_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == MAIN_WRITE_REQUESTCODE) {
            Toast.makeText(getApplicationContext(), "작성하기에서 돌아옴", Toast.LENGTH_SHORT).show();
        } else if (requestCode == MAIN_SEE_REQUESTCODE) {
            Toast.makeText(getApplicationContext(), "모두보기에서 돌아옴", Toast.LENGTH_SHORT).show();
        }
        movieDetailsFragment.adapter.items.clear();
        movieDetailsFragment.requestCommentList(movieDetailsFragment.movieId);
    }

    public void onFragmentSelected(int position) {
        Fragment curFragment;
        if (position == LIST_FRAG_POSITION) {
            getSupportFragmentManager().beginTransaction().hide(movieDetailsFragment).commit();
            curFragment = movieListFragment;
            curPosition = LIST_FRAG_POSITION;
            toolbar.setTitle("영화 목록");
        } else {
            getSupportFragmentManager().beginTransaction().hide(movieListFragment).commit();
            curFragment = movieDetailsFragment;
            curPosition = DETAIL_FRAG_POSITION;
            toolbar.setTitle("영화 상세");
            requestMovieDetails(position);
        }

        getSupportFragmentManager().beginTransaction().show(curFragment).commit();
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
            onFragmentSelected(LIST_FRAG_POSITION);
        } else if (id == R.id.nav_movie_api) {

        } else if (id == R.id.nav_book) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
