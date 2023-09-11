package com.sathsoft.controller;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;
import com.sathsoft.model.Cliente;
import com.sathsoft.model.Pedido;
import com.sathsoft.model.PedidoItem;
import com.sathsoft.model.Produto;
import com.sathsoft.model.TipoFormaPagamento;
import com.sathsoft.model.TipoStatusPedido;
import com.sathsoft.model.Usuario;
import com.sathsoft.repository.dto.PesquisaPedidoDto;
import com.sathsoft.service.ClienteService;
import com.sathsoft.service.PedidoService;
import com.sathsoft.service.ProdutoService;
import com.sathsoft.service.UsuarioService;
import com.sathsoft.util.NegocioException;
import com.sathsoft.util.mail.Mailer;
import com.sathsoft.validation.SKU;

@Named
@ViewScoped
public class PedidoBean extends BaseManagedBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService pedidoService;
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private ClienteService clienteService;
	@Inject
	private ProdutoService produtoService;
	@Inject 
	private Mailer mailer;
	
	private Pedido pedido;

	private PesquisaPedidoDto pesquisaPedidoDto;

	private List<Pedido> listaPedidos;
	private List<Cliente> listaClientes;
	private List<Usuario> listaVendedores;

	private Produto produtoLinhaEditavel;
	
	@SKU
	private String sku;
	
	public void inicializar() {
		if (this.pedido == null) {
			this.limpar();
		} 
			
		this.recalcularPedido();
		
		this.pedido.addicionarItemVazio();
		
		this.listaVendedores = this.usuarioService.buscarTodos();
	}

	public void carregarTodos() {

		pesquisaPedidoDto = new PesquisaPedidoDto();

		if (this.listaPedidos == null) {
			this.listaPedidos = this.pedidoService.buscarPedidos();
		}
	}

	public void salvarPedido() {
		this.pedido.removerItemVazio();
		
		try {
			
			this.pedido = pedidoService.salvarPedido(this.pedido);
			super.addInfoMessage("Pedido salvo com sucesso.");
			
		} catch (Exception e) {
			super.addErrorMessage(e.getMessage());
		}
		
		this.pedido.addicionarItemVazio();
	}
	
	public void emitirPedido() {
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.pedidoService.emitir(this.pedido);
			
			super.addInfoMessage("Pedido emitido com sucesso.");
		} catch (NegocioException e) {
			
			super.addErrorMessage(e.getMessage());
		} finally {
			this.pedido.addicionarItemVazio();
		}
	}
	
	public void cancelarPedido() {
		try {
			this.pedido = this.pedidoService.cancelar(this.pedido);
			super.addInfoMessage("Pedido cancelado com sucesso.");
		} catch (NegocioException e) {
			super.addWarnMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.pedido = new Pedido();
		this.pedido.iniciar();
		this.pedido.setStatus(TipoStatusPedido.Orcamento);
	}

	public void pesquisarPedido() {
		this.listaPedidos = pedidoService.pesquisarPedido(pesquisaPedidoDto);
	}
	
	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}
	
	public List<Usuario> buscarVendedores() {
		return usuarioService.buscarTodos();
	}

	public List<Cliente> buscarClientePorNome(String nome) {
		listaClientes = clienteService.buscarClientePorNome(nome);
		return listaClientes;
	}
	
	public List<Produto> buscarProdutoPorNome(String nome) {
		return produtoService.buscarPorNome(nome);
	}

	public void carregarProdutoLinhaEditavel() {
		PedidoItem item = this.pedido.getItens().get(0);
		
		if (this.produtoLinhaEditavel != null) {
			
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				addErrorMessage("Já existe um item com o produto informado.");
				return;
			}
			item.setProduto(this.produtoLinhaEditavel);
			item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
			
			this.pedido.addicionarItemVazio();
			this.produtoLinhaEditavel = null;
			
			this.pedido.recalcularValorTotal();
		}
	}
	
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for (PedidoItem item: this.pedido.getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}

	public void carregarProdutoPorSku() {
		if (isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtoService.buscarPorSku(sku);
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void atualizarQuantidade(PedidoItem item,int linhaIndex) {
		if (item.getQuantidade() < 1) {
			 if (linhaIndex == 0) {
				item.setQuantidade(1); 
			 } else {
				this.pedido.getItens().remove(linhaIndex);
			 }
		}
		
		this.pedido.recalcularValorTotal();
	}
	
	public TipoStatusPedido[] getStatusPedidoLista() {
		return TipoStatusPedido.values();
	}

	public TipoFormaPagamento[] getFormasPagamentosLista() {
		return TipoFormaPagamento.values();
	}

	public boolean isEditando() {
		return this.pedido.getId() != null;
	}
	
	public void enviarPedidoEmail() {
		MailMessage message = mailer.novaMensagem();
		
		try {
			
			File file = new File("C:\\Temp\\pedido.template");
			
			message.to(this.pedido.getCliente().getEmail())
			.subject("Pedido de Venda nº " + pedido.getId())
			.bodyHtml(new VelocityTemplate(file))
			.put("pedido", this.pedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
	
			super.addInfoMessage("Pedido enviado por e-mail enviado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			super.addErrorMessage("Falha no envio!");
		}
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PesquisaPedidoDto getPesquisaPedidoDto() {
		return pesquisaPedidoDto;
	}

	public void setPesquisaPedidoDto(PesquisaPedidoDto pesquisaPedidoDto) {
		this.pesquisaPedidoDto = pesquisaPedidoDto;
	}

	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Usuario> getListaVendedores() {
		return listaVendedores;
	}

	public void setListaVendedores(List<Usuario> listaVendedores) {
		this.listaVendedores = listaVendedores;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}
