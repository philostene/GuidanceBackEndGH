package intra.poleemploi.web;

import intra.poleemploi.dao.AppliRepository;
import intra.poleemploi.dao.ContentRepository;
import intra.poleemploi.dao.StatistiquesParJourRepository;
import intra.poleemploi.dao.UserAppRepository;
import intra.poleemploi.entities.Appli;
import intra.poleemploi.entities.RoleApp;
import intra.poleemploi.entities.UserApp;
import intra.poleemploi.service.AuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private AppliRepository appliRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private StatistiquesParJourRepository statistiquesParJourRepository;

    @PostMapping("/adminUsers")
    public UserApp register(@RequestBody UserForm userForm){ // données envoyées au format JSON
        return authService.saveUserApp(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
    }

    @PutMapping("/updateUserRoles/{id}")
    public ResponseEntity<UserApp> updateUserRoles(@PathVariable(value="id") Long id, @RequestBody List<RoleApp> roles) throws ResourceNotFoundException {
        RoleApp role = new RoleApp();
        UserApp userBdd = userAppRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        role = roles.get(0);
        userBdd.setRoles(role);
        ResponseEntity<UserApp> response = ResponseEntity.ok(authService.saveUserApp(userBdd));
        return response ;
    }

    @PutMapping("/updateUserApplis/{id}")
    public ResponseEntity<UserApp> updateUserApplis(@PathVariable(value="id") Long id, @RequestBody Set<Appli> applis) throws ResourceNotFoundException {
        UserApp userBdd = userAppRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        // checker le contenu des applis
        userBdd.setApplis(new HashSet<Appli>());
        // pour chaque appli de la liste des applis on ajoute le contenu de l'appli
        for(Appli appli : applis ){
            userBdd.getApplis().add(appliRepository.findAppliByAppliName(appli.getAppliName()));
        }
        return ResponseEntity.ok(authService.saveUserApp(userBdd));
    }

    /*@GetMapping(value = "/adminUsers/{username}")
    public UserApp userAppByUsername(@PathVariable(name="username") String username){
        return userAppRepository.findUserByUsername(username);
    };*/

    /*@GetMapping(value = "/getUserById/{id}")
    public UserApp getUserById(@PathVariable(name="id") Long id){
        return userAppRepository.findUserById(id);
    };*/
}
@Data
class UserForm {
    private String username;
    private String password;
    private String confirmedPassword;
}
