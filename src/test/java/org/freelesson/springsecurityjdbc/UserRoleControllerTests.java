package org.freelesson.springsecurityjdbc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.freelesson.springsecurityjdbc.config.security.AuthServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=AuthServerConfig.class)
public class UserRoleControllerTests {
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired 
	FilterChainProxy filterChainProxy;


	@Value("${test.user.username}")
	String username;
	@Value("${test.user.password}")
	String password;
	@Value("${test.client_id}")
	String clientId;
	@Value("${test.client_secret}")
	String clientSecret;

	MockMvc mockMvc;
	Integer userId;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(filterChainProxy).build();
	}

	private String obtainAccessToken() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("username", username);
		params.add("password", password);

		ResultActions result 
		= mockMvc.perform(post("/oauth/token")
				.params(params)
				.with(httpBasic(clientId,clientSecret))
				.accept("application/json;charset=UTF-8"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
	
	
	
	@Test
	public void givenToken_WhenGetUserRole_thenAuthorized() throws Exception {
		String accessToken = obtainAccessToken();
		mockMvc.perform(get("/userrole")
				.header("Authorization", "Bearer " + accessToken))
		.andExpect(status().isOk());
	}
	@Test
	public void givenNoToken_WhenGetUserRole_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/userrole"))
		.andExpect(status().isUnauthorized());
	}
	@Test
	public void givenTokenAndAllFields_whenCreateUserRole_thenSuccess() throws Exception {
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("roleId", 3);
		dataMap.put("userId", 2);
		Gson gson = new Gson();
		String mapString = gson.toJson(dataMap);
		String accessToken = obtainAccessToken();
		mockMvc.perform(post("/userrole")
				.content(mapString)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("Authorization", "Bearer " + accessToken))
		.andExpect(status().isOk());
	}

	@Test
	public void givenTokenAndNoUserId_whenCreateUserRole_thenBadRequest() throws Exception {
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("roleId", 1);
		Gson gson = new Gson();
		String mapString = gson.toJson(dataMap);
		String accessToken = obtainAccessToken();
		mockMvc.perform(post("/userrole")
				.content(mapString)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("Authorization", "Bearer " + accessToken))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void givenTokenAndNoRoleId_whenCreateUserRole_thenBadRequest() throws Exception {
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("userId", 2);
		Gson gson = new Gson();
		String mapString = gson.toJson(dataMap);
		String accessToken = obtainAccessToken();
		mockMvc.perform(post("/userrole")
				.content(mapString)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("Authorization", "Bearer " + accessToken))
		.andExpect(status().isBadRequest());
	}

	
}
