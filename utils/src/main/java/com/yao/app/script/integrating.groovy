
def binding = new Binding();
def engine = new GroovyScriptEngine()

for(i=0;i<10;i++){
    def greeter = engine.run('test.groovy', binding)
    println greeter.sayHello()
    Thread.sleep(100)
}
