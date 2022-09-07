package com.example.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.domain.Cidade;
import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.Endereco;
import com.example.cursomc.domain.Estado;
import com.example.cursomc.domain.Pagamento;
import com.example.cursomc.domain.PagamentoComBoleto;
import com.example.cursomc.domain.PagamentoComCartao;
import com.example.cursomc.domain.Pedido;
import com.example.cursomc.domain.Produto;
import com.example.cursomc.domain.enums.EstadoPagamento;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.repositories.CidadeRspository;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.repositories.EnderecoRepository;
import com.example.cursomc.repositories.EstadoRepository;
import com.example.cursomc.repositories.PagamentoRepository;
import com.example.cursomc.repositories.PedidoRepository;
import com.example.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRspository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Música");
		
		Produto prod1 = new Produto(null, "Computador", 8000);
		Produto prod2 = new Produto(null, "Teclado", 100);
		Produto prod3 = new Produto(null, "Webcam", 300);
		
		// Relacionamento muitos para muitos entre categoria e produtos
		
		categoria1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		categoria2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(categoria1));
		prod2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		prod3.getCategorias().addAll(Arrays.asList(categoria1));
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente(null,"Maria Silva", "mariasilva@gmail.com", "654325423", TipoCliente.PESSOAFISICA);
		
		cliente1.getTelefones().addAll(Arrays.asList("43623342", "74564643"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "500", "Casa", "Fazenda", "76884567", cliente1, cidade1);
		Endereco e2 = new Endereco(null, "Rua Rosas", "5500", "Apt. 10", "Rua", "9994353", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2022 14:57"), cliente1, e1);
		Pedido pedido2 = new Pedido(null, sdf.parse("30/09/2021 14:57"), cliente1, e2);
		
		Pagamento pgt = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		
		pedido1.setPagamento(pgt);
		
		Pagamento pgt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, sdf.parse("09/03/1994 15:30"), pedido2, null);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pgt, pgt2));
		
	}
	
}
