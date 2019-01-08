package springboot.spring_boot_web;

import springboot.spring_boot_web.model.*;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@EnableBatchProcessing
@PropertySource("classpath:application.properties")
public class BatchConfiguration {

 @Autowired
 private Environment env;
 
 @Autowired
 public JobBuilderFactory jobBuilderFactory;
 
 @Autowired
 public StepBuilderFactory stepBuilderFactory;
 
 @Autowired
 public DataSource dataSource;
 
 @Autowired
 ServletContext context; 
 @Value("${spring.datasource.url}")
 private String dataSourceUrl;
 @Value("${spring.datasource.username}")
 private String dataSourceUsername;
 @Value("${spring.datasource.password}")
 private String dataSourcePassword;
 @Value("${spring.datasource.driverClassName}")
 private String dataSourceDriverClassName;

 
 @Bean
 public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
	return new PropertySourcesPlaceholderConfigurer();
 }
 
 @Bean
 public DataSource dataSource() {
  final DriverManagerDataSource dataSource = new DriverManagerDataSource();
  dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
  dataSource.setUrl(env.getProperty("spring.datasource.url"));
  dataSource.setUsername(env.getProperty("spring.datasource.username"));
  dataSource.setPassword(env.getProperty("spring.datasource.password"));
  
  return dataSource;
 }
 
 	@Bean
	public CustomFlatFileItemReader<User> customReader(){
		CustomFlatFileItemReader<User> reader = new CustomFlatFileItemReader<User>();
		reader.setResource(new ClassPathResource("users.csv"));
		reader.setLineMapper(new DefaultLineMapper<User>() {{ setLineTokenizer(new
			DelimitedLineTokenizer() {{ setNames(new String[] { "name", "salary" }); }});
		setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
		setTargetType(User.class); }});
	  
		}});
	  
		return reader; 
	}
 
 @Bean
 public FlatFileItemReader<User> reader(){
  FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
  reader.setResource(new ClassPathResource("users.csv"));
  reader.setLineMapper(new DefaultLineMapper<User>() {{
   setLineTokenizer(new DelimitedLineTokenizer() {{
    setNames(new String[] { "name", "salary" });
   }});
   setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
    setTargetType(User.class);
   }});
   
  }});
  
  return reader;
 }
 
 @Bean
 public UserItemProcessor processor(){
  return new UserItemProcessor();
 }
 
 @Bean
 public JdbcBatchItemWriter<User> writer(){
  JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
  writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
  writer.setSql("INSERT INTO springbatch.user(name, salary) VALUES (:name, :salary)");
  writer.setDataSource(dataSource);
  
  return writer;
 }
 
 @Bean
 public Step step1() {
  return stepBuilderFactory.get("step1").<User, User> chunk(3)
    .reader(reader())
    .processor(processor())
    .writer(writer())
    .allowStartIfComplete(true)
    .build();
 }
 
 @Bean(name="job1")
 public Job importUserJob() {
  return jobBuilderFactory.get("importUserJob")
    .incrementer(new RunIdIncrementer())
    .flow(step1())
    .end()
    .build();
 }
 
}