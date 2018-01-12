package kumagai.movielist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * 映画リストから構築可能な鑑賞時期ヒストグラムデータのコレクション
 * @author kumagai
 */
public class WatchMonthHistoDataCollection
	extends ArrayList<WatchMonthHistoData>
{
	public static void main(String[] args) throws IOException, ParseException, MovieListException
	{
		if (args.length < 1)
		{
			// 引数は指定されている。
			return;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
		RecordCollectionCollection recordsCollection = new RecordCollectionCollection(reader);
		reader.close();
		WatchMonthHistoDataCollection histoDataCollection = new WatchMonthHistoDataCollection(recordsCollection);

		PrintWriter writer = new PrintWriter(new File("WatchMonthHisto.html"));
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<meta charset='UTF-8' />");
		writer.println("<script src='http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js'></script>");
		writer.println("<script src='http://code.highcharts.com/highcharts.js'></script>");
		writer.println("</head>");
		writer.println("<body>");
		writer.println("<div id='container' style='width: 1000px; height: 600px; margin: 0 auto'></div>");
		writer.println("<script language='JavaScript'>");
		writer.println("$(document).ready(function() {");
		writer.println("var chart = {type: 'bar'};");
		writer.println("var title = {text: 'Release Year Histo'};");
		writer.println("var xAxis = {categories: ['１月','２月','３月','４月','５月','６月','７月','８月','９月','１０月','１１月','１２月'],title: {text: null}};");
		writer.println("var yAxis = { min: 0, title: {text: '件数', align: 'high'}, labels: {overflow: 'justify'}};");
		writer.println("var plotOptions = {bar: {dataLabels: {enabled: true}},series: {stacking: 'normal'}};");
		writer.println("var legend = {layout: 'vertical',align: 'right',verticalAlign: 'top',x: -40,y: 100,floating: true,borderWidth: 1,backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'), shadow: true};");
		writer.println("var credits = {enabled: false};");
		writer.println("var series= [");
		writer.println(histoDataCollection.generateHighchartsPoints());
		writer.println("];");
		writer.println("var json = {};");
		writer.println("json.chart = chart;");
		writer.println("json.title = title;");
		writer.println("json.xAxis = xAxis;");
		writer.println("json.yAxis = yAxis;");
		writer.println("json.series = series;");
		writer.println("json.plotOptions = plotOptions;");
		writer.println("json.legend = legend;");
		writer.println("json.credits = credits;");
		writer.println("$('#container').highcharts(json);");
		writer.println("});");
		writer.println("</script>");
		writer.println("</body>");
		writer.println("</html>");
		writer.close();
	}

	/**
	 * データリストを構築
	 * @param recordsCollection 元データ
	 */
	public WatchMonthHistoDataCollection(RecordCollectionCollection recordsCollection)
	{
		for (RecordCollection records : recordsCollection)
		{
			add(new WatchMonthHistoData(records));
		}
	}

	/**
	 * Highcharts用の値の配列を生成
	 * @return Highcharts用の値の配列
	 */
	public String generateHighchartsPoints()
	{
		StringBuffer buffer = new StringBuffer();
		for (int i=0 ; i<size() ; i++)
		{
			WatchMonthHistoData data = get(i);
			if (i > 0)
			{
				buffer.append(",");
			}
			buffer.append(String.format("{name:'%d年', data:[", data.year));
			for (int j=0 ; j<data.size() ; j++)
			{
				if (j > 0)
				{
					buffer.append(",");
				}
				buffer.append(data.get(j));
			}
			buffer.append("]}");
		}
		return buffer.toString();
	}
}
