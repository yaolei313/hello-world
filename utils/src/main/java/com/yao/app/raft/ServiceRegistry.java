package com.yao.app.raft;

import java.net.URL;
import java.util.List;

/**
 * 描述: 服务注册与发现。服务格式为如下的字符串
 * protocol://host:port/path?param1=xxx&param2=xxx
 *
 * thrift://xxxx:port/clazz?param1=xxx&param2=xxx
 *
 * 参照dubbo
 *
 * @author allen@xiaohongshu.com 2020-02-28
 */
public interface ServiceRegistry {

    void register(URL url);

    void unregister(URL url);

    List<URL> lookup(URL conditionUrl);
}
