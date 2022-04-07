package es.uji.ei1027.clubesportiu.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.uji.ei1027.clubesportiu.dao.ProvaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uji.ei1027.clubesportiu.dao.ClassificacioDao;
import es.uji.ei1027.clubesportiu.dao.NadadorDao;
import es.uji.ei1027.clubesportiu.model.Classificacio;
import es.uji.ei1027.clubesportiu.model.Nadador;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ClassificacioSvc implements ClassificacioService {

    private NadadorDao nadadorDao;
    private ProvaDao provaDao;
    private ClassificacioDao classificacioDao;

    @Override
    public Map<String, List<Nadador>> getClassificationByCountry(String prova) {
        return null;
    }

    public List<String> getNadadorsElegiblesPerProva(String prova) {
        List<Nadador> nadadors = nadadorDao.getNadadors();     // tots els nadadors
        List<String> nomsNadadors = nadadors.stream()          // sols els seus noms
                .map(Nadador::getNom)
                .collect(Collectors.toList());
        List<Classificacio> classifProva = classificacioDao.getClassificacioProva(prova);
        for (Classificacio classif : classifProva) {     // eliminar nadadors ja en la prova
            nomsNadadors.remove(classif.getNomNadador());
        }
        return nomsNadadors;
    }

    /*@Autowired
    NadadorDao nadadorDao;

    @Autowired
    ClassificacioDao classificacioDao;

    @Override
    public Map<String, List<Nadador>> getClassificationByCountry(String prova) {
        List<Classificacio> classProva =
                classificacioDao.getClassificacioProva(prova);
        HashMap<String,List<Nadador>> nadadorsPerPais =
                new HashMap<String,List<Nadador>>();
        for (Classificacio clsf : classProva) {
            Nadador nadador = nadadorDao.getNadador(clsf.getNomNadador());
            if (!nadadorsPerPais.containsKey(nadador.getPais()))
                nadadorsPerPais.put(nadador.getPais(),
                        new ArrayList<Nadador>());
            nadadorsPerPais.get(nadador.getPais()).add(nadador);
        }
        return nadadorsPerPais;
    }
    private ClassificacioService classificacioService;

    @Autowired
    public void setClassificacioService(ClassificacioService                                                classificacioService) {
        this.classificacioService = classificacioService;
    }

    @RequestMapping("/perpais")
    public String listClsfPerPais(Model model) {
        model.addAttribute("classificacions",
                classificacioService.getClassificationByCountry("Duos Sincro"));
        return "classificacio/perpais";
    }*/

}

