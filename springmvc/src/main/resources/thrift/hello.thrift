namespace java com.yao.app.thrift

service  HelloWorldService {
  string sayHello(1:string username)
}