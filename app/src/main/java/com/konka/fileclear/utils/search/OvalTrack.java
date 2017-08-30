package com.konka.fileclear.utils.search;

import android.graphics.PointF;

/**
 * Created by light on 15/7/31.
 */
public class OvalTrack {
    private float a;//椭圆位于X轴上的轴长
    private float b;//椭圆位于Y轴上的轴长
    private PointF centerPoint;//

    public OvalTrack(float a, float b, PointF centerPoint) {
        this.a = a;
        this.b = b;
        this.centerPoint = centerPoint;
    }

    public OvalTrack(float radius, PointF centerPoint) {
        this.a = radius;
        this.b = radius;
        this.centerPoint = centerPoint;
    }


    /**
     * 使用椭圆的参数方程计算角度值对应的坐标值
     *
     * @param degree 角度制的度数，以centerPoint为原点，X轴正方向为0度，绕X轴逆时针旋转为正方向。
     * @return 角度对应的椭圆上的坐标点
     */
    public PointF changeDegreeToPointF(float degree) {
        degree = degree /  180 * (float) Math.PI;
        float SIN = (float) Math.sin((double) degree);
        float COS = (float) Math.cos((double) degree);
        float x = a * COS + centerPoint.x;
        float y = b * SIN + centerPoint.y;

        PointF p = new PointF(x, y);
        return p;
    }


    /**
     * 使用椭圆的参数方程计算坐标值对应的角度值，注意这里如果点的坐标不在该椭圆轨迹上的话，计算出的角度也是不对的
     *
     * @param point 椭圆轨迹上的坐标点
     * @return 坐标点对应的角度
     */
    public float changePointFToDegree(PointF point) {
        double degree = 0;
        point.x = point.x - centerPoint.x;
        point.y = point.y - centerPoint.y;
        double TAN = (point.y / b) / (point.x / a);
        degree = Math.atan(TAN);

        //点处于第一象限
        if (point.x > 0 && point.y > 0) {
            return (float) (degree / Math.PI) * 180;
        }

        //点处于第二、三象限
        else if (point.x < 0) {
            return (float) (degree / Math.PI + 1) * 180;
        }

        //点处于第四象限
        else {
            return (float) (degree / Math.PI + 2) * 180;
        }

    }

}
