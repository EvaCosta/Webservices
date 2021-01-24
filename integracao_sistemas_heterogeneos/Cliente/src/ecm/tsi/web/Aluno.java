package ecm.tsi.web;

public class Aluno {
	private int id; 
	private String nome;
	private boolean matriculado;
	
	private static int contador;
	
	
	
	public Aluno() {
		this.id = ++contador;
	}

	public Aluno(int id, String nome, boolean matriculado) {
		this.id = id;
		this.nome = nome;
		this.matriculado = matriculado;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isMatriculado() {
		return matriculado;
	}
	
	public void setMatriculado(boolean matriculado) {
		this.matriculado = matriculado;
	}

	@Override
	public String toString() {
		return String.format("Aluno [id=%s, nome=%s, matriculado=%s]", id, nome, matriculado);
	}
}
