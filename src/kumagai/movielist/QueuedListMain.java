package kumagai.movielist;

import java.io.*;

/**
 * 順番待ちリスト表示。
 * @author kumagai
 */
public class QueuedListMain
{
	/**
	 * エントリポイント。
	 * @param args [0]=ファイルパス
	 */
	public static void main(String[] args)
		throws IOException
	{
		if (args.length == 1)
		{
			// 引数は指定されている。

			QueuedList queuedList;
			try
			{
				BufferedReader reader =
					new BufferedReader(
						new InputStreamReader(
							new FileInputStream(args[0]), "sjis"));

				queuedList = new QueuedList(reader);
				reader.close();
			}
			catch (FileNotFoundException exception)
			{
				System.out.println(exception.toString());
				return;
			}

			/*
			for (Map.Entry<String, ArrayList<String>> entry
				: queuedList.entrySet())
			{
				System.out.printf(
					"%s : %d\n",
					entry.getKey(),
					entry.getValue().size());
			}
			*/

			int queued =
				queuedList.get("レンタル").size() +
				queuedList.get("購入").size() +
				queuedList.get("鑑賞").size() +
				queuedList.get("困難").size();

			int watched = queuedList.get("鑑賞済み").size();

			System.out.printf("%d\t%d\n", watched, queued);
		}
		else
		{
			System.out.println("Usage: [0]=ファイルパス");
		}
	}
}
