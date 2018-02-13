package com.healthcare.Fragments.module_doctor_chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.healthcare.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImage extends Fragment {


    public ShowImage() {
        // Required empty public constructor
    }

    ImageView imageView;
    String url;
    public static ShowImage newInstance(String url){
        ShowImage image=new ShowImage();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        image.setArguments(bundle);
        return image;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_image, container, false);
        Bundle bundle=getArguments();
        imageView=(ImageView)v.findViewById(R.id.showImage);
        if(bundle!=null){
            url=bundle.getString("url");
            Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher).into(imageView);
        }
        return v;
    }

}
