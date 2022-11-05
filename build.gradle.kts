import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"

	val kotlinVersion = "1.6.21"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
}

allprojects {

	repositories {
		mavenCentral()
	}

	ext {
		set("kotlinTestVersion", "5.5.4")
		set("kotlinLoggingVersion", "3.0.4")
		set("mockVersion", "1.13.2")
		set("springCloudVersion", "2021.0.4")
	}
}

val nonDependenciesProjects = listOf("common")
configure(subprojects.filter { it.name !in nonDependenciesProjects }) {
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-spring")
	apply(plugin = "kotlin-jpa")

	val kotlinTestVersion: String by ext
	val kotlinLoggingVersion: String by ext
	val mockVersion: String by ext
	val springCloudVersion: String by ext

	dependencies {
		compileOnly("org.projectlombok:lombok")
		testCompileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		testAnnotationProcessor("org.projectlombok:lombok")

		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotlinTestVersion")
		testImplementation("io.mockk:mockk:$mockVersion")
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
		}
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

}

val nonDeployedProjects = listOf(rootProject) + subprojects.filter { it.parent?.name in nonDependenciesProjects }
configure(nonDeployedProjects) {
	tasks.bootJar { enabled = false }
	tasks.jar { enabled = true }
}
