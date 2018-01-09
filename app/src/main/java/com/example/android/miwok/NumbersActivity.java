package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Creating up button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize AudioManger object
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Declare and initialize an ArrayList to represent numbers
        final ArrayList<Word> words = new ArrayList<Word>();

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

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        ListView mListView = (ListView) findViewById(R.id.list_view);

        mListView.setAdapter(adapter);

        //Set onItemClickListener to play sound for each item in the list
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //Audio Focus is achieved

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudio());
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

        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    //AudioFocus is gained
                    mediaPlayer.start();

                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                        focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    //code for AudioManager.AUDIO_LOSS_TRANSIENT and AudioManager
                    //AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK is same. So, one if block for both
                    //using or logical operator
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    //AudioFocus is lost. So, release the resources the mediaPlayer object
                    //is holding.
                    releaseMediaPlayer();
                }
            }
        };

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        //check if media player instance is null or not
        if (mediaPlayer != null) {
            mediaPlayer.release();

            //make the media player null after the resource are released
            mediaPlayer = null;

            //Abandoning audiofocus listener when the mediaPlayer is no longer needed.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}
