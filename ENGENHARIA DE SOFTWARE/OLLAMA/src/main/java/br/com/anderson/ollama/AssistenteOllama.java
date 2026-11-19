package br.com.anderson.ollama;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.response.OllamaResult;

import java.util.List;
import java.util.Scanner;

public class AssistenteOllama {

    public static void main(String[] args) {

        try {
            Ollama ollama = new Ollama("http://localhost:11434/");
            ollama.setRequestTimeoutSeconds(180);

            if (!ollama.ping()) {
                System.out.println("[ERRO] O servidor Ollama não respondeu. Verifique se ele está em execução.");
                return;
            }

            System.out.println("[OK] Conexão com o servidor Ollama estabelecida.");

            String modelo = "gemma3:1b";

            // Perguntas iniciais enviadas automaticamente
            List<String> perguntasIniciais = List.of(
                    "Em qual continente fica Liechtenstein e qual o nome do CEO da SpaceX?"
            );

            System.out.println("\n=== Perguntas iniciais ===");
            for (String pergunta : perguntasIniciais) {
                perguntar(ollama, modelo, pergunta);
            }

            // Modo interativo: o usuário digita as perguntas
            System.out.println("\n=== Modo interativo ===");
            System.out.println("Digite sua pergunta (ou \"sair\" para encerrar).");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nVocê: ");
                if (!scanner.hasNextLine()) {
                    break;
                }

                String pergunta = scanner.nextLine().trim();

                if (pergunta.equalsIgnoreCase("sair")) {
                    break;
                }
                if (pergunta.isEmpty()) {
                    continue;
                }

                perguntar(ollama, modelo, pergunta);
            }

            System.out.println("\nSessão encerrada. Até a próxima!");

        } catch (Exception e) {
            System.out.println("\n[ERRO] Falha durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void perguntar(Ollama ollama, String modelo, String pergunta) throws Exception {
        System.out.println("\n>> Pergunta: " + pergunta);
        System.out.println("Processando, aguarde...");

        OllamaGenerateRequest requisicao =
                OllamaGenerateRequest.builder()
                        .withModel(modelo)
                        .withPrompt(pergunta)
                        .build();

        OllamaResult resposta = ollama.generate(requisicao, null);

        System.out.println("\n<< Resposta (" + modelo + "):");
        System.out.println(resposta.getResponse());
    }
}
