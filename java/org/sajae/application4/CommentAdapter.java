package org.sajae.application4;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        CommentItemView view = null;
        if (convertView == null) {
            view = new CommentItemView(parent.getContext());
        } else {
            view = (CommentItemView) convertView;
        }

        CommentItem item = items.get(position);

        view.setItemImage(item.getResId());
        view.setItemId(item.getUserId());
        view.setItemTime(item.getPassTime());
        view.setItemComment(item.getComment());
        view.setItemRating(item.getUserRating());

        return view;
    }

}

