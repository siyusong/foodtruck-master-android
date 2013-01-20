package com.foodtruckmaster.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.foodtruckmaster.android.adapter.ReviewAdapter;
import com.foodtruckmaster.android.model.Entry;
import com.foodtruckmaster.android.model.Review;
import com.foodtruckmaster.android.model.Truck;

public class TruckActivity extends Activity {

	private ListView listView;
	private TextView name;
	private ImageView image;
	private TextView averageStar;
	private TextView reviewCount;
	private TextView address;
	private TextView genre;
	private Button mapButton;

	private Truck truck;
	private String truckId;
	protected Button shareButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		truckId = getIntent().getStringExtra("id");
		refresh();
		// getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void refresh() {
		String url = API.getTruck(truckId);
		GsonTransformer t = new GsonTransformer();
		AQuery aq = new AQuery(this);
		aq.transformer(t).ajax(url, Truck.class, new AjaxCallback<Truck>() {
			private Button reviewButton;
			private Button menuButton;

			public void callback(String url, final Truck truck,
					AjaxStatus status) {
				setContentView(R.layout.activity_truck);

				listView = (ListView) findViewById(R.id.detail_review_list);
				name = (TextView) findViewById(R.id.detail_food_truck_name);
				image = (ImageView) findViewById(R.id.detail_image);
				averageStar = (TextView) findViewById(R.id.detail_average_star);
				reviewCount = (TextView) findViewById(R.id.detail_review_count);
				address = (TextView) findViewById(R.id.detail_address);
				genre = (TextView) findViewById(R.id.detail_genre);

				mapButton = (Button) findViewById(R.id.detail_map_button);
				mapButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String uriString = String.format(
								"geo:0,0?q=%s,%s (%s)",
								String.valueOf(truck.getLocation()[0]),
								String.valueOf(truck.getLocation()[1]),
								truck.getName());
						Intent intent = new Intent(
								android.content.Intent.ACTION_VIEW, Uri
										.parse(uriString));
						startActivity(intent);
					}
				});
				
				menuButton = (Button) findViewById(R.id.detail_menu_button);
				menuButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(TruckActivity.this, MenuActivity.class);
						ArrayList<Entry> list = new ArrayList<Entry>();
						for (Entry e : truck.getEntries()) {
							list.add(e);
						}
						intent.putExtra("name", truck.getName());
						intent.putExtra("list", list);
						startActivity(intent);
					}
				});

				shareButton = (Button) findViewById(R.id.detail_share_button);
				shareButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String str = truck.getName() + "\n" + truck.getGenre()
								+ "\n" + truck.getAddress();
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
						intent.putExtra(Intent.EXTRA_TEXT, str);
						startActivity(Intent.createChooser(intent, "Share"));
					}
				});

				reviewButton = (Button) findViewById(R.id.detail_review_button);
				reviewButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(TruckActivity.this,
								ReviewActivity.class);
						intent.putExtra("id", truckId);
						intent.putExtra("name", truck.getName());
						startActivityForResult(intent, 1);

					}
				});

				TruckActivity.this.truck = truck;

				ReviewAdapter adapter = new ReviewAdapter(TruckActivity.this,
						truck.getReviews());

				name.setText(truck.getName());
				Bitmap icon = BitmapFactory.decodeResource(getResources(),
						R.drawable.placeholder);
				image.setImageBitmap(icon);
				averageStar.setText(Review.getStarString(truck.getAverageStar()));
				
				if (truck.getReviewCount() == 0) {
					averageStar.setVisibility(View.GONE);
					reviewCount.setText("No Reviews Yet");
				} else if (truck.getReviewCount() == 1) {
					averageStar.setVisibility(View.VISIBLE);
					reviewCount.setText("1 Review");
				} else {
					averageStar.setVisibility(View.VISIBLE);
					reviewCount.setText(String.format("%d Reviews",
							truck.getReviewCount()));
				}

				address.setText(truck.getAddress());
				genre.setText(truck.getGenre());

				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

					}
				});
			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_truck, menu);
//		return true;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				refresh();
			}
		}
	}

}
