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

public class SourceActivity extends ActionBarActivity {
	
	
	//String mSource;
	String mCategoryName;
	String mCompanyName;
	String mName;
	int mId ; 	
	SharedPreferences settings;
	List<Item> mSourceList;
	
	public static final  String SOURCE_NAME= "SOURCE_NAME";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_source);
		
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);		
		
		Intent mIntent = getIntent();			
		//Log.i("Grid_List", "onCreate 1 : SourceActivity " + settings.getString(CompanyActivity.COMPANY_NAME, "valeur par d�faut"));
		if(mIntent.hasExtra(CompanyActivity.COMPANY_NAME))
		{
			mId = Integer.parseInt(mIntent.getStringExtra(MainActivity.COUNTRY_ID));			
			mName = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);			
			mCompanyName = mIntent.getStringExtra(CompanyActivity.COMPANY_NAME);
			mCategoryName = mIntent.getStringExtra(CategoryActivity.CATEGORY_NAME);
		
			//onSaveUserChoices();
		}
		else
		{
			onReadUserChoices();	
		}
		
		
		GridView myGridView = (GridView) findViewById(R.id.gridSource);	
		
		ItemPullParser mParser = new ItemPullParser("source", R.raw.sourceus01, this);
		
		mSourceList = mParser.parseXML();		
		
		Integer[] mThumbsIds = mParser.MapListToIntegerArray(mSourceList);
		
		
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
				ItemPullParser mTemp_Parser;
				
				//nom de la source choisie: op�ration ou code
				String sourceChoisie = mSourceList.get(position).get_name();
				
				if(sourceChoisie.equalsIgnoreCase("opérations"))
				{
					mTemp_Parser = new ItemPullParser("operations", R.raw.operationus01, getApplicationContext());
					mIntent =  new Intent(getApplicationContext(),OperationActivity.class);
				}
				else if(sourceChoisie.equalsIgnoreCase("codes"))
				{
					mTemp_Parser = new ItemPullParser("codes", R.raw.codeus01, getApplicationContext());
					mIntent =  new Intent(getApplicationContext(),CodeActivity.class);
				}
				else
				{
					mTemp_Parser = new ItemPullParser("operations", R.raw.operationus01, getApplicationContext());
					mIntent =  new Intent(getApplicationContext(),OperationActivity.class);
				}				
				
//				mIntent.putExtra(MainActivity.COUNTRY_ID,  mId + "");
//				mIntent.putExtra(MainActivity.COUNTRY_NAME, mName);	
//				mIntent.putExtra(CategoryActivity.CATEGORY_NAME, mCategoryName);	
				mIntent.putExtra(SourceActivity.SOURCE_NAME, sourceChoisie);					
				
				kanaPreference.onSaveUserChoices( SourceActivity.SOURCE_NAME, sourceChoisie  , getApplicationContext());
				
				
				Log.i("SourceActivity","nom choix : " +sourceChoisie) ;
				startActivity(mIntent);									
			}
		});
		
		
		
	}
	
	
	
	private void onSaveUserChoices()
	{
		//Log.i("Grid_List", " debut : onSaveUserChoices ");
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putInt(MainActivity.COUNTRY_ID, mId);
		editor.putString(MainActivity.COUNTRY_NAME, mName);
		editor.putString(CompanyActivity.COMPANY_NAME, mCompanyName);
		editor.putString(CategoryActivity.CATEGORY_NAME, mCategoryName);
		//editor.putString(SOURCE_NAME, )
		editor.commit();
		//Log.i("Grid_List", " fin : onSaveUserChoices ");
	}
	
	private void onReadUserChoices()
	{
		//Log.i("Grid_List", " debut : onReadUserChoices ");
		
		if(settings == null)
			settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		
		
		mId = settings.getInt(MainActivity.COUNTRY_ID, 2);
		mName = settings.getString(MainActivity.COUNTRY_NAME, "Côte d'Ivoire");
		mCompanyName = settings.getString(CompanyActivity.COMPANY_NAME, "Orange");
		mCategoryName = settings.getString(CategoryActivity.CATEGORY_NAME, "GSM");
		//Log.i("Grid_List", " fin : onReadUserChoices ");
	}
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.source, menu);
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
