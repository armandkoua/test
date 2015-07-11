package ci.kanasoft.gridlisttest;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UssdCodeActivity extends ActionBarActivity {
	
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
		setContentView(R.layout.activity_ussd_code);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		settings =  getApplicationContext().getSharedPreferences("mes_prefs" , MODE_PRIVATE);
		
		
		Intent mIntent = getIntent();
		UssdCode mUssd =  new UssdCode(this);
		
		if(mIntent.hasExtra(CodeActivity.CODE_NAME)) {
				mCode = mIntent.getStringExtra(CodeActivity.CODE_NAME);	
		}
		else {
				mOperation = mIntent.getStringExtra(OperationActivity.OPERATION_NAME);
				isCode = false;
		}
	
		mName = mIntent.getStringExtra(MainActivity.COUNTRY_NAME);			
		mCompanyName = mIntent.getStringExtra(CompanyActivity.COMPANY_NAME);
		mCategoryName = mIntent.getStringExtra(CategoryActivity.CATEGORY_NAME);
		mSource = mIntent.getStringExtra(SourceActivity.SOURCE_NAME);
		
		if(isCode){
			mUssd = mUssd.fnGetForCode(mName, mCompanyName, mCategoryName, mCode);
		}
		else{
			mUssd = mUssd.fnGetForOperation(mName, mCompanyName, mCategoryName, mOperation);
		}		
		
		if(isCode)
			Log.i("UssdCodeActivity", mName + mCompanyName + mCategoryName + mSource + mCode);
		else
			Log.i("UssdCodeActivity", mName + mCompanyName + mCategoryName + mSource + mOperation);
		
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
		getMenuInflater().inflate(R.menu.ussd_code, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
