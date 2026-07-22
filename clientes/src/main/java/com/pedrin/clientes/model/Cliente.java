package com.pedrin.clientes.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * create table clientes (
 * 	codigo serial not null primary key,
 * 	nome varchar(150) not null,
 * 	cpf char(11) not null unique,
 * 	logradouro varchar(100),
 * 	numero varchar(10),
 * 	bairro varchar(100),
 * 	email varchar(150),
 * 	telefone varchar(20)
 * );
 */

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "logradouro", length = 100)
    private String logradouro;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;
}
