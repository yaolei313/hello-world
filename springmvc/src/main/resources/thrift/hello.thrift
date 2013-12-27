namespace java com.yao.app.springmvc.thrift

service  HelloWorldService {
  string sayHello(1:string username)
}