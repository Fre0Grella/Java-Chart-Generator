import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChartCreator {

    /**
     * Creates and saves a line chart.
     *
     * @param outputPath  The file path where the chart image will be saved.
     * @param title       The title of the chart.
     * @param xAxisLabel  The label for the X-axis.
     * @param yAxisLabel  The label for the Y-axis.
     * @param xData1      The X values for the first data series.
     * @param yData1      The Y values for the first data series.
     * @param label1      The label for the first data series.
     * @param xData2      The X values for the second data series.
     * @param yData2      The Y values for the second data series.
     * @param label2      The label for the second data series.
     */
    public static void createLineChart(String outputPath, String title, String xAxisLabel, String yAxisLabel,
                                       List<Double> xData1, List<Double> yData1, String label1,
                                       List<Double> xData2, List<Double> yData2, String label2) {
        // Creation of the data series
        XYSeries series1 = new XYSeries(label1);
        for (int i = 0; i < xData1.size(); i++) {
            series1.add(xData1.get(i), yData1.get(i));
        }

        XYSeries series2 = new XYSeries(label2);
        for (int i = 0; i < xData2.size(); i++) {
            series2.add(xData2.get(i), yData2.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        // Chart Creation
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true, // Show Legend
                false,
                false
        );

        XYPlot plot = chart.getXYPlot();

        // Background
        plot.setBackgroundPaint(Color.WHITE);

        // Grid
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setDomainGridlineStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2.0f, 2.0f}, 0));
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setRangeGridlineStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2.0f, 2.0f}, 0));


        // Configuration for X axis with integer number
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Configuration for X axis with percentage
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRange(0, 110); //  range from 0% to 110%
        yAxis.setLabel(yAxisLabel);

        // Style Modification
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Series 1: Red line with square
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Rectangle(5, 5));

        // Series 2: Blu line with circles
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));

        plot.setRenderer(renderer);

        // Image Creation
        try {
            ChartUtils.saveChartAsPNG(new File(outputPath), chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Usage Example
        createLineChart(
                "strongScalingChart.png",
                "Strong scaling efficiency",
                "N. of cores",
                "Efficiency",
                List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0),
                List.of(100.0, 99.0, 99.5, 99.7, 99.8, 99.9, 99.6, 99.5, 99.3, 99.2, 99.0, 97.0),
                "N grande, IT piccolo",
                List.of(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0),
                List.of(100.0, 95.0, 92.0, 90.0, 87.0, 85.0, 80.0, 75.0, 70.0, 65.0, 60.0, 50.0),
                "N piccolo, IT grande"
        );
        System.out.println("png creato");
    }
}
