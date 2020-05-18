package no.nav.tpregisteret.controller

import no.nav.tpregisteret.support.ImportTpregisteretBeans
import no.nav.tpregisteret.support.TestData.ORG_1
import no.nav.tpregisteret.support.TestData.ORG_2
import no.nav.tpregisteret.support.TestData.TP_ORDNING_1
import no.nav.tpregisteret.support.TestData.VAULT_TP_ORDNING_1
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureDataJpa
@ImportTpregisteretBeans
class OrganisationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Check returns 204 on valid TpNr for OrgNr`(){
        mockMvc.perform(
                get("/organisation/")
                        .header("orgnr", TP_ORDNING_1.orgNr)
                        .header("tpnr", TP_ORDNING_1.tpNr))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `Check returns 204 on valid vault TpNr for OrgNr`(){
        mockMvc.perform(
                get("/organisation/")
                        .header("orgnr", VAULT_TP_ORDNING_1.orgNr)
                        .header("tpnr", VAULT_TP_ORDNING_1.tpNr))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `Check returns 404 on invalid TpNr for OrgNr`(){
        mockMvc.perform(
                head("/organisation/")
                        .header("orgnr", ORG_1.orgNr)
                        .header("tpnr", ORG_2.tpOrdninger.first().tpNr))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `OrgNr returns 200 on valid TSS ID`() {
        mockMvc.perform(
                get("/organisation/orgnr/")
                        .header("tssid", TP_ORDNING_1.tssId))
                .andExpect(status().isOk)
                .andExpect(content().string(TP_ORDNING_1.orgNr))
    }

    @Test
    fun `OrgNr returns 404 on invalid TSS ID`() {
        mockMvc.perform(
                get("/organisation/orgnr/")
                        .header("tssid", "12345678910"))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `Name returns 200 on valid OrgNr`() {
        mockMvc.perform(
                get("/organisation/navn")
                        .header("orgnr", ORG_1.orgNr))
                .andExpect(status().isOk)
                .andExpect(content().string(ORG_1.json))
    }

    @Test
    fun `Name returns 404 on invalid OrgNr`() {
        mockMvc.perform(
                get("/organisation/navn")
                        .header("orgnr", "123456789"))
                .andExpect(status().isNotFound)
    }
}