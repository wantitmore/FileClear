package com.konka.fileclear.utils.search;

import android.graphics.PointF;

/**
 * Created by light on 15/7/31.
 */
public class SlantCoordTranslator implements CoordTranslator {

    private float slantAngle;//Y轴倾斜角,直角坐标系下为90
    private float scale;//Y缩放比例
    private float reviseX;//X轴偏移像素点，正值为向左平移的像素点
    private float reviseY;//Y轴偏移像素点，正值为向上平移的像素点

    private float SIN;
    private float COS;

    public SlantCoordTranslator() {
        this.slantAngle = 90;
        this.scale = 1;
        reviseX = 0;
        reviseY = 0;
        SIN=(float) Math.sin((double)slantAngle* Math.PI/180);
        COS=(float) Math.cos((double)slantAngle* Math.PI/180);
    }

    public SlantCoordTranslator(float slantAngle, float scale, float reviseX, float reviseY) {
        this.slantAngle = slantAngle;
        this.scale = scale;
        this.reviseX = reviseX;
        this.reviseY = reviseY;
        SIN=(float) Math.sin((double)slantAngle* Math.PI/180);
        COS=(float) Math.cos((double)slantAngle* Math.PI/180);
    }

    @Override
    public PointF translatePointF(PointF point) {
        if(point!=null) {
            float y = scale * point.y * SIN - reviseY;
            float x = point.x + scale * point.y * COS - reviseX;
            PointF p = new PointF(x, y);
            return p;
        }
        else return new PointF(0,0);
    }


    public float getReviseX() {
        return reviseX;
    }

    public void setReviseX(float reviseX) {
        this.reviseX = reviseX;
    }

    public float getReviseY() {
        return reviseY;
    }

    public void setReviseY(float reviseY) {
        this.reviseY = reviseY;
    }

    public float getSlantAngle() {
        return slantAngle;
    }

    public void setSlantAngle(float slantAngle) {
        this.slantAngle = slantAngle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
