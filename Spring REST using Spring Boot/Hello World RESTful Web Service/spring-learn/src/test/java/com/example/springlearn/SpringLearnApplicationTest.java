package com.example.springlearn;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpringLearnApplicationTest {

    @Test
    public void dateFormatBeanIsLoadedAndParsesCorrectly() throws ParseException {
        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");

        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);
        assertNotNull(format, "dateFormat bean should not be null");

        Date date = format.parse("31/12/2018");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals(2018, cal.get(Calendar.YEAR));
        assertEquals(Calendar.DECEMBER, cal.get(Calendar.MONTH));
        assertEquals(31, cal.get(Calendar.DAY_OF_MONTH));

        ((ClassPathXmlApplicationContext) context).close();
    }
}
