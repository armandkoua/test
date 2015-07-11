package ci.kanasoft.gridlisttest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class MobileCompany extends Item {
	
	private int _idCountry;
	private MobileCompanyPullParser mParser;

	public MobileCompany() {
		super();		
	}

	public int get_idCountry() {
		return _idCountry;
	}

	public void set_idCountry(int _idCountry) {
		this._idCountry = _idCountry;
	}
	
	public List<MobileCompany> fnGetMobileCompaniesByCountryId(int idCountry , Context context )
	{
		mParser = new MobileCompanyPullParser("mobilecompany", R.raw.mobilecompanyus01 , context);
		List<MobileCompany> returnList = new ArrayList<MobileCompany>();
		
		List<MobileCompany> mLists = mParser.parseXML() ;
		for (MobileCompany item : mLists) {
			
			if(item.get_idCountry() == idCountry){
				Log.i("Grid_List", "fnGetMobileCompanyByCountryId : " + item.get_name()  );
				returnList.add(item);			
			}
		}
		
		return returnList;		
	}
	
	

}
