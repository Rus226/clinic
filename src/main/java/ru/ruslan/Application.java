package ru.ruslan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Date;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

//    String d = "24/10/2012";
//    Date date = new Date();
//        System.out.println(date);
//                Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(d);
//                System.out.println(date2);
//
//                System.out.println(new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(date));