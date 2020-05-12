package no.nav.tpregisteret.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_YTELSE")
class Ytelse: AbstractPersistentDomainObject {
    @Column(name = "YTELSE_ID")
    @Id
    var id: Long = 0

    @ManyToOne
    @JoinTable(name = "T_FORHOLD_YTELSE_HISTORIKK",
            joinColumns = [JoinColumn(name = "FORHOLD_ID_FK")],
            inverseJoinColumns = [JoinColumn(name = "YTELSE_ID_FK")]
    )
    lateinit var forhold: Forhold

    @Column(name = "DATO_BRUK_FOM")
    lateinit var datoFom: LocalDate

    @Column(name = "DATO_BRUK_TOM")
    var datoTom: LocalDate? = null
}