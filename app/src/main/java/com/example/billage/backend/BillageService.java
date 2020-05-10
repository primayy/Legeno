package com.example.billage.backend;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BillageService extends Service {
    //public UnityPlayer mUnityPlayer;
    private int number;
    IBinder mBinder = new BillageBinder();

     class BillageBinder extends Binder {
        BillageService getService(){
            return BillageService.this;
        }
    }

//    UnityPlayer getmUnityPlayer(){
//        return mUnityPlayer;
//    }
    public int getNumber(){
        return number++;
    }
    @Override
    public IBinder onBind(Intent intent){
        // 액티비티에서 bindService() 를 실행하면 호출됨
        // 리턴한 IBinder 객체는 서비스와 클라이언트 사이의 인터페이스 정의한다
        return mBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");
        //mUnityPlayer = new UnityPlayer(this);
        Log.d("test", "유니티 플레이어 생성");
        //mUnityPlayer.start();
        Log.d("test", "유니티 플레이어 시작");

        number = 0;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("test", "서비스의 onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //mUnityPlayer.destroy();
        Log.d("test", "유니티 플레이어 종료");
        super.onDestroy();
        // 서비스가 종료될 때 실행

        Log.d("test", "서비스의 onDestroy");

    }


}
