package no.nav.tpregisteret.controller

import no.nav.security.token.support.core.api.Protected
import no.nav.tpregisteret.service.OrganisationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@Protected
@RestController
@RequestMapping("/organisation")
class OrganisationController(private val organisationService: OrganisationService) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(OrganisationController::class.java)
        private val regex = Regex("""\d{9},\d{4}""")
    }

    @Value("\${orgnr.mapping}")
    lateinit var orgNrMapping: String

    @GetMapping
    @ResponseStatus(NO_CONTENT)
    fun getTpOrdningerForPerson(
            @RequestHeader("orgNr") orgNr: String,
            @RequestHeader("tpId") tpId: String
    ) = if (validVaultOrgnrMapping(orgNr, tpId)) handleValidMapping(orgNr, tpId)
    else organisationService.hasTpIdInOrg(orgNr, tpId)

    @GetMapping("/orgnr")
    fun getOrganisationByTSSId(
            @RequestHeader("tssId") tssId: String
    ) = organisationService.getOrgNrByTssId(tssId)

    @GetMapping("/navn")
    fun getOrganisationName(
            @RequestHeader("orgNr") orgNr: String
    ) = organisationService.getNameByOrgNr(orgNr)

    private fun validVaultOrgnrMapping(orgNr: String, tpId: String): Boolean {
        LOG.info("Validate orgNr/tpId:$orgNr,$tpId")
        return orgNrMapping.split('|')
                .mapNotNull { regex.find(it)?.value }
                .onEach { LOG.info("Vault mapping: $it") }
                .any("$orgNr,$tpId"::equals)
    }

    private fun handleValidMapping(orgNr: String, tpId: String) {
        LOG.info("Valid vault mapping: orgNr $orgNr for tpId $tpId")
    }
}