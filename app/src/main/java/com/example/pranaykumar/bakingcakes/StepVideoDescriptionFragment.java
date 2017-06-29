package com.example.pranaykumar.bakingcakes;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 27-06-2017.
 */

public class StepVideoDescriptionFragment extends Fragment {

  public interface OnButtonClickedInterface {

    void OnPreNextButtonClicked(int position, Bundle bundle);
  }

  private StepVideoDescriptionFragment.OnButtonClickedInterface mListener;

  private SimpleExoPlayerView playerView;
  private SimpleExoPlayer player;
  private long playbackPosition;
  private int currentWindow;
  private boolean playWhenReady = true;

  String videoUrl = "";

  TextView descTxtView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater
        .inflate(R.layout.fragment_step_video_description, container, false);
    final Bundle bundle = getArguments();
    final int position = bundle.getInt("position");
    ArrayList<String> currentStep = bundle.getStringArrayList(String.valueOf(position));
    videoUrl = currentStep.get(2);
    playerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
    if (videoUrl.equals("")) {
      Log.d("O_MY", currentStep.get(0));
      playerView.setVisibility(View.GONE);
    }
    boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
    int orientation = getResources().getConfiguration().orientation;
    if (!isTablet && Configuration.ORIENTATION_LANDSCAPE == orientation) {
      if (container != null) {
        container.removeAllViews();
      }
      return view;
    } else {
      mListener = (OnButtonClickedInterface) getActivity();
      descTxtView = (TextView) view.findViewById(R.id.textViewDescription);
      descTxtView.setText(currentStep.get(1));
      Log.d("O_MY",
          "Position=" + String.valueOf(position) + "Bundle=" + String.valueOf(bundle.size() - 2));
      if (!isTablet) {
        Button nxtBtn = (Button) view.findViewById(R.id.nxt_btn);
        Button preBtn = (Button) view.findViewById(R.id.prev_btn);
        if (position == (bundle.size() - 2)) {
          nxtBtn.setVisibility(View.GONE);
          Log.d("O_MY", "LastPosition" + String.valueOf(position) + "Bundle=" + String
              .valueOf(bundle.size() - 2));
        }
        nxtBtn.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {

            mListener.OnPreNextButtonClicked(position + 1, bundle);
          }
        });

        if (position == 0) {
          preBtn.setVisibility(View.GONE);
        }
        preBtn.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            bundle.remove("position");
            mListener.OnPreNextButtonClicked(position - 1, bundle);
          }
        });
      }
      if (container != null) {
        container.removeAllViews();
      }

      return view;
    }

  }

  private void initializePlayer() {
    player = ExoPlayerFactory.newSimpleInstance(
        getActivity(),
        new DefaultTrackSelector(), new DefaultLoadControl());

    playerView.setPlayer(player);

    player.setPlayWhenReady(playWhenReady);
    player.seekTo(currentWindow, playbackPosition);
    Uri uri = Uri.parse(videoUrl);
    MediaSource mediaSource = buildMediaSource(uri);
    player.prepare(mediaSource, true, false);

  }

  private MediaSource buildMediaSource(Uri uri) {
    return new ExtractorMediaSource(uri,
        new DefaultHttpDataSourceFactory(Util.getUserAgent(getActivity()
            , "BakingApp")),
        new DefaultExtractorsFactory(), null, null);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (Util.SDK_INT > 23) {
      initializePlayer();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if ((Util.SDK_INT <= 23 || player == null)) {
      initializePlayer();
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (Util.SDK_INT <= 23) {
      releasePlayer();
    }
  }

  private void releasePlayer() {
    if (player != null) {
      playbackPosition = player.getCurrentPosition();
      currentWindow = player.getCurrentWindowIndex();
      playWhenReady = player.getPlayWhenReady();

      player.release();
      player = null;
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (Util.SDK_INT > 23) {
      releasePlayer();
    }
  }
}
