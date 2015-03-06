package nl.elements.spotlighttutoriallibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import nl.elements.spotlighttutoriallibrary.R;
import nl.elements.spotlighttutoriallibrary.utils.SpotlightViewUtils;

/**
 * The normal spotlightview with no additional effects.
 */
public class SpotlightView extends View
{
    /**
     * The "Container" that will contain the contents of the shader.
     */
    private Bitmap mMask;
    private int    mTargetId;

    private float mMaskX;
    private float mMaskY;
    private float mMaskScale;

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint  mPaint        = new Paint();

    private AnimationSetupCallback mCallback;

    public SpotlightView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpotlightView, 0, 0);
        try
        {
            mTargetId = a.getResourceId(R.styleable.SpotlightView_target, 0);

            int maskId = a.getResourceId(R.styleable.SpotlightView_mask, 0);
            mMask = SpotlightViewUtils.convertToAlphaMask(BitmapFactory.decodeResource(getResources(), maskId));

            init();
        } catch (Exception e)
        {
            throw new RuntimeException("Error while creating the view.", e);
        } finally
        {
            a.recycle();
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // usual calculations to place the mask
        float halfWidth = mMask.getWidth() / 2.0f;
        float halfHeight = mMask.getHeight() / 2.0f;

        float x = mMaskX - halfWidth * mMaskScale;
        float y = mMaskY - halfHeight * mMaskScale;

        // move the shader
        mShaderMatrix.setScale(1.0f / mMaskScale, 1.0f / mMaskScale);
        mShaderMatrix.preTranslate(-x, -y);

        if (!isInEditMode())
        {
            // If displaying on Android Studio preview, then ignore next call. It will show
            // an Exception otherwise.
            if (mPaint.getShader() != null)
            {
                mPaint.getShader().setLocalMatrix(mShaderMatrix);
            } else
            {
                throw new RuntimeException("no shader defined");
            }
        }

        // move the mask
        canvas.translate(x, y);
        canvas.scale(mMaskScale, mMaskScale);
        canvas.drawBitmap(mMask, 0.0f, 0.0f, mPaint);
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout()
            {
                createShader();
                setMaskX(-300);
                setMaskY(-300);
                setMaskScale(1.1f);

                if (mCallback != null)
                {
                    Runnable runnable = mCallback.onSetupAnimation(SpotlightView.this);
                    animate().alpha(1.0f).withLayer().withEndAction(runnable);
                }

                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return true;
    }

    private void init()
    {
        // other SpotlightView variants have different implementations here
    }

    private void createShader()
    {
        View target = getRootView().findViewById(mTargetId);
        Bitmap bitmapOfView = SpotlightViewUtils.createBitmap(target);

        Shader targetShader = new BitmapShader(bitmapOfView, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(targetShader);
    }

    public void swapCanvas(int layoutToSwap)
    {
        //first hide the current layout shown in the background.
        getRootView().findViewById(mTargetId).setVisibility(INVISIBLE);
        //update our local property to hold the new view id
        mTargetId = layoutToSwap;
        //find the new view
        getRootView().findViewById(mTargetId).setVisibility(VISIBLE);

        //recreate the shader
        createShader();
    }

    public void setAnimationSetupCallback(AnimationSetupCallback callback)
    {
        mCallback = callback;
    }

    public float computeMaskScale(float d)
    {
        // Let's assume the mask is square
        return d / (float) mMask.getHeight();
    }

    /* all getters, setters */

    public float getMaskScale()
    {
        return mMaskScale;
    }

    public void setMaskScale(float maskScale)
    {
        mMaskScale = maskScale;
        invalidate();
    }

    public void setMaskX(float maskX)
    {
        mMaskX = maskX;
        invalidate();
    }

    public float getMaskX()
    {
        return mMaskX;
    }

    public float getMaskY()
    {
        return mMaskY;
    }

    public void setMaskY(float maskY)
    {
        mMaskY = maskY;
        invalidate();
    }

    /**
     * When the spotlightView is Ready, this callback will be called
     * and you can start using the Builder to generate all animations.
     */
    public interface AnimationSetupCallback
    {
        /**
         * @param spotlight The spotlightview that is activated.
         * @return This Runnable will be executed. You can return the Runnable that can be generated by TutorialBuilder,
         * or your own Runnable of course.
         */
        Runnable onSetupAnimation(SpotlightView spotlight);
    }
}
