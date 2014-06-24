package yhshih.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import yhshih.apps.basictwitter.models.Tweet;
import yhshih.apps.basictwitter.models.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	User me;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();

		getMyLoginInfo();
		populateTimeline(1, false);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

				customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount); 
			}
		});
		
	}
	
	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	populateTimeline(offset, false);

    }
    
	public void populateTimeline(int offset, boolean clear) {
		final boolean clearResult = clear;

		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
//				Log.d("debug", json.toString());
				if(clearResult == true){
					tweets.clear();
				}
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, offset);
	}
	
	public void getMyLoginInfo() {
		client.getLoginUser(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json) {
				me = User.fromJson(json);
			}
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    getMenuInflater().inflate(R.menu.twitterclient_menu, menu);
        return true;
	}
	
	public void onCompose(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		// pass data
		i.putExtra("me", me);
		// executing
		startActivityForResult(i, 50);
	}
	
	public void updateTweet(String status) {
//		Toast.makeText(this, "Refreshing tweets", Toast.LENGTH_SHORT).show();
		client.updateTweet(new JsonHttpResponseHandler() {
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
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if(requestCode == 50) {
				String status = data.getStringExtra("status");
				Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
				updateTweet(status);
			}
		}
	}
}
