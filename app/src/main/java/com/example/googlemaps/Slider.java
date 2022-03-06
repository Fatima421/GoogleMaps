package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class Slider extends PagerAdapter {

    private ArrayList<String> imagesUrl;
    private Context context;

    public Slider() {}

    public Slider(Context context, ArrayList<String> imagesUrl) {
        this.context = context;
        this.imagesUrl = imagesUrl;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
       return imagesUrl.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.item_image, view, false);

        final ImageView imageView = imageLayout.findViewById(R.id.imageView);

        GlideApp.with(context)
                .load(imagesUrl.get(position))
                .into(imageView);

        Objects.requireNonNull(view).addView(imageLayout);

        return imageLayout;
    }

}
