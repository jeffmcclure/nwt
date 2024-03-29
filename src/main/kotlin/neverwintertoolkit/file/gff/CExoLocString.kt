package neverwintertoolkit.file.gff

import com.fasterxml.jackson.annotation.JsonIgnore
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable
import neverwintertoolkit.UpperCamelCaseStrategy

@Serdeable
@ReflectiveAccess
@Introspected
@com.fasterxml.jackson.databind.annotation.JsonNaming(UpperCamelCaseStrategy::class)
class CExoLocString(
    var stringRef: Long? = 4294967295L,
    var strings: List<GffFile.GffIdString> = listOf()
) {
    constructor(str: String) : this(4294967295L, listOf(GffFile.GffIdString(str)))

//    var stringRef: Long? = stringRef
//    var strings: List<GffFile.GffIdString> = strings

    override fun toString(): String {
        return "CExoLocString(stringRef=$stringRef, strings=$strings)"
    }

    @get:JsonIgnore
    val isBlank: Boolean
        get() = stringRef == 4294967295L && strings.isEmpty()

}