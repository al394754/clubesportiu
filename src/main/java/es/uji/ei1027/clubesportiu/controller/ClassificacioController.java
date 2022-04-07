package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.ClassificacioDao;
import es.uji.ei1027.clubesportiu.dao.ProvaDao;
import es.uji.ei1027.clubesportiu.model.Classificacio;
import es.uji.ei1027.clubesportiu.services.ClassificacioSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.clubesportiu.dao.NadadorDao;
import org.springframework.web.util.UriUtils;


@Controller
@RequestMapping("/classificacio")
public class ClassificacioController {

    private NadadorDao nadadorDao;
    private ProvaDao provaDao;
    private ClassificacioDao classificacioDao;
    private ClassificacioSvc classificacioService;

    @Autowired
    public void setClassificacioDao(ClassificacioDao classificacioDao) { this.classificacioDao = classificacioDao; }

    @RequestMapping(value = "/delete/{nNadador}/{nProva}")
    public String processDeleteClassif(@PathVariable String nNadador,
                                       @PathVariable String nProva) {
        classificacioDao.deleteClassificacio(nNadador, nProva);
        return "redirect:../../list";
    }
    @RequestMapping("/list")
    public String listClassificacio(Model model) {
        model.addAttribute("classificacions", classificacioDao.getClassificacions());
        return "classificacio/list";
    }

    @RequestMapping(value="/add")
    public String addClassificacio(Model model){
        model.addAttribute("classificacio", new Classificacio());
        return "classificacio/add";
    }


    @RequestMapping(value="/update/{nom_nadador}/{nom_prova}", method = RequestMethod.GET)
    public String editClassicicacio(Model model, @PathVariable String nom_nadador, @PathVariable String nom_prova) {
        model.addAttribute("classificacio", classificacioDao.getClassificacio(nom_nadador, nom_prova));
        return "classificacio/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("classificacio") Classificacio classificacio,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "classificacio/update";
        classificacioDao.updateClassificacio(classificacio);
        return "redirect:list";
    }
    @RequestMapping(value="/delete/{nom_nadador}/{nom_prova}")
    public String processDelete(@PathVariable String nom_nadador, @PathVariable String nom_prova) {
        classificacioDao.deleteClassificacio(classificacioDao.getClassificacio(nom_nadador, nom_prova));
        return "redirect:../list";
    }

    @RequestMapping(value="/addPerProva/{nom}")
    public String addClassifPerProva(Model model,
                                     @PathVariable String nom) {
        Classificacio classificacio = new Classificacio();
        classificacio.setNomProva(nom);
        model.addAttribute("novaclassificacio", classificacio);
        model.addAttribute("classificacions",
                classificacioDao.getClassificacioProva(nom));
        model.addAttribute("nadadors",
                classificacioService.getNadadorsElegiblesPerProva(nom));
        return "classificacio/addPerProva";
    }

    @RequestMapping(value="/addPerProva", method=RequestMethod.POST)
    public String processAddSubmitPerProva(
            @ModelAttribute("classificacio") Classificacio classificacio,
            BindingResult bindingResult) {
        // Here we should include the validation
        // ...
        if (bindingResult.hasErrors())
            return "classificacio/addPerProva";
        classificacioDao.addClassificacio(classificacio);
        String nameUri="redirect:addPerProva/" + classificacio.getNomProva();
        nameUri = UriUtils.encodePath(nameUri, "UTF-8");
        return nameUri;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddClassif(
            @ModelAttribute("classificacio") Classificacio classificacio,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "classificacio/add";
        try {
            classificacioDao.addClassificacio(classificacio);
        } catch (DuplicateKeyException e) {
            throw new ClubesportiuException(
                    "Ja existeix una classificacio d'aquest nadador en "
                            +classificacio.getNomProva(), "CPduplicada");
        } catch (DataAccessException e) {
            throw new ClubesportiuException(
                    "Error en l'accés a la base de dades", "ErrorAccedintDades");
        }
        return "redirect:list";
    }




}
