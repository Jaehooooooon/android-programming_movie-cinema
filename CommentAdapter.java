package org.sajae.application4;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.sajae.application4.data.CommentItem;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    ArrayList<CommentItem> items = new ArrayList<CommentItem>();

    public void addItem(CommentItem item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentItemView view;
        if (convertView == null) {
            view = new CommentItemView(parent.getContext());
        } else {
            view = (CommentItemView) convertView;
        }

        CommentItem item = items.get(position);

        view.setItemId(item.getId());
        view.setItemImage(/*item.getResId()*/);
        view.setItemWriter(item.getWriter());
        view.setItemTime(item.getTime());
        view.setItemComment(item.getContents());
        view.setItemRating(item.getRating());
        view.setItemRecommend(item.getRecommend());

        return view;
    }

}

