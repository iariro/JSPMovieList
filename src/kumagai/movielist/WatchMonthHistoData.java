package kumagai.movielist;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ktool.datetime.DateTime;
import ktool.datetime.TimeSpan;

/**
 * 映画リストから構築可能な鑑賞時期ヒストグラムデータ。
 * @author kumagai
 */
public class WatchMonthHistoData
	extends ArrayList<Integer>
{
	/**
	 * エントリポイント。
	 * @param args [0]=ファイルパス
	 * @throws IOException
	 */
	public static void main(String[] args)
		throws Exception
	{
		if (args.length == 1)
		{
			// 引数は指定されている。

			BufferedReader reader =
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(args[0])));

			try
			{
				RecordCollectionCollection recordsCollection =
					new RecordCollectionCollection(reader);

				for (int i=0 ; i<recordsCollection.size() ; i++)
				{
					if (i > 0)
					{
						System.out.println();
					}

					WatchMonthHistoData histoData =
						new WatchMonthHistoData(recordsCollection.get(i));

					histoData.dump();
				}
			}
			catch (MovieListException exception)
			{
				System.out.println(exception);
			}
			finally
			{
				reader.close();
			}
		}
	}

	public final int year;
	public final DateTime endDate;
	public int count;

	/**
	 * データ構築。
	 * @param records レコードコレクション
	 * @throws IOException
	 */
	public WatchMonthHistoData(RecordCollection records)
	{
		this.year = records.year;

		DateTime today = new DateTime();

		if (today.getYear() == records.year)
		{
			// 今年。

			this.endDate = today;
		}
		else
		{
			// 去年以前。

			this.endDate = new DateTime(records.year, 12, 31, 0, 0, 0);
		}

		for (int i=0 ; i<12 ; i++)
		{
			add(0);

			boolean find = false;
			for (Record record : records)
			{
				if (record.watchDate.getMonth() == (i + 1))
				{
					// 対象の月である

					set(i, get(i) + 1);
					find = true;
				}
				else if (find)
				{
					// 対象の月を過ぎた

					break;
				}
			}
		}
	}

	/**
	 * ダンプ。
	 */
	public void dump()
	{
		DateTime day = new DateTime(this.endDate);

		System.out.printf("%d年 %2d件\n", year, count);

		for (int i=0 ; i<size() ; i++)
		{
			System.out.printf(
				"%d/%02d : %2d ",
				day.getYear(),
				day.getMonth(),
				get(i));

			day.setDay(1);
			day.add(new TimeSpan(-24, 0, 0));

			for (int j=0 ; j<get(i) ; j++)
			{
				System.out.print("*");
			}

			System.out.println();
		}
	}
}
