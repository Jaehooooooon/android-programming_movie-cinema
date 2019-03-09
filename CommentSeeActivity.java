package org.sajae.application4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


public class CommentSeeActivity extends AppCompatActivity implements RecommendCallback {
    int movieId;
    String movieTitle;
    int movieGrade;
    ImageView gradeView;

    ListView listView;
    CommentAdapter adapter;
    CommentList commentList;

    private static final int SEE_WRITE_REQUESTCODE = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_see);

        Intent intent = getIntent();

        TextView titleView = findViewById(R.id.see_title_view);
        gradeView = findViewById(R.id.see_grade_view);
        TextView reviewerRatingView = findViewById(R.id.reviewer_rating_view);
        RatingBar reviewerRatingBar = findViewById(R.id.reviewer_ratingBar);
        adapter = new CommentAdapter();

        movieId = intent.getIntExtra("movieId", 0);
        movieTitle = intent.getStringExtra("title");
        movieGrade = intent.getIntExtra("grade", 15);
        titleView.setText(movieTitle);
        selectGradeImage(movieGrade);
        reviewerRatingView.setText(toString().valueOf(intent.getFloatExtra("reviewerRating", 5)));
        reviewerRatingBar.setRating(intent.getFloatExtra("reviewerRating", 5)/2);

        listView = (ListView) findViewById(R.id.listView);
        requestCommentList(movieId);
        listView.setAdapter(adapter);

        Button commentWriteButton = (Button) findViewById(R.id.comment_write_button);
//작성하기 버튼
        commentWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "작성하기", Toast.LENGTH_SHORT).show();
                showCommentWriteActivity();
            }
        });

    }

    public void showCommentWriteActivity() {
        adapter.items.clear();
        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("title", movieTitle);
        intent.putExtra("grade", movieGrade);
        startActivityForResult(intent, SEE_WRITE_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == SEE_WRITE_REQUESTCODE) {
            requestCommentList(movieId);
            listView.setAdapter(adapter);
            Toast.makeText(getApplicationContext(), "작성하기에서 돌아옴", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetComment() {
        adapter.items.clear();
        requestCommentList(movieId);
        listView.setAdapter(adapter);
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

    public void requestCommentList(final int movieId) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + movieId;

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
                        Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
                    }
                }
        );

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

}
