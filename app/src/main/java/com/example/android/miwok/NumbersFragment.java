package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //Audio Focus is gained
                        mediaPlayer.start();
                    } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        /*code for AudioManager.AUDIO_LOSS_TRANSIENT and AudioManager
                        AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK is same. So, one if block for both
                        using or logical operator */
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //AudioFocus is lost. So, release the resources the mediaPlayer object
                        //is holding.
                        releaseMediaPlayer();
                    }
                }
            };

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_list, container, false);

        //Initializing audioManager object
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Declare and initialize Arraylist to store word objects
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kennekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        //set ListView Adapter
        listView.setAdapter(wordAdapter);

        //setItemOnClickListener to playing sound for each item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //AudioFocus is achieved
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudio());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        //Check if the mediaPlayer instance in null or not
        if(mediaPlayer != null) {
            //if it is not null, release the mediaPlayer
            mediaPlayer.release();

            //set mediaPlayer equals to null after media player is released
            mediaPlayer = null;

            //Abondon audio focus if the releaseMediaPlayer method is called
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
