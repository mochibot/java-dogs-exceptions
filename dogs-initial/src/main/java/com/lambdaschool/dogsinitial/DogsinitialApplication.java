package com.lambdaschool.dogsinitial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc    //made dispatcherservlet available
@SpringBootApplication
public class DogsinitialApplication
{

    public static DogList ourDogList;
    public static void main(String[] args)

    {
        ourDogList = new DogList();
        ApplicationContext ctx = SpringApplication.run(DogsinitialApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");  //make sure this is spelled correctly
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}

