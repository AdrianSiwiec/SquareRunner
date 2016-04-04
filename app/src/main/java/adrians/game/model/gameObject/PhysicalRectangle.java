package adrians.game.model.gameObject;

import android.graphics.PointF;

import adrians.framework.util.MathUtil;

/**
 * Created by pierre on 02/03/16.
 */
public class PhysicalRectangle extends PhysicalGameObject{
    private PointF tmpPoints[];
    private PointF ret;
    public PhysicalRectangle(PointF pos, PointF size, int color) {
        super(pos, size, color);
        tmpPoints = new PointF[4];
        for(int i=0; i<4; i++) {
            tmpPoints[i] = new PointF();
        }
    }

    public boolean isTouching(PlayerSquare player, float eps) {
        return rectangle.intersects(player.rectangle.left+eps, player.rectangle.top+eps,
                player.rectangle.right-eps, player.rectangle.bottom-eps);
    }


    public MathUtil.Directions closestPossible(PlayerSquare player) {
        float eps=0;
        tmpPoints[0].set(rectangle.left - player.size.x-eps, player.pos.y);
        tmpPoints[1].set(rectangle.right + player.size.x+eps, player.pos.y);
        tmpPoints[2].set(player.pos.x, rectangle.top - player.size.y-eps);
        tmpPoints[3].set(player.pos.x, rectangle.bottom + player.size.y+eps);
        float minDist = 1e20f;
        int min=0;
        ret = new PointF(player.pos.x, player.pos.y);
        for(int i=0; i<4; i++) {
            if(MathUtil.distance(player.pos, tmpPoints[i]) < minDist) {
                minDist = MathUtil.distance(player.pos, tmpPoints[i]);
                ret.set(tmpPoints[i].x, tmpPoints[i].y);
                min = i;
            }
        }
        MathUtil.Directions retDirection=null;
        switch (min) {
            case(0):
                retDirection = MathUtil.Directions.RIGHT;
                break;
            case(1):
                retDirection = MathUtil.Directions.LEFT;
                break;
            case(2):
                retDirection = MathUtil.Directions.DOWN;
                break;
            case(3):
                retDirection = MathUtil.Directions.UP;
                break;
        }
        return retDirection;
    }

    public PointF getClosestPossible(PlayerSquare player) {
        closestPossible(player);
        return ret;
    }
}
