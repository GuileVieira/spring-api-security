package estudos.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()  // permite acesso público à rota raiz
                        //.requestMatchers("/login").permitAll()  // permite acesso público ao login
                        .requestMatchers("/managers").hasAnyRole("MANAGER", "ADMIN")  // acesso para gerentes e administradores
                        .requestMatchers("/users").hasAnyRole("USER", "ADMIN", "MANAGER")  // acesso para usuários e administradores
                        .anyRequest().authenticated()  // exige autenticação para as demais rotas
                );
                //.httpBasic(withDefaults -> {}); // Configuração para login básico (se preferir formulário, substitua conforme abaixo)

        http.formLogin(form -> form
                .defaultSuccessUrl("/", true)  // Página de redirecionamento após login bem-sucedido
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("{noop}user")
                .roles("USER")
                .build();

        UserDetails manager = User.withDefaultPasswordEncoder()
                .username("manager")
                .password("{noop}manager")
                .roles("MANAGER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}

