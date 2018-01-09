package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ron on 04-08-2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    int backgroundColor;
    MediaPlayer mMediaPlayer;

    public WordAdapter(Activity context, ArrayList<Word> androidWord, int backgroundColor) {
        super(context, 0, androidWord);
        this.backgroundColor = backgroundColor;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Word currentWord = getItem(position);

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        TextView mMiwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        mMiwokTextView.setText(currentWord.getMiwokTranslation());

        TextView mDefaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        mDefaultTextView.setText(currentWord.getDefaultTranslation());

        ImageView mMiwokImageView = (ImageView) listItemView.findViewById(R.id.miwok_image_view);

        //set linearLayout reference of the list Item layout
        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.miwok_linerlayout);


        int color = ContextCompat.getColor(getContext(), backgroundColor);
        linearLayout.setBackgroundColor(color);

        if(currentWord.hasResouceId()) {
            mMiwokImageView.setImageResource(currentWord.getImageResourceId());
        }
        else {
            mMiwokImageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
    }
