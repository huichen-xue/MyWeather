package activity;

import com.myweather.app.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import util.Utility;

public class ChooseAreaActivity extends Activity {
	String httpUrl = "http://apis.baidu.com/heweather/weather/free";
	String httpArg;
	TextView text_view;
	Button button;
	EditText edit_text;
	String txt;
	private ProgressDialog progressDialog;
	private boolean isFromWeatherActivity;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity",false);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if(prefs.getBoolean("city_selected", false) && !isFromWeatherActivity){
			Intent intent = new Intent(this,WeatherActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		intUI();
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgressDialog();
				new Thread() {
					public void run() {
						super.run();
						httpArg = edit_text.getText().toString();
						Log.d("ChooseAreaActivity", httpArg);
						String result = Utility.request(httpUrl, httpArg);
						if (result != null) {
							Utility.handleWeatherResponse(ChooseAreaActivity.this, result);
							System.out.println("成功啦！！！！！！！！！！！！！！");
							closeProgressDialog();
							Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
							startActivity(intent);
							finish();
							// System.out.println("1111111111"+txt);
							// System.out.println(result);
							// text_view.setText(result);
						} else {
							System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
						}
					}
				}.start();
			}
		});

	}

	private void intUI() {
		text_view = (TextView) findViewById(R.id.tv1);
		button = (Button) findViewById(R.id.bt1);
		edit_text = (EditText) findViewById(R.id.et1);

	}
	private void showProgressDialog(){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在查询，请稍后");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	private void closeProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}

}
