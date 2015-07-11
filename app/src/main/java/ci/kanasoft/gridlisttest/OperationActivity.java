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

public class OperationActivity extends ActionBarActivity {
	
	String mOperation;
	String mCode;
	String mSource;
	String mCategoryName;
	String mCompanyName;
	String mName;
	int mId ; 	
	SharedPreferences settings;
	List<Item> mSourceList;
	
	public static final  String OPERATION_NAME= "OPERATION_NAME";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operation);
		
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);		
		
		Intent mIntent = getIntent();			
		//Log.i("Grid_List", "onCreate 1 : SourceActivity " + settings.getString(CompanyActivity.COMPANY_NAME, "valeur par d�faut"));
		if(mIntent.hasExtra(CompanyActivity.COMPANY_NAME))
		{
			mId = Integer.parseInt(mIntent.getStringExtra(MainActivity.COUNTRY_ID));			
			mName = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);			
			mCompanyName = mIntent.getStringExtra(CompanyActivity.COMPANY_NAME);
			mCategoryName = mIntent.getStringExtra(CategoryActivity.CATEGORY_NAME);
			mSource = mIntent.getStringExtra(SourceActivity.SOURCE_NAME);
			
			//onSaveUserChoices();
		}
		else
		{
			onReadUserChoices();	
		}
		
		GridView myGridView = (GridView) findViewById(R.id.gridOperation);	
		
		UssdCode mUssdCode =  new UssdCode(getApplicationContext());
		ItemPullParser mParser = new ItemPullParser("operation", R.raw.operationus01, this);		
		mSourceList = mUssdCode.fnSelectOperationsFrom(mName, mCompanyName, mCategoryName);	
		
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
				String operationChoisie = mSourceList.get(position).get_name();
				
				mTemp_Parser = new ItemPullParser("ussd", R.raw.ussdcodesus01, getApplicationContext());
				mIntent =  new Intent(getApplicationContext(),UssdCodeActivity.class);
//				
//				mIntent.putExtra(MainActivity.COUNTRY_ID,  mId + "");
//				mIntent.putExtra(MainActivity.COUNTRY_NAME, mName);	
//				mIntent.putExtra(CompanyActivity.COMPANY_NAME, mCompanyName);
//				mIntent.putExtra(CategoryActivity.CATEGORY_NAME, mCategoryName);	
//				mIntent.putExtra(SourceActivity.SOURCE_NAME, mSource);
				mIntent.putExtra(OPERATION_NAME, operationChoisie);
				kanaPreference.onSaveUserChoices( OPERATION_NAME, operationChoisie , getApplicationContext());		
				
				Log.i("OperationActivity"," choix : " +operationChoisie) ;
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
		editor.putString(SourceActivity.SOURCE_NAME, mSource);
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
		mSource = settings.getString(SourceActivity.SOURCE_NAME, "codes");
		//Log.i("Grid_List", " fin : onReadUserChoices ");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.operation, menu);
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
