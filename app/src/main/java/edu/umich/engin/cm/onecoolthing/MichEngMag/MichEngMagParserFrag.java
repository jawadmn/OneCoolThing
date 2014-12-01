package edu.umich.engin.cm.onecoolthing.MichEngMag;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.umich.engin.cm.onecoolthing.R;

/**
 * Created by jawad on 20/11/14.
 */
public class MichEngMagParserFrag extends Fragment {
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_michengmag, container, false);

        // Cache the listView to set it up later
        mListView = (ListView) view.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Now start the initial setup process
        initSetup();
    }

    private void initSetup() {
        // Set up the adapter for the listView
        MichEngMagListAdapter adapter = new MichEngMagListAdapter(getActivity());

        // Set the adapter as the listView's adapter
        mListView.setAdapter(adapter);
    }
}