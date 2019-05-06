package kiun.com.bvroutine.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import kiun.com.bvroutine.R;

@SuppressLint("AppCompatCustomView")
public class DrawLinearLayout extends LinearLayout {

    int radius = 100;
    int paintColor = 0;
    int padding = 0;
    Paint paint = new Paint();
    Path mPath = new Path();
    RectF mRect = new RectF();

    public DrawLinearLayout(Context context) {
        super(context);
    }

    public DrawLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public DrawLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DrawLinearLayout);
        paintColor = array.getColor(R.styleable.DrawLinearLayout_paintColor, 0xFF3699FF);
        radius = array.getDimensionPixelOffset(R.styleable.DrawLinearLayout_radius, 0);


        paint.setColor(paintColor);
        int paintStyle = array.getInt(R.styleable.DrawLinearLayout_paintStytle, 1);
        paint.setStyle(Paint.Style.values()[paintStyle]);
        paint.setStrokeWidth(array.getDimensionPixelOffset(R.styleable.DrawLinearLayout_stroke, 1));
        padding = array.getDimensionPixelOffset(R.styleable.DrawLinearLayout_drawPadding, -1);

        array.recycle();
        setWillNotDraw(false);
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        float left = padding == -1 ? paint.getStrokeWidth()/2 : padding;
        canvas.drawRoundRect(new RectF(left,left,this.getWidth()-left, this.getHeight()-left), radius, radius, paint);

        for (int i = 1; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (this.getOrientation() == HORIZONTAL){
                canvas.drawLine(view.getX(), left, view.getX(), this.getHeight()-left, paint);
            }else{
                canvas.drawLine(left, view.getY(), this.getWidth()-left, view.getY(), paint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.reset();
        mRect.set(0, 0, w, h);
        mPath.addRoundRect(mRect, radius, radius, Path.Direction.CW);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mPath);
        super.dispatchDraw(canvas);
        canvas.restore();
    }
}
