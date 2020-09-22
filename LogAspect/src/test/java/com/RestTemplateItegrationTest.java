package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // 整合springfox-swagger2后需加@WebAppConfiguration注解
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class RestTemplateItegrationTest {
	@Autowired
	@Qualifier("rest")
    RestTemplate rest;
 
    @Test
    public void givenRestTemplate_whenRequested_thenLogAndModifyResponse() {
        LoginForm loginForm = new LoginForm("username", "password");
        HttpEntity<LoginForm> requestEntity
          = new HttpEntity<LoginForm>(loginForm);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        ResponseEntity<String> responseEntity
          = rest.postForEntity(
            "http://httpbin.org/post", requestEntity, String.class
          );
        assertThat(
          responseEntity.getHeaders().get("Foo").get(0),
          is(equalTo("bar"))
        );
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(
          responseEntity.getStatusCode(),
          is(equalTo(HttpStatus.OK))
        );
        
    }
}