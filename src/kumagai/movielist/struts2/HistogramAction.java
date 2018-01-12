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

import ktool.datetime.DateTime;
import kumagai.movielist.AcquisitionTypeDataCollection;
import kumagai.movielist.CategoryDataCollection;
import kumagai.movielist.ChromeTypeDataCollection;
import kumagai.movielist.RecordCollectionCollection;
import kumagai.movielist.ReleaseYearHistoDataCollection;
import kumagai.movielist.WatchMonthHistoDataCollection;

/**
 * 視聴関連情報ヒストグラム表示アクション。
 */
@Namespace("/movielist")
@Results
({
	@Result(name="success", location="/movielist/histogram.jsp"),
	@Result(name="error", location="/movielist/error.jsp")
})
public class HistogramAction
{
	public String watchMonthHistoHistoPoints;
	public String releaseYearHistoPoints;
	public String releaseYearHistoXLabel;
	public String acquisitionTypeHistoPoints;
	public String acquisitionTypeHistoXLabel;
	public String categoryDataHistoPoints;
	public String categoryDataHistoXLabel;
	public String chromeTypeHistoPoints;
	public String chromeTypeHistoXLabel;
	public String message;

	/**
	 * 視聴関連情報ヒストグラム表示アクション。
	 * @return 処理結果
	 */
	@Action("histogram")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();
		String titleListPath = context.getInitParameter("MovieListTitleListPath");
		String startYearString = context.getInitParameter("MovieListStartYear");
		String yearStepString = context.getInitParameter("MovieListYearStep");

		if (titleListPath != null && startYearString != null && yearStepString != null)
		{
			// 必要なパラメータは指定されている

			int startYear = Integer.valueOf(startYearString);
			int endYear = new DateTime().getYear();
			int yearStep = Integer.valueOf(yearStepString);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(titleListPath), "sjis"));
			RecordCollectionCollection recordsCollection = new RecordCollectionCollection(reader);
			reader.close();

			WatchMonthHistoDataCollection watchMonthHistoDataCollection = new WatchMonthHistoDataCollection(recordsCollection);
			watchMonthHistoHistoPoints = watchMonthHistoDataCollection.generateHighchartsPoints();

			ReleaseYearHistoDataCollection releaseYearHistoDataCollection = new ReleaseYearHistoDataCollection(recordsCollection, startYear, endYear, yearStep);
			releaseYearHistoPoints = releaseYearHistoDataCollection.generateHighchartsPoints();
			releaseYearHistoXLabel = releaseYearHistoDataCollection.generateXLabel();

			AcquisitionTypeDataCollection acquisitionTypeDataCollection = new AcquisitionTypeDataCollection(recordsCollection);
			acquisitionTypeHistoPoints = acquisitionTypeDataCollection.generateHighchartsPoints();
			acquisitionTypeHistoXLabel = acquisitionTypeDataCollection.generateXLabel();

			CategoryDataCollection categoryDataCollection = new CategoryDataCollection(recordsCollection);
			categoryDataHistoPoints = categoryDataCollection.generateHighchartsPoints();
			categoryDataHistoXLabel = categoryDataCollection.generateXLabel();

			ChromeTypeDataCollection chromeTypeDataCollection = new ChromeTypeDataCollection(recordsCollection);
			chromeTypeHistoPoints = chromeTypeDataCollection.generateHighchartsPoints();
			chromeTypeHistoXLabel = chromeTypeDataCollection.generateXLabel();

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
