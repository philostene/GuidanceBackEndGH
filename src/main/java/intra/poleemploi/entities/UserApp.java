package intra.poleemploi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Table(name="userapp")
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean activated;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name="userapp_roles", joinColumns=@JoinColumn(name="user_app_id"), inverseJoinColumns=@JoinColumn(name="roles_id"))
//    private Collection<RoleApp> roles = new ArrayList<>();
    /*@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name="userapp_applis", joinColumns = @JoinColumn(name="user_app_id"), inverseJoinColumns = @JoinColumn(name="applis_id"))
    @LazyCollection(LazyCollectionOption.FALSE)*/
   // @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    //@JoinTable(name = "userapp_applis", joinColumns = @JoinColumn(name = "user_app_id"), inverseJoinColumns = @JoinColumn(name = "applis_id"))
    @ManyToMany
    @JoinTable(name = "userapp_applis", joinColumns = @JoinColumn(name = "user_app_id"), inverseJoinColumns = @JoinColumn(name = "applis_id"))
    private Set<Appli> applis = new HashSet<>();
    @ManyToOne
    @JoinColumn(name="user_role_id")
    private RoleApp roles;

}
