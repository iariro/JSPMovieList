package kumagai.movielist;

import java.text.ParseException;

import ktool.datetime.DateTime;

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
	 * 表示用の洋画／邦画の区別を取得
	 * @return 表示用の洋画／邦画の区別
	 */
	public String getCategory()
	{
		return category + "画";
	}

	/**
	 * 表示用のカラー／モノクロの区別を取得
	 * @return 表示用のカラー／モノクロの区別
	 */
	public String getChromeType()
	{
		if (chromeType.equals("カ"))
		{
			return "カラー";
		}
		else if (chromeType.equals("モ"))
		{
			return "モノクロ";
		}
		return chromeType;
	}

	/**
	 * 表示用の視聴方法を取得
	 * @return 表示用の視聴方法
	 */
	public String getAcquisitionType()
	{
		if (acquisitionType.equals("NR"))
		{
			return "ネットレンタル";
		}
		else if (acquisitionType.equals("DR"))
		{
			return "DVDレンタル";
		}
		else if (acquisitionType.equals("DP"))
		{
			return "DVD購入";
		}
		else if (acquisitionType.equals("DA"))
		{
			return "DVDオークション";
		}
		else if (acquisitionType.equals("DB"))
		{
			return "DVD人から借りて";
		}
		else if (acquisitionType.equals("VA"))
		{
			return "VHS購入";
		}
		else if (acquisitionType.equals("LP"))
		{
			return "LD購入";
		}
		return acquisitionType;
	}

	/**
	 * 表示用の視聴日を取得
	 * @return 表示用の視聴日
	 */
	public String getWatchDate()
	{
		return watchDate.toString().substring(5);
	}

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
