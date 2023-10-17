package br.com.andrelima.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/*
 * ***  Modificadores - Seria para os tipos de acessos: ***
 * public
 * private
 * protected
*/

@RestController
@RequestMapping("/users")

// Ex: http://localhost:8080/ ---- Aqui: Rota da aplicação ----

 /*
    *** Métodos de acesso via HTTP ***
     *  GET - Buscar uma informação;
     *  POST - Adicionar um dado/informação;
     *  PUT - Alterar uma informação;
     *  DELETE - Remover um dado;
     *  PATCH - Alterar somente uma parte da informação/um dado;
    ***
    */

public class UserController {

    /*
    * *** Tipos: ***
    * 
    * String - Texto
    * Interger - (int) - Números inteiros
    * Double - (double) - Numeros decimais
    * Float - (float) -Números de caracteres a mais que Double
    * char - Um Caracter
    * Date - Data
    * void - Sem retorno aquele método/funcionalidade
    */

    @Autowired
    private IUserRepository userRepository;

    /*
     * Vai passar por dentro do Body(corpo) da requisição as informações do usuario.
     */

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        // System.out.println(userModel.getUsername());

        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            // System.out.println("Usuário já existe");

            // *Mensagem de erro;
            // *Status Code

            // return null;

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);

        var userCreated = this.userRepository.save(userModel);

        // return userCreated;

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}


// Parei na aula para assistir - Aula 03 - Implementando segurança nos dados do usuário