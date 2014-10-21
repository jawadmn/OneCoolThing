package edu.umich.engin.cm.onecoolthing.CoolThings;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import edu.umich.engin.cm.onecoolthing.Fragments.FragmentCoolThing;
import edu.umich.engin.cm.onecoolthing.NetworkUtils.ImageLoader;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by jawad on 20/10/14.
 *
 * Displays FragmentCoolThings in a ViewPager
 */
public class CoolThingsPagerAdapter extends FragmentPagerAdapter {
    // List of fragments to display
    ArrayList<FragmentCoolThing> listOfFragCoolThings;

    // Array of all coolThings
    ArrayList<CoolThing> listOfCoolThings;

    // Fragment/page limiter, for debugging purposes
    int pageLimit = 5;

    // Imageloader instance, to load images
    ImageLoader imageLoader;

    public CoolThingsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void initAdapter(Context context, ArrayList<CoolThing> coolThings) {
        // Initialize the imageLoader
        imageLoader = new ImageLoader(context);

        // Save the list of coolThings for later use
        listOfCoolThings = coolThings;

        // Initialize the array of cool thing fragments
        listOfFragCoolThings = new ArrayList<FragmentCoolThing>();

        // Init fragments and with titles, for now
        for(int i = 0; i < pageLimit; ++i) {
            // Initialize fragment
            FragmentCoolThing thisFrag = new FragmentCoolThing();

            // Insert a title into the fragment
            CoolThing thisThing = listOfCoolThings.get(i);
            thisFrag.setTitleText( thisThing.getTitle() );

            // Add the fragment to the array
            listOfFragCoolThings.add(thisFrag);
        }
    }

    // TODO: Set up the background image lazily and efficiently
    @Override
    public Fragment getItem(int i) {
        // Get the fragment
        FragmentCoolThing frag = listOfFragCoolThings.get(i);

        // Let the fragment set up its background
        String url = listOfCoolThings.get(i).getImageURL();
        frag.setBackgroundURL(url);

        return frag;
    }

    @Override
    public int getCount() {
        return pageLimit;
    }
}