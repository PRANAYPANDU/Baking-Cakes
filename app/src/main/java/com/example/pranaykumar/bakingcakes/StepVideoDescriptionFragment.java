package com.example.pranaykumar.bakingcakes;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import butterknife.Unbinder;
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
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 27-06-2017.
 */

public class StepVideoDescriptionFragment extends Fragment {

  public interface OnButtonClickedInterface {

    void OnPreNextButtonClicked(int position, Bundle bundle);
  }

  private StepVideoDescriptionFragment.OnButtonClickedInterface mListener;
  @BindView(R.id.playerView) SimpleExoPlayerView playerView;
  @Nullable@BindView(R.id.thumbnail) ImageView thumnailView;
  private SimpleExoPlayer player;
  private long playbackPosition;
  private int currentWindow;
  private boolean playWhenReady = true;

  String videoUrl = "";
  String thumbnailUrl="";

  @Nullable@BindView(R.id.textViewDescription) TextView descTxtView;
  @Nullable@BindView(R.id.nxt_btn) Button nxtBtn;
  @Nullable@BindView(R.id.prev_btn) Button preBtn;

  Unbinder unbinder;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater
        .inflate(R.layout.fragment_step_video_description, container, false);
    unbinder=ButterKnife.bind(this,view);
    final Bundle bundle = getArguments();
    final int position = bundle.getInt("position");
    ArrayList<String> currentStep = bundle.getStringArrayList(String.valueOf(position));
    videoUrl = currentStep.get(2);
    thumbnailUrl=currentStep.get(3);

    if(TextUtils.isEmpty(videoUrl))  {
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
      if(TextUtils.isEmpty(thumbnailUrl)){
        thumnailView.setVisibility(View.INVISIBLE);
      }else{
        Picasso.with(getContext()).load(currentStep.get(3)).into(thumnailView);
      }
      mListener = (OnButtonClickedInterface) getActivity();
      descTxtView.setText(currentStep.get(1));
      Log.d("O_MY",
          "Position=" + String.valueOf(position) + "Bundle=" + String.valueOf(bundle.size() - 2));
      if (!isTablet) {

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
            ,"BakingApp")),
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

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
