# nebula项目 #
###路线图
1. 添加log4jdbc ~ok
2. 添加thrift ~utils project
3. 添加restful ~预计spring mvc+RESTTemplate
4. log4j修改为logback ~ok
5. 添加权限框架Shiro Security 
6. jgravatar添加
7. 思考service层不允许调用同层service的接口，定义为互不相干的基本服务，增加service facade层来对外提供服务，并封装多个service层接口?
8. 修改jsp为thymeleaf ~尝试后取消，thyemeleaf有点

...

###logback
1. 转换符%
logger{length} 日志类
date{SimpleDateFormat} 时间
msg 应用程序日志信息
n 平台换行符\n或\r\n
level 日志级别
thread 线程名

2. 格式修饰符
%-5[.10]level
-为左对齐修饰符，没出现表示右对齐，5表示字段最小字符数
.表示最大字符数，10表示最大字符数
level是上边的日志级别

3. filter
执行一个过滤器会返回DENY,NEUTRAL,ACCEPT
DENY表示日志被抛弃，且不再经过其它过滤器
NEUTRAL表示当前过滤器不处理日志
ACCEPT表示日志被处理，且不再经过其它过滤器
常用LevelFilter，包含属性level,onMatch,onMismatch
