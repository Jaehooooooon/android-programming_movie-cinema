package org.sajae.application4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MovieFragment1 extends Fragment {
    int index;
    ImageView movieImage;
    TextView movieTitle;

    FragmentCallback callback;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movie_fragment1, container, false);

        movieImage = (ImageView) rootView.findViewById(R.id.movie_image);
        movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        Button button = (Button) rootView.findViewById(R.id.button);
        parseBundle();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //상세화면 전환하기
                callback.onFragmentSelected(0);
            }
        });

        return rootView;
    }

    private void parseBundle() {
        Bundle bundle = getArguments();

        if (bundle == null) {
            return;
        } else {
            index = bundle.getInt("index");
            int imgId = bundle.getInt("imgId");
            String title = bundle.getString("title");

            movieImage.setImageResource(imgId);
            movieTitle.setText(title);
        }
    }

}
