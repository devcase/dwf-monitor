package br.com.devcase.webmonitor.persistence.dao;

import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.devcase.webmonitor.persistence.domain.MonitoredResource;
import dwf.persistence.dao.BaseDAOImpl;
import dwf.persistence.dao.DefaultQueryBuilder;
import dwf.persistence.dao.QueryBuilder;
import dwf.persistence.dao.QueryReturnType;
import dwf.utils.ParsedMap;

@Repository("monitoredResourceDAO")
@Transactional
public class MonitoredResourceDAOImpl extends BaseDAOImpl<MonitoredResource> implements MonitoredResourceDAO{

	public MonitoredResourceDAOImpl() {
		super(MonitoredResource.class);
	}

	@Override
	protected QueryBuilder createQueryBuilder() {
		return new DefaultQueryBuilder(this) {

			@Override
			protected void appendConditions(ParsedMap filter, QueryReturnType<?> returnType, Map<String, Object> params,
					StringBuilder query, String alias) {
				super.appendConditions(filter, returnType, params, query, alias);
				if(Boolean.TRUE.equals(filter.getBoolean("pending"))) {
					query.append(" and (");
						query.append(" (").append(alias).append(".nextHealthCheck is null) ");
						query.append(" or ");
						query.append(" (").append(alias).append(".nextHealthCheck <= :now) ");
					query.append(")");
					params.put("now", new Date());
				}
			}
			
		};
	}

	
}