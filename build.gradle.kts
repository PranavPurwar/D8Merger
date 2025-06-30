plugins {
    kotlin("jvm") version "2.1.20"
    id("maven-publish")
}

group = "com.github.PranavPurwar"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools:r8:8.9.35")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("com.android.tools:sdk-common:31.12.0-alpha05")
    implementation("com.android.tools:common:31.12.0-alpha05")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.PranavPurwar"
            artifactId = "d8-merger"
            version = "1.0.0"

            pom {
                name.set("D8 Merger")
                description.set("Library to merge dex files using R8/D8.")
                url.set("http://github.com/PranavPurwar/D8Merger")
                developers {
                    developer {
                        id.set("pranavpurwar")
                        name.set("Pranav Purwar")
                        email.set("purwarpranav80@gmail.com")
                    }
                }
            }

            afterEvaluate {
                from(components["java"])
            }
        }
    }
}
