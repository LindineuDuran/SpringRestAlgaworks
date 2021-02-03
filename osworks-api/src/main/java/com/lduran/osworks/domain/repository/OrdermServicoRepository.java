package com.lduran.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lduran.osworks.domain.model.OrdemServico;

@Repository
public interface OrdermServicoRepository extends JpaRepository<OrdemServico, Long>
{

}
