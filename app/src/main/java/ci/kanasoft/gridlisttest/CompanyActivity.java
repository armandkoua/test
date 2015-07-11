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

public class CompanyActivity extends ActionBarActivity {
	
	Boolean flag_monde = false;
	String mName;
	int mId ; 	
	SharedPreferences settings;
	List<Item> mSourceList;
	
	public static final String COMPANY_ID = "COMPANY_ID" ; 
	public static final String COMPANY_NAME = "COMPANY_NAME" ;
	public static final String MONDE_CODE_OR_OP = "MONDE_CODE_OR_OP" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		
		//Log.i("Grid_List" , "onCreate : Company Activity "); 
		
		
			// c'est la premi�re fois que l'activit� a �t� lanc�e
			
			Intent mIntent = getIntent();			
			
			if(mIntent.hasExtra(MainActivity.COUNTRY_NAME))
			{
				mName  = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);
				mId  = Integer.parseInt(mIntent.getStringExtra(MainActivity.COUNTRY_ID) );	
				
				//onSaveUserChoices();
			}
			else
			{
				//Log.i("Grid_List" , "mIntent est  nulle : Company Activity ");		
				onReadUserChoices();				
				//Log.i("Grid_List" , "mId :" + mId + "  " + mName);
			}
			
		
		
		
		GridView myGridView = (GridView) findViewById(R.id.gridCompany);
				
		if(mName.equalsIgnoreCase("monde"))
		{
			//code ou op�ration			
			ItemPullParser mParser =  new ItemPullParser("source", R.raw.sourceus01, this);	
			mSourceList = mParser.parseXML();
			Integer [] mThumbIds = mParser.MapListToIntegerArray(mSourceList);
			myGridView.setAdapter(new ItemAdapter(this, mThumbIds));
			flag_monde = true;
		}
		else
		{
			UssdCode ussdCode = new UssdCode(this);
			Integer [] mThumbIds = ussdCode.fnSelectMobileCompaniesInCountry(mId, mName);
			Integer [] mVide = new Integer[]{ R.drawable.small0093};
			
			if(mThumbIds != null)
				myGridView.setAdapter(new ItemAdapter(this, mThumbIds));
			else
				myGridView.setAdapter(new ItemAdapter(this, mVide));
			
			flag_monde =  false;
		}
		
		
		myGridView.setOnItemClickListener( new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_LONG).show();
				Intent mIntent;
				ItemPullParser mTemp_Parser = new ItemPullParser("mobilecompany", R.raw.mobilecompanyus01, getApplicationContext());
							
				
				if(flag_monde)
				{
					if(mSourceList.get(position).get_name().equalsIgnoreCase("opérations")){
						
						mIntent = new Intent(getApplicationContext(),CodeOpFromMondeActivity.class);						
					}
					else {
						
						mIntent = new Intent(getApplicationContext(),CodeCodeFromMondeActivity.class);
					}										
					mIntent.putExtra(CompanyActivity.MONDE_CODE_OR_OP,mSourceList.get(position).get_name() );//										
					kanaPreference.onSaveUserChoices( CompanyActivity.MONDE_CODE_OR_OP, mSourceList.get(position).get_name()  , getApplicationContext());
				}
				else {
					
					mIntent =  new Intent(getApplicationContext(),CategoryActivity.class);					
					mIntent.putExtra(COMPANY_NAME, (mTemp_Parser.parseXML()).get(position).get_name());					
					kanaPreference.onSaveUserChoices( COMPANY_NAME, (mTemp_Parser.parseXML()).get(position).get_name()  , getApplicationContext());
				}			
				
				
//				mIntent.putExtra(MainActivity.COUNTRY_ID,  mId + "");
//				mIntent.putExtra(MainActivity.COUNTRY_NAME, mName);	
				
				
				
				Log.i("Grid_List","identifiant " + mId + "    " + (mTemp_Parser.parseXML()).get(position).get_name()) ;
				startActivity(mIntent);				
			}
		});
		
		
		
	}
	
	private void onSaveUserChoices()
	{
		//Log.i("Grid_List", " debut : onSaveUserChoices ");
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();		
		
		editor.putString(MainActivity.COUNTRY_NAME, mName);		
		editor.commit();
		//Log.i("Grid_List", " fin : onSaveUserChoices ");
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
	
	private void onReadUserChoices()
	{
		//Log.i("Grid_List", " debut : onReadUserChoices ");
		
		if(settings == null)
			settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		
		
		mId = settings.getInt(MainActivity.COUNTRY_ID, 2);
		mName = settings.getString(MainActivity.COUNTRY_NAME, "Côte d'Ivoire");
		//Log.i("Grid_List", " fin : onReadUserChoices ");
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.company, menu);
		return true;
	}
	
	
	
	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();		
		Log.i("Grid_List" , "onRestart : Company Activity ");
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("Grid_List" , "onPause : Company Activity ");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("Grid_List" , "onStop : Company Activity");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("Grid_List" , "onResume : Company Activity" );
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
