package nl.elements.spotlighttutorialdemo.gradientexamples.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by edward on 2/16/15.
 */
public class GradientOnPaintView extends View
{
    private final Paint colorbrush    = new Paint();
    private final Paint gradientBrush = new Paint();

    public GradientOnPaintView(Context context)
    {
        this(context, null);
    }

    public GradientOnPaintView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GradientOnPaintView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        colorbrush.setColor(getResources().getColor(nl.elements.spotlighttutorialdemo.R.color.yellow));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        if(changed)
        {
            LinearGradient shader = new LinearGradient(0, 0, 0, getHeight(), getColors(), null, Shader.TileMode.CLAMP);
            gradientBrush.setShader(shader);
            gradientBrush.setStrokeWidth(30f);
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, 200, 200, gradientBrush);
        canvas.drawRect(100, 400, 200, 850, gradientBrush);
        canvas.drawLine(0, 0, getWidth(), getHeight(), gradientBrush);

        canvas.drawRect(400, 400, 500, 500, colorbrush);
    }

    private int[] getColors()
    {
        return new int[]{
                getResources().getColor(nl.elements.spotlighttutorialdemo.R.color.gradient_bluish),
                getResources().getColor(nl.elements.spotlighttutorialdemo.R.color.gradient_reddish),
                getResources().getColor(nl.elements.spotlighttutorialdemo.R.color.gradient_dark_blue),
                getResources().getColor(nl.elements.spotlighttutorialdemo.R.color.gradient_pink),
                getResources().getColor(nl.elements.spotlighttutorialdemo.R.color.gradient_dark_gray),
                getResources().getColor(nl.elements.spotlighttutorialdemo.R.color.gradient_greenish)
        };
    }
}
