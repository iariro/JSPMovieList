package kumagai.movielist;

/**
 * 映画情報リスト例外。
 * @author kumagai
 */
public class MovieListException
	extends Exception
{
	/**
	 * 基底クラスの初期化。
	 * @param message メッセージ
	 */
	public MovieListException(String message)
	{
		super(message);
	}
}
