package br.com.ericksilverio.testeturing.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ericksilverio.testeturing.models.Transferencia;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long>{
	
}
