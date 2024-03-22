package neverwintertoolkit.file.gff

open class GenericGffWriter<T : GffObj>(gffObj: T, val extension: String, val version: String = "V3.2") : GffAnnotationWriter(gffObj) {
    init {
        if (extension.length != 4 || extension.substring(0, 1) != ".")
            throw RuntimeException("invalid extension '$extension'")

        if (version.length != 4)
            throw RuntimeException("invalid extension '$version'")
    }

    override fun createHeader(): GffFile.GffHeader = GffFile.GffHeader(
        fileType = "${extension.uppercase().substring(1)} ",
        fileVersion = version
    )

}