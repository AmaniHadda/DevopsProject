//package tn.esprit.devops_project.services;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import tn.esprit.devops_project.entities.Invoice;
//import tn.esprit.devops_project.entities.Operator;
//import tn.esprit.devops_project.entities.Supplier;
//import tn.esprit.devops_project.entities.SupplierCategory;
//import tn.esprit.devops_project.repositories.InvoiceRepository;
//import tn.esprit.devops_project.repositories.OperatorRepository;
//import tn.esprit.devops_project.repositories.SupplierRepository;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static tn.esprit.devops_project.entities.SupplierCategory.ORDINAIRE;
//
//
//@SpringBootTest
//class InvoiceServiceImplTest {
//
//    @Autowired
//    private InvoiceServiceImpl invoiceService;
//
//    @Autowired
//    private InvoiceRepository invoiceRepository;
//
//    @Autowired
//    private OperatorRepository operatorRepository;
//
//    @Autowired
//    private SupplierRepository supplierRepository;
//    @Autowired
//    SupplierServiceImpl supplierService;
//
//    @Test
//    void retrieveAllInvoices() {
//        List<Invoice> invoices = invoiceService.retrieveAllInvoices();
//        assertNotNull(invoices);
//        assertFalse(invoices.isEmpty());
//    }
//
//    @Test
//    @Transactional
//    void cancelInvoice() {
//        // Créer des données de test
//        Invoice invoice = new Invoice();
//        invoice.setArchived(false);
//
//        // Ajouter la facture à la base de données pour les tests
//        invoice = invoiceRepository.save(invoice);
//
//        // Appel de la méthode
//        invoiceService.cancelInvoice(invoice.getIdInvoice());
//
//        // Vérification
//        assertTrue(invoice.getArchived()==true);
//    }
//
//    @Test
//    @Transactional
//    void retrieveInvoice() {
//        // Créer des données de test
//        Invoice invoice = new Invoice();
//        invoice = invoiceRepository.save(invoice);
//
//        // Appel de la méthode
//        Invoice result = invoiceService.retrieveInvoice(invoice.getIdInvoice());
//
//        // Vérification
//        assertNotNull(result);
//        assertEquals(invoice.getIdInvoice(), result.getIdInvoice());
//    }
//
//    @Test
//    @Transactional
//    void getInvoicesBySupplier() {
//        // Créer des données de test
//        Supplier supplier = new Supplier(1L, "code1", "label1", SupplierCategory.ORDINAIRE, null, null);
//        supplier.setInvoices(new ArrayList<>());
//
//        List<Invoice>invoices=new ArrayList<Invoice>(){
//            {
//                add(new Invoice(1L, 100.0f, 200.0f ,new Date(2023,7,14), new Date(2023,10,14), false, null, null,null));
//                add(new Invoice(2L, 300.0f, 400.0f ,new Date(2023,9,10), new Date(2023,10,14), false, null, null,null));
//
//            }
//        };
//
//        supplier.setInvoices(invoices);
//
//        supplier = supplierService.addSupplier(supplier);
//
//        // Appel de la méthode
//        List<Invoice> listinvoices = invoiceService.getInvoicesBySupplier(supplier.getIdSupplier());
//
//        // Vérification
////        assertNotNull(invoices);
//        assertFalse(listinvoices.isEmpty());
//    }
//    @Test
//    @Transactional
//    void assignOperatorToInvoice() {
//        // Créez des données de test
//        Operator operator = new Operator();
//        operator = operatorRepository.save(operator);
//
//        Invoice invoice = new Invoice();
//        invoice = invoiceRepository.save(invoice);
//
//        // Appel de la méthode
//        invoiceService.assignOperatorToInvoice(operator.getIdOperateur(), invoice.getIdInvoice());
//
//        // Vérification
//        Operator updatedOperator = operatorRepository.findById(operator.getIdOperateur()).orElse(null);
//        Invoice updatedInvoice = invoiceRepository.findById(invoice.getIdInvoice()).orElse(null);
//
//        assertNotNull(updatedOperator);
//        assertNotNull(updatedInvoice);
//        assertTrue(updatedOperator.getInvoices().contains(updatedInvoice));
//    }
//
//
////    @Test
////    @Transactional
////    void getTotalAmountInvoiceBetweenDates() {
////        // Créer des données de test
////        Date startDate = new Date();
////        Date endDate = new Date();
////
////        // Configurez le mock pour que la méthode getTotalAmountInvoiceBetweenDates renvoie une valeur spécifique
////        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(100.00f);
////
////        // Appel de la méthode
////        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);
////
////        // Vérification
////        assertEquals(100.00f, totalAmount);
////    }
//
//}
//
