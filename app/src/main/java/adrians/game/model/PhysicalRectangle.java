package adrians.game.model;

import android.graphics.PointF;

import adrians.framework.util.MathUtil;

/**
 * Created by pierre on 02/03/16.
 */
public class PhysicalRectangle extends PhysicalGameObject{
    PointF tmpPoints[];
    public PhysicalRectangle(PointF pos, PointF size, int color) {
        super(pos, size, color);
        tmpPoints = new PointF[4];
        for(int i=0; i<4; i++) {
            tmpPoints[i] = new PointF();
        }
    }

    public boolean isTouching(PlayerSquare player) {
        float eps= 0f;
        return rectangle.intersects(player.rectangle.left+eps, player.rectangle.top+eps,
                player.rectangle.right-eps, player.rectangle.bottom-eps);
    }

    public PointF closestPossible(PlayerSquare player) {
        float eps=0;
        tmpPoints[0].set(rectangle.left - player.size.x-eps, player.pos.y);
        tmpPoints[1].set(rectangle.right + player.size.x+eps, player.pos.y);
        tmpPoints[2].set(player.pos.x, rectangle.top - player.size.y-eps);
        tmpPoints[3].set(player.pos.x, rectangle.bottom + player.size.y+eps);
        float minDist = 1e12f;
        PointF ret = new PointF(player.pos.x, player.pos.y);
        for(int i=0; i<4; i++) {
            if(MathUtil.distance(player.pos, tmpPoints[i]) < minDist) {
                minDist = MathUtil.distance(player.pos, tmpPoints[i]);
                ret.set(tmpPoints[i].x, tmpPoints[i].y);
            }
        }
        return ret;
    }
}
