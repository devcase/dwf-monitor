package br.com.devcase.webmonitor.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.devcase.webmonitor.persistence.domain.MonitoredResource;
import br.com.devcase.webmonitor.service.CheckResourceService;
import dwf.web.controller.BaseCrudController;

@Controller
@RequestMapping("/monitoredResource/")
@ConditionalOnWebApplication
public class MonitoredResourceController extends BaseCrudController<MonitoredResource, Long> {
	@Autowired
	private CheckResourceService checkResourceService;
	
	public MonitoredResourceController() {
		super(MonitoredResource.class);
	}

	@RequestMapping("check/{id}")
	public ResponseEntity<Boolean> check(@PathVariable Long id) {
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}

	@RequestMapping("pending")
	public ResponseEntity<List<MonitoredResource>> pending() {
		return new ResponseEntity<>(getDAO().findByFilter("pending", Boolean.TRUE), HttpStatus.OK);
	}

	@RequestMapping("check")
	public Callable<ResponseEntity<Boolean>> check() {
		return new Callable<ResponseEntity<Boolean>>() {

			@Override
			public ResponseEntity<Boolean> call() throws Exception {
				checkResourceService.checkPending();
				return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
			}

		};
	}

}
