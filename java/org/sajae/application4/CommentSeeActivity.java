package org.sajae.application4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class CommentSeeActivity extends AppCompatActivity {
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_see);

        Intent intent = getIntent();
        adapter = new CommentAdapter();

        ArrayList<CommentItem> intentitem = intent.getParcelableArrayListExtra("items");
        adapter.items = intentitem;

        ListView listView = (ListView) findViewById(R.id.listView);

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
        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        startActivityForResult(intent, 202);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 202) {
            Toast.makeText(getApplicationContext(), "작성하기에서 돌아옴", Toast.LENGTH_SHORT).show();

            if (intent != null) {
                String contents = intent.getStringExtra("contents");
                float rating = intent.getFloatExtra("rating", 0.0f);

                Collections.reverse(adapter.items);
                adapter.addItem(new CommentItem(R.drawable.user1, "seo71**", "1분전",contents, rating));
                Collections.reverse(adapter.items);
                //메인에도 넘겨줘야함
                intent.putParcelableArrayListExtra("items", adapter.items);
                setResult(RESULT_OK, intent);

                adapter.notifyDataSetChanged(); //어댑터 새로고침
            }
        }
    }

}
