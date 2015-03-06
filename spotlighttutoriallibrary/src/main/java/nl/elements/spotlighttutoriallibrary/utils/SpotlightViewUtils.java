package nl.elements.spotlighttutoriallibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * Created by edward on 3/5/15.
 */
public class SpotlightViewUtils
{
    public static Bitmap convertToAlphaMask(Bitmap b)
    {
        Bitmap a = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ALPHA_8);
        Canvas c = new Canvas(a);
        c.drawBitmap(b, 0.0f, 0.0f, null);

        return a;
    }

    public static Bitmap createBitmap(View target)
    {
        Bitmap b = Bitmap.createBitmap(target.getWidth(), target.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        target.draw(c);

        /* enable this to invert the image: */
        //invertImage(b);

        return b;
    }

    public static void invertImage(Bitmap b)
    {
        int A, R, G, B;
        int pixelColor;
        int height = b.getHeight();
        int width = b.getWidth();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                pixelColor = b.getPixel(x, y);
                A = Color.alpha(pixelColor);

                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);

                b.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
    }
}
