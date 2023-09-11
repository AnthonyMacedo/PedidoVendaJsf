package com.sathsoft.service;

import java.util.List;

import javax.inject.Inject;

import com.sathsoft.model.Pedido;
import com.sathsoft.model.PedidoItem;
import com.sathsoft.model.TipoStatusPedido;
import com.sathsoft.repository.PedidoDao;
import com.sathsoft.repository.dto.PesquisaPedidoDto;
import com.sathsoft.util.NegocioException;
import com.sathsoft.util.jpa.Transactional;

public class PedidoService extends BaseService {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoDao pedidoDao;

	@Transactional
	public Pedido salvarPedido(Pedido pedido) throws NegocioException {
		
		pedido.recalcularValorTotal();
		
		if (pedido.isNaoAlteravel()) {
			throw new NegocioException("Pedido não pode ser alterado no status. " + pedido.getStatus() + ".");
		}
		
		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		} 
		
		if (pedido.isValorTotalNegativo()) {
			throw new NegocioException("Valor total do pedido não pode ser negativo.");
		}
		
		pedido = pedidoDao.salvar(pedido);
		return pedido;
	}
	
	public List<Pedido> buscarPedidos() {
		return pedidoDao.buscarTodos(Pedido.class);
	}

	@Transactional
	public Pedido buscarPorId(Long id) {
		return pedidoDao.buscarPorId(Pedido.class, id);
	}

	@Transactional
	public List<Pedido> pesquisarPedido(PesquisaPedidoDto pesquisaPedidoDto) {
		return pedidoDao.buscarPedido(pesquisaPedidoDto);
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) throws NegocioException {
		pedido = this.pedidoDao.salvar(pedido);
		
		if( pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido não pode ser emitido com status " + pedido.getStatus().getDescricao() + ".");
		}
		
		this.baixarItensEstoque(pedido);
		
		pedido.setStatus(TipoStatusPedido.Emitido);
		
		pedido = this.pedidoDao.salvar(pedido);
		
		return pedido;
	}
	
	public void baixarItensEstoque(Pedido pedido) throws NegocioException {
		pedido = this.buscarPorId(pedido.getId());
		
		for (PedidoItem item: pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
		
	}
	
	@Transactional
	public Pedido cancelar(Pedido pedido) throws NegocioException {
		pedido = this.buscarPorId(pedido.getId());
		
		if (pedido.isNaoCancelavel()) {
			throw new NegocioException("Pedido não pode ser cancelado no status " + pedido.getStatus().getDescricao() + ".");
		}
		
		if (pedido.isEmitido()) {
			this.estornarItensEstoque(pedido);
		}
		
		pedido.setStatus(TipoStatusPedido.Cancelado);
		
		pedido = this.pedidoDao.salvar(pedido);
		
		return pedido;
	}

	private void estornarItensEstoque(Pedido pedido) {
		pedido = this.buscarPorId(pedido.getId());
		
		for (PedidoItem item: pedido.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
		
	}
} 
