package com.pruebaticket.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClientRepository clientRepository;

	@BeforeEach
	void cleanDatabase() {
		clientRepository.deleteAll();
	}

	@Test
	void createClientReturns201AndPersists() throws Exception {
		mockMvc.perform(post("/api/clients")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"fullName":"Ana Lopez","email":"ana@example.com","phone":"5512345678"}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.fullName").value("Ana Lopez"))
				.andExpect(jsonPath("$.email").value("ana@example.com"))
				.andExpect(jsonPath("$.phone").value("5512345678"));

		assertThat(clientRepository.findAll()).singleElement().satisfies(client -> {
			assertThat(client.getFullName()).isEqualTo("Ana Lopez");
			assertThat(client.getEmail()).isEqualTo("ana@example.com");
			assertThat(client.getPhone()).isEqualTo("5512345678");
		});
	}

	@Test
	void createClientWithoutRequiredFieldsReturns400() throws Exception {
		mockMvc.perform(post("/api/clients")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"email":"ana@example.com"}
								"""))
				.andExpect(status().isBadRequest());

		assertThat(clientRepository.count()).isZero();
	}

}
