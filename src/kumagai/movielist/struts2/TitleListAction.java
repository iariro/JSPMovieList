package kumagai.movielist.struts2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import kumagai.movielist.RecordCollectionCollection;

/**
 * タイトル一覧アクション。
 */
@Namespace("/movielist")
@Results
({
	@Result(name="success", location="/movielist/titlelist.jsp"),
	@Result(name="error", location="/movielist/error.jsp")
})
public class TitleListAction
{
	public RecordCollectionCollection recordsCollection;
	public String message;

	/**
	 * タイトル一覧アクション。
	 * @return 処理結果
	 */
	@Action("titlelist")
	public String execute()
	{
		try
		{
			ServletContext context = ServletActionContext.getServletContext();
			String titleListPath = context.getInitParameter("MovieListTitleListPath");
			if (titleListPath != null)
			{
				// 必要なパラメータは指定されている

				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(titleListPath), "sjis"));
				recordsCollection = new RecordCollectionCollection(reader);
				reader.close();

				return "success";
			}
			else
			{
				// 必要なパラメータは指定されていない

				message = "必要なパラメータは指定されている";
			}
		}
		catch (Exception exception)
		{
			message = exception.toString();
		}

		return "error";
	}
}
