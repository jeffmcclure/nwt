package neverwintertoolkit.model.annotation

enum class BlankBehavior {

    /** If a blank value exists it will be retained.  This is the default behavior. */
    RETAIN,

    /** Generate blank values in compiled artifacts if when json source is null; don't show blank values in json. */
    GENERATE,

    /** Suppress blank values in compiled artifacts and json. */
    SUPPRESS,

    /** when null or blank populate GFF field with value from defaultValue attribute */
    DEFAULT_VALUE
}

@Target(AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class NwnField(
    val name: kotlin.String,
    val type: kotlin.String,
    val structType: kotlin.Long = -1,
    val indexedStructId: kotlin.Boolean = false,
    val blankBehavior: BlankBehavior = BlankBehavior.RETAIN,
    val elementName: kotlin.String = "",
    val elementType: kotlin.String = "",
    val gffOrder: kotlin.Int = Int.MAX_VALUE,
    val defaultValue: String = "",
)