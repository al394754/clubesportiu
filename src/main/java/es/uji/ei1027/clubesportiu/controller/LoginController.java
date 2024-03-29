package es.uji.ei1027.clubesportiu.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.clubesportiu.dao.FakeUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.clubesportiu.dao.UserDao;
import es.uji.ei1027.clubesportiu.model.UserDetails;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return UserDetails.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDetails usuario = (UserDetails) target;
        if (usuario.getUsername().equals(""))
            errors.rejectValue("username", "badUser", "user required");
        if (usuario.getPassword().equals("")) {
            errors.rejectValue("password", "badPasswd", "password required");
        }
        // Exercici: Afegeix codi per comprovar que
        // l'usuari i la contrasenya no estiguen buits
        // ...

    }
}

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;
    private FakeUserProvider fakeUserProvider;


    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta");
            return "login";
        }
        session.setAttribute("user", user);

        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        if (session.getAttribute("nextUrl") != null)
            return "redirect:" + session.getAttribute("nextUrl").toString();

        // Torna a la pàgina principal
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
