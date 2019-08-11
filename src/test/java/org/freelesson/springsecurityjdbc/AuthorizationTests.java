package org.freelesson.springsecurityjdbc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.freelesson.springsecurityjdbc.config.security.AuthServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=AuthServerConfig.class)
public class AuthorizationTests {
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired 
	FilterChainProxy filterChainProxy;
	
	MockMvc mockMvc;
	@Value("${test.user.username}")
	String username;
	@Value("${test.user.password}")
	String password;
	@Value("${test.client_id}")
	String clientId;
	@Value("${test.client_secret}")
	String clientSecret;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(filterChainProxy).build();
	}
	
	@Test
	public void auth() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("username", username);
	    params.add("password", password);
	    params.add("grant_type","password");
	    mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
				.params(params)
				.with(httpBasic( clientId,clientSecret))
				.accept("application/json;charset=UTF-8"))
		.andExpect(status().isOk());
	}
}
