package midsummer.sort.utils;

import java.util.Comparator;

import midsummer.sort.SortModel;

/**
 * 项目名称：Sort
 * 类描述：排序
 * 创建人：77.
 * 创建时间：2015/12/6 20:38
 * 修改人：77.
 * 修改时间：2015/12/6 20:38
 * 修改备注：
 * QQ：951203598
 */
public class PinyinComparator implements Comparator<SortModel>
{
	@Override
	public int compare(SortModel lhs, SortModel rhs)
	{
		return lhs.getSortLetter().compareTo(rhs.getSortLetter());
	}
}
