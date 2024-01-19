package com.molmasscalc.demo.Controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.molmasscalc.demo.Model.Molecule;
import com.molmasscalc.demo.Service.MoleculeService;



@Controller
public class MoleculeController {
    
    private MoleculeService molService = null;

	@Autowired
	public MoleculeController(MoleculeService service){
		this.molService = service;
	}

	@GetMapping("/home")
	public String showHome(@ModelAttribute Molecule mol) throws FileNotFoundException{
		molService.setCurr(mol);
		return "input";
	}

	@PostMapping("/processFormula")
	public String processInput(@RequestParam("chemicalFormula") String formula, Model model) throws FileNotFoundException{
		molService.setCurr(new Molecule(formula));
		molService.setDbConnect();
		if(molService.isValid()){
			model.addAttribute("chemicalFormula", molService.getCurr().getFormula());
			model.addAttribute("molecularMass", molService.calcWeight() + "g/mol");
			if(!molService.getDbConnect().isInDb()){
				molService.insertbyFormula();
			}
		}else{
			return "invalid";
		}
		return "final";
	}

}
