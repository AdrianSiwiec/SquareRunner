package adrians.game.model.gameObject;

import android.graphics.PointF;

import java.util.Vector;

import adrians.framework.util.MathUtil.Directions;
import adrians.game.model.TouchListener;

/**
 * Created by pierre on 02/03/16.
 */
public class PlayerSquare extends PhysicalGameObject{
    TouchListener touchListener;
    private boolean isHappy = false;
    private float timeSinceLastMove=0;
    public PlayerSquare(PointF pos, PointF size, int color, TouchListener touchListener) {
        super(pos, size, color);
        this.touchListener=touchListener;
    }


    float maximumDelta = 0.04f;
    public synchronized void update(float delta, Vector<PhysicalRectangle> rectangles) {
        if(delta > maximumDelta) {
//            Log.d("PlayerUpdate", "exceeded maximum delta:" + delta);
            update(delta - maximumDelta, rectangles);
            delta-=maximumDelta;
        }
        vel.y+=180*delta;
        vel.x+=touchListener.getWantedVelX()*delta;
        vel.y+=touchListener.getWantedVelY()*delta;
        if(touchListener.getPointersSize()==0) {
            vel.x *= 0.95;
        }
        if(isHappy) {
            return;
        }
        pos.y+=vel.y*delta;
        pos.x+=vel.x*delta;

        for(PhysicalRectangle rectangle: rectangles) { //todo faster?
            Directions direction = rectangle.closestPossible(this);
            if(rectangle.isTouching(this, 0.001f)) {
                switch (direction) {
                    case LEFT:
                    case RIGHT:
                        vel.y*=0.8;
                }
            }
            if(rectangle.isTouching(this, 0)) {
                switch (direction) {
                    case UP:
                        vel.y=Math.max(vel.y, 0);
                        break;
                    case DOWN:
                        vel.y= Math.min(vel.y, 0);
                        break;
                    case LEFT:
                        vel.x = Math.max(vel.x, 0);
                        break;
                    case RIGHT:
                        vel.x = Math.min(vel.x, 0);
                }
                pos = rectangle.getClosestPossible(this);
            }
            if(rectangle.isTouching(this, -0.5f)) {
                if(touchListener.getWantedJump()) {
                    jump(direction);
                    break;
                }
            }

        }

        if(Math.abs(vel.x)<10 && Math.abs(vel.y)<10) {
            timeSinceLastMove+=delta;
        } else {
            timeSinceLastMove=0;
        }

        updateRectangle();

    }

    private void jump(Directions direction) {
        float jumpForce = 120;
        switch (direction) {
            case DOWN:
                vel.y=-jumpForce;
                break;
            case LEFT:
                vel.x=jumpForce*0.6f;
                vel.y=-jumpForce*0.8f;
                break;
            case RIGHT:
                vel.x=-jumpForce*0.6f;
                vel.y=-jumpForce*0.8f;
                break;
        }
    }

    public void beHappy() {
        isHappy = true;
    }

    public float getTimeSinceLastMove() {
        return timeSinceLastMove;
    }

    public boolean isHappy() {
        return isHappy;
    }
}
