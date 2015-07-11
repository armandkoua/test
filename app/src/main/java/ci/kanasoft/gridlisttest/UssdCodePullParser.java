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

public class UssdCodePullParser {
	private static final String LOGTAG = "EXPLORECA";	
	
	private static final String UC_COUNTRY = "country";
	private static final String UC_MOBILE = "mobilecompany";
	private static final String UC_CATEGORY = "category";
	
	private static final String UC_OPERATION = "operation";
	private static final String UC_CODE = "code";
	private static final String UC_SYNTAX = "syntax";
	
	private static final String UC_COST = "cost";
	private static final String UC_EXPLANATION = "explanation";
	private static final String UC_INFO_SOURCE = "informationsource";
	
	
	
	private String tagName;
	private int xmlResource;
	private Context context;
	
	
	
	public  UssdCodePullParser(String tagName, int xmlResource, Context context) {		
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

	
	
	
	private UssdCode currentItem  = null;
	private String currentTag = null;
	List<UssdCode> items = new ArrayList<UssdCode>();
	
	
	
	
	public List<UssdCode> parseXML() {

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
	
	public Integer[]  MapListToIntegerArray(List<Item> mList)
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
	
	public Integer[]  MapMobileCompanyListToIntegerArray(List<MobileCompany> mList)
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
			
			if (currentTag.equals(UC_COUNTRY)) {
				//Integer id = Integer.parseInt(xmlText);
				currentItem.set_country(xmlText);
			} 
			else if (currentTag.equals(UC_MOBILE)) {
				currentItem.set_mobileCompany(xmlText);
			}
			else if (currentTag.equals(UC_CATEGORY)) {
				currentItem.set_category(xmlText);
			}
			if (currentTag.equals(UC_OPERATION)) {
//				Integer id = Integer.parseInt(xmlText);
				currentItem.set_operation(xmlText);
			} 
			else if (currentTag.equals(UC_CODE)) {
				currentItem.set_code(xmlText);
			}
			else if (currentTag.equals(UC_SYNTAX)) {
				currentItem.set_syntax(xmlText);
			}
			if (currentTag.equals(UC_COST)) {
				//Double id = Double.parseInt(xmlText);
				currentItem.set_cost(xmlText);
			} 
			else if (currentTag.equals(UC_EXPLANATION)) {
				currentItem.set_explanation(xmlText);
			}
			else if (currentTag.equals(UC_INFO_SOURCE)) {
				currentItem.set_informationSource(xmlText);
			}
			
		}
	}

	private void handleStartTag(String name) {
		if (name.equals(tagName)) {
			currentItem = new UssdCode();
			items.add(currentItem);			
		}
		else {
			currentTag = name;
		}
	}
}
