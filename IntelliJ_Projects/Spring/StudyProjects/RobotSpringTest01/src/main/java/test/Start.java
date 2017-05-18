package test;

import core.implimintations.robot.T1000;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Context.xml");

        Object obj = context.getBean("T1000");

        if(obj instanceof T1000){
            T1000 T1000 = (T1000) context.getBean("T1000");
            T1000.action();
            T1000.dance();
        }
    }

}
