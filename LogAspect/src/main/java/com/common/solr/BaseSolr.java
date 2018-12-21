package com.common.solr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SimpleSolrMappingContext;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.solr.repository.query.SolrEntityInformation;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.springframework.data.solr.repository.support.SolrEntityInformationCreatorImpl;

public abstract class BaseSolr<T,ID extends Serializable> implements SolrCrudRepository<T, ID> {
	private final SolrClient solrClient;
	public SolrClient getSolrClient() {
		return solrClient;
	}

	private Class<T> entityClass;
	private SimpleSolrRepository<T, ID> simpleSolrRepository;
	
	private List<SimpleSolrRepository<T, ID>> listRepositories;
	private final SolrEntityInformation<T, ID> solrEntityInformation;
	public BaseSolr(SolrClient solrClient){
		this.solrClient = solrClient;
		this.entityClass = (Class<T>) GenericTypeResolver.resolveTypeArguments(getClass(), BaseSolr.class)[0];
		this.solrEntityInformation = new SolrEntityInformationCreatorImpl(new SimpleSolrMappingContext()).getEntityInformation(entityClass);
		
		this.simpleSolrRepository = new SimpleSolrRepository<T, ID>(solrEntityInformation,solrClient.getQuerySolrTemplate());
		
		this.listRepositories = new ArrayList<SimpleSolrRepository<T,ID>>();
		for(SolrTemplate updateSolrTemplate : solrClient.getUpdateSolrTemplateList()){
			listRepositories.add(new SimpleSolrRepository<T, ID>(solrEntityInformation,updateSolrTemplate));
		}
	}
	
	@Override
	public T findOne(ID id) {
		// TODO Auto-generated method stub
		return simpleSolrRepository.findOne(id);
	}
	
	@Override
	public Iterable<T> findAll() {
		// TODO Auto-generated method stub
		return simpleSolrRepository.findAll();
	}
	
	@Override
	public Page<T> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return simpleSolrRepository.findAll(pageable);
	}
	
	@Override
	public Iterable<T> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return simpleSolrRepository.findAll(sort);
	}
	
	@Override
	public Iterable<T> findAll(Iterable<ID> ids) {
		// TODO Auto-generated method stub
		return simpleSolrRepository.findAll(ids);
	}
	
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		for(SimpleSolrRepository<T, ID> repository : listRepositories){
			repository.deleteAll();
		}
		//simpleSolrRepository.deleteAll();
	}
	
	@Override
	public void delete(Iterable<? extends T> entities) {
		// TODO Auto-generated method stub
		for(SimpleSolrRepository<T, ID> repository : listRepositories){
			repository.delete(entities);
		}
		//simpleSolrRepository.delete(entities);
	}
	
	@Override
	public void delete(ID id) {
		// TODO Auto-generated method stub
		for(SimpleSolrRepository<T, ID> repository : listRepositories){
			repository.delete(id);
		}
		//simpleSolrRepository.delete(id);
	}
	
	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		for(SimpleSolrRepository<T, ID> repository : listRepositories){
			repository.delete(entity);
		}
	}
	
	@Override
	public <S extends T> S save(S entities) {
		// TODO Auto-generated method stub
		S result = null;
		for(SimpleSolrRepository<T, ID> resRepository : listRepositories ){
			result =  simpleSolrRepository.save(entities);
		}
		return result;
		//return simpleSolrRepository.save(entities);
	}
	
	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		Iterable<S> result = null;
		for(SimpleSolrRepository<T, ID> resRepository : listRepositories ){
			result =  simpleSolrRepository.save(entities);
		}
		return result;
		//return simpleSolrRepository.save(entities);
	}
	
	@Override
	public boolean exists(ID id) {
		// TODO Auto-generated method stub
//		return simpleSolrRepository.exists(id);
		return findOne(id) !=null;
	}
	
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return simpleSolrRepository.count();
	}
	
	//仅可以用于查询
	public SolrOperations getSolrOperations(){
		return simpleSolrRepository.getSolrOperations();
	}
}
