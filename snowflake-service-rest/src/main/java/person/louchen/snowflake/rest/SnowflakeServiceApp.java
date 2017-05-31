package person.louchen.snowflake.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import person.louchen.snowflake.SnowflakeService;

/**
 * Created by louchen on 2017/5/31.
 */
@SpringBootApplication
@RestController
public class SnowflakeServiceApp {

    @Autowired
    private SnowflakeService snowflakeService;

    @GetMapping("")
    public Long get(){
        return snowflakeService.getUID();
    }

    @GetMapping("/{id}")
    public String parse(@PathVariable Long id){
        return snowflakeService.parseUID(id);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(SnowflakeServiceApp.class).web(true).run(args);
    }



}
