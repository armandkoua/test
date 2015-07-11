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

public class CodeOpFromMondeActivity extends ActionBarActivity {
	String mName;
	int mId ;
	String source ;
	
	SharedPreferences settings;
	List<Item> mSourceList;
	public static final  String OPERATION_NAME= "OPERATION_NAME";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code_op_from_monde);
		
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);		
		Intent mIntent = getIntent();
		
		Log.i("CodeOpFromMondeActivity","debut ");
		
		if(mIntent.hasExtra(CompanyActivity.MONDE_CODE_OR_OP)) 		{
			
			mName  = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);
			mId  = Integer.parseInt(mIntent.getStringExtra(MainActivity.COUNTRY_ID) );
			source = mIntent.getStringExtra(CompanyActivity.MONDE_CODE_OR_OP);			
			//onSaveUserChoices();			
		}
		else {
			
			onReadUserChoices();	
			
		}
		
		GridView myGridView = (GridView) findViewById(R.id.gridCodeOpFromMonde);
		
		
		
		UssdCode mUssdCode =  new UssdCode(getApplicationContext());
		ItemPullParser mParser = new ItemPullParser("operation", R.raw.operationus01, this);		
		mSourceList = mUssdCode.fnSelectOperationsFromMonde(mName);
		
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
				mIntent =  new Intent(getApplicationContext(),UssdCodeFromMondeActivity.class);
				
//				mIntent.putExtra(MainActivity.COUNTRY_ID,  mId + "");
//				mIntent.putExtra(MainActivity.COUNTRY_NAME, mName);							
				mIntent.putExtra(OperationActivity.OPERATION_NAME, operationChoisie);				
				kanaPreference.onSaveUserChoices( OPERATION_NAME, operationChoisie , getApplicationContext());
				
				Log.i("UssdCodeFromMondeActivity"," choix : " +operationChoisie) ;
				startActivity(mIntent);									
			}
		});		
		
		Log.i("CodeOpFromMondeActivity",source);	
		
	}	
	
	private void onSaveUserChoices()	{
		
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();		
		editor.putInt(MainActivity.COUNTRY_ID, mId);
		editor.putString(MainActivity.COUNTRY_NAME, mName);		
		editor.commit();		
	}
	
	private void onReadUserChoices() {
		
		if(settings == null)
			settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);		
		
		mId = settings.getInt(MainActivity.COUNTRY_ID, 2);
		mName = settings.getString(MainActivity.COUNTRY_NAME, "Côte d'Ivoire");
	}
	
	
	
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.code_op_from_monde, menu);
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
