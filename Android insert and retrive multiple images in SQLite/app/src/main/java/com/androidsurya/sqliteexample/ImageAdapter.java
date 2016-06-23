package com.androidsurya.sqliteexample;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<Employee> employeeList = new ArrayList<Employee>();

	// Constructor
	public ImageAdapter(Context c, ArrayList<Employee> employeeList) {
		mContext = c;
		this.employeeList = employeeList;
	}

	@Override
	public int getCount() {
		return employeeList.size();

	}

	@Override
	public Object getItem(int position) {
		return employeeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		imageView.setImageBitmap(employeeList.get(position).getBitmap());
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
		return imageView;
	}

}
