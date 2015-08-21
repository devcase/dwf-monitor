package br.com.devcase.webmonitor.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dwf.user.dao.BaseUserDAO;
import dwf.user.domain.BaseUser;
import dwf.user.domain.BaseUserRole;

@Configuration
public class DataLoader {
	@Autowired
	private BaseUserDAO baseUserDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void loadData() {
		baseUserDAO.findOrSaveNew(new BaseUser("root@devcase.com.br", passwordEncoder.encode("root@devcase.com.br"), null, BaseUserRole.BACKOFFICE_ADMIN, BaseUserRole.SUPERUSER));
	}

}
