package kumagai.movielist;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 映画リストから構築可能な年別ヒストグラムデータ。
 * @author kumagai
 */
public class ReleaseYearHistoData
{
	/**
	 * エントリポイント。
	 * @param args [0]=ファイルパス [1]=開始年 [2]=終了年 [3]=年の増分
	 * @throws IOException
	 */
	public static void main(String[] args)
		throws Exception
	{
		if (args.length == 4)
		{
			// 引数は指定されている。

			BufferedReader reader =
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(args[0])));

			int startYear = Integer.valueOf(args[1]);
			int endYear = Integer.valueOf(args[2]);
			int step = Integer.valueOf(args[3]);

			endYear = ((endYear + step - 1) / step) * step;

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

					ReleaseYearHistoData histoData =
						new ReleaseYearHistoData(
							recordsCollection.get(i),
							startYear,
							endYear,
							step);

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

	public int [] yearHisto;
	private int count;
	public final int startYear;
	public final int endYear;
	public final int step;
	public final int year;

	/**
	 * データ構築。
	 * @param records レコードコレクション
	 * @param startYear 開始年
	 * @param endYear 終了年
	 * @param step 年の増分
	 * @throws IOException
	 */
	public ReleaseYearHistoData
		(RecordCollection records, int startYear, int endYear, int step)
	{
		this.year = records.year;
		this.yearHisto = new int [(endYear - startYear) / step];
		this.startYear = startYear;
		this.endYear = endYear;
		this.step = step;

		for (Record record : records)
		{
			int index = record.year - startYear;

			if (index >= 0 && index / step < yearHisto.length)
			{
				// 範囲内。

				yearHisto[index / step]++;
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

		for (int i=0 ; i<(endYear - startYear)/step ; i++)
		{
			System.out.printf("%d : %2d ", startYear + i * step, yearHisto[i]);

			for (int j=0 ; j<yearHisto[i] ; j++)
			{
				System.out.print("*");
			}

			System.out.println();
		}
	}
}
