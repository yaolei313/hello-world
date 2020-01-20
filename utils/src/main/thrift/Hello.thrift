namespace java com.yao.app.protocol.thrift.service

service THelloWorldService {
  string sayHello(1:required string username)
}