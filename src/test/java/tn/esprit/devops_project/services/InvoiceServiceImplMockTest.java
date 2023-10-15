package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class InvoiceServiceImplMockTest {
    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Test
    public void retrieveAllInvoices() {
        // Créez des données de test
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(1L, 100.0f, 200.0f, new Date(2023,9,10), new Date(2023,10,14), false, null, null,null));
        invoices.add(new Invoice(2L, 200.0f, 200.0f, new Date(2023,9,10), new Date(2023,10,14), false, null, null,null));
        when(invoiceRepository.findAll()).thenReturn(invoices);

        // Appel de la méthode
        List<Invoice> retrievedInvoices = invoiceService.retrieveAllInvoices();

        // Vérification
        assertNotNull(retrievedInvoices);
        assertEquals(2, retrievedInvoices.size());
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    public void retrieveInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice(2L, 100.0f, 200.0f, new Date(2023,9,10), new Date(2023,10,14), false, null, null,null); // Créez un objet facture factice
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        Invoice retrievedInvoice = invoiceService.retrieveInvoice(invoiceId);

        assertEquals(invoice, retrievedInvoice);
    }

    @Test
    public void cancelInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice(2L, 100.0f, 200.0f ,new Date(2023,9,10), new Date(2023,10,14), false, null, null,null); // Créez un objet factice
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        invoiceService.cancelInvoice(invoiceId);

        assertTrue(invoice.getArchived());
        verify(invoiceRepository).save(invoice);
    }
    @Test
    void addInvoice() {
        Invoice invoice = new Invoice(2L, 100.0f, 200.0f ,new Date(2023,9,10), new Date(2023,10,14), false, null, null,null);
        Mockito.when(invoiceRepository.save(Mockito.any(Invoice.class))).thenReturn(invoice);

       Invoice invTest=invoiceService.addInvoice(invoice);
        Assertions.assertNotNull(invTest);
    }

//    @Test
//    public void getInvoicesBySupplier() {
//        Long supplierId = 1L;
//        Supplier supplier = new Supplier(1L, "code1", "label1", SupplierCategory.ORDINAIRE, null, null); // Créez un objet fournisseur factice
//        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));
//        Invoice invoice1 = new Invoice(1L, 100.0f, 200.0f ,new Date(2023,7,14), new Date(2023,10,14), false, null, null,null);
//        Invoice invoice2 = new Invoice(2L, 300.0f, 400.0f ,new Date(2023,9,10), new Date(2023,10,14), false, null,null,null);
//        supplier.getInvoices().add(invoice1);
//        supplier.getInvoices().add(invoice2);
//
//        List<Invoice> invoices = invoiceService.getInvoicesBySupplier(supplierId);
//
//        assertEquals(2, invoices.size());
//        assertTrue(invoices.contains(invoice1));
//        assertTrue(invoices.contains(invoice2));
//        verify(supplierRepository).findById(supplierId);
//    }
@Test
public void getInvoicesBySupplier() {
    Long supplierId = 1L;

    // Créez un objet Supplier factice avec des factures associées
    Supplier mockSupplier = new Supplier(1L, "code1", "label1", SupplierCategory.ORDINAIRE, null, null);
    mockSupplier.setInvoices(List.of(new Invoice(1L, 100.0f, 200.0f, new Date(2023, 7, 14), new Date(2023, 10, 14), false, null, null, null),
            new Invoice(2L, 300.0f, 400.0f, new Date(2023, 9, 10), new Date(2023, 10, 14), false, null, null, null)));

    when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(mockSupplier));

    List<Invoice> result = invoiceService.getInvoicesBySupplier(supplierId);

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(supplierRepository, times(1)).findById(supplierId);
}
    @Test
    public void assignOperatorToInvoice() {
        Long operatorId = 1L;
        Long invoiceId = 2L;

        // Créez un objet Invoice factice
        Invoice mockInvoice = new Invoice(2L, 300.0f, 400.0f, null,null , false, null, null, null);

        // Créez un objet Operator factice
        Operator mockOperator = new Operator(1L, "yosra", "elbich", "yosra123", null);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(mockInvoice));
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(mockOperator));

        invoiceService.assignOperatorToInvoice(operatorId, invoiceId);

        // Vérifiez que l'opérateur contient la facture
//        assertTrue(mockOperator.getInvoices().contains(mockInvoice));

        // Vérifiez que la méthode save a été appelée pour sauvegarder l'opérateur
        verify(invoiceRepository, times(1)).save(mockInvoice);
    }


    @Test
    public void getTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date(2023,9,22); // Remplacez ces dates par des dates valides
        Date endDate = new Date(2023,10,14); // Remplacez ces dates par des dates valides
        float expectedTotalAmount = 100.0f;
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(expectedTotalAmount);

        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        assertEquals(expectedTotalAmount, totalAmount, 0.01); // Utilisez une tolérance appropriée
        verify(invoiceRepository).getTotalAmountInvoiceBetweenDates(startDate, endDate);
    }
}
