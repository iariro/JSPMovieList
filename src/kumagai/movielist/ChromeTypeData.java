package kumagai.movielist;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * 映画リストから構築可能なモノクロ・カラー区分集計データ。
 * @author kumagai
 */
public class ChromeTypeData
	extends HashMap<String, Integer>
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

					ChromeTypeData chromeTypeData =
						new ChromeTypeData(recordsCollection.get(i));

					chromeTypeData.dump();
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
	private int count;

	/**
	 * データ構築。
	 * @param records レコードコレクション
	 * @throws IOException
	 */
	public ChromeTypeData(RecordCollection records)
		throws IOException, ParseException
	{
		this.year = records.year;

		for (Record record : records)
		{
			if (containsKey(record.chromeType))
			{
				// 登場済み。

				put(record.chromeType, get(record.chromeType) + 1);
			}
			else
			{
				// 初登場。

				put(record.chromeType, 1);
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

		System.out.printf("カラー：%d\n", get("カ"));
		System.out.printf("モノクロ：%d\n", get("モ"));
	}
}
