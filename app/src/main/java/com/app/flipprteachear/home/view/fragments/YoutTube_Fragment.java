package com.app.flipprteachear.home.view.fragments;

import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.flipprteachear.R;
import com.app.flipprteachear.utillsa.Constants_All;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class YoutTube_Fragment extends Fragment {

    //private YouTubePlayerView youTubePlayer;
    private YouTubePlayer youTubePlayerComM;
  //  private YouTubePlayerFragment youtube_fragment;
    // RelativeLayout rel_youtube_page;
    //View view, youTubeView;
    View view;
    String video_Id="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);

        }
        // Inflate the layout for this fragment
        try {
            if(youTubePlayerComM!=null)
                youTubePlayerComM.release();

        } catch (InflateException e) { e.printStackTrace();}
        try {
            view = inflater.inflate(R.layout.fragment_yout_tube_, container, false);
           // rel_youtube_page= view.findViewById(R.id.rel_youtube_page);
        } catch (InflateException e) { e.printStackTrace();}
        try {
            video_Id = getArguments().getString("video_Id");
        }catch(Exception e) { e.printStackTrace();}

      /*  try {
            if(youTubeView!=null)
                rel_youtube_page.removeAllViews();

            youTubeView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_yout_tube_new, null);
            youTubeView.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rel_youtube_page.addView(youTubeView);
        } catch (Exception e) { e.printStackTrace(); }

        try {
            youtube_fragment =  (YouTubePlayerFragment )requireActivity().getFragmentManager().findFragmentById(R.id.youtube_fragment);
            //   youtube_fragment =  (YouTubePlayerFragment ) youTubeView.findViewById(R.id.youtube_fragment);
            youtube_fragment.initialize(getResources().getString(R.string.youTubeKey),
                    new YouTubePlayer.OnInitializedListener(){
                        @Override
                        public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider, com.google.android.youtube.player.YouTubePlayer youTubePlayerCom, boolean b) {
                            youTubePlayerComM = youTubePlayerCom;
                            youTubePlayerComM.loadVideo(video_Id);
                        }

                        @Override
                        public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            Log.e("taaa", "onInitializationFailure: "+youTubeInitializationResult );
                        }
                    });


        }catch(Exception e) { e.printStackTrace();}*/
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {


        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction(); //getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment1, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(Constants_All.INSTANCE.getYouTubeKey(), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayerComM = youTubePlayer;
                youTubePlayerComM.loadVideo(video_Id);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }


        });

        }catch(Exception e) { e.printStackTrace();}

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(youTubePlayerComM!=null)
            youTubePlayerComM.release();

      /*  if(youTubeView!=null)
            rel_youtube_page.removeAllViews();*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if (youtube_fragment != null) {
            youtube_fragment.onDestroy();

        }*/

    }
    @Override
    public void onDetach(){
        super.onDetach();
       /* FragmentManager fm = getFragmentManager();

        Fragment xmlFragment = fm.findFragmentById(R.id.youtube_fragment);
        if(xmlFragment != null){
            fm.beginTransaction().remove(xmlFragment).commit();
        }*/
    }

}