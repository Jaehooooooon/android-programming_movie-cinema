package org.sajae.application;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentItemView extends LinearLayout {
    ImageView itemImage;
    TextView itemId;
    TextView itemTime;
    TextView itemComment;
    TextView itemRecommandCount;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_item_view, this, true);

        itemImage = (ImageView) findViewById(R.id.item_image);
        itemId = (TextView) findViewById(R.id.item_id);
        itemTime = (TextView) findViewById(R.id.item_time);
        itemComment = (TextView) findViewById(R.id.item_comment);
        itemRecommandCount = (TextView) findViewById(R.id.recommand_count_view);
    }

    public void setItemImage(int resId) {itemImage.setImageResource(resId);}
    public void setItemId(String userId) {itemId.setText(userId);}
    public void setItemTime(String passTime) {itemTime.setText(passTime);}
    public void setItemComment(String comment) {itemComment.setText(comment);}
    public void setItemRecommandCount(int recommandCount) {itemRecommandCount.setText(String.valueOf(recommandCount));}

}
