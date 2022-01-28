package com.example.kotlincrud.controller

import com.example.kotlincrud.model.Person
import com.example.kotlincrud.repository.PersonRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
//@RequestMapping("/api")
class PersonController(private val personRepository: com.example.kotlincrud.repository.PersonRepository) {

    @GetMapping("/")
    fun display(): String = "Spring boot CRUD operation with Kotlin and MySQL...!"

    @GetMapping("/persons")
    fun fetchPersons(): ResponseEntity<List<Person>> {
        val persons = personRepository.findAll()
        if (persons.isEmpty()) {
            return ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<Person>>(persons, HttpStatus.OK)
    }

    @GetMapping("/persons/{id}")
    fun fetchPersonById(@PathVariable("id") personId: Long) : ResponseEntity<Person> {
        val person = personRepository.findById(personId)
        if (person.isPresent) {
            return ResponseEntity<Person>(person.get(), HttpStatus.OK)
        }
        return ResponseEntity<Person>(HttpStatus.NO_CONTENT)
    }

    @PostMapping("/persons")
    fun addNewPerson(@RequestBody person: Person, uri:
    UriComponentsBuilder) : ResponseEntity<Person> {
        val persistedPerson = personRepository.save(person)
        if (ObjectUtils.isEmpty(persistedPerson)) {
            return ResponseEntity<Person>(HttpStatus.BAD_REQUEST)
        }
        val headers = HttpHeaders()
        headers.location = uri.path("/person/{personId}").buildAndExpand(person.id).toUri()
        return ResponseEntity<Person>(headers, HttpStatus.CREATED)
    }

    @PutMapping("/persons/{id}")
    fun updatePersonById(@PathVariable("id") personId: Long, @RequestBody person: Person): ResponseEntity<Person> {
        return personRepository.findById(personId).map { personDetails ->
            val updatedPerson: Person = personDetails.copy(
                    name = person.name,
                    surname = person.surname,
                    email = person.email,
                    phone = person.phone,
                    dateOfBirth = person.dateOfBirth,
                    age = person.age,
                    username = person.username,
                    password = person.password
            )
            ResponseEntity(personRepository.save(updatedPerson), HttpStatus.OK)
        }.orElse(ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    @DeleteMapping("/persons/{id}")
    fun removePersonById(@PathVariable("id") personId: Long):
            ResponseEntity<Void> {
        val person = personRepository.findById(personId)
        if (person.isPresent) {
            personRepository.deleteById(personId)
            return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/persons")
    fun removePersons(): ResponseEntity<Void> {
        personRepository.deleteAll()
        return ResponseEntity<Void>(HttpStatus.OK)
    }

    @Repository
    interface PersonRepository : JpaRepository<Person, Long>
}