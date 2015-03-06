package nl.elements.spotlighttutoriallibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.elements.spotlighttutoriallibrary.model.ViewSection;
import nl.elements.spotlighttutoriallibrary.utils.Animations;
import nl.elements.spotlighttutoriallibrary.utils.Dimensions;
import nl.elements.spotlighttutoriallibrary.views.SpotlightView;

/**
 * Created by edward on 2/9/15.
 */
public class TutorialBuilder
{
    private final Context                context;
    private final ViewGroup              parentView;
    private final SpotlightView          spotlightView;
    private final List<ViewSection>      allSections;
    private       FinishTutorialCallback finalCallback;


    public TutorialBuilder(Context context, ViewGroup rootLayout, SpotlightView spotlightView)
    {
        allSections = new ArrayList<ViewSection>();
        this.context = context;
        this.parentView = rootLayout;
        this.spotlightView = spotlightView;
    }

    /**
     * This method adds a view to put the spotlight on.
     *
     * @param tutorial_start_here an id of a view
     * @return the container for convenience
     */
    public ViewSection addViewId(int tutorial_start_here)
    {
        View view = parentView.findViewById(tutorial_start_here);

        if (view == null)
        {
            throw new RuntimeException("view is null while adding ViewSection");
        }

        ViewSection section = new ViewSection(view, context, parentView);
        allSections.add(section);
        return section;
    }

    public Runnable build()
    {
        final ViewGroup rootLayout = this.parentView;

        int[] spotlightViewLocationOnScreen = new int[2];
        spotlightView.getLocationOnScreen(spotlightViewLocationOnScreen);
        final int yOffset = spotlightViewLocationOnScreen[1];

        return new Runnable()
        {
            final SparseArray<AnimatorSet> animationCollections = new SparseArray<AnimatorSet>();
            final AnimatorSet initialSet = new AnimatorSet();
            int lastAnimationIndex = 0;

            @Override
            public void run()
            {
                //start here
                spotlightView.setMaskX(-300);
                spotlightView.setMaskY(-300);

                //this adds a zooming-in effect
                float maxDimension = Math.max(spotlightView.getHeight(), spotlightView.getWidth()) * 5.0f;
                float scale = spotlightView.computeMaskScale(maxDimension);
                spotlightView.setMaskScale(scale);

                for (int i = 0; i < allSections.size(); i++)
                {
                    final AnimatorSet curAnimationSet;

                    if (animationCollections.get(i, null) != null)
                    {
                        // an animation set has been created before, use it!
                        curAnimationSet = animationCollections.get(i);
                    } else
                    {
                        curAnimationSet = initialSet;
                    }

                    final ViewSection itrSection = allSections.get(i);

                    // Add Animation
                    int duration = 800;

                    curAnimationSet
                            .play(Animations.zoomIn(spotlightView, duration, 1.1f))
                            .with(Animations.moveAnimation(spotlightView, "maskX", Dimensions.getCenterX(itrSection), duration))
                            .with(Animations.moveAnimation(spotlightView, "maskY", Dimensions.getCenterY(yOffset,itrSection), duration));

                    if (itrSection.getAddClickToGoToNext() != null)
                    {
                        //there is a button, so show it and the billboard if there is one.
                        curAnimationSet.addListener(new AnimatorListenerImpl(itrSection, rootLayout));

                        if (allSections.size() == i + 1)
                        {
                            if (itrSection.getFinishedCallback() != null)
                            {
                                itrSection.setClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        itrSection.getFinishedCallback().onFinish();
                                        v.setVisibility(View.INVISIBLE);
                                    }
                                });
                            } else
                            {
                                throw new RuntimeException("The last animation is not finished. Please use the finish() method.");
                            }
                        } else
                        {
                            AnimatorSet newSet = new AnimatorSet();
                            animationCollections.put((i + 1), newSet);

                            itrSection.setClickListener(new ButtonClickListener(newSet));
                        }
                    }
                    lastAnimationIndex = i;
                }

                initialSet.start();
            }
        };
    }

    class ButtonClickListener implements View.OnClickListener
    {
        private final AnimatorSet animatorSet;

        public ButtonClickListener(AnimatorSet nextIndex)
        {
            this.animatorSet = nextIndex;
        }

        @Override
        public void onClick(View v)
        {
            animatorSet.start();

            //this should be optional
            v.setVisibility(View.INVISIBLE);
        }
    }

    class AnimatorListenerImpl implements Animator.AnimatorListener
    {
        private final ViewSection itrSection;
        private final ViewGroup rootLayout;

        AnimatorListenerImpl(ViewSection itrSection, ViewGroup rootLayout)
        {
            this.itrSection = itrSection;
            this.rootLayout = rootLayout;
        }

        @Override
        public void onAnimationStart(Animator animation) {}

        @Override
        public void onAnimationEnd(Animator animation)
        {
            //show the button
            itrSection.getAddClickToGoToNext().setVisibility(View.VISIBLE);
            //show the billboard
            if (itrSection.getBillboard() != null)
            {
                View billboardView = itrSection.getBillboard().getBillboardView();
                rootLayout.addView(billboardView);
                billboardView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    }

    public interface FinishTutorialCallback
    {
        void onFinish();
    }
}
