package kumagai.movielist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * 映画リストから構築可能なカテゴリー集計データのコレクション
 */
public class CategoryDataCollection
	extends ArrayList<CategoryData>
{
	public static void main(String[] args) throws IOException, ParseException, MovieListException
	{
		if (args.length < 1)
		{
			// 引数は指定されている。
			return;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "sjis"));
		RecordCollectionCollection recordsCollection = new RecordCollectionCollection(reader);
		reader.close();

		CategoryDataCollection histoDataCollection = new CategoryDataCollection();
		for (int i=0 ; i<recordsCollection.size() ; i++)
		{
			histoDataCollection.add(new CategoryData(recordsCollection.get(i)));
		}

		PrintWriter writer = new PrintWriter(new File("CategoryDataHisto.html"));
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
		writer.println("var chart = {type: 'column'};");
		writer.println("var title = {text: 'Category Data Histo'};");
		writer.println("var xAxis = {categories: [");
		writer.println(histoDataCollection.generateXLabel());
		writer.println("],title: {text: null}};");
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
		System.out.println("done");
	}

	/**
	 * X軸ラベルを生成
	 * @return X軸ラベル
	 */
	public String generateXLabel()
	{
		StringBuffer buffer = new StringBuffer();
		for (int i=0 ; i<size() ; i++)
		{
			if (i > 0)
			{
				buffer.append(",");
			}
			buffer.append(String.format("'%d年'", get(size() - i - 1).year));
		}
		return buffer.toString();
	}

	/**
	 * Highcharts用の値の配列を生成
	 * @return Highcharts用の値の配列
	 */
	public String generateHighchartsPoints()
	{
		TreeMap<String, Function<CategoryData, Integer>> keys = new TreeMap<String, Function<CategoryData, Integer>>();
		keys.put("洋画", (data)->{return data.yougaCount;});
		keys.put("邦画", (data)->{return data.hougaCount;});
		StringBuffer buffer = new StringBuffer();
		int count = 0;
		for (String key : keys.keySet())
		{
			if (count > 0)
			{
				buffer.append(",");
			}
			buffer.append(String.format("{name:'%s', data:[", key));
			for (int j=0 ; j<size() ; j++)
			{
				if (j > 0)
				{
					buffer.append(",");
				}
				buffer.append(keys.get(key).apply(get(size() - j - 1)));
			}
			buffer.append("]}");

			count++;
		}
		return buffer.toString();
	}
}
