package yhshih.apps.basictwitter.fragments;

import java.util.ArrayList;

import yhshih.apps.basictwitter.R;
import yhshih.apps.basictwitter.TweetArrayAdapter;
import yhshih.apps.basictwitter.TwitterApplication;
import yhshih.apps.basictwitter.TwitterClient;
import yhshih.apps.basictwitter.listeners.EndlessScrollListener;
import yhshih.apps.basictwitter.models.Tweet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public abstract class TweetsListFragment extends Fragment {

	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	private TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Non-view initialization
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApplication.getRestClient();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// Assign our view references
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		
//		String screen_name = getArguments().getString("screen_name");

		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

				customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount); 
			}
		});
		return v;
	}
	
	// return the adapter to the activity
//	public TweetArrayAdapter getAdapter() {
//		return aTweets;
//	}
	
	// Delegate the adding to the internal adapter -- preferred, minimize exposure, reduce chain call
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public void clearTweets() {
		if(tweets != null)
			tweets.clear();
	}
	
	public TwitterClient getClient(){
		return client;
	}
	
	abstract void populateTimeline(int offset, boolean clear);
	
	// Append more data into the adapter
	public void customLoadMoreDataFromApi(int offset) {
		// This method probably sends out a network request and appends new data items to your adapter. 
		// Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		// Deserialize API response and then construct new objects to append to the adapter
		populateTimeline(offset, false);
	}
}
