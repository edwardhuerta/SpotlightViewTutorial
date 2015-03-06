package nl.elements.spotlighttutoriallibrary.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.elements.spotlighttutoriallibrary.TutorialBuilder;

/**
 *
 * The container holding a single view to put the spotlight on,
 * and all the billboards that will appear.
 */
public class ViewSection
{
    private final Context                                context;
    private final ViewGroup                              spotlightView;
    private       Billboard                              billboard;
    private final View                                   view;
    private       View                                   addClickToGoToNext;
    private       Runnable                               inbetweenCallback;
    private       TutorialBuilder.FinishTutorialCallback finishedCallback;

    public Billboard getBillboard()
    {
        return billboard;
    }

    public TutorialBuilder.FinishTutorialCallback getFinishedCallback()
    {
        return finishedCallback;
    }

    public ViewSection(View view, Context context, ViewGroup spotlightView)
    {
        this.view = view;
        this.context = context;
        this.spotlightView = spotlightView;
    }

    public ViewSection addViewIdForBillboard(int billboardId)
    {
        View billboardView = LayoutInflater.from(context).inflate(billboardId, null, false);
        this.billboard = new Billboard(billboardView);
        return this;
    }

    public View getView()
    {
        return view;
    }

    public ViewSection afterClick(int goto_next_screen)
    {
        View addClickToGoToNext = spotlightView.findViewById(goto_next_screen);

        if (addClickToGoToNext == null)
        {
            throw new RuntimeException("view for clicking is null when calling afterClick");
        }

        addClickToGoToNext.setVisibility(View.INVISIBLE);

        this.addClickToGoToNext = addClickToGoToNext;

        return this;
    }

    public void inbetweenCallback(Runnable runnable)
    {
        this.inbetweenCallback = runnable;
    }

    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        if (view == null)
        {
            b.append("no view set");
        } else
        {
            b.append("view set: ").append(this.view);
        }
        b.append(" ");
        if (addClickToGoToNext != null)
        {
            b.append("has click listener");
        } else
        {
            b.append("no click listener attached.");
        }

        return b.toString();
    }

    public void finish(TutorialBuilder.FinishTutorialCallback callback)
    {
        this.finishedCallback = callback;
    }

    public void setClickListener(final View.OnClickListener wrappedListener)
    {
        addClickToGoToNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (billboard != null && billboard.getBillboardView() != null)
                {
                    billboard.getBillboardView().setVisibility(View.INVISIBLE);
                }

                if(inbetweenCallback != null)
                {
                    inbetweenCallback.run();
                }

                wrappedListener.onClick(v);
            }
        });
    }

    public View getAddClickToGoToNext()
    {
        return addClickToGoToNext;
    }
}
