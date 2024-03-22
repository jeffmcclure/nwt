package neverwintertoolkit.file.gff

open class GenericGffFactory<T : GffObj>(clazz: Class<T>, extension: String) : GffFactoryBase<T>(clazz, extension) {

    companion object {
        val logger = org.slf4j.LoggerFactory.getLogger(GenericGffFactory::class.java)
    }

    override fun readGff(gffFile: GffFile): T {
        logger.info("readGff {}", gffFile.file)
        return gffFile.mapToObject(0, clazz.kotlin)
    }


}
