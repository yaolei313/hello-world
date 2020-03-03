package com.yao.app.protocol.thrift.client.pool;

import java.io.Closeable;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-02-28
 */
public class ThriftServiceClientPool<T> implements Closeable {

    private final GenericObjectPool<T> internalPool;

    public ThriftServiceClientPool(final GenericObjectPoolConfig poolConfig, final ThriftConfig thriftConfig) {
        PooledObjectFactory<T> factory = new ThriftServiceClientPooledObjectFactory<>(thriftConfig);
        this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
    }

    @Override
    public void close() {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw new RuntimeException("Could not destroy the pool", e);
        }
    }

    public boolean isClosed() {
        return this.internalPool == null || this.internalPool.isClosed();
    }

    public T getClient() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("Could not get a resource from the pool", e);
        }
    }

    public void returnClient(final T resource) {
        if (resource == null) {
            return;
        }
        try {
            internalPool.returnObject(resource);
        } catch (Exception e) {
            throw new RuntimeException("Could not return the resource to the pool", e);
        }
    }

    public void returnBrokenClient(final T resource) {
        if (resource == null) {
            return;
        }

        try {
            internalPool.invalidateObject(resource);
        } catch (Exception e) {
            throw new RuntimeException("Could not return the resource to the pool", e);
        }
    }
}
