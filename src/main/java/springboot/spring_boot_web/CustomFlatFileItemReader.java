package springboot.spring_boot_web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.servlet.ServletContext;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;


public class CustomFlatFileItemReader<T> extends FlatFileItemReader<T> {
	
	@Autowired
	ServletContext context;
	
	@Override
	public void setResource(Resource resource) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream is = classLoader.getResourceAsStream("users.csv");
	    
	    // Create springbatch input stream resource
	    InputStreamResource res = new InputStreamResource(is);

	    // Set resource
	    super.setResource(res);
	}
	
	public String convert(InputStream inputStream, Charset charset) throws IOException {
		
		try (Scanner scanner = new Scanner(inputStream, charset.name())) {
			return scanner.useDelimiter("\\A").next();
		}
	}

}
