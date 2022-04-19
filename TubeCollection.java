package com.example.flappygame;

import java.util.Random;

public class TubeCollection {
    private int xTube; //this will hold the X coordinate
    private int upTubeCollection_Y;
    private Random rand;
    private int colorTube;
    public TubeCollection(int xTube,int upTubeCollection_Y) {
        this.xTube = xTube;
        this.upTubeCollection_Y=upTubeCollection_Y;
        rand=new Random();
    }

    public void setColorTube(){
        colorTube=rand.nextInt(2);
    }
    public int getColorTube(){
        return colorTube;
    }
    public int getUpTubeCollection_Y(){
        return upTubeCollection_Y;
    }
    public int getXTube(){
      return xTube;
    }
    public int getUpTube_Y(){
        return upTubeCollection_Y - AppHolder.getBitmapControl().getTubeHeight();
    }
    public int getDownTube_Y(){
        return upTubeCollection_Y + AppHolder.tubeGap;
    }
    public void setXTube(int x_Tube){
        this.xTube = x_Tube;
    }
    public void setUpTubeCollection_Y(int upTubeCollection_Y){
        this.upTubeCollection_Y=upTubeCollection_Y;
    }
}
