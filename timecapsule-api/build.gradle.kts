dependencies {
    implementation(project(":common:model"))
    implementation(project(":common:timecapsule-database"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
}
