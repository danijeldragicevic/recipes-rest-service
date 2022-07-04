package ddane.recipes.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final BeansConfig beansConf;
	private final UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(beansConf.getEncoder());

		/*
		To enable hardcoded user, uncomment the lines below

		auth
			.inMemoryAuthentication()
			.withUser("user1")
			.password(beansConf.getEncoder().encode("pass1"))
			.roles()
			.and()
			.passwordEncoder(beansConf.getEncoder());
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.mvcMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
				.mvcMatchers("/api/register").permitAll()
				.mvcMatchers("/**").authenticated()
				.and()
					.httpBasic()
				.and()
					.csrf().disable()
					.headers().frameOptions().disable();
	}
}
