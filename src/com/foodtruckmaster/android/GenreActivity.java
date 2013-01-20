package com.foodtruckmaster.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.foodtruckmaster.android.adapter.TruckInfoAdapter;
import com.foodtruckmaster.android.model.TruckInfo;

public class GenreActivity extends ListActivity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		listView = getListView();
		String genre = getIntent().getStringExtra("genre");
		String url = API.getGenre(genre);

		setTitle(genre);

		GsonTransformer t = new GsonTransformer();
		AQuery aq = new AQuery(this);
		aq.transformer(t).progress(R.id.list_view)
				.ajax(url, TruckInfo[].class, new AjaxCallback<TruckInfo[]>() {
					public void callback(String url,
							final TruckInfo[] truckList, AjaxStatus status) {

						listView.setAdapter(new TruckInfoAdapter(
								GenreActivity.this, truckList));

						listView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								String truckId = truckList[(int) id].getId();
								Intent intent = new Intent(GenreActivity.this,
										TruckActivity.class);
								intent.putExtra("id", truckId);
								startActivity(intent);
							}

						});
					}
				});

//		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_genre, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			// This ID represents the Home or Up button. In the case of this
//			// activity, the Up button is shown. Use NavUtils to allow users
//			// to navigate up one level in the application structure. For
//			// more details, see the Navigation pattern on Android Design:
//			//
//			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
//			//
//			NavUtils.navigateUpFromSameTask(this);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

}
