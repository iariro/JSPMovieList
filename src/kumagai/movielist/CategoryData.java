package kumagai.movielist;

import java.io.*;
import java.text.*;

/**
 * 映画リストから構築可能なカテゴリー集計データ。
 * @author kumagai
 */
public class CategoryData
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

					CategoryData categoryData =
						new CategoryData(recordsCollection.get(i));

					categoryData.dump();
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

	private final int year;
	private int yougaCount;
	private int hougaCount;
	private int count;

	/**
	 * データ構築。
	 * @param records レコードコレクション
	 */
	public CategoryData(RecordCollection records)
		throws IOException, ParseException
	{
		this.year = records.year;

		for (Record record : records)
		{
			if (record.category.equals("洋"))
			{
				// 洋画。

				yougaCount++;
			}
			else if (record.category.equals("邦"))
			{
				// 邦画。

				hougaCount++;
			}

			count++;
		}
	}

	/**
	 * ダンプ。
	 */
	public void dump()
	{
		System.out.printf("%d年 %2d件\n", year, count);

		System.out.printf(
			"洋画：%d (%2.2f%%)\n",
			yougaCount,
			(float)yougaCount * 100 / (float)(yougaCount + hougaCount));
		System.out.printf(
			"報画：%d (%2.2f%%)\n",
			hougaCount,
			(float)hougaCount * 100 / (float)(yougaCount + hougaCount));
	}
}
