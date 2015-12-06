package midsummer.sort.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import midsummer.sort.R;
import midsummer.sort.SortModel;

/**
 * 项目名称：Sort
 * 类描述：
 * 创建人：77.
 * 创建时间：2015/12/6 20:42
 * 修改人：77.
 * 修改时间：2015/12/6 20:42
 * 修改备注：
 * QQ：951203598
 */
public class SortAdapter extends BaseAdapter
{
	private Context context;
	private List<SortModel> data;
	
	public SortAdapter(Context context, List<SortModel> data)
	{
		this.context = context;
		this.data = data;
	}
	
	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		// 每个条目的数据
		SortModel sortModel = data.get(position);
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
			holder.name = (TextView) convertView.findViewById(R.id.title);
			holder.sortLetter = (TextView) convertView.findViewById(R.id.catalog);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		// 首字母字符
		int selection = getSelectionByPosition(position);
		int index = getPositionBySection(selection);
		if (position == index)
		{
			// 第一个
			holder.sortLetter.setVisibility(View.VISIBLE);
			// 显示字母
			holder.sortLetter.setText(sortModel.getSortLetter());
		} else
		{
			holder.sortLetter.setVisibility(View.GONE);
		}
		holder.name.setText(sortModel.getName());
		return convertView;
	}
	
	public int getSelectionByPosition(int position)
	{
		return data.get(position).getSortLetter().charAt(0);
	}
	
	/**
	 * 通过首字母获取该首字母姓名的人
	 *
	 * @param section
	 * @return
	 */
	public int getPositionBySection(int section)
	{
		for (int i = 0; i < getCount(); i++)
		{
			String sortStr = data.get(i).getSortLetter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section)
			{
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public int getCount()
	{
		return data.size();
	}
	
	class ViewHolder
	{
		TextView name;
		TextView sortLetter;
	}
}
