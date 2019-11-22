package com.example.building.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.building.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageDialog extends DialogFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_image, container);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.scaleImageView);
        photoView.setImageResource(R.mipmap.map);
        photoView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }
}
