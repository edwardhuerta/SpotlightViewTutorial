package nl.elements.spotlighttutoriallibrary.model;

import android.view.View;

/**
 * A billboard is the sign that is used together with a spotlight.
 * It contains the text explaining to the user what the element under
 * the spotlight does.
 */
public class Billboard
{
    private final View billboardView;

    public Billboard(View billboardView)
    {
        this.billboardView = billboardView;
    }

    public View getBillboardView()
    {
        return billboardView;
    }
}
