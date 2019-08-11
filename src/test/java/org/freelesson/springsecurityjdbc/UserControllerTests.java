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
public class UserControllerTests {
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
	
	Long userId;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(filterChainProxy).build();
	}
	
	private String obtainAdminAccessToken() throws Exception {
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
	private String obtainUserAccessToken(String user, String pass) throws Exception {
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", user);
	    params.add("password", pass);
	 
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
	public void givenToken_WhenGetUser_thenAuthorized() throws Exception {
	    String accessToken = obtainAdminAccessToken();
	    mockMvc.perform(get("/user")
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk());
	}
	@Test
	public void givenNoToken_WhenGetUser_thenAuthorized() throws Exception {
	    mockMvc.perform(get("/user"))
	      .andExpect(status().isUnauthorized());
	}
	@Test
	public void givenTokenAndUsernameAndPassword_whenCreateUser_thenSuccess() throws Exception {
		Map<String,Object> userMap = new HashMap<>();
		userMap.put("username", "user1@example.com");
		userMap.put("password", "password1");
		Gson gson = new Gson();
		String mapString = gson.toJson(userMap);
	    String accessToken = obtainAdminAccessToken();
	    mockMvc.perform(post("/user")
	    		.content(mapString)
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void givenTokenAndUsername_whenCreateUser_thenBadRequest() throws Exception {
		Map<String,Object> userMap = new HashMap<>();
		userMap.put("username", "ra@da.com");
		Gson gson = new Gson();
		String mapString = gson.toJson(userMap);
	    String accessToken = obtainAdminAccessToken();
	    mockMvc.perform(post("/user")
	    		.content(mapString)
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isBadRequest());
	}
	@Test
	public void givenTokenAndPassword_whenCreateUser_thenBadRequest() throws Exception {
		Map<String,Object> userMap = new HashMap<>();
		userMap.put("password", "ra@da.com");
		Gson gson = new Gson();
		String mapString = gson.toJson(userMap);
	    String accessToken = obtainAdminAccessToken();
	    mockMvc.perform(post("/user")
	    		.content(mapString)
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isBadRequest());
	}
	@Test
	public void givenBadId_WhenGetUserById_thenObjectNotFound() throws Exception {
	    String accessToken = obtainAdminAccessToken();
	    mockMvc.perform(get("/user/102")
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isNotFound());
	}
	
	
	
	@Test
	public void givenCorrectId_WhenGetUserById_thenSuccess() throws Exception {
	    String accessToken = obtainAdminAccessToken();
	    mockMvc.perform(get("/user/1")
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void givenTokenAndInsufficientPermissions_whenCreateUser_thenForbidden() throws Exception {
		Map<String,Object> userMap = new HashMap<>();
		userMap.put("username", "user2.example.com");
		userMap.put("password", "password1");
		Gson gson = new Gson();
		String mapString = gson.toJson(userMap);
	    String accessToken = obtainUserAccessToken("basicuser@example.com", "password1");
	    mockMvc.perform(post("/user")
	    		.content(mapString)
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isForbidden());
	}
	@Test
	public void givenTokenAndUsernameNonUnique_whenCreateUser_thenBadRequest() throws Exception {
		Map<String,Object> userMap = new HashMap<>();
		userMap.put("username", "user1@example.com");
		userMap.put("password", "password1");
		Gson gson = new Gson();
		String mapString = gson.toJson(userMap);
	    String accessToken = obtainAdminAccessToken();
	    mockMvc.perform(post("/user")
	    		.content(mapString)
	    		.contentType(MediaType.APPLICATION_JSON_UTF8)
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isBadRequest());
	}
}
