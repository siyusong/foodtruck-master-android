/**
 * 
 */
package com.foodtruckmaster.android;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.foodtruckmaster.android.adapter.SeparatedListAdapter;
import com.foodtruckmaster.android.adapter.TruckInfoAdapter;
import com.foodtruckmaster.android.model.TruckInfo;

/**
 * @author siyusong
 * 
 */
public class TruckListFragment extends Fragment implements LocationListener {
	ListView listView;

	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";

	LocationManager locationManager;
	Location location;
	String provider;

	private AQuery aq;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list, container, false);
		listView = (ListView) view.findViewById(R.id.list_view);
		
		if (location != null) {
			refreshList(location);
		}
		return view;
	}

	private void refreshList(Location location) {
		String url = API.getNearby(String.valueOf(location.getLatitude()),
				String.valueOf(location.getLongitude()));
		
		Log.i("DEBUG", url);
		
		GsonTransformer t = new GsonTransformer();
		aq = new AQuery(getActivity());
		aq.transformer(t)
				.ajax(url, TruckInfo[].class, new AjaxCallback<TruckInfo[]>() {
					public void callback(String url, final TruckInfo[] truckList,
							AjaxStatus status) {
						final String[] genreList = getGenreList();
						
						SeparatedListAdapter adapter = new SeparatedListAdapter(
								getActivity());
						adapter.addSection("Nearby", new TruckInfoAdapter(
								getActivity(), truckList));

						adapter.addSection("Category",
								new ArrayAdapter<String>(getActivity(),
										R.layout.list_item, getGenreList()));

						listView.setAdapter(adapter);
						
						listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								if (id >= 1 && id <= truckList.length) {
									String truckId = truckList[(int) (id-1)].getId();
									Intent intent = new Intent(getActivity(), TruckActivity.class);
									intent.putExtra("id", truckId);
									startActivity(intent);
								} else if (id > truckList.length + 1){
									String genre = genreList[(int) (id - truckList.length - 2)];
									Intent intent = new Intent(getActivity(), GenreActivity.class);
									intent.putExtra("genre", genre);
									startActivity(intent);
								}
							}
						});
					}
				});

	}

	private String[] getGenreList() {
		return new String[] { "American", "Asian", "Chinese", "Fruit", "Indian",
				"Jamaican", "Japanese", "Korean", "Mexican", "Middle-Eastern", "Sandwiches" };
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the location manager
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			Log.i("DEBUG", "Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			Log.i("DEBUG", "Location not available");
			// latituteField.setText("Location not available");
			// longitudeField.setText("Location not available");
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onLocationChanged(android.location.
	 * Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
//		int lat = (int) (location.getLatitude());
//		int lng = (int) (location.getLongitude());
//		Log.i("Debug", String.format("%f, %f", lat, lng));
		if (this.location == null) {
			this.location = location;
			refreshList(location);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String,
	 * int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
