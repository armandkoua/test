package ci.kanasoft.gridlisttest;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UssdCodeFromMondeActivity extends ActionBarActivity {
	
	String mCode;
	String mOperation;
	String mSource;
	String mCategoryName;
	String mCompanyName;
	String mName;		
	SharedPreferences settings;
	
	Boolean  isCode = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ussd_code_from_monde);	
		
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		
		Intent mIntent = getIntent();
		UssdCode mUssd =  new UssdCode(this);
		Log.i("UssdCodeFromMondeActivity", "mMilieu");
		
		if(mIntent.hasExtra(CodeActivity.CODE_NAME)) {
				
				mName = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);			
				mCode = mIntent.getStringExtra(CodeActivity.CODE_NAME);	
		}
		else {
				
				mName = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);
				mOperation = mIntent.getStringExtra(OperationActivity.OPERATION_NAME);
				isCode = false;
		}
		
		
		
		
		Log.i("UssdCodeFromMondeActivity", "Fin");
		mName = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);		
		
		if(isCode){
			mUssd = mUssd.fnGetForCodeFromMonde(mName, mCode);
		}
		else{
			mUssd = mUssd.fnGetForOperationFromMonde(mName,  mOperation);
		}		
		
		if(isCode)
			Log.i("UssdCodeFromMondeActivity", mName + mCode);
		else
			Log.i("UssdCodeFromMondeActivity", mName +  mOperation);
		
		if(mUssd != null)
			mapObjectToForm( mUssd );
	}
	
	
	public void setField(int idField , String value) {
		
		TextView mView  = (TextView) findViewById(idField);
		
		mView.setText(value);		
	}
	
	
	private void mapObjectToForm(UssdCode ussdCode ) {
		// TODO Auto-generated method stub
		setField(R.id.txt_country, ussdCode.get_country());
		setField(R.id.txt_information_source, ussdCode.get_informationSource());
		setField(R.id.txt_mobile_company, ussdCode.get_mobileCompany());
		setField(R.id.txt_ussd_category, ussdCode.get_category());
		setField(R.id.txt_ussd_explanation, ussdCode.get_explanation());
		setField(R.id.txt_ussd_syntax, ussdCode.get_syntax());
		if(isCode)
			setField(R.id.txt_ussd_source, ussdCode.get_operation());
		else
			setField(R.id.txt_ussd_source, ussdCode.get_code());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ussd_code_from_monde, menu);
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
