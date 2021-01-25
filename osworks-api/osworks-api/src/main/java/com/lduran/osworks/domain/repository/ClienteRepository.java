package com.lduran.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lduran.osworks.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
	List<Cliente> findByNome(String nome);
	List<Cliente> findByNomeContaining(String nome);

}
