package midsummer.sort;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import midsummer.sort.Adapter.SortAdapter;
import midsummer.sort.utils.CharacterParser;
import midsummer.sort.utils.PinyinComparator;

/**
 * 项目名称：Sort
 * 类描述：
 * 创建人：77.
 * 创建时间：2015/12/6 18:02
 * 修改人：77.
 * 修改时间：2015/12/6 18:02
 * 修改备注：
 * QQ：951203598
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
{
	@ViewById
	Toolbar toolbar;
	@ViewById
	ListView listview;
	@ViewById
	SideBar sidebar;
	@ViewById
	TextView dialog;
	CharacterParser characterParser;
	private List<String> list = new ArrayList<>();
	private String[] names;
	private SortAdapter adapter;
	
	@AfterViews
	void mainActivity()
	{
		setSupportActionBar(toolbar);
		characterParser = new CharacterParser();
		sidebar.setTextView(dialog);
		
		sidebar.setOnLetterSelectedListener(new SideBar.OnLetterSelectedListener()
		{
			@Override
			public void onLetterSelected(String s)
			{
				int position = adapter.getPositionBySection(s.charAt(0));
				listview.setSelection(position);
			}
		});
		
		// 查询系统中所有的联系人
		Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		
		// cursor转换成数组
		String name;
		assert cursor != null;
		while (cursor.moveToNext())
		{
			name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			list.add(name);
		}
		cursor.close();
		for (int i = 0; i < list.size(); i++)
		{
			names = list.toArray(new String[list.size()]);
		}
		List<SortModel> data = fillData(names);
		
		// 获取到姓名数据
		Collections.sort(data, new PinyinComparator());
		adapter = new SortAdapter(this, data);
		listview.setAdapter(adapter);
	}
	
	public List<SortModel> fillData(String[] names)
	{
		List<SortModel> sortModels = new ArrayList<>();
		for (String name : names)
		{
			SortModel model = new SortModel();
			// 名字
			model.setName(name);
			String pingyin = characterParser.getSelling(name);
			// 获取名字拼音的首字母大写
			String sortLetter = pingyin.substring(0, 1).toUpperCase();
			model.setSortLetter(sortLetter);
			sortModels.add(model);
		}
		return sortModels;
	}
}
