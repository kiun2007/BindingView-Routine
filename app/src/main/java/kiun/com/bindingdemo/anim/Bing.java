package kiun.com.bindingdemo.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kiun.com.bindingdemo.R;


/**
 * @Description: java类作用描述
 * @Author: laoMo
 * @CreateDate: 2019/5/13 15:09
 */
public class Bing extends LinearLayout {

    private float mWidth;
    private float mHeight;
    private Paint mPaint;
    private float mCenterX;
    private float mCenterY;
    private Handler mHandler;

    //整个动画的进度
    private float mCurrentStep = 0;
    private Bitmap mBitmap_bg, mBitmap_quan_1, mBg_quan_2;
    private Rect mBg_src;
    private RectF mBg_dst;
    private Rect mQuan_1_src;
    private RectF mQuan_1_dst;
    private Rect mQuan_2_src;
    private RectF mQuan_2_dst;
    private Bitmap mBing_bing;
    private Rect mBing_bing_src;
    private RectF mBing_bing_dst;
    private Bitmap mBing_quan_1;
    private Rect mBing_quan_1_src;
    private RectF mBing_quan_1_dst;
    private Bitmap mBing_quan_2;
    private Rect mBing_quan_2_src;
    private RectF mBing_quan_2_dst;


    private float mBingEndPointX = 0;
    private float mBingEndPointY = 0;

    public Bing(Context context) {
        this(context, null);
    }

    public Bing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mCurrentStep += 2;
                if (mCurrentStep >= 200) {
                    if (mListener != null) {
                        mListener.onFinish();
                    }
                    return;
                }
                mCurrentStep %= 200;
                invalidate();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(0);
                    }
                }, 10);
            }
        };
        setGravity(Gravity.CENTER);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mBitmap_bg == null) {
            return;
        }

        //动画时间分段,time_subsection之前为放大动画,后边是翻转动画
        float time_subsection = 40;

        long startTime = System.currentTimeMillis();

        //局部使用的进度
        float mCurrentStep = this.mCurrentStep;
        if (this.mCurrentStep > 100) {
            mCurrentStep = 100 - (this.mCurrentStep % 100);
        }

        //半透明背景色
        int j = (int) (mCurrentStep / 200 * 255);
        if (j < 16) {
            canvas.drawColor(Color.parseColor(String.format("#%s000000", "0".concat(Integer.toHexString(j)))));
        } else {
            canvas.drawColor(Color.parseColor(String.format("#%s000000", "".concat(Integer.toHexString(j)))));
        }

        //背景的透明度
        if (this.mCurrentStep > 100) {
            float s = mCurrentStep / 100f - 0.4f;
            s = s < 0 ? 0 : s;
            mPaint.setAlpha((int) (s* 255));
        } else {
            mPaint.setAlpha((int) (mCurrentStep / 100f * 255));
        }
        //当前旋转的角度
        float angle = (this.mCurrentStep % 100) / 100 * 360;

        //背景图
        canvas.drawBitmap(mBitmap_bg, mBg_src, mBg_dst, mPaint);

        //第一圈
        canvas.save();
        canvas.rotate(angle, mCenterX, mCenterY);
        canvas.drawBitmap(mBitmap_quan_1, mQuan_1_src, mQuan_1_dst, mPaint);
        canvas.restore();

        //第二圈
        canvas.save();
        canvas.rotate(angle * -1, mCenterX, mCenterY);
        canvas.drawBitmap(mBg_quan_2, mQuan_2_src, mQuan_2_dst, mPaint);
        canvas.restore();

        if (mCurrentStep > time_subsection && this.mCurrentStep < 100) {
            //透明度从最大到最小的临界值 (70)
            float a = (100 - time_subsection) / 2 + time_subsection;


            //进度
            float step = (Math.abs(a - mCurrentStep)) / (a - time_subsection);

            //从透明到可见
            if (a > mCurrentStep) {
                mPaint.setAlpha((int) ((1 - step) * 255));
            } else {//从可见到透明
                mPaint.setAlpha((int) ((1 - step) * 255));
            }


            //背景圆1
            canvas.save();
            canvas.rotate(angle, mCenterX, mCenterY);
            canvas.drawBitmap(mBing_quan_1, mBing_quan_1_src, mBing_quan_1_dst, mPaint);
            canvas.restore();

            //背景圆2
            canvas.save();
            canvas.rotate(angle * -1, mCenterX, mCenterY);
            canvas.drawBitmap(mBing_quan_2, mBing_quan_2_src, mBing_quan_2_dst, mPaint);
            canvas.restore();
        }


        //小冰雪糕图
        mPaint.setAlpha(255);
        if (this.mCurrentStep < 100) {

            float a = mCurrentStep / time_subsection;
            a = a>1?1:a;
            float r = mBing_bing.getWidth() / 2f / 2;
            r = r * a;
            mBing_bing_dst = new RectF(mCenterX - r, mCenterY - r, mCenterX + r, mCenterY + r);
            canvas.drawBitmap(mBing_bing, mBing_bing_src, mBing_bing_dst, mPaint);

        } else {//移动到左边
            float r = mBing_bing.getWidth() / 2f / 2;
            float a = mCurrentStep / 100;
            a = a > 1 ? 1 : a;
            r = r * a;

            //定义一个最终的半径值,根据当前进度来修改
            float temp_r = 30;

            if (r < temp_r) {
                r = temp_r;
            }

            float pX = mBingEndPointX;
            float pY = mBingEndPointY;

            float z = (pX - mCenterX) * -1;
            float y = (pY - mCenterY) * -1;

            float mCenterX = this.mCenterX - (this.mCurrentStep % 100) / 100 * z;
            float mCenterY = this.mCenterY - (this.mCurrentStep % 100) / 100 * y;

            mBing_bing_dst = new RectF(mCenterX - r, mCenterY - r, mCenterX + r, mCenterY + r);
            canvas.drawBitmap(mBing_bing, mBing_bing_src, mBing_bing_dst, mPaint);
        }
        super.dispatchDraw(canvas);

        Log.i("BING", mCurrentStep + " use time = " + (System.currentTimeMillis() - startTime));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;

        //背景大图圆点
        mBitmap_bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_dian);
        mBg_src = new Rect(0, 0, mBitmap_bg.getWidth(), mBitmap_bg.getHeight());
        mBg_dst = new RectF(0, 0, mWidth, mHeight);

        //第一圈
        mBitmap_quan_1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_quan_1);
        float r = mCenterX / 2;
        mQuan_1_src = new Rect(0, 0, mBitmap_quan_1.getWidth(), mBitmap_quan_1.getHeight());
        mQuan_1_dst = new RectF(mCenterX - r, mCenterY - r, mCenterX + r, mCenterY + r);

        //第二圈
        mBg_quan_2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_quan_2);
        mQuan_2_src = new Rect(0, 0, mBg_quan_2.getWidth(), mBg_quan_2.getHeight());
        mQuan_2_dst = new RectF(mCenterX - r, mCenterY - r, mCenterX + r, mCenterY + r);


        //小冰雪糕图
        mBing_bing = BitmapFactory.decodeResource(getResources(), R.drawable.bing);
        mBing_bing_src = new Rect(0, 0, mBing_bing.getWidth(), mBing_bing.getHeight());

        //背景圆1
        mBing_quan_1 = BitmapFactory.decodeResource(getResources(), R.drawable.bing_quan_1);
        r = mBing_bing.getWidth() / 4f;
        r += r / 7;
        mBing_quan_1_src = new Rect(0, 0, mBing_quan_1.getWidth(), mBing_quan_1.getHeight());
        mBing_quan_1_dst = new RectF(mCenterX - r, mCenterY - r, mCenterX + r, mCenterY + r);


        //背景圆2
        mBing_quan_2 = BitmapFactory.decodeResource(getResources(), R.drawable.bing_quan_2);
        mBing_quan_2_src = new Rect(0, 0, mBing_quan_2.getWidth(), mBing_quan_2.getHeight());
        mBing_quan_2_dst = new RectF(mCenterX - r, mCenterY - r, mCenterX + r, mCenterY + r);
    }


    //开始播放动画
    public void start() {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(0);
            if (mListener != null) {
                mListener.onStart();
            }
        }
    }

    //停止播放动画
    public void stop() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(0);
        }
    }

    //设置小冰图标最终要到达坐标点
    public void setEndPoint(float bingEndPointX, float bingEndPointY) {
        mBingEndPointX = bingEndPointX;
        mBingEndPointY = bingEndPointY;
    }

    private OnAnimationListener mListener;

    public void setListener(OnAnimationListener listener) {
        mListener = listener;
    }

    public interface OnAnimationListener {
        void onStart();
        void onFinish();
    }
}

