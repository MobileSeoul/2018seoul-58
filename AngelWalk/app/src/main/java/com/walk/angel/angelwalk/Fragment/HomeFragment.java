package com.walk.angel.angelwalk.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.walk.angel.angelwalk.Activity.BoardActivity;
import com.walk.angel.angelwalk.Activity.SightsActivity;
import com.walk.angel.angelwalk.Activity.WheelchairChargingActivity;
import com.walk.angel.angelwalk.R;

public class HomeFragment extends Fragment {

    private Button btnCard, btnTourism, btnCharging, btnPost;
    private ImageView mainImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup HomeFragment = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        mainImage = (ImageView) HomeFragment.findViewById(R.id.mainImage);
        //btnCard = (Button) HomeFragment.findViewById(R.id.btnCard);

        btnTourism = (Button) HomeFragment.findViewById(R.id.btnTourism);
        btnTourism.setOnClickListener(btnClickListener);

        btnCharging = (Button) HomeFragment.findViewById(R.id.btnCharging);
        btnCharging.setOnClickListener(btnClickListener);

        btnPost = (Button) HomeFragment.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(btnClickListener);

        return HomeFragment;
    }


    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnTourism:
                    Intent intentTourism = new Intent(getView().getContext(), SightsActivity.class);
                    startActivity(intentTourism);
                    break;

                case R.id.btnCharging:
                    Intent intentCharging = new Intent(getView().getContext(), WheelchairChargingActivity.class);
                    startActivity(intentCharging);
                    break;

                case R.id.btnPost:
                    Intent intentBoard = new Intent(getView().getContext(), BoardActivity.class);
                    startActivity(intentBoard);
            }
        }
    };
}
