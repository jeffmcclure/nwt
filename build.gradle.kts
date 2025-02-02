import io.ktor.plugin.features.javaVersion
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
  ./gradlew publishToMavenLocal
  ./gradlew clean build -x test
  ./gradlew dependencyUpdates
  ./gradlew wrapper --gradle-version 8.12.1
  ./gradlew build --warning-mode all -x test
  ./gradlew buildEnvironment # Show dependency tree
  g
  gv

  ./gradlew nativeCompile
  ./gradlew nc

  ./gradlew cleanTest test --tests "neverwintertoolkit.con.JsonTest.one"
  ./gradlew -q dependencies --configuration testRuntimeClasspath
  ./gradlew --refresh-dependencies
  ./gradlew --refresh-dependencies compileJava
 */
println("gradle version " + gradle.gradleVersion)
println(javaVersion.name)
println(System.getProperty("java.vm.version"))
println(System.getProperty("java.vm.name"))
println(System.getProperty("java.specification.vendor"))
val binDir = System.getenv("BIN_DIR")

plugins {
//    kotlin("jvm").version("1.8.22")
    id("org.jetbrains.kotlin.jvm") version "2.1.20-Beta2"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.1.20-Beta2"
    id("com.google.devtools.ksp") version "2.1.20-Beta2-1.0.29"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.5"

//    id("org.jetbrains.kotlin.jvm") version "1.8.22"
//    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.22"
//    id("com.google.devtools.ksp") version "1.8.22-1.0.11"
//    id("com.github.johnrengelman.shadow") version "8.1.1"
//    id("io.micronaut.application") version "4.1.2"

    id("com.github.ben-manes.versions") version "0.52.0" // provides ./gradlew dependencyUpdates
    id("io.ktor.plugin") version "3.0.0"
    id("org.ajoberstar.grgit") version "5.2.2"
//    id("kotlinx-serialization")
    kotlin("plugin.serialization") version "2.0.0-RC1"

}

version = "1.0-SNAPSHOT"
group = "neverwintertoolkit"

val kotlinVersion = project.properties["kotlinVersion"]
repositories {
    mavenCentral()
}

dependencies {
    ksp("info.picocli:picocli-codegen")
//    ksp("info.picocli:picocli-codegen:4.7.5")
    ksp("io.micronaut.serde:micronaut-serde-processor")
//    ksp("io.micronaut.serde:micronaut-serde-processor:2.7.0")
    implementation("io.micronaut.serde:micronaut-serde-processor")
//    implementation("io.micronaut.serde:micronaut-serde-processor:2.7.0")
    implementation("info.picocli:picocli")
//    implementation("info.picocli:picocli:4.7.5")
//    api("info.picocli:picocli:4.7.5")
    implementation("org.slf4j:slf4j-api")

//    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
//    implementation("io.micronaut.serde:micronaut-serde-jackson:2.7.0")

    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.picocli:micronaut-picocli")
//    implementation("io.micronaut.picocli:micronaut-picocli:5.2.0")
    implementation("io.micronaut.xml:micronaut-jackson-xml")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
//    implementation("io.github.microutils:kotlin-logging-jvm:4.0.0-beta-2")
    api("ch.qos.logback:logback-classic:1.5.16")
//    runtimeOnly("ch.qos.logback:logback-classic")

    implementation("io.github.java-diff-utils:java-diff-utils:4.12")

//    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")
//    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.0")
//    implementation("com.fasterxml.jackson.core:jackson-databind:")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
//    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.16.0-rc1")

    implementation("com.github.lalyos:jfiglet:0.0.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("javax.json:javax.json-api:1.1.4")
    testImplementation(kotlin("test"))
//    implementation("io.micronaut.serde:micronaut-serde-jackson:2.3.3")
}

application {
    mainClass.set("neverwintertoolkit.command.NwtCommandKt")
}

java {
//    sourceCompatibility = JavaVersion.toVersion("17")
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks {
    compileKotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    compileTestKotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

graalvmNative.toolchainDetection.set(false)

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("neverwintertoolkit.*")
    }

    graalvmNative {
        binaries {
            named("main") {
//                buildArgs.add("--initialize-at-build-time=org.slf4j.LoggerFactory,ch.qos.logback.core.FileAppender")
//            buildArgs.add("--initialize-at-build-time=org.slf4j.LoggerFactory,ch.qos.logback")
                buildArgs.add("--initialize-at-build-time=com.fasterxml.jackson.databind.annotation.JsonNaming,org.slf4j.LoggerFactory")
                buildArgs.add("-H:ReflectionConfigurationFiles=../../../src/test/resources/reflection-config.json")
                buildArgs.add("-H:IncludeResources=logback.xml")
            }
        }
        binaries.all {
//            buildArgs.add("--initialize-at-build-time=org.slf4j.LoggerFactory,ch.qos.logback.core.FileAppender")
//            buildArgs.add("--initialize-at-build-time=org.slf4j.LoggerFactory,ch.qos.logback")
            buildArgs.add("--initialize-at-build-time=com.fasterxml.jackson.databind.annotation.JsonNaming,org.slf4j.LoggerFactory")
            buildArgs.add("-H:ReflectionConfigurationFiles=../../../src/test/resources/reflection-config.json")
            buildArgs.add("-H:IncludeResources=logback.xml")
        }
    }
}

tasks.register("nc") {
    finalizedBy("copyNative")
    dependsOn("nativeCompile")
}

tasks.register<Copy>("copyNative") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(layout.buildDirectory.file("native/nativeCompile"))
    include("nwt")
    rename("nwt", "nwtn")
    //println("installing native into " + binDir)
    into(binDir)
}

tasks.register<Copy>("copyJarShell") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(layout.projectDirectory.file("src/main/scripts"))
    include("nwt")
    println("installing shell into " + binDir)
    into(binDir)
    filePermissions {
        user {
            read = true
            execute = true
        }
        other.execute = true
        group.execute = true
    }
}

tasks.register<Copy>("copyJar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(layout.buildDirectory.file("libs"))
    include("nwt-${version}-all.jar")
//    rename("nwt-${version}-all.jar", "nwt-${version}.jar")
    //rename("nwt-${version}-all.jar", "nwt-${version}-jar-with-dependencies.jar")
    rename("nwt-${version}-all.jar", "nwt.jar")
    println("installing jar into" + binDir)
    into(binDir)
    finalizedBy("copyJarShell")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("build") {
    if (binDir != null) {
        finalizedBy("copyJar")
    }
}

ktor {
    fatJar {
        archiveFileName.set("nwt-${version}-all.jar")
    }
}

tasks.withType<Test> {
    testLogging {
        showStandardStreams = true
        events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED)
    }
}

tasks.named("processResources") {
    finalizedBy("writeVersionProperties")
}

tasks.register("writeVersionProperties") {
    doLast {
        val timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
        println("Timestamp = $timestamp")
        val dir = layout.buildDirectory.asFile.get().resolve("resources").resolve("main")
        Files.createDirectories(dir.toPath())
        dir.resolve("version.properties").writeText(
            """Buildtime: $timestamp
Version: $version
Revision: ${grgit.head().id}
"""
        )
    }
}
