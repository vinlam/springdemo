package com.common.solr;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;

public abstract class BaseSolr<T,ID extends Serializable> implements SolrCrudRepository<T, ID> {
	
	private Class<T> entityClass;
	private SimpleSolrRepository<T, ID> simpleSolrRepository;
	
	private List<SimpleSolrRepository<T, ID>> listRepositories;
	
	public BaseSolr(){
		
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
		simpleSolrRepository.deleteAll();
	}
	
	@Override
	public void delete(Iterable<? extends T> entities) {
		// TODO Auto-generated method stub
		simpleSolrRepository.delete(entities);
	}
	
	@Override
	public void delete(ID id) {
		// TODO Auto-generated method stub
		simpleSolrRepository.delete(id);
	}
	
	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return simpleSolrRepository.save(entities);
	}
	
	@Override
	public boolean exists(ID id) {
		// TODO Auto-generated method stub
		return simpleSolrRepository.exists(id);
	}
	
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return simpleSolrRepository.count();
	}
	
	
}
