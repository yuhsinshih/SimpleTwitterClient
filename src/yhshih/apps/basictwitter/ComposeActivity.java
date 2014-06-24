package yhshih.apps.basictwitter;

import yhshih.apps.basictwitter.models.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
//	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		User me = (User) getIntent().getSerializableExtra("me");
		TextView tvName = (TextView) findViewById(R.id.tvComposeName);
		TextView tvScreen = (TextView) findViewById(R.id.tvComposeScreen);
		tvName.setText(me.getName());
		tvScreen.setText("@"+me.getScreenName());
		// Setup user image
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivUser);
		ivImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(me.getProfileImageUrl(), ivImage);
	}
	
	public void onSubmit(View v) {
		Intent i = new Intent();
		EditText etStatus = (EditText) findViewById(R.id.etStatus);
		String postMsg = etStatus.getText().toString();
		if(postMsg.length() > 140) {
			Toast.makeText(this, "Message must less than 140 characters.", Toast.LENGTH_SHORT).show();
			return;
		}
		i.putExtra("status", postMsg);
		setResult(RESULT_OK, i);
		finish();
	}
}
