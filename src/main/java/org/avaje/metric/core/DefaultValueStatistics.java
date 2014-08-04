package org.avaje.metric.core;

import org.avaje.metric.ValueStatistics;


/**
 * Snapshot of the current statistics for a Counter or TimeCounter.
 */
public class DefaultValueStatistics implements ValueStatistics {

  protected final long startTime;

  protected final long count;

  protected final long total;

  protected final long max;

  /**
   * Construct for Counter which doesn't collect time or high water mark.
   */
  public DefaultValueStatistics(long collectionStart, long count) {
    this.startTime = collectionStart;
    this.count = count;
    this.total = 0;
    this.max = 0;
  }

  /**
   * Construct for TimeCounter.
   */
  public DefaultValueStatistics(long collectionStart, long count, long total, long max) {
    this.startTime = collectionStart;
    this.count = count;
    this.total = total;
    this.max = max;
  }

  public String toString() {
    return "count:" + count + " total:" + total + " max:" + max;
  }

  /**
   * Return the time the counter started statistics collection.
   */
  @Override
  public long getStartTime() {
    return startTime;
  }

  /**
   * Return the count of values collected.
   */
  @Override
  public long getCount() {
    return count;
  }

  /**
   * Return the total of all the values.
   */
  @Override
  public long getTotal() {
    return total;
  }

  /**
   * Return the Max value collected.
   */
  @Override
  public long getMax() {
    return max;
  }

  /**
   * Return the mean value rounded up.
   */
  @Override
  public long getMean() {
    return (count < 1) ? 0l : Math.round((double)(total / count));
  }

}