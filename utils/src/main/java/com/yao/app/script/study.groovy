package com.yao.app.script
/**
 * Created by yaolei02 on 2017/3/26.
 */
def shell = new GroovyShell()
def result = shell.evaluate "3*5"
println result
def result2 = shell.evaluate(new StringReader("2*5"))
println result2
def script = shell.parse '4*5'
assert script instanceof groovy.lang.Script
assert script.run() == 20

def sharedData = new Binding()
def shell2 = new GroovyShell(sharedData)
def now = new Date()
sharedData.setProperty('text','I am shared data')
sharedData.setProperty('data',now)

String result3 = shell2.evaluate('"At $data,$text"')
println result3
shell2.evaluate('foo=12')
shell2.parse('foo2=13').run()
println sharedData.getProperty('foo')
println sharedData.getProperty('foo2')
// evaluate,parse可以向binding（即脚本执行的上下文）添加值
// 不能使用def和explicit type，不然就是local variable，即不能为int foo2=13
// 多线程环境慎用，Binding不是线程安全的

shell2.evaluate('int tt=3')
try{
    println sharedData.getProperty('tt')
}catch (MissingPropertyException e){
    println 'foo is defined as a local variable'
}

def shell3 = new GroovyShell()
def b1 = new Binding(x:3)
def script2 = shell3.parse('x=2*x')
script2.binding = b1
script2.run()
println b1.getProperty('x')

def b2 = new Binding(x:4)
script2.binding = b2
script2.run()
println b2.getProperty('x')

def gcl = new GroovyClassLoader()
def clazz = gcl.parseClass('''
class MyScript{
    String name

    String greet(){
        "Hello $name"
    }
}
'''
)

def a = clazz.newInstance()
a.setName('libai')
println a.greet()
