package no.nav.tpregisteret.controller

import no.nav.tpregisteret.domain.Forhold
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@Suppress("UNREACHABLE_CODE")
@RestController
@RequestMapping("/forhold")
class ForholdController {

    @PostMapping
    @ResponseStatus(CREATED)
    fun lagreNewForhold(@RequestBody forhold: Forhold) = forhold

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    fun lagreExistingForhold(@RequestBody forhold: Forhold) {
        TODO()
    }

    @GetMapping
    fun finnValidForholdMedId(@RequestHeader("forholdId") forholdId: String) = TODO() as Forhold

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun slettForholdCascade(@RequestHeader("forholdId") forholdId: String) {
        TODO()
    }

    @PostMapping("/tjenestepensjon")
    @ResponseStatus(CREATED)
    fun lagreTjenestepensjonForhold(@RequestHeader("tpId") tpId: String, @RequestBody forhold: Forhold) {
        TODO()
    }
}