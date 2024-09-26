package com.example.demo;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	/**
	 * Prueba unitaria que verifica el endpoint GET para obtener todos los países.
	 *
	 * Se espera que la respuesta del endpoint contenga 28 países cuando los datos están guardados.
	 *
	 * Este test realiza una solicitud HTTP GET al endpoint /api/v1 y valida:
	 * - Que el código de estado HTTP sea 200 OK.
	 * - Que el campo 'count' en la respuesta sea igual a 28.
	 * - Que la lista de países tenga exactamente 28 elementos.
	 */

	@Test
	@DisplayName("GET - allCountries")
	void shouldReturn28WhenDataIsSaved() {
		ResponseEntity<CountryResponse> response = restTemplate.getForEntity("/api/v1", CountryResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getCount()).isEqualTo(28);
		assertThat(response.getBody().getItems().size()).isEqualTo(28);
	}

	/**
	 * Prueba unitaria que verifica el endpoint GET para obtener un país específico.
	 *
	 * Se espera que la respuesta del endpoint contenga información del país solicitado
	 * cuando se realiza una solicitud con un identificador de país.
	 *
	 * Este test realiza una solicitud HTTP GET al endpoint /api/v1/0 y valida:
	 * - Que el código de estado HTTP sea 200 OK.
	 * - Que el nombre del país devuelto en el campo 'item' sea "Alemania".
	 */
	@Test
	@DisplayName("GET - oneCountry")
	void shouldReturn1WhenDataIsRequestedByCountryItem() {
		ResponseEntity<ItemResponse> response = restTemplate.getForEntity("/api/v1/0", ItemResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getItem()).isEqualTo("Alemania");
	}

	/**
	 * Prueba unitaria que verifica el endpoint POST para insertar un país.
	 *
	 * Se espera que, cuando se inserta un país (en este caso "San Marino"), el endpoint:
	 * - Devuelva un código de estado 201 CREATED.
	 * - Incluya un mensaje en la respuesta indicando que el país fue insertado correctamente.
	 * - El país insertado esté presente en la lista de países tras una validación posterior con una solicitud GET.
	 *
	 * Este test realiza lo siguiente:
	 * - Envía una solicitud POST para insertar "San Marino".
	 * - Verifica que el código de estado sea 201.
	 * - Extrae y verifica el mensaje de respuesta.
	 * - Valida que "San Marino" esté en la lista de países tras la inserción.
	 */
	@Test
	@DirtiesContext
	@DisplayName("POST - oneCountry")
	void shouldReturnMessageWhenOneRegistryIsInserted() {
		ResponseEntity<String> response = restTemplate.postForEntity("/api/v1","San Marino", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String message = (String) documentContext.read("$.status");
		assertThat(message).isEqualTo("Item inserted succesfully");
		ResponseEntity<CountryResponse> responseValidation = restTemplate.getForEntity("/api/v1", CountryResponse.class);
		assertThat(responseValidation.getBody().getItems()).contains("San Marino");
	}

	/**
	 * Prueba unitaria que verifica el endpoint PUT para actualizar un país.
	 *
	 * Se espera que, cuando se actualice un país (en este caso el país con ID 0), el endpoint:
	 * - Devuelva un código de estado 200 OK.
	 * - Incluya un mensaje en la respuesta indicando que el país fue actualizado correctamente.
	 * - El país actualizado esté presente en la lista de países tras una validación posterior con una solicitud GET.
	 *
	 * Este test realiza lo siguiente:
	 * - Envía una solicitud PUT para actualizar el país con ID 0 a "San Marino Island".
	 * - Verifica que el código de estado sea 200 OK.
	 * - Extrae y verifica el mensaje de respuesta.
	 * - Valida que "San Marino Island" esté en la lista de países tras la actualización.
	 */
	@Test
	@DirtiesContext
	@DisplayName("PUT - oneCountry")
	void shouldReturnMessageWhenOneRegistryIsUpdated() {
		ResponseEntity<String> response = restTemplate.exchange("/api/v1/0", HttpMethod.PUT, new HttpEntity<>("San Marino Island"), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String message = (String) documentContext.read("$.Info");
		assertThat(message).isEqualTo("Item updated successfully");
		ResponseEntity<CountryResponse> responseValidation = restTemplate.getForEntity("/api/v1", CountryResponse.class);
		assertThat(responseValidation.getBody().getItems()).contains("San Marino Island");
	}

	/**
	 * Prueba unitaria que verifica el endpoint DELETE para eliminar un país.
	 *
	 * Se espera que, cuando se elimine un país (en este caso el país con ID 0), el endpoint:
	 * - Devuelva un código de estado 200 OK.
	 * - Incluya un mensaje en la respuesta indicando que el país fue eliminado correctamente.
	 * - El país eliminado ya no esté presente en la lista de países tras una validación posterior con una solicitud GET.
	 *
	 * Este test realiza lo siguiente:
	 * - Envía una solicitud DELETE para eliminar el país con ID 0 ("San Marino Island").
	 * - Verifica que el código de estado sea 200 OK.
	 * - Extrae y verifica el mensaje de respuesta.
	 * - Valida que "San Marino Island" ya no esté en la lista de países tras la eliminación.
	 */
	@Test
	@DirtiesContext
	@DisplayName("DELETE - oneCountry")
	void shouldReturnMessageWhenOneRegistryIsDeleted() {
		ResponseEntity<String> response = restTemplate.exchange("/api/v1/0", HttpMethod.DELETE, new HttpEntity<>("San Marino Island"), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String message = (String) documentContext.read("$.Info");
		assertThat(message).isEqualTo("Item deleted successfully");
		ResponseEntity<CountryResponse> responseValidation = restTemplate.getForEntity("/api/v1", CountryResponse.class);
		assertThat(responseValidation.getBody().getItems()).doesNotContain("San Marino Island");
	}
}
