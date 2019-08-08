package org.freelesson.springsecurityjdbc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SpringSecurityJdbcApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void auth() throws Exception {
		Map<String, String> params = new HashMap<>();
	    params.put("username", "user@example.com");
	    params.put("password", "userPass!2#");
	    Gson gson = new Gson();
	    String mapVal = gson.toJson(params);
		mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
				.param("grant_type", "password")
				.content(mapVal)
				.with(httpBasic( "spring-security-jdbc-app","spring-security-jdbc-s#cr@t"))
				.header("Content-Type", MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.ALL))
		.andExpect(status().isOk());
	}
}
