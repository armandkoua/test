package ci.kanasoft.gridlisttest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class MobileCompanyPullParser {
	private static final String LOGTAG = "EXPLORECA";	
	private static final String ITEM_ID = "id";
	private static final String ITEM_NAME = "name";
	private static final String ITEM_IMAGE = "image";
	private static final String ITEM_COUNTRY_ID = "idcounty";
	
	
	private String tagName;
	private int xmlResource;
	private Context context;
	
	
	
	public MobileCompanyPullParser(String tagName, int xmlResource, Context context) {		
		this.tagName = tagName;
		this.xmlResource = xmlResource;
		this.context = context;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public int getXmlResource() {
		return xmlResource;
	}
	public void setXmlResource(int xmlResource) {
		this.xmlResource = xmlResource;
	}

	
	
	
	private MobileCompany currentItem  = null;
	private String currentTag = null;
	List<MobileCompany> items = new ArrayList<MobileCompany>();
	
	
	
	
	public List<MobileCompany> parseXML() {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			InputStream stream = this.context.getResources().openRawResource(xmlResource);
			xpp.setInput(stream, null);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					handleStartTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					currentTag = null;
				} else if (eventType == XmlPullParser.TEXT) {
					handleText(xpp.getText());
				}
				eventType = xpp.next();
			}

		} catch (NotFoundException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (XmlPullParserException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (IOException e) {
			Log.d(LOGTAG, e.getMessage());
		}

		return items;
	}
	
	
	public List<MobileCompany> fnFindMobileCompaniesByCountryId(int idCountry )
	{
//		MobileCompany item = new MobileCompany();
		
		List<MobileCompany> mList = this.parseXML();
		
		List<MobileCompany> returnList =  new ArrayList<MobileCompany>();
		
		for (MobileCompany mobileCompany : mList) {
			if(mobileCompany.get_idCountry() == idCountry )
			{
				returnList.add(mobileCompany);
			}
		}
		
		return returnList;
		
	}
	
	public Integer[]  MapListToIntegerArray(List<MobileCompany> mList)
	{
		
		Integer[] mArray = null ; 
		if(mList.size() > 0)
		{
			mArray = new Integer[mList.size()];
			
			int index = 0 ;
			for (Item item : mList) {
				
				mArray[index] = this.context.getResources().getIdentifier(item.get_image(), "drawable", this.context.getPackageName());
				index++;
			}			
		}
		
		return mArray;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (currentItem != null && currentTag != null) {
			if (currentTag.equals(ITEM_ID)) {
				Integer id = Integer.parseInt(xmlText);
				currentItem.set_id(id);
			} 
			else if (currentTag.equals(ITEM_NAME)) {
				currentItem.set_name(xmlText);
			}
			else if (currentTag.equals(ITEM_IMAGE)) {
				currentItem.set_image(xmlText);
			}
			else if (currentTag.equals(ITEM_COUNTRY_ID)) {
				Integer id = Integer.parseInt(xmlText);		
				currentItem.set_idCountry(id);
				}				
			}	
		}
	

	private void handleStartTag(String name) {
		if (name.equals(tagName)) {
			currentItem = new MobileCompany();
			items.add(currentItem);			
		}
		else {
			currentTag = name;
		}
	}
}
