[Return to README.md](README.md)

Information on developing the nwt project.

# Mapping GFF types to Kotlin types
| GFF Type | Kotlin Type | Bytes |
|----------|-------------|-------|
| DWORD    | UInt        | 4     |
| WORD     | UShort      | 2     |
| BYTE     | Short       | 2     |
| INT      | Int         | 4     |

# Native Compile
The Micronaut framework has been incorporated into the project.  The original reason for this was to enable a native executable build.   During testing the native build did not meaningfully outperform the standard java build.  The initial load time was faster, but in practice the execution time for normal tasks was so close it did not warrant a compile build.  Of course this may change in the future.   The native build was teted with GraalVM 21.01 and Micronaut 4.3.1.   Support for native compile has not been maintained, the necessary annotations have not been added everywhere needed (e.g. @Serdeable, @Introspected, @ReflectiveAccess)
