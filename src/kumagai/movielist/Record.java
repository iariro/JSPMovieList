package kumagai.movielist;

import java.text.*;
import ktool.datetime.*;

/**
 * 映画情報１レコード。
 * @author kumagai
 */
public class Record
{
	public final int year;
	public final String category;
	public final String chromeType;
	public final String acquisitionType;
	public final DateTime watchDate;
	public final String title;

	/**
	 * 映画情報１レコードを構築する。
	 * @param fields １レコード分のフィールド
	 * @param year 鑑賞年を表す日付オブジェクト
	 * @throws ParseException
	 */
	public Record(String [] fields, DateTime year)
		throws ParseException
	{
		String dateString =
			String.format("%d/%s", year.getYear(), fields[4]);

		this.year = Integer.valueOf(fields[0]);
		this.category = fields[1];
		this.chromeType = fields[2];
		this.acquisitionType = fields[3];
		this.watchDate = DateTime.parseDateString(dateString);
		this.title = fields[5];
	}
}
