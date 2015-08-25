package br.com.devcase.webmonitor.persistence.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import dwf.persistence.annotations.IgnoreActivityLog;
import dwf.persistence.annotations.UpdatableProperty;
import dwf.persistence.domain.BaseEntity;

@Entity
public class MonitoredResource extends BaseEntity<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3283914510397623467L;
	
	public static interface HealthCheckUpdate {}

	/**
	 * Configuration fields
	 */
	private String name;
	@URL
	private String healthUrl;
	private Integer healthCheckPeriod;
	private Integer healthCheckPeriodOnError;
	private Integer healthCheckTimeout;
	private Integer expectedHttpCode;
	private String expectedText;
	
	
	/**
	 * HealthCheckUpdate fields
	 */
	private Date lastHealthCheck;
	private Date nextHealthCheck;
	private Boolean healthCheckResult;
	private Date lastError;
	private Long lastErrorDuration;
	private Integer lastHttpCode;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHealthUrl() {
		return healthUrl;
	}
	public void setHealthUrl(String healthUrl) {
		this.healthUrl = healthUrl;
	}
	@Override
	protected String displayText() {
		return name;
	}
	public Integer getHealthCheckPeriod() {
		return healthCheckPeriod;
	}
	public void setHealthCheckPeriod(Integer healthCheckPeriod) {
		this.healthCheckPeriod = healthCheckPeriod;
	}
	public Integer getHealthCheckPeriodOnError() {
		return healthCheckPeriodOnError;
	}
	public void setHealthCheckPeriodOnError(Integer healthCheckPeriodOnError) {
		this.healthCheckPeriodOnError = healthCheckPeriodOnError;
	}
	public Integer getHealthCheckTimeout() {
		return healthCheckTimeout;
	}
	public void setHealthCheckTimeout(Integer healthCheckTimeout) {
		this.healthCheckTimeout = healthCheckTimeout;
	}
	public Integer getExpectedHttpCode() {
		return expectedHttpCode;
	}
	public void setExpectedHttpCode(Integer expectedHttpCode) {
		this.expectedHttpCode = expectedHttpCode;
	}
	public String getExpectedText() {
		return expectedText;
	}
	public void setExpectedText(String expectedText) {
		this.expectedText = expectedText;
	}
	
	@UpdatableProperty(groups=HealthCheckUpdate.class)
	@NotNull(groups=HealthCheckUpdate.class)
	@IgnoreActivityLog
	public Date getLastHealthCheck() {
		return lastHealthCheck;
	}
	public void setLastHealthCheck(Date lastHealthCheck) {
		this.lastHealthCheck = lastHealthCheck;
	}
	@UpdatableProperty(groups=HealthCheckUpdate.class)
	@NotNull(groups=HealthCheckUpdate.class)
	@IgnoreActivityLog
	public Date getNextHealthCheck() {
		return nextHealthCheck;
	}
	public void setNextHealthCheck(Date nextHealthCheck) {
		this.nextHealthCheck = nextHealthCheck;
	}
	@UpdatableProperty(groups=HealthCheckUpdate.class)
	@NotNull(groups=HealthCheckUpdate.class)
	public Boolean getHealthCheckResult() {
		return healthCheckResult;
	}
	public void setHealthCheckResult(Boolean heathCheckResult) {
		this.healthCheckResult = heathCheckResult;
	}
	@UpdatableProperty(groups=HealthCheckUpdate.class)
	public Date getLastError() {
		return lastError;
	}
	public void setLastError(Date lastError) {
		this.lastError = lastError;
	}
	@UpdatableProperty(groups=HealthCheckUpdate.class)
	public Long getLastErrorDuration() {
		return lastErrorDuration;
	}
	public void setLastErrorDuration(Long lastErrorDuration) {
		this.lastErrorDuration = lastErrorDuration;
	}
	@UpdatableProperty(groups=HealthCheckUpdate.class)
	public Integer getLastHttpCode() {
		return lastHttpCode;
	}
	public void setLastHttpCode(Integer lastHttpCode) {
		this.lastHttpCode = lastHttpCode;
	}
	
}
