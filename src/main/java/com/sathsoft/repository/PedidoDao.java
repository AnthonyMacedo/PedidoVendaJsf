package com.sathsoft.repository;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sathsoft.model.Pedido;
import com.sathsoft.repository.dto.PesquisaPedidoDto;
import com.sathsoft.util.jpa.Transactional;

public class PedidoDao extends DaoGeneric<Pedido, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	
	@Transactional
	public List<Pedido> buscarPedido(PesquisaPedidoDto pesquisaPedidoDto) {
		
		String addFiltros = "";
		
		if (pesquisaPedidoDto != null) {
			
			if (pesquisaPedidoDto.getPedidoIdInicial() != null && pesquisaPedidoDto.getPedidoIdFinal() != null) {
				addFiltros += "and p.id Between :pedidoIdInicial and :pedidoIdFinal ";
			}
			
			if (pesquisaPedidoDto.getDataInicial() != null && pesquisaPedidoDto.getDataFinal() != null) {
				addFiltros += "and p.dataCriacao between :dataInicial and :dataFinal ";
			}
			
			if (pesquisaPedidoDto.getNomeVendedor() != null && isNotEmpty(pesquisaPedidoDto.getNomeVendedor())) {
				addFiltros += "and UPPER(p.vendedor.nome) like UPPER(:nomeVendedor) ";
			}
			
			if (pesquisaPedidoDto.getNomeCliente() != null && isNotEmpty(pesquisaPedidoDto.getNomeCliente())) {
				addFiltros += "and UPPER(p.cliente.nome) like UPPER(:nomeCliente) ";
			}
			
			if (pesquisaPedidoDto.getStatusPedido() != null && pesquisaPedidoDto.getStatusPedido().length > 0) {
				addFiltros += "and p.status in (:statusPedido) ";
			}
		}
		
		TypedQuery<Pedido> query = manager.createQuery("From Pedido p Where p.id Is Not null "
				+ addFiltros
				+ "Order By p.id Desc", Pedido.class);
		
				
		if (addFiltros.contains(":pedidoIdInicial")) {
			query.setParameter("pedidoIdInicial", pesquisaPedidoDto.getPedidoIdInicial());
			query.setParameter("pedidoIdFinal", pesquisaPedidoDto.getPedidoIdFinal());
		}
		
		if (addFiltros.contains(":dataInicial")) {
			query.setParameter("dataInicial", pesquisaPedidoDto.getDataInicial());
		}
		
		if (addFiltros.contains(":nomeVendedor")) {
			query.setParameter("nomeVendedor",  "%" + pesquisaPedidoDto.getNomeVendedor() + "%");
		}
		
		if (addFiltros.contains(":nomeCliente")) {
			query.setParameter("nomeCliente", "%" + pesquisaPedidoDto.getNomeCliente() + "%");
		}
		
		if (addFiltros.contains(":statusPedido")) {
			query.setParameter("statusPedido", Arrays.asList(pesquisaPedidoDto.getStatusPedido()));
		}
		
		return query.getResultList();
	}

}
