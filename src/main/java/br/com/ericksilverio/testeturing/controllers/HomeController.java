package br.com.ericksilverio.testeturing.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.ericksilverio.testeturing.models.Usuario;
import br.com.ericksilverio.testeturing.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/home")
	public ModelAndView index(HttpSession session) {
		
		Usuario usuarioLogado =  (Usuario) session.getAttribute("usuarioLogado");
		
		if (usuarioLogado != null) {
			
			Long idUsuarioLogado = usuarioLogado.getId();
			
			Optional<Usuario> optional = this.usuarioRepository.findById(idUsuarioLogado);
			
			Usuario usuario = optional.get();
			
			ModelAndView mv = new ModelAndView("home/index");
			
			mv.addObject("usuario", usuario);
			
			return mv;
		
		}
		
		else {
			ModelAndView mv = new ModelAndView("redirect:/login");
			
			return mv;
		}
		
		
		
	}
	@GetMapping("/faleconosco")
		public String faleConosco() {
			return "faleconosco/index";
		}
		

}
