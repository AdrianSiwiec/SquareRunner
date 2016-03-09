package adrians.game.model.gameObject;

import android.graphics.PointF;
import android.util.Log;

import java.util.Vector;

import adrians.game.model.TouchListener;

/**
 * Created by pierre on 02/03/16.
 */
public class PlayerSquare extends PhysicalGameObject{
    TouchListener touchListener;
    public PlayerSquare(PointF pos, PointF size, int color, TouchListener touchListener) {
        super(pos, size, color);
        this.touchListener=touchListener;
    }


//    public void onObstacleTouch(PhysicalRectangle obj) {
//        if(!obj.isTouching(this)) {
//            return;
//        }
//        if(touchesRight(obj) || touchesLeft(obj) || touchesDown(obj) || touchesUp(obj)) {
//            pos = obj.closestPossible(this);
//        }
//        updateRectangle();
//    }

    private boolean touchesRight(PhysicalRectangle obj) {
        return rectangle.intersects(obj.pos.x - obj.size.x, obj.pos.y - obj.size.y, obj.pos.x - obj.size.x, obj.pos.x + obj.size.y);
    }

    private boolean touchesLeft(PhysicalRectangle obj) {
        return rectangle.intersect(obj.pos.x + obj.size.x, obj.pos.y - obj.size.y, obj.pos.x + obj.size.x, obj.pos.x + obj.size.y);
    }

    private boolean touchesDown(PhysicalRectangle obj) {
        return rectangle.intersect(obj.pos.x-obj.size.x, obj.pos.y-obj.size.y, obj.pos.x+obj.size.x, obj.pos.y-obj.size.y);
    }

    private boolean touchesUp(PhysicalRectangle obj) {
        return rectangle.intersect(obj.pos.x-obj.size.x, obj.pos.y+obj.size.y, obj.pos.x+obj.size.x, obj.pos.y+obj.size.y);
    }

    float maximumDelta = 0.05f;
    public synchronized void update(float delta, Vector<PhysicalRectangle> rectangles) {
        if(delta > maximumDelta) {
            Log.d("PlayerUpdate", "exceeded maximum delta:" + delta);
            update(delta - maximumDelta, rectangles);
            delta-=maximumDelta;
        }
        vel.y+=400*delta;
        vel.x+=touchListener.getWantedVelX()*delta;
        vel.y+=touchListener.getWantedVelY()*delta;
        if(touchListener.getPointersSize()==0) {
            vel.x *= 0.95;
        }
        pos.y+=vel.y*delta;
        pos.x+=vel.x*delta;

        for(PhysicalRectangle rectangle: rectangles) { //todo faster?
            if(rectangle.isTouching(this)) {
                int index = rectangle.closestPossible(this);
                if(index < 2) {
                    vel.x = 0;
                } else {
                    vel.y = 0;
                }
                pos = rectangle.getClosestPossible(this);
                if(pos == null) {
                    System.exit(4);
                }
            }
        }

        updateRectangle();

    }

//    private synchronized boolean collides(PointF newPos, Vector<PhysicalRectangle> rectangles) {
//        PointF oldPos = new PointF(pos.x, pos.y); //UGLY todo make better
//        pos = newPos;
//        updateRectangle();
//        for(PhysicalRectangle rectangle: rectangles) {
//            if(rectangle.isTouching(this)) {
//                pos = oldPos;
//                return true;
//            }
//        }
//        pos = oldPos;
//        return false;
//    }
}
