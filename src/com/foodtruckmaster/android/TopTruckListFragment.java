/**
 * 
 */
package com.foodtruckmaster.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.foodtruckmaster.android.adapter.TruckInfoAdapter;
import com.foodtruckmaster.android.model.TruckInfo;

/**
 * @author siyusong
 * 
 */
public class TopTruckListFragment extends Fragment {
	ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.top_list, container, false);
//		listView = (ListView) view.findViewById(R.id.top_list_view);
		listView = new ListView(getActivity());
		
		String url = API.getTopTrucks(10);

		GsonTransformer t = new GsonTransformer();
		AQuery aq = new AQuery(getActivity());
		aq.transformer(t)
				.ajax(url, TruckInfo[].class, new AjaxCallback<TruckInfo[]>() {
					public void callback(String url,
							final TruckInfo[] truckList, AjaxStatus status) {

						listView.setAdapter(new TruckInfoAdapter(getActivity(),
								truckList));

						listView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {

							}
						});
					}
				});
		return listView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
	}
}
