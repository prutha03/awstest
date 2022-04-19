package com.example.flappygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    BgImage bgImage;
    flyingBird bird;
    static int gameState;
    ArrayList<TubeCollection>tubeCollections;
    Random rand;
    int scoreCount; // this will be used to store the score
    int winningTube; // this will determine the winning tube obstacle
    Paint designPaint;

    public GameManager() {
        bgImage = new BgImage();
        bird = new flyingBird();
        gameState=0;
        tubeCollections = new ArrayList<>();
        rand = new Random();
        generateTubeObject();
        initScoreVariable();
    }

    public void initScoreVariable(){
        scoreCount=0;
        winningTube=0;
        designPaint=new Paint();
        designPaint.setColor(Color.YELLOW);
        designPaint.setTextSize(250);
        designPaint.setStyle(Paint.Style.FILL);
        designPaint.setFakeBoldText(true);
        designPaint.setShadowLayer(5.0f,20.0f,20.0f,Color.BLACK);
    }
/*      game state==0:not running
        game state==1:the game is running
        game state==2:the game is over
*/
    public void generateTubeObject(){
        for(int j=0; j<AppHolder.tubeGap;j++){
          int tubeX = AppHolder.SCRN_WIDTH_X+j*AppHolder.tubeDistance;
          int upTubeCollectionY=AppHolder.minimumTubeCollection_Y;
          rand.nextInt(AppHolder.maximumTubeCollection_Y - AppHolder.minimumTubeCollection_Y + 1);
          TubeCollection tubeCollection = new TubeCollection(tubeX,upTubeCollectionY);
          tubeCollections.add(tubeCollection);
        }
    }

    public void scrollingTube(Canvas can){
       if(gameState ==1){

           if((tubeCollections.get(winningTube).getXTube()< bird.getX() + AppHolder.getBitmapControl().getBirdWidth())
           &&(tubeCollections.get(winningTube).getUpTubeCollection_Y()>bird.getY()
           ||tubeCollections.get(winningTube).getDownTube_Y()<bird.getY()+
                   AppHolder.getBitmapControl().getBirdHeight())){
               gameState=2;
               AppHolder.getSoundPlay().playCrash();
               Context mContext =AppHolder.gameActivityContext;
               Intent mIntent = new Intent(mContext, GameOver.class);
               mIntent.putExtra("score",scoreCount);
               mContext.startActivity(mIntent);
               ((Activity)mContext).finish();
           }

           if(tubeCollections.get(winningTube).getXTube()<bird.getX()-AppHolder.getBitmapControl().getTubeWidth()){
               scoreCount++;
               winningTube++;
               AppHolder.getSoundPlay().playPing();
               if(winningTube>AppHolder.tube_numbers-1){
                   winningTube=0;
               }
           }
           for(int j=0;j<AppHolder.tube_numbers;j++){
               if(tubeCollections.get(j).getXTube()<-AppHolder.getBitmapControl().getTubeWidth()){
                tubeCollections.get(j).setXTube(tubeCollections.get(j).getXTube()
                + AppHolder.tube_numbers*AppHolder.tubeDistance);
                int upTubeCollectionY = AppHolder.minimumTubeCollection_Y +
                        rand.nextInt(AppHolder.maximumTubeCollection_Y - AppHolder.minimumTubeCollection_Y+1);
                tubeCollections.get(j).setUpTubeCollection_Y(upTubeCollectionY);
                tubeCollections.get(j).setColorTube();
               }
               tubeCollections.get(j).setXTube(tubeCollections.get(j).getXTube() - AppHolder.tubeVelocity);
               if(tubeCollections.get(j).getColorTube()==0){
                   can.drawBitmap(AppHolder.getBitmapControl().getUpTube(),tubeCollections.get(j).getXTube(),tubeCollections.get(j).getUpTube_Y(),null);
                   can.drawBitmap(AppHolder.getBitmapControl().getDownTube(),tubeCollections.get(j).getXTube(),tubeCollections.get(j).getDownTube_Y(),null);
               }else{
                   can.drawBitmap(AppHolder.getBitmapControl().getUpColoredTube(),tubeCollections.get(j).getXTube(),tubeCollections.get(j).getUpTube_Y(),null);
                   can.drawBitmap(AppHolder.getBitmapControl().getDownColoredTube(),tubeCollections.get(j).getXTube(),tubeCollections.get(j).getDownTube_Y(),null);
               }
           }
           can.drawText(""+scoreCount,620,400,designPaint);
       }
    }


    public void birdAnimation(Canvas canvas) {
        if (gameState==1){
            if(bird.getY()<(AppHolder.SCRN_HEIGHT_Y -AppHolder.getBitmapControl().getBirdHeight())||bird.getVelocity()<0){
                bird.setVelocity(bird.getVelocity()+AppHolder.gravityPull);
                bird.setY(bird.getY()+ bird.getVelocity());
        }

        }
        int CurrentFrame = bird.getCurrentFrame();
        canvas.drawBitmap(AppHolder.getBitmapControl().getBird(CurrentFrame),bird.getX(),bird.getY(),null);
        CurrentFrame++;
        if(CurrentFrame>bird.maximumFrame){
            CurrentFrame=0;
        }
        bird.setCurrentFrame(CurrentFrame);
    }
    public void backgroundAnimation(Canvas canvas){
        bgImage.setX(bgImage.getX()- bgImage.getVelocity());
        if(bgImage.getX()< - AppHolder.getBitmapControl().getBackgroundWidth()){
            bgImage.setX(0);
        }
        canvas.drawBitmap(AppHolder.getBitmapControl().getBackground(),bgImage.getX(),bgImage.getY(),null);
        if (bgImage.getX()<-(AppHolder.getBitmapControl().getBackgroundWidth()-AppHolder.SCRN_WIDTH_X)){
          canvas.drawBitmap(AppHolder.getBitmapControl().getBackground(),bgImage.getX() +
                  AppHolder.getBitmapControl().getBackgroundWidth(),bgImage.getY(),null);
        }
    }
}
