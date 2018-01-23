package kumagai.movielist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 映画リストから構築可能な入手方法集計データのコレクション
 */
public class AcquisitionTypeDataCollection
	extends ArrayList<AcquisitionTypeData>
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

		AcquisitionTypeDataCollection histoDataCollection = new AcquisitionTypeDataCollection(recordsCollection);

		PrintWriter writer = new PrintWriter(new File("../AcquisitionTypeHisto.html"));
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
		writer.println("var title = {text: 'Acquisition Type Histo'};");
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
	}

	/**
	 * データリストを構築
	 * @param recordsCollection 元データ
	 */
	public AcquisitionTypeDataCollection(RecordCollectionCollection recordsCollection)
	{
		for (RecordCollection records : recordsCollection)
		{
			add(new AcquisitionTypeData(records));
		}
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
		LinkedHashMap<String, String> keys = new LinkedHashMap<String, String>();
		keys.put("DR", "DVDレンタル");
		keys.put("DP", "DVD購入");
		keys.put("DA", "DVDオークション");
		keys.put("NR", "ネットレンタル");
		keys.put("TV", "TV視聴");
		keys.put("VA", "VHSオークション");
		keys.put("LP", "LD購入");
		keys.put("DB", "ひとのDVD");

		StringBuffer buffer = new StringBuffer();
		int count = 0;
		for (String key : keys.keySet())
		{
			if (count > 0)
			{
				buffer.append(",");
			}
			buffer.append(String.format("{name:'%s', data:[", keys.get(key)));
			for (int j=0 ; j<size() ; j++)
			{
				if (j > 0)
				{
					buffer.append(",");
				}
				AcquisitionTypeData data = get(size() - j - 1);
				buffer.append(data.get(key));
			}
			buffer.append("]}");
			count++;
		}
		return buffer.toString();
	}
}
