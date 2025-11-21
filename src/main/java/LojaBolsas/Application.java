package LojaBolsas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Ao rodar essa linha, o Spring inicializa e chama o "run" do LojaBolsas.Sistema.java
        SpringApplication.run(Application.class, args);
    }
}