package kumagai.movielist;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	public static void main(String[] args) throws IOException, ParseException, MovieListException
	{
		if (args.length < 1)
		{
			// 引数は指定されている。
			return;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "sjis"));
		RecordCollectionCollection recordsCollection = new RecordCollectionCollection(reader);
		reader.close();
		System.out.println(recordsCollection.generateTitleCountPoints());
	}

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

	/**
	 * 総視聴件数折れ線グラフ用座標を生成
	 * @return 総視聴件数折れ線グラフ用座標
	 */
	public String generateTitleCountPoints()
	{
		StringBuffer buffer = new StringBuffer();
		int count = 0;
		int totalCount = 0;
		DateTime pdate = null;
		for (int i=size()-1 ; i>=0 ; i--)
		{
			if (i < size()-1)
			{
				buffer.append(",");
			}
			buffer.append(String.format("{name: '%s年',data: [", get(i).year));

			int outCount = 0;
			for (int j=get(i).size()-1 ; j>=0 ; j--)
			{
				if (pdate != null)
				{
					// ２個目以降

					if (get(i).get(j).watchDate.compareTo(pdate) == 0)
					{
						// 同じ日

						count++;
					}
					else
					{
						// 違う日

						if (outCount > 0)
						{
							buffer.append(",");
						}
						totalCount += count;
						buffer.append(String.format("[%d,%d]\n", pdate.getCalendar().getTimeInMillis(), totalCount));
						outCount++;
						count = 1;
					}
				}
				else
				{
					count = 1;
				}
				pdate = get(i).get(j).watchDate;
			}
			if (pdate != null && count > 0)
			{
				if (outCount > 0)
				{
					buffer.append(",");
				}
				totalCount += count;
				buffer.append(String.format("[%d,%d]\n", pdate.getCalendar().getTimeInMillis(), totalCount));
			}
			buffer.append("]}\n");
		}
		return buffer.toString();
	}
}
