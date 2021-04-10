package com.yao.app.protocol.thrift.client.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.TConfiguration;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.layered.TFastFramedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述:
 */
public class ThriftServiceClientPooledObjectFactory<T> extends BasePooledObjectFactory<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ThriftServiceClientPooledObjectFactory.class);

    private ThriftConfig thriftConfig;

    public ThriftServiceClientPooledObjectFactory(ThriftConfig thriftConfig) {
        this.thriftConfig = thriftConfig;
    }

    @Override
    public T create() throws Exception {
        TTransport transport = null;
        if (thriftConfig.isNonblock()) {
            transport = new TNonblockingSocket(thriftConfig.getHost(), thriftConfig.getPort(), thriftConfig.getTimeout());
        } else {
            transport = new TSocket(TConfiguration.DEFAULT, thriftConfig.getHost(), thriftConfig.getPort(), thriftConfig.getTimeout());
        }

        if (thriftConfig.isFramed()) {
            transport = new TFastFramedTransport(transport);
        }
        transport.open();

        TProtocol protocol = thriftConfig.getProtocolFactory().getProtocol(transport);

        return ThriftClassUtils.getClientInstance(thriftConfig.getServiceTopClass(), protocol);
    }

    @Override
    public void destroyObject(PooledObject<T> p) throws Exception {
        TServiceClient client = (TServiceClient) p.getObject();
        TTransport transport = client.getInputProtocol().getTransport();
        if (transport != null) {
            transport.close();
        }
    }

    @Override
    public boolean validateObject(PooledObject<T> p) {
        TServiceClient client = (TServiceClient) p.getObject();
        TTransport transport = client.getInputProtocol().getTransport();
        if (transport == null) {
            return false;
        }
        return transport.isOpen();
    }

    @Override
    public PooledObject<T> wrap(T obj) {
        return new DefaultPooledObject(obj);
    }

}
