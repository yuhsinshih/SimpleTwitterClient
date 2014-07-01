package yhshih.apps.basictwitter;

import org.json.JSONObject;

import yhshih.apps.basictwitter.fragments.HomeTimelineFragment;
import yhshih.apps.basictwitter.fragments.MentionsTimelineFragment;
import yhshih.apps.basictwitter.listeners.FragmentTabListener;
import yhshih.apps.basictwitter.models.User;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
	
	User me;
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
		client = TwitterApplication.getRestClient();
		getMyLoginInfo();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
						HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
			    		MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}

	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}
	
	public void getMyLoginInfo() {
		client.getMyInfo(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json) {
				me = User.fromJson(json);
			}
			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
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
	
//	public void updateTweet(String status) {
//		client.updateTweet(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject json) {
//				populateTimeline(1, true);
//			}
//			@Override
//			public void onFailure(Throwable e, String s) {
//				Log.d("debug", e.toString());
//				Log.d("debug", s.toString());
//			}
//		}, status);
//	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if(requestCode == 50) {
				String status = data.getStringExtra("status");
				Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
//				updateTweet(status);
			}
		}
	}
}
