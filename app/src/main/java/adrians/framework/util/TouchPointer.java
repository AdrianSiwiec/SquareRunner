package adrians.framework.util;

import android.graphics.Point;
import android.graphics.PointF;

import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 13/02/16.
 */
public class TouchPointer {
    private PointF beg, cur, begRel, curRel;
    private PhysicalGameObject originObject;
    private int id;

    public  TouchPointer(PointF pt, int id, PhysicalGameObject originObject) {
        this.originObject = originObject;
        this.id = id;
        cur = pt;
        beg = pt;
        begRel = new PointF(0, 0);
        curRel = new PointF(0, 0);
        if(originObject != null) {
            updateBegRel();
            updateCurRel();
        }
    }

    private void updateCurRel() {
        curRel.x = (cur.x - originObject.getPosX()) / originObject.getWidth();
        curRel.y = (cur.y - originObject.getPosY()) / originObject.getHeight();
    }

    private void updateBegRel() {
        begRel.x = (beg.x - originObject.getPosX()) / originObject.getWidth();
        begRel.y = (beg.y - originObject.getPosY()) / originObject.getHeight();
    }

    public void update(PointF point) {
        cur = point;
        if(originObject != null) {
            updateCurRel();
        }
    }

    public PointF getBeg() {
        return beg;
    }

    public void setBeg(PointF beg) {
        this.beg = beg;
    }

    public PointF getCur() {
        return cur;
    }

    public void setCur(PointF cur) {
        this.cur = cur;
    }

    public PointF getBegRel() {
        return begRel;
    }

    public PointF getCurRel() {
        return curRel;
    }

    public PhysicalGameObject getOriginObject() {
        return originObject;
    }

    public void setOriginObject(PhysicalGameObject originObject) {
        this.originObject = originObject;
        updateBegRel();
        updateCurRel();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
