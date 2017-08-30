package com.konka.fileclear.utils.search;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by light on 15/8/3.
 */
public class SatelliteAnimator extends ValueAnimator {

    private OvalTrack track;

    private View targetView;

    private SlantCoordTranslator slantCoordTranslator;

    private RotateCoordTranslator rotateCoordTranslator;

    private PointF currentPoint;

    private boolean autoScaleEnable;

    public SatelliteAnimator(final OvalTrack track, final SlantCoordTranslator slantCoordTranslator, final RotateCoordTranslator rotateCoordTranslator) {
        this.track = track;
        this.slantCoordTranslator = slantCoordTranslator;
        this.rotateCoordTranslator = rotateCoordTranslator;
        autoScaleEnable=false;
        setInterpolator(new LinearInterpolator());
        addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentDegree = (float) animation.getAnimatedValue();
                currentPoint = track.changeDegreeToPointF(currentDegree);
                currentPoint = slantCoordTranslator.translatePointF(currentPoint);
                currentPoint = rotateCoordTranslator.translatePointF(currentPoint);

                targetView.setX(currentPoint.x - targetView.getWidth() / 2);
                targetView.setY(currentPoint.y - targetView.getHeight() / 2);

            }
        });
    }

    public SatelliteAnimator(final OvalTrack track) {
        this.track = track;
        addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentDegree = (float) animation.getAnimatedValue();
                currentPoint = track.changeDegreeToPointF(currentDegree);

                targetView.setX(currentPoint.x - targetView.getWidth() / 2);
                targetView.setY(currentPoint.y - targetView.getHeight() / 2);
            }
        });


    }

    SatelliteAnimator(Rect rect, float slantDegree, float rotateDegree) {
        PointF centerPoint = new PointF(rect.centerX(), rect.centerY());
        float a = rect.width() / 2 * 0.9f;
        float b = rect.height() / 2 * 0.9f;
        float reviseX = rect.width() * (float) Math.cos(slantDegree / 180 * (float) Math.PI) / 2f;
        slantCoordTranslator = new SlantCoordTranslator(slantDegree, 1.0f, reviseX, 0);
        centerPoint = slantCoordTranslator.translatePointF(centerPoint);
        rotateCoordTranslator = new RotateCoordTranslator(rotateDegree, centerPoint);
        track = new OvalTrack(a, b, new PointF(rect.centerX(), rect.centerY()));
        setInterpolator(new LinearInterpolator());

        addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentDegree = (float) animation.getAnimatedValue();
                currentPoint = track.changeDegreeToPointF(currentDegree);
                currentPoint = slantCoordTranslator.translatePointF(currentPoint);
                currentPoint = rotateCoordTranslator.translatePointF(currentPoint);
                if(autoScaleEnable){
                    float scale;
                    if(currentDegree<180){
                        scale=1.0f-currentDegree/180*0.5f;
                    }
                    else{
                        scale=currentDegree/180*0.5f;
                    }
                    targetView.setScaleX(scale);
                    targetView.setScaleY(scale);
                }

                targetView.setX(currentPoint.x - targetView.getWidth() / 2);
                targetView.setY(currentPoint.y - targetView.getHeight() / 2);
            }
        });
    }

    public SatelliteAnimator(Rect rect) {
        this(rect, 90, 0);
    }


    public SatelliteAnimator(Rect rect, float rotateDegree) {
        this(rect, 90, rotateDegree);
    }


    public void startSatelliteAnimation(View v, float startAngle, float endAngle, int duration) {
        setFloatValues(startAngle, endAngle);
        setDuration(duration);
        targetView = v;
        start();
    }

    public RotateCoordTranslator getRotateCoordTranslator() {
        return rotateCoordTranslator;
    }

    public void setRotateCoordTranslator(RotateCoordTranslator rotateCoordTranslator) {
        this.rotateCoordTranslator = rotateCoordTranslator;
    }

    public SlantCoordTranslator getSlantCoordTranslator() {
        return slantCoordTranslator;
    }

    public void setSlantCoordTranslator(SlantCoordTranslator slantCoordTranslator) {
        this.slantCoordTranslator = slantCoordTranslator;
    }

    public OvalTrack getTrack() {
        return track;
    }

    public void setTrack(OvalTrack track) {
        this.track = track;
    }

    public void setTargetView(View targetView) {
        this.targetView = targetView;
    }

    public View getTargetView() {
        return targetView;
    }

    public PointF getCurrentPoint(){
        return currentPoint;
    }

    public boolean isAutoScaleEnable() {
        return autoScaleEnable;
    }

    public void setAutoScaleEnable(boolean autoScaleEnable) {
        this.autoScaleEnable = autoScaleEnable;
    }
}
