package edu.umich.engin.cm.onecoolthing.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.umich.engin.cm.onecoolthing.NetworkUtils.ImageLoader;
import edu.umich.engin.cm.onecoolthing.R;

/**
 * Created by jawad on 20/10/14.
 *
 * Displays a single "CoolThing" object, with only title and background
 */
public class FragmentCoolThing extends Fragment {
    // View elements
    private ImageView background;
    private TextView titleView;

    // Determines if the layout is ready to be modified or not
    private boolean viewReady = false;
    // Makes sure that the image+text isn't assigned twice
    private boolean assignedData = false;

    // Holds the data to display for this image
    private String titleText;
    private String imageURL;

    // Placeholder background, until image loads
    private Bitmap placeholderBG;

    // Spinner to display until cool thing loads
    private ProgressBar spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cool_things, container, false);

        // Get the spinner and set it visible
        spinner = (ProgressBar) view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        // Get the view elements
        background = (ImageView) view.findViewById(R.id.background);
        titleView = (TextView) view.findViewById(R.id.title);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // If an imageURL has been given AND bg hasn't been set up yet, set it up
        if(imageURL != null && !assignedData) {
            setUpCoolThing();
        }

        // State that the layout is ready to be modified
        viewReady = true; // Set now, as currently used layout modifiers use GetActivity()
    }

    // Sets up the Cool Thing
    private void setUpCoolThing() {
        // State that the image has - supposedly - been assigned already
        assignedData = true;

        // If there's a placeholder BG, place it now
        if(placeholderBG != null) {
            background.setImageBitmap(placeholderBG);
        }

        // Call the imageLoader to lazily set up the imageView AND text at the same time
            // AND allow it to stop the spinner
                // TODO: Bad design, rework this so a single class/thread handles this?
        ImageLoader imageLoader = new ImageLoader(getActivity());
        imageLoader.DisplayImageAndTextAndSpinner(imageURL, background, 1, titleView,
                titleText, spinner);
    }

    // Set the background image's URL and title text at the same time, to avoid any potential errors
    public void setImageURLAndText(String url, String title) {
        // Set the data
        setBackgroundURL(url);
        setTitleText(title);

        // If the fragment's layout has been set up already and image not yet set, set it up
        if(viewReady && !assignedData) setUpCoolThing();
    }

    public void setBackgroundURL(String url) {
        // Set the background image url
        this.imageURL = url;
    }

    public void setTitleText(String title) {
       // Set the title text for this cool thing
       this.titleText = title;
    }

    // Set a placeholder background image to use
    public void setBackgroundPlaceholder(Bitmap bitmap) {
        this.placeholderBG = bitmap;
    }

    // Check if this fragment has been set up yet or not
    public boolean checkIfSet() {
        return assignedData;
    }
}
