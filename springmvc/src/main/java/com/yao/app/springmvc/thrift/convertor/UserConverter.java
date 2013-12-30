package com.yao.app.springmvc.thrift.convertor;

import com.yao.app.springmvc.domain.UserBean;
import com.yao.app.springmvc.thrift.YTimestamp;
import com.yao.app.springmvc.thrift.YUser;

public class UserConverter implements Converter {

    @Override
    public Object convertToThrift(Object obj) {
        UserBean user = (UserBean)obj;
        YUser y = new YUser();
        y.setId(user.getId());
        y.setName(user.getName());
        y.setEmail(user.getEmail());
        y.setGravatarMail(user.getGravatarMail());
        
        YTimestamp registerTime = null;
        y.setRegisterTime(registerTime);
        // TODO
        
        return null;
    }

    @Override
    public Object convertToNormal(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

}
