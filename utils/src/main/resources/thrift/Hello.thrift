namespace java com.yao.app.protocol.thrift.service

service  HelloWorldService {
  string sayHello(1:string username)
}