package kumagai.movielist;

import java.util.*;

/**
 * 年ごとのレコードコレクション。
 */
public class RecordCollection
	extends ArrayList<Record>
{
	public final int year;

	/**
	 * 指定の値をメンバーに割り当て。
	 * @param year 年
	 */
	public RecordCollection(int year)
	{
		this.year = year;
	}
}
