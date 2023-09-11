package com.sathsoft;

import java.io.File;
import java.io.IOException;

public class TestePedido {

	public static void main(String[] args) throws IOException {
		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("sathsoftPU");
//		EntityManager manager = emf.createEntityManager();
//		
//		EntityTransaction trx = manager.getTransaction();
//		trx.begin();
		
		File file = new File("/emails/pedido.template");
		System.out.println(file.getAbsolutePath() + " - " + file.getCanonicalPath());
		
//		Cliente cliente = manager.find(Cliente.class, 1L);
//		Produto produto = manager.find(Produto.class, 1L);
//		Usuario vendedor = manager.find(Usuario.class, 1L);
//		
//		EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
//		enderecoEntrega.setLogradouro("Rua dos Mercados");
//		enderecoEntrega.setNumero("1000");
//		enderecoEntrega.setCidade("Uberlândia");
//		enderecoEntrega.setUf("MG");
//		enderecoEntrega.setEstado("Minas Gerais");
//		enderecoEntrega.setCep("38400-123");
//		
//		Pedido pedido = new Pedido();
//		pedido.setCliente(cliente);
//		pedido.setDataCriacao(new Date());
//		pedido.setDataEntrega(new Date());
//		pedido.setFormaPagamento(TipoFormaPagamento.CARTAO_DEBITO);
//		pedido.setObservacao("Aberto das 08 às 18h");
//		pedido.setStatus(TipoStatusPedido.ORCAMENTO);
//		pedido.setValorDesconto(BigDecimal.ZERO);
//		pedido.setValorFrete(BigDecimal.ZERO);
//		pedido.setValorTotal(new BigDecimal(23.2));
//		pedido.setVendedor(vendedor);
//		pedido.setEnderecoEntrega(enderecoEntrega);
//		
//		PedidoItem item = new PedidoItem();
//		item.setProduto(produto);
//		item.setQuantidade(10);
//		item.setValorUnitario(new BigDecimal(2.32));
//		item.setPedido(pedido);
//		
//		pedido.getItens().add(item);
//		
//		manager.persist(pedido);
//		
//		
//		trx.commit();
//		
//		manager.close();
	}

}
