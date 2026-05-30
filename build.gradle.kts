plugins {
    kotlin("jvm") version "2.4.0-RC2"
    kotlin("kapt") version "2.4.0-RC2"
}

group = "de.fruxz.vhub"
version = "2.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {

    compileOnly("com.velocitypowered:velocity-api:3.5.0-20260528.100554-32")
    kapt("com.velocitypowered:velocity-api:3.5.0-20260528.100554-32")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(25)
}