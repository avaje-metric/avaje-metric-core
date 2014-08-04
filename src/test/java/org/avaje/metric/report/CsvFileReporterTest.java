package org.avaje.metric.report;

import org.avaje.metric.MetricManager;
import org.avaje.metric.TimedEvent;
import org.avaje.metric.TimedMetric;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class CsvFileReporterTest {

  @Test
  public void exercise_with_MetricReportManager() throws InterruptedException, IOException {
    
    CsvFileReporter fileReporter = new CsvFileReporter(".", "metric-csv-exercise");
    MetricReportManager report = new MetricReportManager(1, fileReporter);

    TimedMetric timedMetric = MetricManager.getTimedMetric("group.type.junk");

    Random random = new Random();
    
    for (int i = 0; i < 50; i++) {
      TimedEvent event = timedMetric.startEvent();
      int plus = random.nextInt(20);
      Thread.sleep(20+plus);
      event.endWithSuccess();
    }
    
    Thread.sleep(2000);

    report.hashCode();
  }
  
}