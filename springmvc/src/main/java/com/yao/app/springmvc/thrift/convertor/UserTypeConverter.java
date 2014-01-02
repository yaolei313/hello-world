package com.yao.app.springmvc.thrift.convertor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.yao.app.springmvc.domain.UserBean;
import com.yao.app.springmvc.thrift.service.YTimestamp;
import com.yao.app.springmvc.thrift.service.YUser;

public class UserTypeConverter implements TypeConverter {

    @Autowired
    TypeConverterRegistry registry;

    @Override
    public Class<?> getNormalType() {
        return UserBean.class;
    }

    @Override
    public Class<?> getThriftType() {
        return YUser.class;
    }

    @Override
    public Object convert(Object obj) {
        if (obj == null) {
            return null;
        }

        if (this.getNormalType().isAssignableFrom(obj.getClass())) {
            // 入参为UserBean或UserBean的子类
            UserBean user = (UserBean) obj;
            YUser y = new YUser();
            y.setId(user.getId());
            y.setName(user.getName());
            y.setEmail(user.getEmail());
            y.setGravatarMail(user.getGravatarMail());

            YTimestamp registerTime = (YTimestamp) registry.getRegisterConverter(Date.class).convert(user.getRegisterTime());
            y.setRegisterTime(registerTime);

            return y;
        } else if (this.getThriftType().isAssignableFrom(obj.getClass())) {
            // 入参为YUser或YUser的子类
            YUser y = (YUser) obj;
            UserBean user = new UserBean();

            user.setId(y.getId());
            user.setName(y.getName());
            user.setEmail(y.getEmail());
            user.setGravatarMail(y.getGravatarMail());

            Date registerTime = (Date) registry.getRegisterConverter(YTimestamp.class).convert(y.getRegisterTime());
            user.setRegisterTime(registerTime);

            return user;
        }
        return null;
    }

}
