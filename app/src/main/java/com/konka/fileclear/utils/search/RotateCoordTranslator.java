package com.konka.fileclear.utils.search;

import android.graphics.PointF;

/**
 * Created by light on 15/7/31.
 */
public class RotateCoordTranslator implements CoordTranslator {

    private float rotateAngle;
    private PointF centerPoint;

    public RotateCoordTranslator(float rotateAngle, PointF centerPoint) {
        this.rotateAngle = rotateAngle;
        this.centerPoint = centerPoint;
    }


    @Override
    public PointF translatePointF(PointF point) {
        if(point!=null&&centerPoint!=null) {
            float deltaX = point.x - centerPoint.x;
            float deltaY = point.y - centerPoint.y;
            float radius = (float) Math.sqrt((double) (deltaX * deltaX + deltaY * deltaY));

            float angle = (float) Math.atan2(deltaY, deltaX);

            angle = (float) (rotateAngle / 180 * Math.PI) + angle;

            float x = (float) (radius * Math.cos((double) angle)) + centerPoint.x;
            float y = (float) (radius * Math.sin((double) angle)) + centerPoint.y;

            PointF p = new PointF(x, y);

            return p;
        }
        else return new PointF(0,0);
    }

    public float getRotateAngle() {
        return rotateAngle;
    }

    public void setRotateAngle(float rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public PointF getCenterPoint() {
        return centerPoint;
    }

    public void getCenterPoint(PointF centerPoint) {
        this.centerPoint = centerPoint;
    }

}
