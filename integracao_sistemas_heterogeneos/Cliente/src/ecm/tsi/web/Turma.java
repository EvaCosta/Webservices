package ecm.tsi.web;

import java.util.ArrayList;
import java.util.List;

public class Turma {
	private int id_turma;
	private int ano;
	private String nome;
	private String curso;
	
	private List<Aluno> alunos;
	private static int contador;
	
	public Turma(){
		this.id_turma = ++contador;
        this.alunos = new ArrayList<>();
    }
	
	public Turma(int id, int ano, String curso, String nome) {
		this.id_turma = id;
		this.ano = ano;
		this.curso = curso;
		this.nome = nome;
	}

	public int getId() {
		return id_turma;
	}

	public void setId(int id) {
		this.id_turma = id;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void adicionaAluno( Aluno aluno ){
        this.alunos.add( aluno );
    }

    public Aluno[] getAlunos(){
        return this.alunos.toArray(new Aluno[0]);
    }

	@Override
	public String toString() {
		return String.format("Turma [id=%s, ano=%s, nome=%s, curso=%s, alunos=%s]", id_turma, ano, nome, curso, alunos);
	}
}
