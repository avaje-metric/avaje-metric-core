package io.avaje.metrics.core;

import io.avaje.metrics.MetricName;
import io.avaje.metrics.ValueMetric;

class ValueMetricFactory implements MetricFactory<ValueMetric> {

  @Override
  public ValueMetric createMetric(MetricName name, int[] bucketRanges) {
    return new DefaultValueMetric(name);
  }

}
