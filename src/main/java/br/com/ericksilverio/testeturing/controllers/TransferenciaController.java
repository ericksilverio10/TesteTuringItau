package br.com.ericksilverio.testeturing.controllers;

import java.lang.ProcessBuilder.Redirect;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ericksilverio.testeturing.models.Extrato;
import br.com.ericksilverio.testeturing.models.Transferencia;
import br.com.ericksilverio.testeturing.models.Usuario;
import br.com.ericksilverio.testeturing.repositories.ExtratoRepository;
import br.com.ericksilverio.testeturing.repositories.TransferenciaRepository;
import br.com.ericksilverio.testeturing.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class TransferenciaController {
	

	@Autowired
	private ExtratoRepository extratoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;
	
	@GetMapping("/transferencia/nova")
	public ModelAndView novaTransferencia(HttpSession session) {

		

		Usuario usuarioLogado =  (Usuario) session.getAttribute("usuarioLogado");
		
		if(usuarioLogado != null) {
			
			Long idUsuarioLogado = usuarioLogado.getId();
			
			Optional<Usuario> optional = this.usuarioRepository.findById(idUsuarioLogado);
			
			List<Usuario> usuarios = this.usuarioRepository.findIdReceptores(idUsuarioLogado);

			Usuario usuario = optional.get();
			
			ModelAndView mv = new ModelAndView("transferencia/nova");
		
			mv.addObject("usuarios",usuarios);
			
			mv.addObject("usuario",usuario);
		
			return mv;
		}
		else {
			ModelAndView mv = new ModelAndView("redirect:/login");
			
			return mv;
		}
		
	}
	
	@PostMapping("/home")
	public ModelAndView salvarTransferencia(Transferencia transferencia, RedirectAttributes attributes) {

		
		
		String data = transferencia.getData().toString();		
		
		String tipo = transferencia.getTipo().toString();	
		
		String id_emissor = transferencia.getIdEmissor().toString();		
		
		String id_receptor = transferencia.getIdReceptor().toString();	
		
		Double valor = transferencia.getValor();
		
		Double saldoEmissorTemp = this.usuarioRepository.mostraSaldo(id_emissor);
		
		if (valor > 0) {
			if (valor > saldoEmissorTemp) {
				ModelAndView mv = new ModelAndView("redirect:/transferencia/nova");
				
				attributes.addFlashAttribute("mensagem","Saldo insuficiente! Seu saldo atual: " + saldoEmissorTemp);

				return mv;
			}
			
			if (tipo == "PIX" && valor > 5000) {
				ModelAndView mv = new ModelAndView("redirect:/transferencia/nova");
				
				attributes.addFlashAttribute("mensagem","O valor máximo do PIX é R$ 5.000,00!");

				return mv;
			}
			
				if (tipo == "TED" && valor < 5000 ) {
					
				ModelAndView mv = new ModelAndView("redirect:/transferencia/nova");
				
				attributes.addFlashAttribute("mensagem","O valor MÍNIMO do TED é R$ 5.000,00!");

				return mv;
			}

				if (tipo == "TED" && valor > 10000 ) {
					
				ModelAndView mv = new ModelAndView("redirect:/transferencia/nova");
				
				attributes.addFlashAttribute("mensagem","O valor MÁXIMO do TED é R$ 10.000,00!");

				return mv;
			}
				
				if (tipo == "DOC" && valor < 10000 ) {
					
					ModelAndView mv = new ModelAndView("redirect:/transferencia/nova");
					
					attributes.addFlashAttribute("mensagem","O valor MÍNIMO do DOC é R$ 10.000,00!");

					return mv;
				}
				
			
			else {
				this.transferenciaRepository.save(transferencia);
				
				this.usuarioRepository.adicionaSaldoReceptor(valor, id_receptor);
				
				this.usuarioRepository.descontaSaldoEmissor(valor, id_emissor);
				
				Double saldoEmissor = this.usuarioRepository.mostraSaldo(id_emissor);
				Double saldoReceptor = this.usuarioRepository.mostraSaldo(id_receptor);
				
				ModelAndView mv = new ModelAndView("redirect:/home");
				attributes.addFlashAttribute("mensagem","Sua transferência foi realizada com sucesso! Saldo do Emissor: R$ " + saldoEmissor + ", Saldo do Receptor: R$ " + saldoReceptor);

				
				return mv;
				
			}
		}
		else {
			ModelAndView mv = new ModelAndView("redirect:/transferencia/nova");
			
			attributes.addFlashAttribute("mensagem","Dígite um valor válido!");

			return mv;
		}
		
	}
	
	@GetMapping("/extrato")
	public ModelAndView extrato(HttpSession session) {
		
		Usuario usuarioLogado =  (Usuario) session.getAttribute("usuarioLogado");
		
		if(usuarioLogado != null) {

			Long idUsuarioLogado = usuarioLogado.getId();
			
			List<Extrato> extrato = this.extratoRepository.mostraExtrato(idUsuarioLogado);
			
			ModelAndView mv = new ModelAndView("extrato/index");
	
			
			mv.addObject("extrato",extrato);
			
			return mv;
		}
		else {
			ModelAndView mv = new ModelAndView("redirect:login");
			
			return mv;
		}
	}
}
