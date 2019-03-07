package org.sajae.application4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MovieDetailsFragment extends Fragment {
    int index = 1;
    int requestCode;

    int likeNumber = 15;
    int dislikeNumber = 1;
    boolean likeState = false;
    boolean dislikeState = false;
    Button likeButton;
    Button dislikeButton;
    TextView likeNumberView;
    TextView dislikeNumberView;

    FragmentCallback callback;

    CommentAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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

        likeButton = (Button) rootView.findViewById(R.id.like_button);
        dislikeButton = (Button) rootView.findViewById(R.id.dislike_button);
        likeNumberView = (TextView) rootView.findViewById(R.id.like_number_view);
        dislikeNumberView = (TextView) rootView.findViewById(R.id.dislike_number_view);
        Button commentWriteButton = (Button) rootView.findViewById(R.id.comment_write_button);
        Button commentSeeButton = (Button) rootView.findViewById(R.id.comment_see_button);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        adapter = new CommentAdapter();
        adapter.addItem(new CommentItem(R.drawable.user1, "kym71**", "15분전","적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요.", 4));
        adapter.addItem(new CommentItem(R.drawable.user1, "seo72**", "12분전","적당히 재밌다. 오랜만에 잠 안오는 영화 봤네요.", 2));
        Collections.reverse(adapter.items);

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
