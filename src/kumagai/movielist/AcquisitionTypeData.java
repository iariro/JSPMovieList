package kumagai.movielist;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 映画リストから構築可能な入手方法集計データ。
 * @author kumagai
 */
public class AcquisitionTypeData
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

					AcquisitionTypeData histoData =
						new AcquisitionTypeData(recordsCollection.get(i));

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
	public int count;

	/**
	 * データ構築。
	 * @param records レコードコレクション
	 * @throws IOException
	 */
	public AcquisitionTypeData(RecordCollection records)
	{
		this.year = records.year;

		put("DR", 0);
		put("DP", 0);
		put("DA", 0);
		put("TV", 0);
		put("VA", 0);
		put("LP", 0);
		put("DB", 0);

		for (Record record : records)
		{
			if (containsKey(record.acquisitionType))
			{
				// 登場済み。

				put(record.acquisitionType, get(record.acquisitionType) + 1);
			}
			else
			{
				// 初登場。

				put(record.acquisitionType, 1);
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

		System.out.printf("DVDレンタル    : %3d\n", get("DR"));
		System.out.printf("DVD購入        : %3d\n", get("DP"));
		System.out.printf("DVDオークション: %3d\n", get("DA"));
		System.out.printf("TV             : %3d\n", get("TV"));
		System.out.printf("VHSオークション: %3d\n", get("VA"));
		System.out.printf("LD購入         : %3d\n", get("LP"));
		System.out.printf("DVD借りる      : %3d\n", get("DB"));
	}
}
