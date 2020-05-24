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

import com.example.billage.backend.GetADUserInfo;
import com.example.billage.frontend.MainActivity;
import com.unity3d.player.*;

import com.example.billage.R;

import org.json.JSONException;

public class BillageFragment extends Fragment {
    private UnityPlayer mUnityPlayer;
    private MainActivity mainActivity;
    private GetADUserInfo AppDB = new GetADUserInfo();
    String userInfo;
    View playerView;
    FrameLayout father;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        playerView = inflater.inflate(R.layout.billage, container, false);
        father =(FrameLayout)playerView.findViewById(R.id.layout2);

        if(mUnityPlayer!=null) {
                father.addView(mUnityPlayer.getView(), 0);
        }
        else
            Log.d("unity","no  player");
        Log.d("unity","onCreate");
        mUnityPlayer.requestFocus();
        mUnityPlayer.windowFocusChanged(true);

        return playerView;

    }

    @Override
    public void onAttach(Context context) {
        Log.d("unity","onAttached");
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof MainActivity){
            mUnityPlayer = (UnityPlayer)((MainActivity) getActivity()).GetUnityPlayer();
        }
        if(AppDB.IsThereUserInfo()) {
            try {
                userInfo = AppDB.getUserInfo("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("받아온 값", userInfo);
        }

    }


    @Override public void onDestroy ()
    {
        Log.d("unity", "destroied");
        father.removeView(mUnityPlayer.getView());
        super.onDestroy();
    }

    // Pause Unity
    @Override public void onPause()
    {
        Log.d("unity", "paused");
        super.onPause();
    }

    // Resume Unity
    @Override public void onResume()
    {
        Log.d("unity", "resumed");
        super.onResume();
    }

    @Override public void onStart()
    {
        //father.removeView(mUnityPlayer.getView());
        Log.d("unity", "started");
        UnityPlayer.UnitySendMessage("AndroidManager","GetUserID",userInfo);
        super.onStart();
    }

    @Override public void onStop()
    {
        Log.d("unity", "stopped");
        super.onStop();
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
