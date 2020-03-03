package com.yao.app.protocol.thrift.client.pool;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-03-02
 */
public class ThriftConfig {

    private Class<?> serviceTopClass;

    private Class<?> serviceIfaceClass;

    private String host;

    private int port;

    // time out in milliseconds
    private int timeout;

    private boolean nonblock = false;

    private boolean framed = false;

    private TProtocolFactory protocolFactory = new TCompactProtocol.Factory();

    public Class<?> getServiceTopClass() {
        return serviceTopClass;
    }

    public void setServiceTopClass(Class<?> serviceTopClass) {
        this.serviceTopClass = serviceTopClass;
    }

    public Class<?> getServiceIfaceClass() {
        if(serviceIfaceClass == null){
            serviceIfaceClass = ThriftClassUtils.getIfaceClass(serviceTopClass);
        }
        return serviceIfaceClass;
    }

    public void setServiceIfaceClass(Class<?> serviceIfaceClass) {
        this.serviceIfaceClass = serviceIfaceClass;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isNonblock() {
        return nonblock;
    }

    public void setNonblock(boolean nonblock) {
        this.nonblock = nonblock;
    }

    public boolean isFramed() {
        return framed;
    }

    public void setFramed(boolean framed) {
        this.framed = framed;
    }

    public TProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }

    public void setProtocolFactory(TProtocolFactory protocolFactory) {
        if (protocolFactory != null) {
            this.protocolFactory = protocolFactory;
        }
    }
}
