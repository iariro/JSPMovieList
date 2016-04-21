package kumagai.movielist;

import java.io.*;
import java.util.*;

/**
 * 順番待ちリスト。
 * @author kumagai
 */
public class QueuedList
	extends HashMap<String, ArrayList<String>>
{
	/**
	 * 順番待ちリストを構築。
	 * @param reader リストファイル
	 */
	public QueuedList(BufferedReader reader)
		throws IOException
	{
		String line;
		String type = null;
		boolean separate = false;

		while ((line = reader.readLine()) != null)
		{
			if (! separate)
			{
				// 未鑑賞

				if (line.endsWith("："))
				{
					// タイプ行。

					type = line.substring(0, line.length() - 1);

					if (! containsKey(type))
					{
						// 初出

						put(type, new ArrayList<String>());
					}
				}
				else if (line.startsWith("---"))
				{
					// 鑑賞済み仕切り行。

					separate = true;
					type = "鑑賞済み";
					put(type, new ArrayList<String>());
				}
				else
				{
					// 作品行。

					if (line.length() > 0)
					{
						// 空行ではない。

						get(type).add(line);
					}
				}
			}
			else
			{
				// 鑑賞済み

				if (line.length() > 0)
				{
					// 空行ではない。

					if (! line.startsWith("---"))
					{
						// 鑑賞済み仕切り行ではない。

						get(type).add(line);
					}
				}
			}
		}
	}
}
