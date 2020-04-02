package com.yao.app.protocol.thrift;

import com.google.common.collect.Lists;
import com.yao.app.protocol.thrift.service.TUserDetail;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TTransport;

public class TestZip {

    public static void main(String[] args) throws Exception {
        TUserDetail userDetail = createOne();

        TMemoryBuffer trans = new TMemoryBuffer(16);
        TProtocol protocol = new TCompactProtocol(trans);
        userDetail.write(protocol);

        byte[] result = trans.getArray();
        int count = trans.length();
        System.out.println(result.length + "/" + count);

        byte[] t = new byte[count];
        System.arraycopy(result, 0, t, 0, count);

        // 116
        System.out.println(Hex.encodeHexString(t));

        TUserDetail userDetail2 = new TUserDetail();
        TTransport trans2 = new TMemoryInputTransport(t);
        TProtocol protocol2 = new TCompactProtocol(trans2);
        userDetail2.read(protocol2);

        System.out.println(userDetail2);
    }

    public static TUserDetail createOne() throws Exception {
        TUserDetail userDetail = new TUserDetail();
        userDetail.setAccountNo(123L);
        userDetail.setName("测试");
        userDetail.setDepartment("121");
        userDetail.setEmail("123@123.com");
        // 59df2a59de5fb43942cce39a
        userDetail.setSnsAccountNo(Hex.decodeHex("59df2a59de5fb43942cce39a"));
        List<String> permissions = Lists.newArrayList("abcd", "bcde");
        userDetail.setPermissions(permissions);

        return userDetail;
    }
}
