package com.paraparp.gestor.model.dao;
import org.springframework.data.repository.CrudRepository;

import com.paraparp.gestor.model.entity.Cliente;



public interface IClienteDao extends CrudRepository<Cliente, Long> {







	//CRUDREPOSITORY crea las consultas genericas por nosotros sin necesidad de implementar un DAO
	
//	public List<Cliente> findAll();
//	public void save(Cliente cliente);
//	public Cliente finById(Long id);
//	public void delete(Long id);
}
