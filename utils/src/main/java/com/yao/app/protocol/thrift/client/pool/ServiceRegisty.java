package com.yao.app.protocol.thrift.client.pool;

import java.net.URL;
import java.util.List;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-02-28
 */
public interface ServiceRegisty {

    List<URL> getServiceURL(String service, Class clazz);
}
