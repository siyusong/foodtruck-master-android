/**
 * 
 */
package com.foodtruckmaster.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodtruckmaster.android.R;
import com.foodtruckmaster.android.model.Review;
import com.foodtruckmaster.android.model.TruckInfo;

/**
 * @author siyusong
 * 
 */
public class ReviewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;

	Context context;
	Review[] list;

	public ReviewAdapter(Context context, Review[] list) {
		this.context = context;
		this.list = list;

		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);

	}

	/**
	 * The number of items in the list is determined by the number of speeches
	 * in our array.
	 * 
	 * @see android.widget.ListAdapter#getCount()
	 */
	public int getCount() {
		return list.length;
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
			convertView = mInflater.inflate(R.layout.list_item_review, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.review_username);
			holder.star = (TextView) convertView.findViewById(R.id.review_star);
			holder.comments = (TextView) convertView
					.findViewById(R.id.review_comments);
			holder.date = (TextView) convertView.findViewById(R.id.review_date);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		Review review = list[position];
		// holder.name.setText(currentTruckInfo.getName());
		String starString = Review.getStarString(review.getStar());
		holder.star.setText(starString);

		holder.name.setText(review.getName());
		String comments = review.getComments();
		comments = comments.replaceAll("-Ben Franklin", "");
		holder.comments.setText(comments);
		String dateString = DateUtils.getRelativeDateTimeString(context,
				review.getTimestamp(), DateUtils.MINUTE_IN_MILLIS,
				DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
		dateString = dateString.split(",")[0];
		dateString = dateString.replaceAll("/1970", "");
		holder.date.setText(dateString);
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView star;
		TextView comments;
		TextView date;
	}
}