package com.kruserg.labo2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class Spaceship {
    private float x;
    private float y;
    private int height;
    private int width;
    private int drawableId;
    private int speed;

    public Spaceship(float x, float y, int height, int width, int drawableId, int speed) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.drawableId = drawableId;
        this.speed = speed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move(View v, int xSpeed, int ySpeed){
        //getting coordinates
        float x = this.getX();
        float y = this.getY();
        //unstuck x
        if(x<=10) x += 10;
        if(x>=900) x -= 10;
        //unstuck y
        if(y<=10) y += 10;
        if(y>=1600) y -= 10;
        //changing the value of x and y if it's in the limited area
        if(x>10 && x<900  )      x += xSpeed;
        if(y>10 && y<1600  )   y += ySpeed;
        //updating view position
        this.setX(x);
        this.setY(y);
        //applying the changes
        ObjectAnimator move1,move2;
        move1 = ObjectAnimator.ofFloat(v, "x",x );
        move2 = ObjectAnimator.ofFloat(v, "y",y );

        move1.setDuration(0);
        move2.setDuration(0);

        //applying the animation
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(move1,move2);
        animatorSet.start();
    }

    public void stopMoving(View v){
        ObjectAnimator move1 = ObjectAnimator.ofFloat(v, "x", this.getX());
        ObjectAnimator move2 = ObjectAnimator.ofFloat(v, "y", this.getY());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(move1,move2);
        animatorSet.start();
    }
}
