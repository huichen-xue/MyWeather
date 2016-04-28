package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.util.Log;

public class Utility {
	 //String httpUrl = "http://apis.baidu.com/heweather/weather/free";
	 //String httpArg = "city=huhehaote";
	 String jsonResult;
	 static final String TAG = "Utility";
	public static String request(String httpUrl,String httpArg){
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + "city=" + httpArg;
		try{
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("apikey","d1625ac7e9a3fbb5d2febf906f83608b");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null){
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	} 
	public static void handleWeatherResponse(Context context,String response){
		
			try {
				JSONObject jsonObject = new JSONObject(response);
				JSONArray objArray = jsonObject.getJSONArray("HeWeather data service 3.0");
				JSONObject gobj = objArray.getJSONObject(0);
				JSONObject basic = gobj.getJSONObject("basic");
				JSONObject update = basic.getJSONObject("update");
				JSONArray daily_forecast = gobj.getJSONArray("daily_forecast");
				JSONObject daily_obj = daily_forecast.getJSONObject(0);
				JSONObject tmp = daily_obj.getJSONObject("tmp");
				JSONObject cond = daily_obj.getJSONObject("cond");
				JSONObject astro = daily_obj.getJSONObject("astro");
				JSONObject suggestion = gobj.getJSONObject("suggestion");
				JSONObject comf = suggestion.getJSONObject("comf");
				String brf = comf.getString("brf");
				String txt = comf.getString("txt");
				String time = update.getString("loc");//17:07
				String text_weather = cond.getString("txt_d");//晴间多云
				//String date = daily_obj.getString("date");
				String max = tmp.getString("max");
				String min = tmp.getString("min");
				String city = basic.getString("city");
				String cnty = basic.getString("cnty");
				saveWeatherInfo(context,cnty,city,max,min,text_weather,time,brf,txt);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "没有获取到");
			}
		}
	public static void saveWeatherInfo(Context context,String cnty,String city,String temp1,
			String temp2,String weather,String time,String brf,String txt){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("currentDate",sdf.format(new Date()));
		editor.putString("cnty_name",cnty);
		editor.putString("city_name",city);
		editor.putString("temp1",temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather", weather);
		editor.putString("time",time);
		editor.putString("brf",brf);
		editor.putString("txt",txt);
		editor.commit();
		System.out.println("说明解析没有问题"+time);
		Log.d(TAG, "获取成功");
		
	}
	
	 

}
