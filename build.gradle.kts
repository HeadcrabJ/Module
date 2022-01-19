/*
 * UASM Discord Bot.
 * Copyright (C) 2022 untled032, Headcrab

 * UASM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * UASM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with UASM. If not, see https://www.gnu.org/licenses/.
 */

plugins {
    java
    application
	id("com.github.johnrengelman.shadow") version "7.1.0"
    id("com.heroku.sdk.heroku-gradle") version "2.0.0"
}

application {
	mainClass.set("eu.u032.Bot")
}
version = "1.0"

repositories {
    mavenCentral()
	maven("https://m2.dv8tion.net/releases")
	jcenter()
}

dependencies {
	implementation("net.dv8tion:JDA:4.4.0_352") {
		exclude(module = "opus-java")
	}
    implementation("com.jagrosh:jda-utilities:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.2.10")
	implementation("org.codehaus.groovy:groovy:3.0.9")
	implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.2")
	implementation("org.springframework.boot:spring-boot-starter:2.6.2")
	runtimeOnly("org.postgresql:postgresql:42.3.1")
	compileOnly("org.projectlombok:lombok:1.18.22")
	annotationProcessor("org.projectlombok:lombok:1.18.22")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.2")
}

heroku {
    appName = "modulebot"
    jdkVersion = "17"
    includes = listOf("build/libs/UASM-1.0-all.jar")
    isIncludeBuildDir = false
    processTypes = mapOf("worker" to "java -jar build/libs/UASM-1.0-all.jar")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}