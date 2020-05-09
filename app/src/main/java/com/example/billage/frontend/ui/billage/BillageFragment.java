package com.example.billage.frontend.ui.billage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.billage.frontend.MainActivity;
import com.unity3d.player.*;

import com.example.billage.R;

public class BillageFragment extends Fragment {
    private UnityPlayer mUnityPlayer;
    private MainActivity mainActivity;
    View playerView;
    FrameLayout father;
    private BillageViewModel billageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        billageViewModel =
//                ViewModelProviders.of(this).get(BillageViewModel.class);

//        final TextView textView = root.findViewById(R.id.text_notifications);
//        billageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//           public void onChanged(@Nullable String s) {
//               textView.setText(s);
//           }
//        });
        playerView = inflater.inflate(R.layout.billage, container, false);
        father =(FrameLayout)playerView.findViewById(R.id.layout2);

        if(mUnityPlayer!=null)
            father.addView(mUnityPlayer.getView(), 0);
        else
            Log.d("unity","no player");

          //  mUnityPlayer.requestFocus();
        mUnityPlayer.windowFocusChanged(true);
        //LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
       // playerView.setLayoutParams(lp);
//        if(playerView.getParent() != null){
//            ((ViewGroup)playerView.getParent()).removeAllViews();
//        }
        return playerView;
        /*
        View root = inflater.inflate(R.layout.billage, container, false);
        mUnityPlayer = new UnityPlayer(getActivity());

        father = (FrameLayout)root.findViewById(R.id.layout2);


        father.addView(mUnityPlayer.getView(), 0);

        mUnityPlayer.requestFocus();
        mUnityPlayer.windowFocusChanged(true);


        return root;*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof MainActivity){
            mUnityPlayer = (UnityPlayer)((MainActivity) getActivity()).GetUnityPlayer();
        }
    }


    @Override public void onDestroy ()
    {
        Log.d("unity", "destroied");

        father.removeView(mUnityPlayer.getView());
        super.onDestroy();
    }
/*
    // Pause Unity
    @Override public void onPause()
    {
        Log.d("unity", "paused");
        super.onPause();
       mUnityPlayer.pause();
    }

    // Resume Unity
    @Override public void onResume()
    {
        Log.d("unity", "resumed");
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override public void onStart()
    {
        Log.d("unity", "started");
        super.onStart();
        mUnityPlayer.start();
    }

    @Override public void onStop()
    {
        Log.d("unity", "stopped");
        super.onStop();
        //mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }*/
}
