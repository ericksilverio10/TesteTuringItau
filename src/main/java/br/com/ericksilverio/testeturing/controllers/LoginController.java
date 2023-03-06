package br.com.ericksilverio.testeturing.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ericksilverio.testeturing.models.Usuario;
import br.com.ericksilverio.testeturing.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	  @RequestMapping(value = "/", method = RequestMethod.GET)
	    public String indexLogin() {	  
	        return "login/index";
	    }
	
	@GetMapping("/login")
	public String index() {
	  	this.usuarioRepository.criaUsuarioPadrao();	
		return "login/index";
	}
	
	@RequestMapping("logar")
	public ModelAndView logar(String username, String senha, HttpSession session, RedirectAttributes attributes) {
		
		System.out.println(username);
		System.out.println(senha);
		
		Optional<Usuario> usuarioAutenticado = this.usuarioRepository.verificaUsuario(username, senha);
		
		
		if(usuarioAutenticado.isPresent()) {
			ModelAndView mv = new ModelAndView("redirect:home");
			Usuario usuarioLogado = usuarioAutenticado.get();
	        session.setAttribute("usuarioLogado", usuarioLogado);
	        
			return mv;
		}
		else {
			ModelAndView mv = new ModelAndView("redirect:login");

			attributes.addFlashAttribute("mensagem","Usuário ou Senha incorretos!");
			
			return mv;
		}
		
	}
	
	@GetMapping("sair")
	public String sair(HttpSession session) {
		session.invalidate();
		
		return "redirect:login";
	}
	
	@GetMapping("cadastro")
	public String cadastro() {
		return "cadastro/index";
	}
	
	@PostMapping("cadastrar")
		public ModelAndView cadastrar(Usuario usuario, RedirectAttributes attributes, HttpSession session) {
		
		String username = usuario.getUsername();
		
		
		Long qtdNomeUsuario = this.usuarioRepository.verificaNomeUsuario(username);
		
		if (qtdNomeUsuario == 0) {
			this.usuarioRepository.save(usuario);
			
			ModelAndView mv = new ModelAndView("redirect:/home");
			
			session.setAttribute("usuarioLogado", usuario);
			
			return mv;
		}
		else {
			ModelAndView mv = new ModelAndView("redirect:/cadastro");
			
			attributes.addFlashAttribute("mensagem","Esse nome de usuário já existe! Escolha outro");

			return mv;
		}
				
		
	}
	
}
