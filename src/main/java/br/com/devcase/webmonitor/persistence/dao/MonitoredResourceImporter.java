package br.com.devcase.webmonitor.persistence.dao;

import javax.validation.ValidationException;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import br.com.devcase.webmonitor.persistence.domain.MonitoredResource;
import dwf.persistence.export.BaseImporter;

@Component
public class MonitoredResourceImporter extends BaseImporter<MonitoredResource> {

	public MonitoredResourceImporter() {
		super(MonitoredResource.class);
	}
	
	@Override
	protected MonitoredResource readLine(Row row) throws ValidationException {
		MonitoredResource b = new MonitoredResource();
		int i = 0;
		b.setId(getValueAsLong(row, i++));
		b.setName(getValueAsString(row, i++));
		b.setHealthUrl(getValueAsString(row, i++));
		b.setHealthCheckPeriod(getValueAsInteger(row, i++));
		b.setHealthCheckPeriodOnError(getValueAsInteger(row, i++));
		b.setHealthCheckTimeout(getValueAsInteger(row, i++));
		b.setExpectedHttpCode(getValueAsInteger(row, i++));
		b.setExpectedText(getValueAsString(row, i++));
		return b;
	}

}
