package ecm.webservices.viacep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static javax.swing.JOptionPane.*;

import org.json.JSONException;
import org.json.JSONObject;

public class ViaCep {

	private final String TITULO = "Consultar CEP";

	public static void main(String[] args) {
		new ViaCep();
	}

	public ViaCep() {
		viaCep();
	}

	private void viaCep() {
		String cep;

		do {
			cep = lerString("CEP");

			if(!verificaAcao(cep))
				return;

		}while(!validaCep(cep));

		cep = formataCEP(cep);

		String json = obterDados(cep);
		
		if(json.contains("erro") || json == null)
			msgError("CEP invalido!"); 
		
		try {
			JSONObject object = new JSONObject(json);
			
			String dados = String.format("CEP: %s, \nLogradouro: %s, \nComplemento: %s, \nBairro: %s, \nLocalidade: %s, \nUF: %s, \nIBGE:%s, \nGIA: %s, \nDDD: %s, \nSiafi: %s",
					object.getString("cep"), object.getString("logradouro"), object.getString("complemento"),object.getString("bairro"), object.getString("localidade"),
					object.getString("uf"), object.getString("ibge"), object.getString("gia"), object.getString("ddd"), object.getString("siafi"));
			
			msgInfo(dados);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void msgInfo(String dados) {
		showMessageDialog(null, dados, TITULO, INFORMATION_MESSAGE);
	}

	private String obterDados(String cep) {
		//Concatena a url com o cep informado
		String stringUrl = "https://viacep.com.br/ws/%s/json";
		stringUrl = String.format(stringUrl, cep);


		URL url;
		try {
			url = new URL(stringUrl);
			URLConnection con = url.openConnection();

			BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String line;
			StringBuilder source = new StringBuilder();
			
			while((line = input.readLine()) != null)
				source.append(line);
			
			input.close();

			return source.toString();
			
		} catch (MalformedURLException e) {
			msgError("O formato da URL está incorreto!");
			e.printStackTrace();
		} catch (IOException e) {
			return null;
		}
		return null;
	}

	private boolean validaCep(String cep) {
		return cep.matches("[0-9]{5}-[0-9]{3}");
	}

	private String formataCEP(String cep) {
		return cep.replace("-", "");
	}

	private String lerString(String string) {
		return showInputDialog(null, string, TITULO, QUESTION_MESSAGE);
	}
	
	private void msgError(String string) {
		showMessageDialog(null, string, TITULO, ERROR_MESSAGE);
	}

	private boolean verificaAcao(String acao) {
		if(acao == null) {
			showInternalMessageDialog(null, "Ação cancelada");
			return false;
		}
		return true;
	}
}
