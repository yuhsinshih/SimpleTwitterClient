package yhshih.apps.basictwitter;

import org.json.JSONArray;
import org.json.JSONObject;

//import yhshih.apps.basictwitter.fragments.UserTimelineFragment;
import yhshih.apps.basictwitter.models.User;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
//		String screen_name = getIntent().getStringExtra("screen_name");
//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		UserTimelineFragment utfragment = UserTimelineFragment.newInstance(screen_name);
//		ft.replace(R.id.fragmentUserTimeline, utfragment);
//		ft.commit();
//		if(screen_name == null)
			loadProfileInfo();
//		else
//			loadProfileInfo(screen_name);
	}
	
	
	private void loadProfileInfo() {
		Log.d("debug", "loadProfileInfo 1");
		
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("debug", "loadProfileInfo 2");
//				Log.d("debug", json.toString());
				User u = User.fromJson(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
			@Override
			public void onFailure(Throwable arg0, JSONArray arg1) {
				Log.d("debug", "onProfileLoad fail");
			}
		});
	}

//	private void loadProfileInfo(String screen) {
//		TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(JSONObject json) {
//				User u = User.fromJson(json);
//				getActionBar().setTitle("@" + u.getScreenName());
//				populateProfileHeader(u);
//			}
//		}, screen);
//		
//	}
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		SmartImageView ivProfileImage = (SmartImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFollowingCount() + " Following");
		
		// Setup user image
//		ivImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(user.getProfileImageUrl(), ivProfileImage);
	}


//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		getMenuInflater().inflate(R.menu.twitterclient_menu, menu);
//		return true;
//	}
}
