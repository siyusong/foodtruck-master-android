/**
 * 
 */
package com.foodtruckmaster.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtruckmaster.android.R;
import com.foodtruckmaster.android.model.TruckInfo;

/**
 * @author siyusong
 * 
 */
public class TruckInfoAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Bitmap mIcon1;
	private static String BLACK_STAR = "\u2605";
	private static String WHITE_STAR = "\u2606";

	TruckInfo[] truckList;

	public TruckInfoAdapter(Context context, TruckInfo[] list) {
		this.truckList = list;

		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);

		// Icons bound to the rows.
		mIcon1 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.placeholder);
	}

	/**
	 * The number of items in the list is determined by the number of speeches
	 * in our array.
	 * 
	 * @see android.widget.ListAdapter#getCount()
	 */
	public int getCount() {
		return truckList.length;
	}

	/**
	 * Since the data comes from an array, just returning the index is sufficent
	 * to get at the data. If we were using a more complex data structure, we
	 * would return whatever object represents one row in the list.
	 * 
	 * @see android.widget.ListAdapter#getItem(int)
	 */
	public Object getItem(int position) {
		return position;
	}

	/**
	 * Use the array index as a unique id.
	 * 
	 * @see android.widget.ListAdapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Make a view to hold each row.
	 * 
	 * @see android.widget.ListAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unneccessary
		// calls
		// to findViewById() on each row.
		ViewHolder holder;

		// When convertView is not null, we can reuse it directly, there is no
		// need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_icon_text, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.food_truck_name);
			holder.averageStar = (TextView) convertView
					.findViewById(R.id.average_star);
			holder.reviewCount = (TextView) convertView
					.findViewById(R.id.review_count);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.genre = (TextView) convertView.findViewById(R.id.genre);
			holder.photo = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		TruckInfo currentTruckInfo = truckList[position];
		holder.name.setText(currentTruckInfo.getName());
		double wholeStars = Math.floor(currentTruckInfo.getAverageStar());
		String starString = new String(new char[(int) wholeStars]).replace(
				"\0", BLACK_STAR);
		if (Double.compare(currentTruckInfo.getAverageStar() - wholeStars, 0) != 0) {
			starString += WHITE_STAR;
		}
		holder.averageStar.setText(starString);
		if (currentTruckInfo.getReviewCount() == 0) {
			holder.averageStar.setVisibility(View.GONE);
			holder.reviewCount.setText("No Reviews Yet");
		} else if (currentTruckInfo.getReviewCount() == 1) {
			holder.averageStar.setVisibility(View.VISIBLE);
			holder.reviewCount.setText("1 Review");
		} else {
			holder.averageStar.setVisibility(View.VISIBLE);
			holder.reviewCount.setText(String.format("%d Reviews",
					currentTruckInfo.getReviewCount()));
		}

		holder.address.setText(currentTruckInfo.getAddress());
		holder.genre.setText(currentTruckInfo.getGenre());
		holder.photo.setImageBitmap(mIcon1);

		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView averageStar;
		TextView reviewCount;
		TextView address;
		TextView genre;
		ImageView photo;
	}
}