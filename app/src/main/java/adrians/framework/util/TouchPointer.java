package adrians.framework.util;

import android.graphics.PointF;

import adrians.game.model.PhysicalGameObject;

/**
 * Created by pierre on 13/02/16.
 */
public class TouchPointer {
    private PointF cur, begRel, curRel;
    private PointF beg;
    private PhysicalGameObject originObject;
    private int id;

    boolean isWorld;

    public TouchPointer(PointF pt, int id, PhysicalGameObject originObject) {
        this.originObject = originObject;
        this.id = id;
        cur = new PointF(pt.x, pt.y);
        beg = new PointF(pt.x, pt.y);
        begRel = new PointF(0, 0);
        curRel = new PointF(0, 0);
        if(originObject != null) {
            updateBegRel();
            updateCurRel();
        }
    }

    public void updateCurRel() {
        curRel.x = (cur.x - originObject.getPos().x) / originObject.getSize().x;
        curRel.y = (cur.y - originObject.getPos().y) / originObject.getSize().y;
    }

    public void updateBegRel() {
        begRel.x = (beg.x - originObject.getPos().x) / originObject.getSize().x;
        begRel.y = (beg.y - originObject.getPos().y) / originObject.getSize().y;
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

    public PointF getCur() {
        return cur;
    }

    public void setCur(PointF cur) {
        this.cur = cur;
    }

    public void setBeg(PointF beg) {
        this.beg = beg;
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

    public boolean isWorld() {
        return isWorld;
    }

    public void setIsWorld(boolean isWorld) {
        this.isWorld = isWorld;
    }
}
