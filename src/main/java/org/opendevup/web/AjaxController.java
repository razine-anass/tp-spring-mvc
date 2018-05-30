package org.opendevup.web;

import javax.servlet.http.HttpServletRequest;

import org.opendevup.entitee.Etudiant;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("session")
public class AjaxController {
	
	@RequestMapping(value="/ajax")
	public String test(HttpServletRequest request){
		
		Etudiant etd=new Etudiant();
		etd.setNom("hamo");
		
		request.getSession().setAttribute("cle",etd);
		
		return "ajax";
	}

	@RequestMapping(value="/afficher")
	@ResponseBody
	public String afficher(HttpServletRequest request){
		
		Etudiant cart = (Etudiant)request.getSession().getAttribute("cle");
		cart.toString();
	return "anass";
		
	}
	
}
