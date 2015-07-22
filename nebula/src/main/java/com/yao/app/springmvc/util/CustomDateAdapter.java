package com.yao.app.springmvc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CustomDateAdapter extends XmlAdapter<String, Date> {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date unmarshal(String v) throws Exception {
		return df.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		return df.format(v);
	}

}
