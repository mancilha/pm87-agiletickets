package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	/**
	 * 
	 * @param sessao
	 * @return
	 */
	private static boolean ingressosAcabando(Sessao sessao, double percentual) {
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= percentual) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param sessao
	 * @param percentualIngresso
	 * @param percentualPreco
	 * @return
	 */
	private static BigDecimal calculaPreco(Sessao sessao, double percentualIngresso, double percentualPreco) {
		BigDecimal preco;
		
		if (ingressosAcabando(sessao, percentualIngresso)) {
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(percentualPreco)));
		} else {
			preco = sessao.getPreco();
		}
		
		return preco;
	}

	/**
	 * Método responsável por adicionar o acréscimo ao preço da 
	 * 
	 * @param sessao
	 * @param preco
	 * @param acrescimo
	 * @return
	 */
	private static BigDecimal adicionaAcrescimo(Sessao sessao, BigDecimal preco, double acrescimo) {
		if(sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(acrescimo)));
		}
		
		return preco;
	}
	
	/**
	 * 
	 * @param sessao
	 * @param quantidade
	 * @return
	 */
	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		switch (sessao.getEspetaculo().getTipo()) {
			case CINEMA:
			case SHOW:
				preco = calculaPreco(sessao, 0.05, 0.10);
				break;
			
			case BALLET:
			case ORQUESTRA:
				preco = calculaPreco(sessao, 0.50, 0.20);
				preco = adicionaAcrescimo(sessao, preco, 0.10);
				break;
			
			default:
				preco = sessao.getPreco();
		}
		
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}