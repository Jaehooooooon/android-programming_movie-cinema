package org.sajae.application4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentItemView extends LinearLayout {
    ImageView itemImage;
    TextView itemId;
    TextView itemTime;
    TextView itemComment;
    RatingBar itemRatingBar;

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
        itemRatingBar = (RatingBar) findViewById(R.id.ratingBar2);
    }

    public void setItemImage(int resId) {itemImage.setImageResource(resId);}
    public void setItemId(String userId) {itemId.setText(userId);}
    public void setItemTime(String passTime) {itemTime.setText(passTime);}
    public void setItemComment(String comment) {itemComment.setText(comment);}
    public void setItemRating(float userRating) {itemRatingBar.setRating(userRating);}

}
