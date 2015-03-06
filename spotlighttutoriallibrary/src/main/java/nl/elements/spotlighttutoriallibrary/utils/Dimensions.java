package nl.elements.spotlighttutoriallibrary.utils;

import android.graphics.Rect;
import android.view.View;

import nl.elements.spotlighttutoriallibrary.model.ViewSection;

/**
 * Created by edward on 2/10/15.
 */
public class Dimensions
{
    public static float getCenterX(ViewSection viewSection)
    {
        View view = viewSection.getView();
        float width = view.getRight() - view.getLeft();

        int[] pointArr = new int[2];
        view.getLocationOnScreen(pointArr);
        return pointArr[0] + (width / 2f);
    }

    public static float getCenterY(int actionBarHeight, ViewSection viewSection)
    {
        View view = viewSection.getView();

        int bottom = view.getBottom();
        int top = view.getTop();
        float height = bottom - top;

        Rect positionRect = new Rect();
        view.getGlobalVisibleRect(positionRect);
        final int[] locationInWindow = new int[2];
        view.getLocationInWindow(locationInWindow);

        int[] locationOnScreen = new int[2];
        view.getLocationOnScreen(locationOnScreen);

        int referenceTop = positionRect.top - actionBarHeight;

        return referenceTop + (height / 2f);
    }
}
