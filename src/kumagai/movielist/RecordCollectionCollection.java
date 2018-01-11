package kumagai.movielist;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import ktool.datetime.DateTime;

/**
 * 映画情報レコードコレクション。
 * @author kumagai
 */
public class RecordCollectionCollection
	extends ArrayList<RecordCollection>
{
	/**
	 * ファイル内容からレコードコレクションを構築する。
	 * @param reader ファイル読取オブジェクト
	 */
	public RecordCollectionCollection(BufferedReader reader)
		throws IOException, MovieListException, ParseException
	{
		boolean border = false;
		DateTime year = new DateTime();

		String line;
		String monthAndDay = null;
		int count = 1;

		RecordCollection records = new RecordCollection(year.getYear());

		add(records);

		while ((line = reader.readLine()) != null)
		{
			if (border)
			{
				// 仕切り線の後。

				if (line.startsWith("-"))
				{
					// 仕切り線。

					year.setYear(year.getYear() - 1);
					monthAndDay = null;

					records = new RecordCollection(year.getYear());
					add(records);
				}

				String [] fields = line.split("\t");

				if (fields.length == 6)
				{
					// フィールド数は正しい。

					if (monthAndDay != null &&
						monthAndDay.compareTo(fields[4]) < 0)
					{
						// 降順に反する。

						throw new MovieListException("order error in " + count);
					}

					records.add(new Record(fields, year));

					monthAndDay = fields[4];
				}
			}
			else
			{
				// 仕切り線の前。

				border = line.startsWith("-");
			}
			count++;
		}
	}
}
