package yhshih.apps.basictwitter.fragments;

import org.json.JSONArray;

import yhshih.apps.basictwitter.models.Tweet;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

	String screen_name = null;

	public static UserTimelineFragment newInstance(String screen_name) {
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("screen_name", screen_name);
		fragment.setArguments(args);
		return fragment;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.screen_name = getArguments().getString("screen_name");
		if(this.screen_name == null)
			populateTimeline(1, false);
		else
			populateTimeline(1, false, this.screen_name);
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
	
	public void customLoadMoreDataFromApi(int offset) {
		if(this.screen_name == null)
			populateTimeline(offset, false);
		else
			populateTimeline(offset, false, this.screen_name);
	}
}
