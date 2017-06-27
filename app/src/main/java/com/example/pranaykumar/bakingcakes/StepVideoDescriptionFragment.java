package com.example.pranaykumar.bakingcakes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 27-06-2017.
 */

public class StepVideoDescriptionFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_step_video_description_fragment,container,false);
    ArrayList<String> currentStep=getArguments().getStringArrayList("step");

    return view;
  }
}
