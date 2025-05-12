package com.example.storage_manager.service;

import com.example.storage_manager.entity.Product;
import com.example.storage_manager.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Service
public class AlertService {

    private final ProductRepository productRepository;
    private final JavaMailSender mailSender;

    public AlertService(ProductRepository productRepository, JavaMailSender mailSender) {
        this.productRepository = productRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 0 8 * * ?") // Executa diariamente às 8h
    public void checkLowStock() {
        List<Product> lowStockProducts = productRepository.findByQuantityLessThan(10); // Exemplo
        for (Product product : lowStockProducts) {
            if (!product.isPendingAlert()) {
                product.setPendingAlert(true);
                productRepository.save(product);
                sendAlertEmail(product);
            }
        }
    }

    private void sendAlertEmail(Product product) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@example.com");
        message.setSubject("Alerta de Estoque Baixo: " + product.getName());
        message.setText("O produto " + product.getName() + " está com quantidade (" + product.getQuantity() +
                ") abaixo do mínimo (" + product.getMinAlert() + ").");
        mailSender.send(message);
    }
}