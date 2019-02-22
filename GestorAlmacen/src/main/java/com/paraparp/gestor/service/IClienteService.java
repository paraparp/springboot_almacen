package com.paraparp.gestor.service;

import java.util.List;

import com.paraparp.gestor.model.entity.Cliente;


public interface IClienteService {

	
	public List<Cliente> findAll();
	public void save(Cliente cliente);
	public Cliente findById(Long id);
	public void delete(Long id);
}
