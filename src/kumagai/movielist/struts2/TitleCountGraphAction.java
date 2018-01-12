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
 * 総視聴件数折れ線グラフ表示アクション。
 */
@Namespace("/movielist")
@Results
({
	@Result(name="success", location="/movielist/titlecountgraph.jsp"),
	@Result(name="error", location="/movielist/error.jsp")
})
public class TitleCountGraphAction
{
	public String generateTitleCountPoints;
	public String message;

	/**
	 * 総視聴件数折れ線グラフ表示アクション。
	 * @return 処理結果
	 */
	@Action("titlecountgraph")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();
		String titleListPath = context.getInitParameter("MovieListTitleListPath");
		if (titleListPath != null)
		{
			// 必要なパラメータは指定されている

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(titleListPath), "sjis"));
			RecordCollectionCollection recordsCollection = new RecordCollectionCollection(reader);
			reader.close();
			generateTitleCountPoints = recordsCollection.generateTitleCountPoints();

			return "success";
		}
		else
		{
			// 必要なパラメータは指定されていない

			message = "必要なパラメータは指定されている";
		}

		return "error";
	}
}
