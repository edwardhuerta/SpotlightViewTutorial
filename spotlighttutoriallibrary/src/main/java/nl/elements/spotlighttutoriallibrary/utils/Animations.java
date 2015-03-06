package nl.elements.spotlighttutoriallibrary.utils;

import android.animation.ObjectAnimator;

import nl.elements.spotlighttutoriallibrary.views.SpotlightView;


/**
 * Created by edward on 2/10/15.
 */
public class Animations {

    public static ObjectAnimator moveAnimation(SpotlightView spotlightView, String propertyToAnimate, float lastValue, int duration) {
        ObjectAnimator moveX = ObjectAnimator.ofFloat(spotlightView, propertyToAnimate, lastValue);
        moveX.setDuration(duration);
        return moveX;
    }

    /**
     * Creates an animation where the scale of the mask is changed.
     * @param spotlightView the view to change the scale property
     * @param duration how long the animation should last
     * @param maskScale the target size.
     * @return An animation
     */
    public static ObjectAnimator zoomIn(SpotlightView spotlightView, int duration, float maskScale)
    {
        ObjectAnimator animation =  ObjectAnimator.ofFloat(spotlightView, "maskScale", maskScale);
        animation.setDuration(duration);

        return animation;
    }
}
