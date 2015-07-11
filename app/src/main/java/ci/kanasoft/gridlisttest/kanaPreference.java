package ci.kanasoft.gridlisttest;

import android.content.Context;
import android.content.SharedPreferences;

public class kanaPreference {
	
	
	public static void onSaveUserChoices(String key , String mName , Context context)
	{
		//Log.i("Grid_List", " debut : onSaveUserChoices ");
		SharedPreferences settings =  context.getSharedPreferences("mes_prefs" , 0);
		SharedPreferences.Editor editor = settings.edit();		
		
		editor.putString(key, mName);		
		editor.commit();
		//Log.i("Grid_List", " fin : onSaveUserChoices ");
	}

}
