package io.avaje.metrics.core;

import orange.truck.Customer;
import io.avaje.metrics.MetricName;
import io.avaje.metrics.TimedMetric;
import io.avaje.metrics.TimingMetricInfo;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class DefaultMetricManagerTest {

  @Test
  public void testGetRequestTimingMetrics() throws Exception {

    DefaultMetricManager mgr = new DefaultMetricManager();

    TimedMetric m0 = mgr.timed(MetricName.of("org.req.Customer.m0"));
    TimedMetric m1 = mgr.timed(MetricName.of("org.req.Customer.m1"));
    TimedMetric m2 = mgr.timed(MetricName.of("org.req.Customer.m2"), 100, 200);

    List<TimingMetricInfo> timingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(0, timingMetrics.size());

    m0.setRequestTimingCollection(1);
    timingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(1, timingMetrics.size());
    assertEquals(m0.getName().getSimpleName(), timingMetrics.get(0).getName());


    m2.setRequestTimingCollection(10);
    timingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(2, timingMetrics.size());

    m1.setRequestTimingCollection(10);
    timingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(3, timingMetrics.size());

    m0.decrementCollectionCount();
    timingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(2, timingMetrics.size());

  }

  @Test
  public void testSetCollection() {

    DefaultMetricManager mgr = new DefaultMetricManager();

    TimedMetric m0 = mgr.timed(MetricName.of(Customer.class, "doSomething"));

    TimedMetric m0b = mgr.timed(MetricName.of("orange.truck.Customer.doSomething"));

    assertSame(m0, m0b);
    //assertEquals("na.Customer.doSomething", m0.getName().getSimpleName());

    TimedMetric m1 = mgr.timed(MetricName.of("org.req.Customer.m1"));
    TimedMetric m2 = mgr.timed(MetricName.of("org.req.Customer.m2"), 100, 200);

    List<TimingMetricInfo> allTimingMetrics = mgr.getAllTimingMetrics(null);
    assertEquals(3, allTimingMetrics.size());

    List<TimingMetricInfo> requestTimingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(0, requestTimingMetrics.size());

    assertEquals(0, m0.getRequestTimingCollection());

    assertTrue(mgr.setRequestTimingCollection(Customer.class, "doSomething", 1));
    assertEquals(1, m0.getRequestTimingCollection());

    requestTimingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(1, requestTimingMetrics.size());

    List<TimingMetricInfo> changes = mgr.setRequestTimingCollectionUsingMatch("org.req*", 3);
    assertEquals(2, changes.size());
    assertEquals("org.req.Customer.m1", changes.get(0).getName());
    assertEquals("org.req.Customer.m2", changes.get(1).getName());

    requestTimingMetrics = mgr.getRequestTimingMetrics(null);
    assertEquals(3, requestTimingMetrics.size());


    assertFalse(mgr.setRequestTimingCollection(Customer.class, "methodDoesNotExist", 1));

  }
}