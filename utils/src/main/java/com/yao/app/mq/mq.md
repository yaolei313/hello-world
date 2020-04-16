### Message Delivery Semantics消息投递语义
Now that we understand a little about how producers and consumers work, 
let's discuss the semantic guarantees Kafka provides between producer and consumer. 
Clearly there are multiple possible message delivery guarantees that could be provided:

* At most once  
消息可能丢失，但永不被重复投递 Messages may be lost but are never redelivered.
* At least once  
消息永不丢失，但可能被重复投递 Messages are never lost but may be redelivered.
* Exactly once  
消息有且仅被投递一次this is what people actually want, each message is delivered once and only once.

It's worth noting that this breaks down into two problems: 
* the durability guarantees for publishing a message 
* the guarantees when consuming a message.