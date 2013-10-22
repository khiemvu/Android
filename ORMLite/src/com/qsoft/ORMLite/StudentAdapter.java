package com.qsoft.ORMLite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student>
{
    private Context mContext;
    private int row;
    private List<Student> list ;
    
	public StudentAdapter(Context context, int textViewResourceId, List<Student> list) {
		super(context, textViewResourceId, list);

		this.mContext=context;
		this.row=textViewResourceId;
		this.list=list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

	 
		if ((list == null) || ((position + 1) > list.size()))
			return view; // Can't extract item

		Student obj = list.get(position);
		
		holder.name = (TextView)view.findViewById(R.id.tvname);
		holder.age = (TextView)view.findViewById(R.id.tvage);

		if(null!=holder.name&&null!=obj&&obj.getName().length()!=0){
			holder.name.setText(obj.getName());
			holder.age.setText(obj.getAge());
		}
		
		

		return view;
	}

	public static class ViewHolder {
		public TextView name;
        public TextView age;
	}
}
