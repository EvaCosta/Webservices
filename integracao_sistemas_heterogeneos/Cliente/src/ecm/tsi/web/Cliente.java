package ecm.tsi.web;

import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.*;

import com.google.gson.*;

public class Cliente{
	public static void main(String[] args) {
		new Cliente();
	}
	
	public Cliente() {
		cliente();
	}

	public void cliente() {
		List<Turma> turmas = new ArrayList<Turma>();
		
		/*Turma turma1 = new Turma();
		turma1.setAno(2020);
		turma1.setNome("TSI-2020");
		turma1.setCurso("tsi");
		
		Aluno aluno1 = new Aluno();
		aluno1.setNome("Eva");
		aluno1.setMatriculado(true);
		
		turma1.adicionaAluno(aluno1);
		
		Aluno aluno2 = new Aluno();
		aluno2.setNome("Lucas");
		aluno2.setMatriculado(false);
		
		turma1.adicionaAluno(aluno2);
		turmas.add(turma1);
		
		Turma turma2 = new Turma();
		turma2.setAno(2018);
		turma2.setNome("EFI-2018");
		turma2.setCurso("efi");
		
		Aluno aluno3 = new Aluno();
		aluno3.setNome("Fernando");
		aluno3.setMatriculado(true);
		
		turma2.adicionaAluno(aluno3);
		
		Aluno aluno4 = new Aluno();
		aluno4.setNome("Ulisses");
		aluno4.setMatriculado(false);
		
		turma2.adicionaAluno(aluno4);
		turmas.add(turma2);
		
		Turma turma3 = new Turma();
		turma3.setAno(2020);
		turma3.setNome("MAT-2017");
		turma3.setCurso("mat");
		
		Aluno aluno5 = new Aluno();
		aluno5.setNome("Geovanna");
		aluno5.setMatriculado(false);
		
		turma3.adicionaAluno(aluno5);
		turmas.add(turma3);
		*/
		int opcao;
		do {
			Turma turma = new Turma();
			String nomeTurma = showInputDialog(null, "Nome da Turma:", "Cadastro", INFORMATION_MESSAGE);
			if(!verificaAcao(nomeTurma)) {
				return;
			}
			
			//valida se o ano possui 4 digitos e se foram fornecidos somente numeros para a variavel.
			String ano;
			do {
				ano = showInputDialog(null, "Ano:", "Cadastro", INFORMATION_MESSAGE);
			
				if(!verificaAcao(ano)) {
					return;
				}
			}while(ano.length() != 4 || !ano.matches("[0-9]*"));
			
			String curso = showInputDialog(null, "Curso:", "Cadastro", INFORMATION_MESSAGE);
			if(!verificaAcao(curso)) {
				return;
			}
			
			
			turma.setNome(nomeTurma);
			turma.setAno(Integer.parseInt(ano));
			turma.setCurso(curso);
			Aluno aluno;
			do {
				aluno = new Aluno();
				String nome = showInputDialog(null, "Nome do aluno:", "Cadastro", INFORMATION_MESSAGE);
				if(!verificaAcao(nome)) {
					return;
				}
				
				Object[] options = { "Sim", "Não" };
				int matriculado = showOptionDialog(null, "Esta matriculado? ", "Informação", DEFAULT_OPTION, INFORMATION_MESSAGE, null, options, options[0]);
				
				aluno.setNome(nome);
				aluno.setMatriculado((matriculado == 0) ? true : false);
				
				opcao = showConfirmDialog(null, "Cadastrar novo aluno(a)?", "Cadastro", YES_NO_OPTION);
				turma.adicionaAluno(aluno);
			}while(opcao != 1);
			opcao = showConfirmDialog(null, "Cadastrar outra turma?", "Cadastro", YES_NO_OPTION);
			
			turmas.add(turma);
		}while(opcao != 1);
		
		
	  //instancia um objeto da classe Gson
      Gson gson = new Gson();

      //pega os dados do filme, converte para JSON e armazena em string
      String json = gson.toJson(turmas);
		
      
      System.out.println("Dados enviados.");
      enviarMensagem(json);
	}
	
	private boolean verificaAcao(String acao) {
		if(acao == null) {
			showInternalMessageDialog(null, "Ação cancelada");
			return false;
		}
		return true;
	}

	private static void enviarMensagem(String json) {
		try {
			Socket socket;
			socket = new Socket( "127.0.0.1", 16200);

			OutputStreamWriter streamWriter  = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			streamWriter.write(json.toString(), 0, json.toString().length());
			
			streamWriter.close();
			socket.close();
		} catch (Exception e) {
			e.getMessage();
		}
        
	}
}
