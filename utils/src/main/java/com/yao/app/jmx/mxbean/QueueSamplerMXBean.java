/**
 * QueueSamplerMXBean.java - MXBean interface describing the management operations and attributes
 * for the QueueSampler MXBean. In this case there is a read-only attribute "QueueSample" and an
 * operation "clearQueue".
 */

package com.yao.app.jmx.mxbean;

/**
 * The annotation @MXBean can be also
 * used to annotate the Java interface instead of requiring the interface’s name to be followed by
 * the MXBean suffix.
 * @author lei.yao
 *
 */
//@MXBean
public interface QueueSamplerMXBean {
    public QueueSample getQueueSample();

    public void clearQueue();
}
