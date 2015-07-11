package ci.kanasoft.gridlisttest;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ActionBarActivity  {
	
	public static final String COUNTRY_ID = "COUNTRY_ID" ; 
	public static final String COUNTRY_NAME = "COUNTRY_NAME" ; 	
	List<Item> mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GridView myGridView = (GridView) findViewById(R.id.gridview);
		
		
		ItemPullParser mParser =  new ItemPullParser("country", R.raw.countryus01, this);
		
		mList = mParser.parseXML() ;
		
		Log.i("Grid_List", "la taille de ma liste " + mList.size() + "") ;
		
		Integer [] mThumbIds = mParser.MapListToIntegerArray(mList);		
		
//		Log.i("Grid_List", "la taille de mon tableau d'image " +  (mList.get(0)).get_image()+  " ==== " + (mList.get(1)).get_image()) ;
	 	
	
		
		myGridView.setAdapter(new ItemAdapter(this, mThumbIds));
		
		//myGridView.setAdapter(new ImageAdapter(this));
		
		myGridView.setOnItemClickListener( new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_LONG).show();
				
				Intent mIntent =  new Intent(getApplicationContext(),CompanyActivity.class);
				
				mIntent.putExtra(COUNTRY_ID, mList.get(position).get_id()+"");
				mIntent.putExtra(COUNTRY_NAME, mList.get(position).get_name());				
				kanaPreference.onSaveUserChoices( MainActivity.COUNTRY_ID ,mList.get(position).get_id()+""  , getApplicationContext());
				kanaPreference.onSaveUserChoices( MainActivity.COUNTRY_NAME ,mList.get(position).get_name()  , getApplicationContext());
				
				startActivity(mIntent);
				
			}
		});
	}
	
	
	private void onSaveUserChoices(int mId , String mName)
	{
		//Log.i("Grid_List", " debut : onSaveUserChoices ");
		SharedPreferences settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putInt(MainActivity.COUNTRY_ID, mId);
		editor.putString(MainActivity.COUNTRY_NAME, mName);		
		editor.commit();
		//Log.i("Grid_List", " fin : onSaveUserChoices ");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
