package ci.kanasoft.gridlisttest;

import java.util.List;

import android.content.Context;
import android.util.Log;

public class Item {
	private int _id;
	private String _name;
	private String  _image;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_image() {
		return _image;
	}
	public void set_image(String _image) {
		this._image = _image;
	}
	@Override
	public String toString() {
		return  _name ;
	}
	
	
	public Item fnGetCategoryByName(String name , Context context)
	{
		ItemPullParser mParser = new ItemPullParser("category", R.raw.categoryus01, context);
		List<Item> mLists = mParser.parseXML() ;
		for (Item item : mLists) {
			
			if(item.get_name().equalsIgnoreCase(name)){
				Log.i("Grid_List", "fnGetCategoryByName : " + item.get_name() +"   " + name );
				return item;			
			}
		}
		
		return null;		
	}
	
	
	public Item fnGetCodeByName(String name , Context context)
	{
		ItemPullParser mParser = new ItemPullParser("code", R.raw.codeus01, context);
		List<Item> mLists = mParser.parseXML() ;
		for (Item item : mLists) {
			
			if(item.get_name().equalsIgnoreCase(name)){
				Log.i("Grid_List", "fnGetCodeByName : " + item.get_name() +"   " + name );
				return item;			
			}
		}
		
		return null;		
	}
	
	public Item fnGetOperationByName(String name , Context context)
	{
		ItemPullParser mParser = new ItemPullParser("operation", R.raw.operationus01, context);
		List<Item> mLists = mParser.parseXML() ;
		for (Item item : mLists) {
			
			if(item.get_name().equalsIgnoreCase(name)){
				Log.i("Grid_List", "fnGetCodeByName : " + item.get_name() +"   " + name );
				return item;			
			}
		}
		
		return null;		
	}
}
