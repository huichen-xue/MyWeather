package activity;

import com.myweather.app.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.InputFilter.LengthFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity implements OnClickListener{

	private LinearLayout weatherInfoLayout;
	private TextView cityNameText;
	private TextView publishText;
	private TextView weatherDespText;
	private TextView temp1Text;
	private TextView temp2Text;
	private TextView currentDateText;
	private TextView text;
	private String city;
	private String publishTime;
	private String publishDate;
	private String weatherDesp;
	private String temp1;
	private String temp2;
	private String txt;
	private Button switchCity;
	private Button refreshWeather;
	private boolean isExit = false;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			isExit = false;
		}
	};
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		init();
		showWeather();
	}

	public void init() {
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp);
		temp1Text = (TextView) findViewById(R.id.temp1);
		temp2Text = (TextView) findViewById(R.id.temp2);
		currentDateText = (TextView) findViewById(R.id.current_date);
		text = (TextView) findViewById(R.id.weather_txt);
		
		//选择城市和更新天气的按钮
		switchCity = (Button) findViewById(R.id.switch_city);
		switchCity.setOnClickListener(this);
		refreshWeather = (Button) findViewById(R.id.updata);
		refreshWeather.setOnClickListener(this);
	}

	private void showWeather() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		city = "中国-" + prefs.getString("city_name", "");
		publishTime = prefs.getString("time", "") + "发布";
		publishDate = prefs.getString("currentDate", "XXXXXX");
		weatherDesp = prefs.getString("weather", "");
		temp1 = prefs.getString("temp1", "") + "℃";
		temp2 = prefs.getString("temp2", "") + "℃";
		txt = prefs.getString("txt", "");

		cityNameText.setText(city);
		publishText.setText(publishTime);
		weatherDespText.setText(weatherDesp);
		temp1Text.setText(temp2);
		temp2Text.setText(temp1);
		text.setText(txt);
		currentDateText.setText(publishDate);
		// System.out.println(city+"00000000000000000000000000000000000000000000000");
	}
	public void onClick(View v){
		switch(v.getId()){
		case R.id.switch_city:
			Intent intent = new Intent(this,ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity",true);
			startActivity(intent);
			finish();
			break;
		case R.id.updata:
			Toast.makeText(this,"正在更新，请稍后。。。",Toast.LENGTH_SHORT).show();
			Toast.makeText(this,"已经是最新！",Toast.LENGTH_SHORT).show();
		}
		
	}
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(!isExit){
				isExit = true;
				Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
				mHandler.sendEmptyMessageDelayed(0,2000);
				
			}
			else{
				finish();
				System.exit(0);
			}
		}
		return false;
	}
//	protected void creatdialog(){
//		AlertDialog.Builder b = new Builder(WeatherActivity.this);
//		b.setMessage("确认退出吗？");
//		b.setTitle("提示");
//		b.setPositiveButton("确认",new OnClickListener(){
//			public void onClick(DialogInterface dialog, int which){
//				dialog.dismiss();
//				WeatherActivity.this.finish();
//			}
//		});
//		b.setNegativeButton("取消",new OnClickListener() {
//			
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//				
//			}
//		});
//		b.create().show();
//	}
//	public boolean onKeyDown(int keyCode,KeyEvent event){
//		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//			creatdialog();
//		}
//		return false;
//	}
}
