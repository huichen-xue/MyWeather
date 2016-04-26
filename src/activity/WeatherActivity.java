package activity;

import com.myweather.app.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	
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
protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.weather_layout);
	showWeather();
	init();
	cityNameText.setText(city);
	publishText.setText(publishTime);
	weatherDespText.setText(weatherDesp);
	temp1Text.setText(temp2);
	temp2Text.setText(temp1);
	text.setText(txt);
	currentDateText.setText(publishDate);
	
}
public void init(){
	weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
	cityNameText = (TextView) findViewById(R.id.city_name);
	publishText = (TextView) findViewById(R.id.publish_text);
	weatherDespText = (TextView) findViewById(R.id.weather_desp);
	temp1Text = (TextView) findViewById(R.id.temp1);
	temp2Text = (TextView) findViewById(R.id.temp2);
	currentDateText = (TextView) findViewById(R.id.current_date);
	text = (TextView) findViewById(R.id.weather_txt);
}
private void showWeather(){
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	city = "中国-"+prefs.getString("city_name","");
	publishTime = prefs.getString("time","");
	publishDate = prefs.getString("currentDate","XXXXXX");
	weatherDesp = prefs.getString("weather","");
	temp1 = prefs.getString("temp1","")+"℃";
	temp2 = prefs.getString("temp2","")+"℃";
	txt = prefs.getString("txt","");
	//System.out.println(city+"00000000000000000000000000000000000000000000000");
}
}
