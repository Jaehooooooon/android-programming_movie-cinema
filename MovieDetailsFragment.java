package org.sajae.application4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.sajae.application4.data.CommentItem;
import org.sajae.application4.data.CommentList;
import org.sajae.application4.data.ResponseInfo;

import static org.sajae.application4.MainActivity.LIST_FRAG_POSITION;

public class MovieDetailsFragment extends Fragment implements MainActivity.OnBackPressedListener{
    int movieId=1;
    ImageLoadTask task;
    String movieTitle;
    int movieGrade;
    int likeNumber=0;
    int dislikeNumber=0;
    String movieDate;
    String movieGenre;
    int movieDuration;
    int movieReservationGrade;
    float movieReservationRate;
    float movieAudienceRating;
    int movieAudience;
    String movieSynopsis;
    String movieDirector;
    String movieActor;

    boolean likeState = false;
    boolean dislikeState = false;
    Button likeButton;
    Button dislikeButton;

    ImageView imageView;
    TextView titleView;
    ImageView gradeView;
    TextView dateView;
    TextView genreView;
    TextView durationView;
    TextView likeNumberView;
    TextView dislikeNumberView;
    TextView reservationGradeView;
    TextView reservationRateView;
    RatingBar audienceRatingBar;
    TextView audienceRatingView;
    TextView audienceView;
    TextView synopsisView;
    TextView directorView;
    TextView actorView;
    float reviewerRating;

    FragmentCallback callback;

    ListView listView;
    CommentList commentList;
    CommentAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setOnBackPressedListener(this);

        if (context instanceof FragmentCallback) {
            callback = (FragmentCallback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (callback != null) {
            callback = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_details_fragment, container, false);

        imageView = rootView.findViewById(R.id.image_view);
        titleView = rootView.findViewById(R.id.title_view);
        gradeView = rootView.findViewById(R.id.grade_view);
        dateView = rootView.findViewById(R.id.date_view);
        genreView = rootView.findViewById(R.id.genre_view);
        durationView = rootView.findViewById(R.id.duration_view);

        likeButton = (Button) rootView.findViewById(R.id.like_button);
        dislikeButton = (Button) rootView.findViewById(R.id.dislike_button);
        likeNumberView = (TextView) rootView.findViewById(R.id.like_number_view);
        dislikeNumberView = (TextView) rootView.findViewById(R.id.dislike_number_view);

        reservationGradeView = rootView.findViewById(R.id.reservation_grade_view);
        reservationRateView = rootView.findViewById(R.id.reservation_rate_view);
        audienceRatingBar = rootView.findViewById(R.id.reviewer_ratingBar);
        audienceRatingView = rootView.findViewById(R.id.audience_rating_view);
        audienceView = rootView.findViewById(R.id.audience_view);
        synopsisView = rootView.findViewById(R.id.synopsis_view);
        directorView = rootView.findViewById(R.id.director_view);
        actorView = rootView.findViewById(R.id.actor_view);

        Button commentWriteButton = (Button) rootView.findViewById(R.id.comment_write_button);
        Button commentSeeButton = (Button) rootView.findViewById(R.id.comment_see_button);
        listView = (ListView) rootView.findViewById(R.id.listView);

        adapter = new CommentAdapter();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

//작성하기 버튼
        commentWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "작성하기", Toast.LENGTH_SHORT).show();
                callback.showCommentWriteActivity();
            }
        });
//모두보기 버튼
        commentSeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "모두보기", Toast.LENGTH_SHORT).show();
                callback.showCommentSeeActivity();
            }
        });
//좋아요 버튼
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeState) {//좋아요 눌려있음
                    decrLikeCount();;
                } else {//좋아요 안눌려있음
                    if (dislikeState) {//안좋아요에 눌려있음
                        incrLikeCount();
                        decrDislikeCount();
                    } else {//아무버튼도 안눌려있음
                        incrLikeCount();
                    }
                }
            }
        });
//싫어요 버튼
        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dislikeState) {//안좋아요 눌려있음
                    decrDislikeCount();
                } else {//안좋아요 안눌려있음
                    if (likeState) {//좋아요 눌려있음
                        decrLikeCount();
                        incrDislikeCount();
                    } else {//아무버튼도 안눌려있음
                        incrDislikeCount();
                    }
                }
            }
        });

        return rootView;
    }

    public void requestCommentList(final int movieId) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + movieId + "&limit=2";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,    //GET 방식은 요청 path가 필요
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "에러발생", Toast.LENGTH_SHORT).show();
                    }
                }
        );  //POST 방식인 경우(파라미터 있는 경우) 중괄호 추가 후 getParams 메서드를 override

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    public void processResponse(String response) {
        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if (info.code == 200) {
                commentList = gson.fromJson(response, CommentList.class);

                setCommentList();
        }
    }

    public void setCommentList() {
        for (int i = 0; i < commentList.result.size(); i++) {
            CommentItem commentItem = commentList.result.get(i);
            adapter.addItem(commentItem);
        }
        adapter.notifyDataSetChanged();
    }

    public void resetComment() {
        adapter.items.clear();
        requestCommentList(movieId);
        listView.setAdapter(adapter);
    }

    public void setDetails(int id, String image, String title, int grade, String date, String genre, int duration, int like, int dislike, int reservation_grade,
                              float reservation_rate, float audience_rating, int audience, String synopsis, String director, String actor, float reviewer_rating) {
        movieId = id;
        task = new ImageLoadTask(image, imageView);
        task.execute();
        movieTitle = title;
        movieGrade = grade;
        movieDate = date;
        movieGenre = genre;
        movieDuration = duration;
        likeNumber = like;
        dislikeNumber = dislike;
        movieReservationGrade = reservation_grade;
        movieReservationRate = reservation_rate;
        movieAudienceRating = audience_rating;
        movieAudience = audience;
        movieSynopsis = synopsis;
        movieDirector = director;
        movieActor = actor;
        reviewerRating = reviewer_rating;

        setView();
    }

    public void setView() {
        Log.e("set", "setView");
        titleView.setText(movieTitle);
        selectGradeImage(movieGrade);
        dateView.setText(movieDate);
        genreView.setText(movieGenre);
        durationView.setText(toString().valueOf(movieDuration));
        likeNumberView.setText(toString().valueOf(likeNumber));
        dislikeNumberView.setText(toString().valueOf(dislikeNumber));
        reservationGradeView.setText(toString().valueOf(movieReservationGrade));
        reservationRateView.setText(toString().valueOf(movieReservationRate));
        audienceRatingBar.setRating(movieAudienceRating/2);
        audienceRatingView.setText(toString().valueOf(movieAudienceRating));
        audienceView.setText(toString().valueOf(movieAudience));
        synopsisView.setText(movieSynopsis);
        directorView.setText(movieDirector);
        actorView.setText(movieActor);

        requestCommentList(movieId);
    }

    public void selectGradeImage(int grade) {
        switch(grade) {
            case 12:
                gradeView.setImageResource(R.drawable.ic_12);
                break;
            case 15:
                gradeView.setImageResource(R.drawable.ic_15);
                break;
            case 19:
                gradeView.setImageResource(R.drawable.ic_19);
                break;
            default:
                gradeView.setImageResource(R.drawable.ic_15);
                break;
        }
    }

    public void incrLikeCount() {
        likeState = true;
        likeNumber += 1;
        likeNumberView.setText(String.valueOf(likeNumber));
        likeButton.setBackgroundResource(R.drawable.ic_thumb_up_selected);
    }
    public void decrLikeCount() {
        likeState = false;
        likeNumber -= 1;
        likeNumberView.setText(String.valueOf(likeNumber));
        likeButton.setBackgroundResource(R.drawable.ic_thumb_up);
    }

    public void incrDislikeCount() {
        dislikeState = true;
        dislikeNumber += 1;
        dislikeNumberView.setText(String.valueOf(dislikeNumber));
        dislikeButton.setBackgroundResource(R.drawable.ic_thumb_down_selected);
    }
    public void decrDislikeCount() {
        dislikeState = false;
        dislikeNumber -= 1;
        dislikeNumberView.setText(String.valueOf(dislikeNumber));
        dislikeButton.setBackgroundResource(R.drawable.ic_thumb_down);
    }

    @Override
    public void onBack() {
        Log.e("Other", "onBack()");
        // 리스너를 설정하기 위해 Activity 를 받아옵니다.
        MainActivity activity = (MainActivity)getActivity();
        // 한번 뒤로가기 버튼을 눌렀다면 Listener 를 null 로 해제
        activity.setOnBackPressedListener(null);
        callback.onFragmentSelected(LIST_FRAG_POSITION);
        // Activity 에서도 뭔가 처리하고 싶은 내용이 있다면
        // activity.onBackPressed();
    }

}
