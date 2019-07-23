package org.freelesson.springsecurityjdbc;

import static org.junit.Assert.assertEquals;
import static  org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.freelesson.springsecurityjdbc.domain.Welcome;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes=TestConfig.class)
@AutoConfigureMockMvc
public class SpringSecurityJdbcApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void noAuth() {
		String testName = "test";
		String request = "/hello?name="+testName;
		ResponseEntity<Welcome> response = restTemplate.getForEntity(request, Welcome.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	@WithUserDetails("user@example.com")
	public void shouldAllowUserWithNoAuthorities() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/hello?name=Seb")
				.accept(MediaType.ALL))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.greetings").value("Welcome Seb"));
	}
	@Test
	public void shouldRejectIfNoAuthentication() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/hello?name=Seb")
				.accept(MediaType.ALL))
		.andExpect(status().isUnauthorized());
	
	}
	
	@Test
	public void auth() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token").header("Authorization", "Basic c3ByaW5nLXNlY3VyaXR5LWpkYmMtYXBwOnNwcmluZy1zZWN1cml0eS1qZGJjLXMjY3JAdA==").header("Content-Type", "multipart/form-data;")
				.with(user("user@example.com").password("userPass!2#"))
				.param("grant_type", "password")
				.accept(MediaType.ALL))
		.andExpect(status().is2xxSuccessful());
	
	}
	
}
