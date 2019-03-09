package org.sajae.application4;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import static org.sajae.application4.MovieListFragment.preMovieId;

public class MovieFragment1 extends Fragment {
    int index;
    String title;
    ImageView movieImage;
    ImageLoadTask task;
    TextView movieTitle;
    TextView movieReservationRate;
    TextView movieGrade;

    FragmentCallback callback;

    //static Bitmap mSaveBm;

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
        //task = null;
        movieImage = rootView.findViewById(R.id.movie_image);
        movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        movieReservationRate = (TextView) rootView.findViewById(R.id.reservation_rate);
        movieGrade = (TextView) rootView.findViewById(R.id.grade);
        Button button = (Button) rootView.findViewById(R.id.button);
        parseBundle();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //상세화면 전환하기
                preMovieId = index;
                callback.onFragmentSelected(index);
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

            title = bundle.getString("title");
            float reservation_rate = bundle.getFloat("reservation_rate");
            int grade = bundle.getInt("grade");
            String image = bundle.getString("image");

            setData(image, title, reservation_rate, grade);
        }
    }

    public void setData(String image, String title, float reservation_rate, int grade) {
        task = new ImageLoadTask(image, movieImage);
        task.execute();
        //writeBm();
        movieTitle.setText(title);
        movieReservationRate.setText(String.valueOf(reservation_rate));
        movieGrade.setText(String.valueOf(grade));
    }

    /*
    public void writeBm() {
        OutputStream outStream = null;
        String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + '/';

        File file = new File(extStorageDirectory, "downimage.PNG");
        try {
            outStream = new FileOutputStream(file);
            mSaveBm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

            Toast.makeText(getContext(),
                    "Saved", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),
                    e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),
                    e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    */
}
