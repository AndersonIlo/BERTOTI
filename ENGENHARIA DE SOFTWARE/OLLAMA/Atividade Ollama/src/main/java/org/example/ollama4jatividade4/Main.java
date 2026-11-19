
package org.example;

import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.response.OllamaResult;

public class Main {

    public static void main(String[] args) {

        try {
            io.github.ollama4j.Ollama ollama =
                    new io.github.ollama4j.Ollama("http://localhost:11434/");


            ollama.setRequestTimeoutSeconds(180);

            if (!ollama.ping()) {
                System.out.println("Não foi possível conectar ao Ollama.");
                return;
            }

            System.out.println("Conectado ao Ollama!");


            String modelo = "gemma3:1b";


            String pergunta =
                    "Em qual continente fica Linchestein e qual o nome do CEO da SpaceX?";

            System.out.println("\nPergunta: " + pergunta);
            System.out.println("\nAguardando resposta do Ollama...");


            OllamaGenerateRequest requisicao =
                    OllamaGenerateRequest.builder()
                            .withModel(modelo)
                            .withPrompt(pergunta)
                            .build();


            OllamaResult resposta = ollama.generate(requisicao, null);

            System.out.println("\nResposta do Ollama:");
            System.out.println(resposta.getResponse());

        } catch (Exception e) {
            System.out.println("\nErro ao executar o programa: "
                    + e.getMessage());
            e.printStackTrace();
        }
    }
}
