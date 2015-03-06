package nl.elements.spotlighttutorialdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import nl.elements.spotlighttutorialdemo.gradientexamples.FullGradientActivity;
import nl.elements.spotlighttutorialdemo.gradientexamples.GradientOnPaintActivity;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.activity_main_listview);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Constants.mainMenuItems));

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(position == 0)
        {
            startActivity(new Intent(getApplicationContext(), FullGradientActivity.class));
        }
        else if(position == 1)
        {
            startActivity(new Intent(getApplicationContext(), GradientOnPaintActivity.class));
        }
        else if(position == 2)
        {
            startActivity(new Intent(this, SpotlightNormalActivity.class));
        }
    }
}
