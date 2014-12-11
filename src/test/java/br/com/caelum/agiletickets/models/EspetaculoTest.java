package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void naoDeveCriarSessoesQuandoOInicioForIgualAoFim() {
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate inicio = new LocalDate("2010-01-01");
		LocalDate fim = new LocalDate("2010-01-01");
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
				
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		Assert.assertEquals(null, sessoes);
	}
	
	@Test
	public void naoDeveCriarSessoesQuandoOInicioEDepoisDoFim() {
		Espetaculo espetaculo = new Espetaculo();
		
		LocalDate inicio = new LocalDate("2010-01-03");
		LocalDate fim = new LocalDate("2010-01-01");
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
				
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		Assert.assertEquals(null, sessoes);
	}
	
	@Test
	public void deveCriar5SessoesParaEspetaculoComIntervaloDe5DiasEPeriodicidadeDiaria() {
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setId(1L);
		espetaculo.setDescricao("A Paixão de Cristo");
		
		LocalDate inicio = new LocalDate("2010-01-01");
		LocalDate fim = new LocalDate("2010-01-05");
		
		LocalTime horario = new LocalTime(9, 0, 0);  // 09:00:00 
		Periodicidade periodicidade = Periodicidade.DIARIA;
				
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		for (int i = 0; i < 4; i++) {
			Assert.assertEquals(espetaculo.getId(), sessoes.get(i).getEspetaculo().getId());
			Assert.assertEquals(inicio.toDateTime(horario).plusDays(i), sessoes.get(i).getInicio());
		}
		
		Assert.assertEquals(5, sessoes.size());
	}

	@Test
	public void deveCriar2SessoesParaEspetaculoComIntervaloDe8DiasEPeriodicidadeSemanal() {
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setId(1L);
		espetaculo.setDescricao("A Paixão de Cristo");
		
		LocalDate inicio = new LocalDate("2010-01-01");
		LocalDate fim = new LocalDate("2010-01-08");
		
		LocalTime horario = new LocalTime(9, 0, 0);  // 09:00:00 
		Periodicidade periodicidade = Periodicidade.SEMANAL;
				
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		for (int i = 0; i < 2; i++) {
			Assert.assertEquals(espetaculo.getId(), sessoes.get(i).getEspetaculo().getId());
			Assert.assertEquals(inicio.toDateTime(horario).plusDays(i+7), sessoes.get(i).getInicio());
		}
		
		Assert.assertEquals(2, sessoes.size());
	}
}
