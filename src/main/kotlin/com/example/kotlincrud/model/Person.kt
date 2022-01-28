package com.example.kotlincrud.model

import lombok.NoArgsConstructor
import java.net.PasswordAuthentication
import javax.persistence.*

@Entity
//@NoArgsConstructor
@Table(name = "PERSON")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id : Long = -1,
        val name : String = "",
        val surname : String = "",
        val email : String = "",
        val phone : String = "",
        val dateOfBirth : String = "",
        val age : Long = -1,
        val username : String = "",
        val password : String = ""
) {}