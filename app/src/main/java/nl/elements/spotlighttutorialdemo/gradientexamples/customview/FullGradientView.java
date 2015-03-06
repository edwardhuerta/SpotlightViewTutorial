package nl.elements.spotlighttutorialdemo.gradientexamples.customview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import nl.elements.spotlighttutorialdemo.R;

/**
 * Created by edward on 2/16/15.
 */
public class FullGradientView extends View
{
    private final Paint colorbrush = new Paint();

    public FullGradientView(Context context)
    {
        this(context, null);
    }

    public FullGradientView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FullGradientView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();

        GradientDrawable background = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, getColors());
        setBackground(background);

        colorbrush.setColor(getResources().getColor(R.color.yellow));
    }

    private int[] getColors()
    {
        return new int[]{
                getResources().getColor(R.color.gradient_bluish),
                getResources().getColor(R.color.gradient_reddish),
                getResources().getColor(R.color.gradient_dark_blue),
                getResources().getColor(R.color.gradient_pink),
                getResources().getColor(R.color.gradient_dark_gray),
                getResources().getColor(R.color.gradient_greenish)
        };
    }
}
