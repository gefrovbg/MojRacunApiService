import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.spring") version "1.6.21"
}

group = "me.gefro"
version = "1"

repositories {
    mavenCentral()
}

val exposedVersion: String by project
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    testImplementation(kotlin("test"))


    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    implementation ("org.apache.poi:poi-ooxml:5.2.2")
    implementation("commons-beanutils:commons-beanutils:1.9.4")
    implementation("net.sf.ezmorph:ezmorph:1.0.6")
    implementation("commons-collections:commons-collections:3.2.2")
    implementation("commons-lang:commons-lang:2.6")
    implementation("org.apache.poi:poi:5.2.3")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.20.0")
    implementation("org.jsoup:jsoup:1.15.4")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.github.kilmajster:ngrok-spring-boot-starter:0.9.0")

    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("org.springframework.boot:spring-boot-starter-security")

}

tasks.bootBuildImage{
    imageName = "moj-racun-api-service"
//    this.extensions.extraProperties.set("ports", listOf("80:80", "5432:5432"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}