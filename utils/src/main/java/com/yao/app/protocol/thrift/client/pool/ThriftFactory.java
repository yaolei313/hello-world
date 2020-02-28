package com.yao.app.protocol.thrift.client.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.thrift.TServiceClient;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-02-28
 */
public class ThriftFactory<T extends TServiceClient> extends BasePooledObjectFactory<T> {



    @Override
    public T create() throws Exception {
        return null;
    }

    @Override
    public PooledObject<T> wrap(T obj) {
        return null;
    }
}
