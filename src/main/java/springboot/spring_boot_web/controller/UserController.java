package springboot.spring_boot_web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletContext;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import springboot.spring_boot_web.CustomFlatFileItemReader;
import springboot.spring_boot_web.model.User;

@Configuration
@EnableBatchProcessing
@RestController
public class UserController {
	
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;
	@Autowired
	Step step;
	@Autowired
	CustomFlatFileItemReader<User> customReader;
	@Autowired
	ServletContext context;
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/users")
	public ModelAndView users() {
		ModelAndView mav = new ModelAndView("users");
		mav.addObject("message", "");
		
		return mav;
	}

//	@PostMapping("/upload")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws Exception {
		
		if (file.isEmpty()) {
			return  "File is empty";
		}

		try {

			// Get the file
			byte[] bytes = file.getBytes();
			InputStream is = new ByteArrayInputStream(bytes);
			try {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				String pathname= classLoader.getResource("users.csv").getPath();
				PrintWriter writer = new PrintWriter(pathname);
				String result = convert(is, StandardCharsets.UTF_8);
				writer.print(result);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jobLauncher.run(job, new JobParameters());

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return file.getOriginalFilename()+" successfully uploaded";
	}

	public String convert(InputStream inputStream, Charset charset) throws IOException {

		
		  try (Scanner scanner = new Scanner(inputStream, charset.name())) { 
			  return scanner.useDelimiter("\\A").next(); 
		  }
		 
//		final int bufferSize = 1024;
//		final char[] buffer = new char[bufferSize];
//		final StringBuilder out = new StringBuilder();
//		Reader in = new InputStreamReader(inputStream, charset);
//		for (; ; ) {
//		    int rsz = in.read(buffer, 0, buffer.length);
//		    if (rsz < 0)
//		        break;
//		    out.append(buffer, 0, rsz);
//		}
//		return out.toString();
	}
	
	@RequestMapping("/testJson")
	public String TestJson() throws JSONException {
		
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dbList = jdbcTemplate.queryForList("select * from springbatch.user where (salary >= 0 and salary <= 4000)");
		StringBuilder sb = new StringBuilder();
				
		for (Map<String, Object> map : dbList) {
			for (Object key : map.keySet()) {
			    json.put(key.toString(), toJSON(map.get(key)));
			}
			if(sb.length()==0) {
				sb.append("{ <br/> &nbsp;\"result\": { <br/> &nbsp;&nbsp;" + json.toString());
			}else {
				sb.append("<br/>&nbsp;&nbsp;," +json.toString());
			}
		}
		sb.append("<br/>&nbsp;} <br/> }");
		System.out.println("Printing json : "+sb.toString());
		return sb.toString();
	}
	
	public static Object toJSON(Object object) throws JSONException {
        if (object instanceof HashMap) {
            JSONObject json = new JSONObject();
            HashMap map = (HashMap) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable)object)) {
                json.put(toJSON(value));
            }
            return json;
        } else {
            return object;
        }
    }
}
