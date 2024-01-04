package com.herick.lojavirtual.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herick.lojavirtual.dto.ProductDTO;
import com.herick.lojavirtual.entities.Product;
import com.herick.lojavirtual.repositories.ProductRepository;
import com.herick.lojavirtual.services.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException("Recurso não encontrado..."));
		return new ProductDTO(product);
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findByName(String nome) {
		Product product = new Product();
		List<Product> result = repository.findAll();
		if (null != result && !result.isEmpty()) {
			for (Product listAux : result) {
				if (listAux.getName().trim().toLowerCase().equals(nome.trim().toLowerCase())) {
					product = listAux;
				}
			}
		}
		return new ProductDTO(product);
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copydToEntity(dto, entity);
		try {
			entity = repository.save(entity);
			System.out.println("Salvo com sucesso");
		} catch (EmptyResultDataAccessException e) {
			RuntimeException exception = new RuntimeException("Erro ao salvar: ID não encontrado");
		} catch (Exception e) {
			RuntimeException exception = new RuntimeException("Erro ao Salvar");
			exception.printStackTrace();
		}
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		Product entity = repository.getReferenceById(id);
		copydToEntity(dto, entity);
		try {
			entity = repository.save(entity);
			System.out.println("Atualizado com sucesso");
		} catch (EmptyResultDataAccessException e) {
			RuntimeException exception = new RuntimeException("Erro ao atualizar: ID não encontrado");
		} catch (Exception e) {
			RuntimeException exception = new RuntimeException("Erro ao atualizar");
			exception.printStackTrace();
		}
		return new ProductDTO(entity);
	}

	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
			System.out.println("Sucesso ao excluir");
		} catch (EmptyResultDataAccessException e) {
			RuntimeException exception = new RuntimeException("Erro ao deletar: ID não encontrado");
			exception.printStackTrace();
		} catch (Exception e) {
			RuntimeException exception = new RuntimeException("Erro ao deletar");
			exception.printStackTrace();
		}
	}

	private void copydToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());

	}

}
