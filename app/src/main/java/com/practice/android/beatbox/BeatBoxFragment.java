package com.practice.android.beatbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Joseph on 6/14/16.
 */

public class BeatBoxFragment extends Fragment {
    // Inflated views
    private RecyclerView mRecyclerView;

    // Other variables
    private BeatBox mBeatBox;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    /********************************************
     * Override Methods
     ********************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBeatBox = new BeatBox(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_beat_box, container, false);

        // Initialize RecyclerView
        mRecyclerView = (RecyclerView) v.findViewById(R.id.beat_box_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    /********************************************
     * RecyclerView Classes
     ********************************************/

    private class SoundHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Sound mSound;
        private Button mButton;

        public SoundHolder(View itemView) {
            super(itemView);

            // Locate views
            mButton = (Button) itemView.findViewById(R.id.list_item_sound_button);
            mButton.setOnClickListener(this);
        }

        public void bindSound(Sound sound) {
            mSound = sound;
            mButton.setText(mSound.getName());
        }

        @Override
        public void onClick(View view) {
            mBeatBox.play(mSound);
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.list_item_sound, parent, false);
            return new SoundHolder(v);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);
            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
