package org.opendevup.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.opendevup.Dao.EtudiantRepository;
import org.opendevup.entitee.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(value="/Etudiant")
public class EtudiantController {
	
	@Autowired //pour injecter un objet
	EtudiantRepository etudiantRepository;
	@Value("${dir.images}") //pour injecet une propriete
	private String imageDir;
	
	@RequestMapping(value="/Index")
	public String index(Model model,@RequestParam(name="pg", defaultValue="0")int p
			,@RequestParam(name="motCle", defaultValue="")String mc){
		
//		Page<Etudiant> etds=etudiantRepository.findAll(new PageRequest(p, 3));
		Page<Etudiant> etds=etudiantRepository.chercherEtudiants("%"+mc+"%",new PageRequest(p, 3));
		
		int pagesCount=etds.getTotalPages();
		int[] pages=new int [pagesCount];
		for(int i=0;i<pagesCount;i++) pages[i]=i;
		model.addAttribute("pages",pages);
		model.addAttribute("etudiants", etds);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "etudiants";
		
//		List<Etudiant> etds=etudiantRepository.findAll();
//		model.addAttribute("etudiants", etds);
//		return "etudiants";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String formEtudiant(Model model){
		model.addAttribute("etudiant",new Etudiant());
		return "FormEtudiant";
	}
	
	@RequestMapping(value="/saveEtudiant", method=RequestMethod.POST)
	public String save(@Valid Etudiant etd,BindingResult bindingResult,@RequestParam(name="picture") MultipartFile file) throws Exception{
		if(bindingResult.hasErrors()){
			return "FormEtudiant";
		}
		if(!(file.isEmpty())){
			etd.setPhoto(file.getOriginalFilename());
			etudiantRepository.save(etd);
		}
		
		
		if(!(file.isEmpty())){
			etd.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+etd.getId()));
//			file.transferTo(new File(System.getProperty("user.home")+"/application/"+file.getOriginalFilename()));
		}
		
		return "redirect:Index";  // ou /Etudiant/Index
	}
	
    @RequestMapping(value="/getPhoto",produces=MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPhoto(Long id) throws Exception{
		File f=new File(imageDir+id);
		return org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(f));
	}
    
    @RequestMapping(value="/supprimer")
    public String supprimer(Long id){
    	etudiantRepository.deleteById(id);
		return "redirect:Index";
    }
    
    @RequestMapping(value="/editer")
    public String editer(Long id,Model model){
    	Etudiant etd=etudiantRepository.getOne(id);
    	model.addAttribute(etd);
		return "editerEtudiant";
    }
    
    @RequestMapping(value="/updateEtudiant", method=RequestMethod.POST)
	public String update(@Valid Etudiant etd,BindingResult bindingResult,@RequestParam(name="picture") MultipartFile file) throws Exception{
		if(bindingResult.hasErrors()){
			return "editerEtudiant";
		}
		if(!(file.isEmpty())){
			etd.setPhoto(file.getOriginalFilename());
			etudiantRepository.save(etd);
		}
		
		if(!(file.isEmpty())){
			etd.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+etd.getId()));
		}
		return "redirect:Index";  // ou /Etudiant/Index
	}
}
