package edu.umich.engin.cm.onecoolthing.CoolThings;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.umich.engin.cm.onecoolthing.NetworkUtils.ImageLoaderNoCache;
import edu.umich.engin.cm.onecoolthing.R;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by jawad on 14/10/14.
 */
public class OneCoolFeedFrag extends Fragment implements ViewPager.OnPageChangeListener,
        ImageLoaderNoCache.LoaderManager {
    private final String TAG = "MD/OneCoolFeed"; // Tag for logging from this class

    // The background behind the viewPager
    ImageView background;

    // The viewPager shown via this fragment
    VerticalViewPager mViewPager;
    VertPagerCommunicator communicator; // Allows feedback to the activity

    // The adapter that controls the pager
    CoolThingsPagerAdapter pagerAdapter;

    // Caches the current page selected
    int currentPageSelected = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_coolfeed, container, false);

       // Get the background imageView to set the background later
       background = (ImageView) view.findViewById(R.id.background);

       // Get the viewpager from the layout but init later
       mViewPager = (VerticalViewPager) view.findViewById(R.id.pager);

       return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Initialize the viewpager with the activity's context
        initCoolViewPager(getActivity());
    }

    private void initCoolViewPager(Context context) {
        // ONLY set the pagerAdapter if necessary
            // ie, when restarting activity/frag, pagerAdapter already exists
        if(pagerAdapter == null) {
            // Set up the pagerAdapter to handle the different "pages"/fragments
            pagerAdapter = new CoolThingsPagerAdapter(getChildFragmentManager());
            pagerAdapter.initAdapter(context, this);

            // Set the pageAdapter to the ViewPager
            mViewPager.setAdapter(pagerAdapter);

            // Make this fragment listen to the adapter's changes
            mViewPager.setOnPageChangeListener(this);

            // Set the pager to retain 5 of the Cool Things at one time
            // Otherwise, only saves one Cool Thing at a time- so have to reload every one
            mViewPager.setOffscreenPageLimit(5);
        }
    }

    // Passes the appropriate data for the communicator at the position
    private void notifyCommunicator(int position) {
        // Double check that the communicator has been set
        if(communicator == null) {
           Log.e(TAG, "Communicator was null!");
            return;
        }

        // Get the data from the page to pass on
        String subTitle = pagerAdapter.getSubTitle(position);
        String body = pagerAdapter.getBodyText(position);
        String paletteColor = pagerAdapter.getPaletteColor(position);
        String fullItemURL = pagerAdapter.getFullItemURL(position);

        // Send the information to the activity
        communicator.changeRightSlide(subTitle, body, paletteColor, fullItemURL);
    }

    // Set the background of the ViewPager
    public void setBackground(String url) {
        // Use the ImageLoader to get the background bitmap
        ImageLoaderNoCache imageLoader = new ImageLoaderNoCache();
        ImageLoaderNoCache.LoaderManager manager[] = {this};
        imageLoader.GetImage(url, manager);

        // Now the right sliding menu can be set up finally for the first time
        notifyCommunicator(0);
    }


    // Sets the communicator so the fragment can notify the activity of changes in pager
    public void setCommunicator(VertPagerCommunicator comm) {
        this.communicator = comm;
    }

    // Gets the url to share to everybody
    public String getShareUrl() {
        // Simply get and return the current url from the pagerAdapter
        return pagerAdapter.getFullItemURL(currentPageSelected);
    }

    // Gets the text for tweeting
    public String getTweetText() {
        return pagerAdapter.getTweetText(currentPageSelected);
    }

    // Get the subject line - ie short title - for general sharing
    public String getSubjectForSharing() {
        return pagerAdapter.getSubTitle(currentPageSelected);
    }

    @Override
    public void notifyDataLoaded() {
        // Unneccessary
    }

    @Override
    public void notifyRetrievedBitmap(Bitmap bitmap) {
        // Set the background of the ViewPager to the bitmap's
        background.setImageBitmap(bitmap);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    // When a new page is selected, notify the activity so it can change another view to show data
    @Override
    public void onPageSelected(int i) {
        // If no communicator set, do nothing
        if(communicator == null) return;

        // Let the function notify the communicator of the data at position i
        notifyCommunicator(i);

        // Cache the current position
        currentPageSelected = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    // TODO: Change this to work better with final design, including names and actual usage
    // Currently, simply tells the ActivityTestCenter to change the right slider's color
    //      to the color that the center fragment is using
    public interface VertPagerCommunicator {
      public void changeRightSlide(String subTitle, String body, String paletteColor, String fullItemURL);
    }
}
