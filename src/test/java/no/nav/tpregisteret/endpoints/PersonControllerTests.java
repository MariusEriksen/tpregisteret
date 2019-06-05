package no.nav.tpregisteret.endpoints;

import com.github.tomakehurst.wiremock.WireMockServer;
import no.nav.tpregisteret.TestTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static no.nav.tpregisteret.TestPerson.testPerson1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private WireMockServer wireMockServer;

    private String token = TestTokenUtil.getValidAccessToken();

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        wireMockServer.stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo("/sts/jwks"))
            .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200).withBodyFile("jwks.json")));
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    void valid_parameter_returns_200() throws Exception {
        mockMvc.perform(get("/person/" + testPerson1.getFnr() + "/tpordninger")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }

    @Test
    void root_returns_404() throws Exception {
        mockMvc.perform(get("/")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());
    }
}