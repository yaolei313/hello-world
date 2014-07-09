package com.yao.app.extjava.joda;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Test {

	public static void main(String[] args) {
		p("--------all object is immutable------------");
		DateTime dt = new DateTime();
		dt.toString();
		p(dt);
		p(dt.withYear(2000));
		p(dt.plusDays(10));
		p("-----------------------");
		p(dt.getMonthOfYear());
		p(dt.monthOfYear());
		p(dt.monthOfYear().getAsText());
		p(dt.monthOfYear().getAsShortText(Locale.FRENCH));
		p("----leap year 闰年 整百数，能整除400是闰年，非整百数，能整除4为闰年----");
		p(dt.year().isLeap());
		p(dt.withYear(2000).year().isLeap());
		p(dt.withYear(1900).year().isLeap());
		p("---------------------");
		p(dt.dayOfMonth().roundFloorCopy());
		p(dt.dayOfMonth().roundCeilingCopy());
		p(dt.dayOfMonth().roundHalfCeilingCopy());
		p(dt.dayOfMonth().roundHalfFloorCopy());
		p("----");
		DateTime dt1 = dt.withHourOfDay(12).withMinuteOfHour(0)
				.withSecondOfMinute(0).withMillisOfSecond(0);
		p(dt1);
		p(dt1.dayOfMonth().roundFloorCopy());
		p(dt1.dayOfMonth().roundCeilingCopy());
		p(dt1.dayOfMonth().roundHalfFloorCopy());
		p(dt1.dayOfMonth().roundHalfCeilingCopy());

		p("---------------------");
		LocalDate ld = new LocalDate(dt);
		p(ld);
		p(ld.toString());

		LocalTime lt = new LocalTime(dt);
		p(lt);
		p(lt.toString());

		LocalDateTime ldt = new LocalDateTime(dt);
		p(ldt);

		p("---------Interval Period Duration------------");

		Interval inv = new Interval(dt, dt.plusDays(10).plusHours(2));

		Duration d = inv.toDuration();
		p(d.getStandardDays());
		p(d.getStandardHours());

		Period p = new Period().withDays(10);
		p(dt.plus(p));
		
		p("Joda to JDK Class");
		Calendar jcl = dt.toCalendar(Locale.getDefault());
		Date jdate = dt.toDate();
		
		Date jld = ld.toDate();
		
		p("format");
		

	}

	public static void p(Object obj) {
		DateTimeFormatter dft = DateTimeFormat
				.forPattern("yyyy-MM-dd HH:mm:ss");
		if (obj instanceof String) {
			System.out.println((String) obj);
		} else if (obj instanceof LocalDate) {
			// date don't contain time,so dft print wrong
			System.out.println(obj.toString());
		} else if (obj instanceof LocalTime) {
			// time don't contain date,so dft print wrong
			System.out.println(obj.toString());
		} else if (obj instanceof ReadablePartial) {
			System.out.println(dft.print((ReadablePartial) obj));
		} else if (obj instanceof ReadableInstant) {
			System.out.println(dft.print((ReadableInstant) obj));
		} else {
			System.out.println(obj.toString());
		}
	}
}
