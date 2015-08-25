package br.com.devcase.webmonitor.persistence.dao;

import org.springframework.stereotype.Component;

import br.com.devcase.webmonitor.persistence.domain.MonitoredResource;
import dwf.persistence.export.BaseExporter;
import dwf.persistence.export.ExcelBuilder;

@Component
public class MonitoredResourceExporter extends BaseExporter<MonitoredResource> {

	public MonitoredResourceExporter() {
		super(MonitoredResource.class);
	}

	@Override
	protected void buildHeader(ExcelBuilder excelBuilder) {
		excelBuilder.column("ID");
		excelBuilder.column("Name");
		excelBuilder.column("HealthUrl");
		excelBuilder.column("HealthCheckPeriod");
		excelBuilder.column("HealthCheckPeriodOnError");
		excelBuilder.column("HealthCheckTimeout");
		excelBuilder.column("ExpectedHttpCode");
		excelBuilder.column("ExpectedText");
	}

	@Override
	protected void buildLine(ExcelBuilder excelBuilder, MonitoredResource bean) {
		excelBuilder.column(bean.getId());
		excelBuilder.column(bean.getName());
		excelBuilder.column(bean.getHealthUrl());
		excelBuilder.column(bean.getHealthCheckPeriod());
		excelBuilder.column(bean.getHealthCheckPeriodOnError());
		excelBuilder.column(bean.getHealthCheckTimeout());
		excelBuilder.column(bean.getExpectedHttpCode());
		excelBuilder.column(bean.getExpectedText());
	}

}
