package adrians.game.model;

import android.graphics.PointF;

import java.util.Vector;

/**
 * Created by pierre on 02/03/16.
 */
public class PlayerSquare extends PhysicalGameObject{
    TouchListener touchListener;
    public PlayerSquare(PointF pos, PointF size, int color, TouchListener touchListener) {
        super(pos, size, color);
        this.touchListener=touchListener;
    }


    public void onObstacleTouch(PhysicalRectangle obj) {
        if(!obj.isTouching(this)) {
            return;
        }
        if(touchesRight(obj) || touchesLeft(obj) || touchesDown(obj) || touchesUp(obj)) {
            pos = obj.closestPossible(this);
        }
        updateRectangle();
    }

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

    public synchronized void update(float delta, Vector<PhysicalRectangle> rectangles) {
        PointF oldPos=new PointF(pos.x, pos.y);
        vel.y+=100*delta;
        vel.x+=touchListener.getWantedVelX()*delta;
        vel.y+=touchListener.getWantedVelY()*delta;
        if(touchListener.getPointersSize()==0) {
            vel.x *= 0.9;
        }
        pos.y+=vel.y*delta;
        pos.x+=vel.x*delta;

        for(PhysicalRectangle rectangle: rectangles) {
            if(rectangle.isTouching(this)) {
                pos = rectangle.closestPossible(this);
                vel.y = 0;
            }
        }

        updateRectangle();

    }

    private synchronized boolean collides(PointF newPos, Vector<PhysicalRectangle> rectangles) {
        PointF oldPos = new PointF(pos.x, pos.y); //UGLY todo make better
        pos = newPos;
        updateRectangle();
        for(PhysicalRectangle rectangle: rectangles) {
            if(rectangle.isTouching(this)) {
                pos = oldPos;
                return true;
            }
        }
        pos = oldPos;
        return false;
    }
}
