package com.marlabs.question.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.marlabs.question.BaseTest;
import com.marlabs.question.QuestionServiceApplication;
import com.marlabs.question.model.Answer;
import com.marlabs.question.model.Question;
import com.marlabs.question.util.QuestionConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuestionServiceApplication.class)
@WebAppConfiguration
public class QuestionControllerIntegrationTests extends BaseTest {

	private MockMvc mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getQuestionTest() throws Exception {
		String url = "/api/v1/question";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Question question = mapFromJson(content, Question.class);
		assertNotNull(question.getSecret());
		assertNotNull(question.getQuestion());
	}

	@Test
	public void validateQuestionWithNoTokenTest() throws Exception {
		String url = "/api/v1/question";
		Answer answer = new Answer();
		answer.setQuestion("Please sum the numbers 1,8,7");
		answer.setSecret("");
		answer.setSum(16);
		String requestBody = mapToJson(answer);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody))
				.andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void testGetQuestionAndValidateAnswerTest() throws Exception {
		String url = "/api/v1/question";
		Answer answer = new Answer();

		MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		Question question = mapFromJson(mvcGetResult.getResponse().getContentAsString(), Question.class);

		answer.setQuestion(question.getQuestion());
		answer.setSecret(question.getSecret());

		String[] split = question.getQuestion().substring(QuestionConstants.QUESTION_PREFIX.length()).split(",");
		int sum = 0;
		for (String string : split) {
			sum += Integer.valueOf(string);
		}
		answer.setSum(sum);
		String requestBody = super.mapToJson(answer);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody))
				.andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals(QuestionConstants.SUCCESS_MESSAGE, mvcResult.getResponse().getContentAsString());

		answer.setSum(-1);
		requestBody = mapToJson(answer);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody))
				.andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());

		answer.setQuestion("Please sum the numbers 8,2");
		answer.setSum(5);
		requestBody = mapToJson(answer);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody))
				.andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

}
