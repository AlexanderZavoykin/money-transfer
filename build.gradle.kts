import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("org.flywaydb.flyway") version "9.8.1"
    id("nu.studer.jooq") version "7.1.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20-Beta"
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

val jooqVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    val koinVersion: String by project
    implementation("io.insert-koin:koin-core:$koinVersion")

    val ktorVersion: String by project
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-request-validation:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")

    implementation("com.sksamuel.hoplite:hoplite-core:2.7.1")
    runtimeOnly("com.sksamuel.hoplite:hoplite-yaml:2.7.1")

    implementation("org.jooq:jooq:$jooqVersion")
    implementation("org.jooq:jooq-meta:$jooqVersion")

    val postgresVersion: String by project
    implementation("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.flywaydb:flyway-core:9.14.1")

    implementation("ch.qos.logback:logback-classic:1.4.5")

    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("practice.money.transfer.Application")
}


val dbUrl: String by project
val dbUser: String by project
val dbPassword: String by project

flyway {
    url = dbUrl
    user = dbUser
    password = dbPassword
}

val genDir = "${project.projectDir}/persistence/src/main/kotlin"

jooq {
    version.set(jooqVersion)
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = dbUrl
                    username = dbUser
                    password = dbPassword
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        schemata.addAll(arrayOf(
                            org.jooq.meta.jaxb.SchemaMappingType().apply { inputSchema = "public" }
                        ))
                        excludes = "flyway_.*"
                    }
                    generate.apply {
                        isRoutines = false
                    }
                    target.apply {
                        directory = genDir
                        packageName = "practice.money.transfer.persistence"
                    }
                }
            }
        }
    }
}

sourceSets["main"].java.srcDir(file(genDir))