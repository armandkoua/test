package ci.kanasoft.gridlisttest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class UssdCode {
	String _country;
	String _mobileCompany;
	String _category;
	String _operation;
	String _code ;
	String _syntax;
	String _cost;
	String _explanation;
	String _informationSource;
	Context context;
	UssdCodePullParser mParser;
	
	
	
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}	
	
	public UssdCode() {		
	}
	
	public UssdCode(Context context) {
		mParser =  new UssdCodePullParser("ussdcode", R.raw.ussdcodesus01, context);
		this.context = context;
	}
	public String get_country() {
		return _country;
	}
	public void set_country(String _country) {
		this._country = _country;
	}
	public String get_mobileCompany() {
		return _mobileCompany;
	}
	public void set_mobileCompany(String _mobileCompany) {
		this._mobileCompany = _mobileCompany;
	}
	public String get_category() {
		return _category;
	}
	public void set_category(String _category) {
		this._category = _category;
	}
	public String get_operation() {
		return _operation;
	}
	public void set_operation(String _operation) {
		this._operation = _operation;
	}
	public String get_code() {
		return _code;
	}
	public void set_code(String _code) {
		this._code = _code;
	}
	public String get_syntax() {
		return _syntax;
	}
	public void set_syntax(String _syntax) {
		this._syntax = _syntax;
	}
	public String get_cost() {
		return _cost;
	}
	public void set_cost(String _cost) {
		this._cost = _cost;
	}
	public String get_explanation() {
		return _explanation;
	}
	public void set_explanation(String _explanation) {
		this._explanation = _explanation;
	}
	public String get_informationSource() {
		return _informationSource;
	}
	public void set_informationSource(String _informationSource) {
		this._informationSource = _informationSource;
	}
	
	
	
	
	public List<UssdCode> fnSelectAll() {			
		
		return mParser.parseXML();	
	}
	
	
	
	public UssdCode fnGetForOperation(String coutryName , String companyName , String categoryName , String sourceName ) {
		
		List<UssdCode> mUssdCodes = this.fnSelectAll();		
			
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(		ussdCode.get_country().equalsIgnoreCase(coutryName)  
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName) 
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName)
					&& ussdCode.get_category().equalsIgnoreCase(categoryName)
					&& ussdCode.get_operation().equalsIgnoreCase(sourceName))	{
				
				return ussdCode;
			}
		}
		
		return null;		
	}
	
	
	public UssdCode fnGetForOperationFromMonde(String coutryName  , String sourceName ) {
		
		List<UssdCode> mUssdCodes = this.fnSelectAll();	
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(		ussdCode.get_country().equalsIgnoreCase(coutryName)  
					&& ussdCode.get_operation().equalsIgnoreCase(sourceName))	{				
				return ussdCode;
			}
		}		
		return null;		
	}
	
	
	public UssdCode fnGetForCode(String coutryName , String companyName , String categoryName , String sourceName ) {
		
		List<UssdCode> mUssdCodes = this.fnSelectAll();		
			
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(		ussdCode.get_country().equalsIgnoreCase(coutryName)  
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName) 
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName)
					&& ussdCode.get_category().equalsIgnoreCase(categoryName)
					&& ussdCode.get_code().equalsIgnoreCase(sourceName))
			{
				return ussdCode;
			}
		}
		
		return null;		
	}
	
	
	public UssdCode fnGetForCodeFromMonde(String coutryName ,  String sourceName ) {
		
		List<UssdCode> mUssdCodes = this.fnSelectAll();		
			
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(		ussdCode.get_country().equalsIgnoreCase(coutryName)  
					&& ussdCode.get_code().equalsIgnoreCase(sourceName))	{
				
				return ussdCode;
			}
		}
		
		return null;		
	}
	
	
	
	
	public Integer [] fnSelectMobileCompaniesInCountry(int idCountry , String coutryName) {		

		List<UssdCode> mUssdCodes = this.fnSelectAll();
		
		List<MobileCompany> mMobileCompaniesList = new ArrayList<MobileCompany>();
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(ussdCode.get_country().equalsIgnoreCase(coutryName))			{
				
				mMobileCompaniesList = (new MobileCompany()).fnGetMobileCompaniesByCountryId(idCountry, this.context);
				break;	
			}
		}
		
		return mParser.MapMobileCompanyListToIntegerArray(mMobileCompaniesList);
	}
	
	
	public Integer [] fnSelectCategoriesInCountryAndCompany(String coutryName , String companyName) {		

		List<UssdCode> mUssdCodes = this.fnSelectAll();	
		
		List<Item> mCategoriesList = new ArrayList<Item>();		
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(ussdCode.get_country().equalsIgnoreCase(coutryName)  && ussdCode.get_mobileCompany().equalsIgnoreCase(companyName))
			{
				Item mItem = (new Item()).fnGetCategoryByName(ussdCode.get_category(), this.context);
				
				Log.i("Grid_List", "fnSelectCategoriesInCountryAndCompany : " + ussdCode.get_category()  ); 
				if(mItem != null)
					mCategoriesList.add(mItem);
			}
		}
		
		return mParser.MapListToIntegerArray(mCategoriesList);
	}	
	
	public List<Item> fnSelectCodesFrom(String coutryName , String companyName , String categoryName ) {		

		List<UssdCode> mUssdCodes = this.fnSelectAll();	
		
		List<Item> mCodesList = new ArrayList<Item>();		
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(		ussdCode.get_country().equalsIgnoreCase(coutryName)  
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName) 
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName)
					&& ussdCode.get_category().equalsIgnoreCase(categoryName) )
			{
				Item mItem = (new Item()).fnGetCodeByName(ussdCode.get_code(), this.context);
				
				Log.i("Grid_List", "fnGetCodeByName : " + ussdCode.get_code()  ); 
				if(mItem != null)
					mCodesList.add(mItem);
			}
		}
		
		return mCodesList;
	}
	
	

	public List<Item> fnSelectOperationsFrom(String coutryName , String companyName , String categoryName ) {		

		List<UssdCode> mUssdCodes = this.fnSelectAll();	
		
		List<Item> mOperationsList = new ArrayList<Item>();		
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(		ussdCode.get_country().equalsIgnoreCase(coutryName)  
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName) 
					&& ussdCode.get_mobileCompany().equalsIgnoreCase(companyName)
					&& ussdCode.get_category().equalsIgnoreCase(categoryName) )
			{
				Item mItem = (new Item()).fnGetOperationByName(ussdCode.get_operation(), this.context);
				
				Log.i("Grid_List", "fnSelectOperationsFrom : " + ussdCode.get_operation()  ); 
				if(mItem != null)
					mOperationsList.add(mItem);
			}
		}
		
		return mOperationsList;
	}
	
	
	public List<Item> fnSelectOperationsFromMonde(String coutryName  ) {		

		List<UssdCode> mUssdCodes = this.fnSelectAll();	
		
		List<Item> mOperationsList = new ArrayList<Item>();		
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(	ussdCode.get_country().equalsIgnoreCase(coutryName)  )
			{
				Item mItem = (new Item()).fnGetOperationByName(ussdCode.get_operation(), this.context);
				
				Log.i("Grid_List", "fnSelectOperationsFromMonde : " + ussdCode.get_operation()  ); 
				if(mItem != null)
					mOperationsList.add(mItem);
			}
		}
		
		return mOperationsList;
	}
	
	
	public List<Item> fnSelectCodesFromMonde(String coutryName ) {		

		List<UssdCode> mUssdCodes = this.fnSelectAll();	
		
		List<Item> mOperationsList = new ArrayList<Item>();		
		
		for (UssdCode ussdCode : mUssdCodes) {
			if(	ussdCode.get_country().equalsIgnoreCase(coutryName)  )
			{
				Item mItem = (new Item()).fnGetCodeByName(ussdCode.get_code(), this.context);
				
				Log.i("Grid_List", "fnSelectCodesFromMonde : " + ussdCode.get_code()  ); 
				if(mItem != null)
					mOperationsList.add(mItem);
			}
		}
		
		return mOperationsList;
	}
	
	

}
