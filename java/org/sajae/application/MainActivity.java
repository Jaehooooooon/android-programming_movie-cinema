package org.sajae.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    int likeNumber = 15;
    boolean likeState = false;
    int dislikeNumber = 1;
    boolean dislikeState = false;
    Button likeButton;
    Button dislikeButton;
    TextView likeNumberView;
    TextView dislikeNumberView;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        likeButton = (Button) findViewById(R.id.like_button);
        dislikeButton = (Button) findViewById(R.id.dislike_button);
        likeNumberView = (TextView) findViewById(R.id.like_number_view);
        dislikeNumberView = (TextView) findViewById(R.id.dislike_number_view);
        Button commentWriteButton = (Button) findViewById(R.id.comment_write_button);
        Button commentSeeButton = (Button) findViewById(R.id.comment_see_button);
        ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new CommentAdapter();
        adapter.addItem(new CommentItem(R.drawable.user1, "kym71**", "10분전", "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요."));
        adapter.addItem(new CommentItem(R.drawable.user1, "seo72**", "10분전", "적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요."));

        listView.setAdapter(adapter);

        commentWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "작성하기", Toast.LENGTH_SHORT).show();
            }
        });

        commentSeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "모두보기", Toast.LENGTH_SHORT).show();
            }
        });

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

}
