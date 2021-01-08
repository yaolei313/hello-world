package com.yao.app.protocol.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class PSTest {

    public static void main(String[] args) {
        Foo foo = new Foo("foo", 1);

        // this is lazily created and cached by RuntimeSchema
        // so its safe to call RuntimeSchema.getSchema(Foo.class) over and over
        // The getSchema method is also thread-safe
        Schema<Foo> schema = RuntimeSchema.getSchema(Foo.class);

        // Re-use (manage) this buffer to avoid allocating on every serialization
        LinkedBuffer buffer = LinkedBuffer.allocate(512);

        // ser
        final byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(foo, schema, buffer);
        } finally {
            buffer.clear();
        }

        // deser
        Foo fooParsed = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, fooParsed, schema);

        // --------

        Integer obj = 100;
        Schema<Integer> schema2 = RuntimeSchema.getSchema(Integer.class);
        final byte[] bytes2;
        try {
            bytes2 = ProtostuffIOUtil.toByteArray(obj, schema2, buffer);
        } finally {
            buffer.clear();
        }
        Integer obj2 = schema2.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes2, obj2, schema2);
        System.out.println(obj2);
    }
}
