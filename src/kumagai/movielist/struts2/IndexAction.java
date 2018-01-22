package kumagai.movielist.struts2;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * トップページ表示アクション。
 */
@Namespace("/movielist")
@Results
({
	@Result(name="success", location="/movielist/index.jsp"),
	@Result(name="error", location="/movielist/error.jsp")
})
public class IndexAction
{
	public String listPath;
	public String message;

	/**
	 * トップページ表示アクション。
	 * @return 処理結果
	 */
	@Action("index")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();
		listPath = context.getInitParameter("MovieListTitleListPath");

		if (listPath != null)
		{
			// 必要なパラメータは指定されている

			return "success";
		}
		else
		{
			// 必要なパラメータは指定されていない

			message = "必要なパラメータは指定されていない";
		}

		return "error";
	}
}
