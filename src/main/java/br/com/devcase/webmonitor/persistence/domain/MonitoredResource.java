package br.com.devcase.webmonitor.persistence.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.URL;

import dwf.persistence.annotations.IgnoreActivityLog;
import dwf.persistence.annotations.UpdatableProperty;
import dwf.persistence.domain.BaseEntity;
import dwf.persistence.validation.ValidationGroups;

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
	private String healthUrl;
	private Integer healthCheckPeriod;
	private Integer healthCheckPeriodOnError;
	private Integer healthCheckTimeout;
	private Integer expectedHttpCode;
	private String expectedText;
	private Integer downtimeAlert;
	private Integer newAlertPeriod;
	private String alertMentions; 
	
	
	/**
	 * HealthCheckUpdate fields
	 */
	private Date lastHealthCheck;
	private Date nextHealthCheck;
	private Boolean healthCheckResult;
	private Date lastError;
	private Long lastErrorDuration;
	private Integer lastHttpCode;
	private Date lastAlertTime;

	@Override
	protected String displayText() {
		return name;
	}

	
	@UpdatableProperty(groups={Default.class, ValidationGroups.ImportFromFile.class})
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@UpdatableProperty(groups={Default.class, ValidationGroups.ImportFromFile.class})
	@URL
	public String getHealthUrl() {
		return healthUrl;
	}
	public void setHealthUrl(String healthUrl) {
		this.healthUrl = healthUrl;
	}
	@UpdatableProperty(groups={Default.class, ValidationGroups.ImportFromFile.class})
	public Integer getHealthCheckPeriod() {
		return healthCheckPeriod;
	}
	public void setHealthCheckPeriod(Integer healthCheckPeriod) {
		this.healthCheckPeriod = healthCheckPeriod;
	}
	@UpdatableProperty(groups={Default.class, ValidationGroups.ImportFromFile.class})
	public Integer getHealthCheckPeriodOnError() {
		return healthCheckPeriodOnError;
	}
	public void setHealthCheckPeriodOnError(Integer healthCheckPeriodOnError) {
		this.healthCheckPeriodOnError = healthCheckPeriodOnError;
	}
	@UpdatableProperty(groups={Default.class, ValidationGroups.ImportFromFile.class})
	public Integer getHealthCheckTimeout() {
		return healthCheckTimeout;
	}
	public void setHealthCheckTimeout(Integer healthCheckTimeout) {
		this.healthCheckTimeout = healthCheckTimeout;
	}
	@UpdatableProperty(groups={Default.class, ValidationGroups.ImportFromFile.class})
	public Integer getExpectedHttpCode() {
		return expectedHttpCode;
	}
	public void setExpectedHttpCode(Integer expectedHttpCode) {
		this.expectedHttpCode = expectedHttpCode;
	}
	@UpdatableProperty(groups={Default.class, ValidationGroups.ImportFromFile.class})
	public String getExpectedText() {
		return expectedText;
	}
	public void setExpectedText(String expectedText) {
		this.expectedText = expectedText;
	}
	
	public Integer getDowntimeAlert() {
		return downtimeAlert;
	}
	public void setDowntimeAlert(Integer downtimeAlert) {
		this.downtimeAlert = downtimeAlert;
	}
	public Integer getNewAlertPeriod() {
		return newAlertPeriod;
	}
	public void setNewAlertPeriod(Integer newAlertPeriod) {
		this.newAlertPeriod = newAlertPeriod;
	}
	public String getAlertMentions() {
		return alertMentions;
	}
	public void setAlertMentions(String alertMentions) {
		this.alertMentions = alertMentions;
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
	@UpdatableProperty(groups=HealthCheckUpdate.class)
	public Date getLastAlertTime() {
		return lastAlertTime;
	}
	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}
	
	
}
