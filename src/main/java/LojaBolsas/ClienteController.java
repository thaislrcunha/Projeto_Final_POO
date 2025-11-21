package LojaBolsas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarCliente(@RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = clienteService.cadastrar(dto);
            return ResponseEntity.ok("Cliente " + cliente.getNome() + " cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String user = loginData.get("username");
        String pass = loginData.get("password");

        Cliente cliente = clienteService.autenticar(user, pass);

        if (cliente != null) {
            // Retorna o objeto cliente (sem a senha, por segurança, idealmente)
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos.");
        }
    }
}