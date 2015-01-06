/**
 * QueueSampler.java - MXBean implementation for the QueueSampler MXBean. This class must implement
 * all the Java methods declared in the QueueSamplerMXBean interface, with the appropriate behavior
 * for each one.
 */

package com.yao.app.jmx.mxbean;

import java.util.Date;
import java.util.Queue;

/**
 * In the same way as for standard MBeans, an MXBean is defined by writing a Java interface called
 * SomethingMXBean and a Java class that implements that interface. However, unlike standard MBeans,
 * MXBeans do not require the Java class to be called Something. Every method in the interface
 * defines either an attribute or an operation in the MXBean. 
 * 
 * @author lei.yao
 *
 */
public class QueueSampler implements QueueSamplerMXBean {

    private Queue<String> queue;

    public QueueSampler(Queue<String> queue) {
        this.queue = queue;
    }

    public QueueSample getQueueSample() {
        synchronized (queue) {
            return new QueueSample(new Date(), queue.size(), queue.peek());
        }
    }

    public void clearQueue() {
        synchronized (queue) {
            queue.clear();
        }
    }
}
