package com.example.kotlincrud

import com.example.kotlincrud.model.Person
import com.example.kotlincrud.repository.PersonRepository
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@OpenAPIDefinition
@SpringBootApplication
class KotlinCrudApplication {
	@Bean
	fun run(repository: PersonRepository) = ApplicationRunner {
		repository.save(Person(
				id = 1,
				name = "Test1",
				surname = "Test1",
				email = "Test1",
				phone = "Test1",
				dateOfBirth = "Test1",
				age = 84,
				username = "Test1",
				password = "Test1"
		))
		repository.save(Person(
				id = 2,
				name = "Test2",
				surname = "Test2",
				email = "Test2",
				phone = "Test2",
				dateOfBirth = "Test2",
				age = 85,
				username = "Test2",
				password = "Test2"
		))
	}
}

fun main(args: Array<String>) {
	runApplication<KotlinCrudApplication>(*args)
}