package yhshih.apps.basictwitter.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import yhshih.apps.basictwitter.models.Tweet;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateTimeline(1, false);
	}
	
	public void populateTimeline(int offset, boolean clear) {
		final boolean clearResult = clear;

		getClient().getHomeTimeline(new JsonHttpResponseHandler() {
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
	
	public void updateTweet(String status) {
		getClient().updateTweet(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				populateTimeline(1, true);
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, status);
	}
}
