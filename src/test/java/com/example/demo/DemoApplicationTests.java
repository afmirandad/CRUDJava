package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void shouldReturn28WhenDataIsSaved() {
		ResponseEntity<CountryResponse> response = restTemplate.getForEntity("/api/v1", CountryResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getCount()).isEqualTo(28);
		assertThat(response.getBody().getItems().size()).isEqualTo(28);
	}

	@Test
	void shouldReturn1WhenDataIsRequestedByCountryItem() {
		ResponseEntity<ItemResponse> response = restTemplate.getForEntity("/api/v1/0", ItemResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getItem()).isEqualTo("Alemania");
	}

	@Test
	@DirtiesContext
	void shouldReturnMessageWhenOneRegistryIsInserted() {
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1","San Marino", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isEqualTo("Item insertado con éxito!!");
		ResponseEntity<CountryResponse> responseValidation = restTemplate.getForEntity("/api/v1", CountryResponse.class);
		assertThat(responseValidation.getBody().getItems()).contains("San Marino");
	}

}
