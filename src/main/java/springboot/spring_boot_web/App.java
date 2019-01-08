package springboot.spring_boot_web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("springboot") //to scan packages mentioned

public class App  
{
    private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
    
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Override
//    public void run(String... strings) throws Exception {
//
//        log.info("Creating tables");
//
//        jdbcTemplate.execute("DROP TABLE springbatch.user IF EXISTS");
//        jdbcTemplate.execute("CREATE TABLE springbatch.user(" +
//                "id SERIAL, name VARCHAR(255), salary decimal(10,2))");
//    }

}
