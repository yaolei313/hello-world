package com.yao.app.protocol.thrift.client;

import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransport;

public class TMultiplexedProtocolFactory implements TProtocolFactory {

    private static final long serialVersionUID = 1L;

    private final String serviceName_;

    private final TProtocolFactory protocolFactory_;

    public TMultiplexedProtocolFactory(TProtocolFactory protocolFactory,
            String serviceName) {
        protocolFactory_ = protocolFactory;
        serviceName_ = serviceName;
    }

    public TProtocol getProtocol(TTransport trans) {
        return new TMultiplexedProtocol(protocolFactory_.getProtocol(trans),
                serviceName_);
    }
}
