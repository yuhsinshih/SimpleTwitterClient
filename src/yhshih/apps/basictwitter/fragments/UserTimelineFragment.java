package yhshih.apps.basictwitter.fragments;

import org.json.JSONArray;

import yhshih.apps.basictwitter.models.Tweet;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {


	public static UserTimelineFragment newInstance(String screen_name) {
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("screen_name", screen_name);
		fragment.setArguments(args);
		return fragment;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String screen_name = getArguments().getString("screen_name");
		if(screen_name == null)
			populateTimeline(1, false);
		else
			populateTimeline(1, false, screen_name);
	}
	
	public void populateTimeline(int offset, boolean clear) {
		final boolean clearResult = clear;

		getClient().getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				if(clearResult == true){
					clearTweets();
				}
				addAll(Tweet.fromJSONArray(json));
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, offset);
	}

	public void populateTimeline(int offset, boolean clear, String screen) {
		final boolean clearResult = clear;

		getClient().getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				if(clearResult == true){
					clearTweets();
				}
				addAll(Tweet.fromJSONArray(json));
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, offset, screen);
	}
}
