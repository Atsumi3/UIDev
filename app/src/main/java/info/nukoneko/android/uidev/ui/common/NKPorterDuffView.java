package info.nukoneko.android.uidev.ui.common;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

public final class NKPorterDuffView extends View {
    public interface Listener {
        @ColorInt
        int getColorBackGround();

        @ColorInt
        int getColorCrossLine();

        @ColorInt
        int getColorCenter();

        @ColorInt
        int getColorCenterAnimation(float animationValue);
    }

    private final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
    private final static int ANIM_DURATION_MILLIS = 2000;
    private final Paint paint = new Paint() {{
        setAntiAlias(true);
    }};
    private PorterDuff.Mode porterDuff = null;

    public NKPorterDuffView(Context context) {
        this(context, null, 0);
    }

    public NKPorterDuffView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NKPorterDuffView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) return;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        animator.setDuration(ANIM_DURATION_MILLIS);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        animator.start();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        final float animationValue = (Float) animator.getAnimatedValue();
        final float halfWidth = getWidth() / 2;

        setBackgroundColor(getColorBackGround());

        if (porterDuff != null) {
            paint.setXfermode(new PorterDuffXfermode(porterDuff));
        } else {
            paint.setXfermode(null);
        }

        paint.setColor(getColorCross());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
        canvas.drawLine(0, getHeight(), getWidth(), 0, paint);

        paint.setColor(getColorCenterCircle());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(halfWidth, halfWidth, halfWidth / 2, paint);

        paint.setColor(getColorCenterAnimationCircle(1 - animationValue));
        canvas.drawCircle(halfWidth, halfWidth, halfWidth * animationValue, paint);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec > heightMeasureSpec) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }

    public void setPorterDuff(PorterDuff.Mode porterDuff) {
        this.porterDuff = porterDuff;
    }

    @ColorInt
    private int getColorBackGround() {
        if (getContext() instanceof Listener) {
            return ((Listener) getContext()).getColorBackGround();
        } else {
            return Color.argb(255, 0, 0, 0);
        }
    }

    @ColorInt
    private int getColorCross() {
        if (getContext() instanceof Listener) {
            return ((Listener) getContext()).getColorCrossLine();
        } else {
            return Color.argb(255, 255, 0, 0);
        }
    }

    @ColorInt
    private int getColorCenterCircle() {
        if (getContext() instanceof Listener) {
            return ((Listener) getContext()).getColorCenter();
        } else {
            return Color.argb(255, 0, 255, 0);
        }
    }

    @ColorInt
    private int getColorCenterAnimationCircle(float animationValue) {
        if (getContext() instanceof Listener) {
            return ((Listener) getContext()).getColorCenterAnimation(animationValue);
        } else {
            return Color.argb((int) (255 * animationValue), 0, 0, 255);
        }
    }
}
