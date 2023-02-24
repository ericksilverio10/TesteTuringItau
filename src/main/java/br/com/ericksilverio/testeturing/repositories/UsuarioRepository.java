package br.com.ericksilverio.testeturing.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ericksilverio.testeturing.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query(value = "SELECT * FROM usuario WHERE id <> :id", nativeQuery = true)
	List<Usuario> findIdReceptores(@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE usuario SET saldo = saldo - :valor WHERE id = :id", nativeQuery = true)
	void descontaSaldoEmissor(Double valor, String id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE usuario SET saldo = saldo + :valor WHERE id = :id", nativeQuery = true)
	void adicionaSaldoReceptor(Double valor, String id);

	@Query(value = "SELECT * FROM usuario WHERE username = :username AND senha = :senha", nativeQuery = true)
	Optional<Usuario> verificaUsuario(String username, String senha);
	
	@Query(value = "SELECT saldo FROM usuario WHERE id = :id", nativeQuery = true)
	Double mostraSaldo(String id);
	
	@Query(value = "SELECT COUNT(1) FROM usuario WHERE username = :username", nativeQuery = true)
	Long verificaNomeUsuario(@Param("username") String username);
	
}
