package yhshih.apps.basictwitter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import yhshih.apps.basictwitter.models.Tweet;
import android.content.Context;
import android.net.ParseException;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for position
		Tweet tweet = getItem(position);
		
		// Find or inflate the template
		View v;	// for performance reason
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		// Find the views within template
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvCreatedTime = (TextView) v.findViewById(R.id.tvCreatedTime);
		ivProfileImage.setImageResource(android.R.color.transparent);	// clear the previous loaded items
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		// Populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvCreatedTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		return v;
	}

	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);

		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return relativeDate;
	}	
}
