package ci.kanasoft.gridlisttest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ItemAdapter extends BaseAdapter {

	private Context mContext;
	// references to our images
    private Integer[] mThumbIds; 
	
	
	public ItemAdapter(Context mContext, Integer[] mThumbIds) {
		//super();
		this.mContext = mContext;
		this.mThumbIds = mThumbIds;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return mThumbIds.length;			
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private View returnView(View convertView ,Integer[] mThumbIds , int position)
	{
		ImageView imageView;
		
		if(convertView == null)
		{
			imageView = new ImageView(mContext);			
			//imageView.setLayoutParams(new GridView.LayoutParams(800,800));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8 , 8 , 8 , 8);			
		}
		else
			imageView = ( ImageView ) convertView;
		
		imageView.setImageResource(mThumbIds[position]);
		
		return imageView;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
			return returnView(convertView, mThumbIds, position);		
	}

}
