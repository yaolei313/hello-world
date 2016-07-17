namespace java com.yao.app.protocol.thrift.service

exception BusinessBasicException {
	1:required string why
}

service  HelloWorldService {
  string sayHello(1:required string username)
  
  string sayHelloWithException(1:required string username) throws (1:BusinessBasicException e)
}