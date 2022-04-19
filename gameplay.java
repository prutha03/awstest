package com.example.flappygame;
//it is a game engine
// surface holder:-it is basically Abstract interface to someone holding a display surface.
//SurfaceView takes care of placing the surface at the correct location on the screen
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class gameplay  extends SurfaceView implements SurfaceHolder.Callback {
    MainThread mainThread;
    public gameplay(Context context) {
        super(context);
        SurfaceHolder myHolder= getHolder();
        myHolder.addCallback(this);
        mainThread = new MainThread(myHolder);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mainThread.setIsRunning(true);
        mainThread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

        boolean retry=true;
        while(retry){
            try{
                mainThread.setIsRunning(false);
                mainThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        retry = false;
        }
    }
    public boolean onTouchEvent(MotionEvent event){
        if(AppHolder.getGameManager().gameState==0){
            AppHolder.getGameManager().gameState = 1;
            AppHolder.getSoundPlay().playSwoosh();
        }else{
            AppHolder.getSoundPlay().playWing();
        }

     AppHolder.getGameManager().bird.setVelocity(AppHolder.JUMP_VELOCITY);
     return true;
    }
}
