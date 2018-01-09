package com.example.android.miwok;

/**
 * Created by Ron on 04-08-2017.
 */

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mAudio;
    private int mImageResourceId = EMPTY_VALUE;

    private static final int EMPTY_VALUE = -1;

    public Word(String defaultTranslation, String miwokTranslation, int audio) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudio = audio;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audio) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudio = audio;
    }

    public int getAudio() {
        return mAudio;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mAudio=" + mAudio +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasResouceId() {
        return mImageResourceId != EMPTY_VALUE;
    }
}
