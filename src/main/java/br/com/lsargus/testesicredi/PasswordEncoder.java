package br.com.lsargus.testesicredi;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// usado apenas para gerar o hash da senha para criar o usuário admin
public class PasswordEncoder {
    static void main() {
        System.out.println(
                new BCryptPasswordEncoder().encode("admin123")
        );
    }
}
