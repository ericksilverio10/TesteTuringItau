package br.com.ericksilverio.testeturing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ericksilverio.testeturing.models.Extrato;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long>{
	@Query(value = "SELECT A.ID, A.DATA, A.TIPO, C.NOME EMISSOR, B.NOME RECEPTOR, CASE WHEN A.ID_RECEPTOR = :id THEN 'table-success'  ELSE 'table-danger' END as CLASSE, CASE WHEN A.ID_RECEPTOR = :id THEN A.VALOR else A.VALOR* (-1) END AS VALOR from transferencia A LEFT JOIN USUARIO B ON A.ID_RECEPTOR = B.ID LEFT JOIN USUARIO C ON A.ID_EMISSOR = C.ID WHERE A.ID_EMISSOR = :id OR A.ID_RECEPTOR = :id ORDER BY ID DESC", nativeQuery = true)
	List<Extrato> mostraExtrato(@Param("id") Long id);
}
