package org.sajae.application4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.sajae.application4.data.CommentList;
import org.sajae.application4.data.ResponseInfo;

import java.util.Map;

public class CommentWriteActivity extends AppCompatActivity {
    int movieId;
    TextView titleView;
    ImageView gradeView;
    RatingBar ratingBar;
    EditText contentsInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);

        Intent intent = getIntent();

        movieId = intent.getIntExtra("movieId", 0);
        String movieTitle = intent.getStringExtra("title");
        int movieGrade = intent.getIntExtra("grade", 15);

        titleView = findViewById(R.id.write_title_view);
        gradeView = findViewById(R.id.write_grade_view);
        ratingBar = findViewById(R.id.write_rating_bar);
        contentsInput = findViewById(R.id.contents_input);
        Button saveButton = findViewById(R.id.save_button);
        Button cancelButton = findViewById(R.id.cancel_button);

        titleView.setText(movieTitle);
        selectGradeImage(movieGrade);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //returnToPre();
                requestCreateComment(movieId);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void requestCreateComment(final int movieId) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/createComment";
        url += "?" + "id=" + movieId + "&writer=sjh&rating=" + ratingBar.getRating() + "&contents=" + contentsInput.getText().toString();

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,    //GET 방식은 요청 path가 필요
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                        setResult(RESULT_OK);

                        finish();
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
            Toast.makeText(getApplicationContext(), "한줄평 생성 완료.", Toast.LENGTH_SHORT).show();
        }
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

}
