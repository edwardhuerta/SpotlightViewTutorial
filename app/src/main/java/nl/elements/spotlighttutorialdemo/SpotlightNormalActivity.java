package nl.elements.spotlighttutorialdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import nl.elements.spotlighttutoriallibrary.TutorialBuilder;
import nl.elements.spotlighttutoriallibrary.views.SpotlightView;

/**
 * The activity that displays a normal SpotlightView with no extra effects.
 * Note: there are three separate activities so that each can be independent of each other.
 */
public class SpotlightNormalActivity extends Activity
{
    private static final boolean ACTIVATE_SPOTLIGHT = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotlight_normal);

        /* example of how to use the SpotlightView */

        // If you don't want the spotlight to activate, then don't event set it up.
        if(ACTIVATE_SPOTLIGHT)
        {
            final SpotlightView spotlightView = (SpotlightView) findViewById(R.id.real_spotlight);

            if (spotlightView != null)
            {
                // this view will help us find all the helper views (for example, the buttons).
                final ViewGroup parentLayout = (ViewGroup) findViewById(R.id.activity_container);

                spotlightView.setAnimationSetupCallback(new SpotlightView.AnimationSetupCallback()
                {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public Runnable onSetupAnimation(final SpotlightView spotlight)
                    {
                        TutorialBuilder builder = new TutorialBuilder(getApplicationContext(), parentLayout, spotlight);

                        builder.addViewId(R.id.tutorial_start_here)
                                .addViewIdForBillboard(R.layout.first_billboard)
                                .afterClick(R.id.click_goto_next_screen);

                        builder.addViewId(R.id.tutorial_second_end_here)
                                .afterClick(R.id.click_to_second_finish);

                        builder.addViewId(R.id.long_text)
                                .afterClick(R.id.click_goto_after_last);

                        builder.addViewId(R.id.tutorial_end_here)
                                .addViewIdForBillboard(R.layout.last_billboard)
                                .afterClick(R.id.click_to_finish)
                                .finish(new TutorialBuilder.FinishTutorialCallback()
                                {
                                    @Override
                                    public void onFinish()
                                    {
                                        Toast.makeText(getApplicationContext(), "Finished Tutorial", Toast.LENGTH_SHORT).show();
                                        spotlight.setVisibility(View.INVISIBLE);
                                        findViewById(R.id.mask_over).setAlpha(1f);
                                    }
                                });

                        return builder.build();
                    }
                });
            }
        }
        else
        {
            // let's say that you want to skip the SpotlightView, so then hide the layout with all the buttons.
            findViewById(R.id.button_container).setVisibility(View.GONE);
        }
    }
}
