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

public class CategoryActivity extends ActionBarActivity {
	
	
	
	String mCompanyName;
	String mName;
	int mId ; 	
	SharedPreferences settings;
	List<Item> mSourceList;
	
	public static final String CATEGORY_NAME = "CATEGORY_NAME"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		Log.i("Grid_List", "onCreate : CategoryActivity ");
		
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		
		
		
		Intent mIntent = getIntent();			
		Log.i("Grid_List", "onCreate 1 : CategoryActivity " + settings.getString(CompanyActivity.COMPANY_NAME, "vaaleur par défaut"));
		if(mIntent.hasExtra(CompanyActivity.COMPANY_NAME))
		{
			Log.i("Grid_List", "onCreate 2 : CategoryActivity "); 
			
			mId = Integer.parseInt(mIntent.getStringExtra(MainActivity.COUNTRY_ID));
			Log.i("Grid_List", "onCreate 3: CategoryActivity ");
			mName = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);
			Log.i("Grid_List", "onCreate 4: CategoryActivity ");
			mCompanyName = mIntent.getStringExtra(CompanyActivity.COMPANY_NAME);
			Log.i("Grid_List", "onCreate 5: CategoryActivity ");
			//onSaveUserChoices();
		}
		else
		{
			onReadUserChoices();	
		}	
	
		Log.i("Grid_List", "onCreate 6:  " + mId + mName + mCompanyName);
		
		GridView myGridView = (GridView) findViewById(R.id.gridCategory);
		
		UssdCode ussdCode = new UssdCode(this);	
		
		
		Integer[] mThumbsIds = ussdCode.fnSelectCategoriesInCountryAndCompany( mName, mCompanyName);
		
		
		Integer [] mVide = new Integer[]{ R.drawable.small0093};
		
		if(mThumbsIds != null)
			myGridView.setAdapter( new ItemAdapter(this, mThumbsIds) );
		else
			myGridView.setAdapter(new ItemAdapter(this, mVide));
		
		
		
			myGridView.setOnItemClickListener( new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent mIntent;
				ItemPullParser mTemp_Parser = new ItemPullParser("category", R.raw.categoryus01, getApplicationContext());
				
				
					mIntent =  new Intent(getApplicationContext(),SourceActivity.class);
//					mIntent.putExtra(MainActivity.COUNTRY_ID,  mId + "");
//					mIntent.putExtra(MainActivity.COUNTRY_NAME, mName);					
//					mIntent.putExtra(CompanyActivity.COMPANY_NAME, mCompanyName);	
					mIntent.putExtra(CATEGORY_NAME, (mTemp_Parser.parseXML()).get(position).get_name()) ;					
					kanaPreference.onSaveUserChoices( CATEGORY_NAME, (mTemp_Parser.parseXML()).get(position).get_name()  , getApplicationContext());
				
				Log.i("Grid_List","identifiant " + mId + "    " + (mTemp_Parser.parseXML()).get(position).get_name()) ;
				startActivity(mIntent);				
			}
		});	
	}
	
	
//	private void onSaveUserChoices(String key , String mName)
//	{
//		//Log.i("Grid_List", " debut : onSaveUserChoices ");
//		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
//		SharedPreferences.Editor editor = settings.edit();		
//		
//		editor.putString(key, mName);		
//		editor.commit();
//		//Log.i("Grid_List", " fin : onSaveUserChoices ");
//	}
	
	
//	
//	private void onSaveUserChoices()
//	{
//		Log.i("Grid_List", " debut : onSaveUserChoices ");
//		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
//		SharedPreferences.Editor editor = settings.edit();
//		
//		editor.putInt(MainActivity.COUNTRY_ID, mId);
//		editor.putString(MainActivity.COUNTRY_NAME, mName);
//		editor.putString(CompanyActivity.COMPANY_NAME, mCompanyName);
//		editor.commit();
//		Log.i("Grid_List", " fin : onSaveUserChoices ");
//	}
	
	private void onReadUserChoices()
	{
		Log.i("Grid_List", " debut : onReadUserChoices ");
		
		if(settings == null)
			settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		
		
		mId = settings.getInt(MainActivity.COUNTRY_ID, 2);
		mName = settings.getString(MainActivity.COUNTRY_NAME, "Côte d'Ivoire");
		mCompanyName = settings.getString(CompanyActivity.COMPANY_NAME, "Orange");
		Log.i("Grid_List", " fin : onReadUserChoices ");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
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
