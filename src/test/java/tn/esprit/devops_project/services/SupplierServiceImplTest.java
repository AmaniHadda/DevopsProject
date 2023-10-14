package tn.esprit.devops_project.services;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)

class SupplierServiceImplTest {
    Supplier supplier = new Supplier(1L,"code1", "label1",SupplierCategory.ORDINAIRE, null, null);
    @Autowired
    @Mock
   // SupplierRepository supplierRepository ;
    SupplierRepository supplierRepository = Mockito.mock(SupplierRepository.class);
    @InjectMocks
    SupplierServiceImpl supplierService;

    @Test
    void retrieveAllSuppliers() {
            List<Supplier>supplList=new ArrayList<Supplier>(){
                {
                    add(new Supplier(3L,"Product3","ll",SupplierCategory.ORDINAIRE,null,null));
                    add(new Supplier(2L,"Product2","lhl",SupplierCategory.ORDINAIRE,null,null));
                    add(new Supplier(4L,"Product4","lsl",SupplierCategory.ORDINAIRE,null,null));
                }
            };
            Mockito.when(supplierRepository.findAll()).thenReturn(supplList);
        List<Supplier>supllList1=supplierService.retrieveAllSuppliers();
        Assertions.assertEquals(3, supllList1.size());
        Mockito.verify(supplierRepository, Mockito.times(1)).findAll();
    }

    @Test
    void addSupplier() {
            Supplier supplier1=new Supplier(1L,"Product1","ll",SupplierCategory.ORDINAIRE,null,null);
            Mockito.when(supplierRepository.save(Mockito.any(Supplier.class))).thenReturn(supplier1);

            Supplier suppTest=supplierService.addSupplier(supplier1);
            Assertions.assertNotNull(suppTest);
        }

    @Test
    void updateSupplier() {
        Supplier suppToUpdate = new Supplier();
        suppToUpdate.setIdSupplier(1L); // ID de l'opérateur à mettre à jour
        suppToUpdate.setLabel("asma");
        suppToUpdate.setCode("codeupdated");
        Mockito.when(supplierRepository.save(Mockito.any(Supplier.class))).thenReturn(suppToUpdate);
        Supplier updatedsupp = supplierService.updateSupplier(suppToUpdate);

        // Vérifiez que la méthode save() de l'opérateurRepository a été appelée une fois avec l'opérateur à mettre à jour.
        Mockito.verify(supplierRepository, Mockito.times(1)).save(suppToUpdate);

        //  Vérifiez que l'opérateur renvoyé par la méthode updateOperator() correspond à l'opérateur mis à jour.
        Assertions.assertEquals(suppToUpdate, updatedsupp);
    }

    @Test
    void deleteSupplier() {
        Supplier suppToDelete = new Supplier();
        suppToDelete.setIdSupplier(1L); // Remplacez par l'ID de l'opérateur à supprimer.

        //Configurez le comportement de Mockito pour la méthode deleteById() de l'opérateurRepository.
        Mockito.doNothing().when(supplierRepository).deleteById(suppToDelete.getIdSupplier());//donothing 9oul ll  mockito de secifier le methode delebyid pour ne rien faire, c'est-à-dire qu'elle n'effectuera aucune action lorsqu'elle sera appelée.

        // Appelez la méthode deleteOperator() de votre service.
        supplierService.deleteSupplier(suppToDelete.getIdSupplier());

        //  Vérifiez que la méthode deleteById() de l'opérateurRepository a été appelée une fois.
        Mockito.verify(supplierRepository, Mockito.times(1)).deleteById(suppToDelete.getIdSupplier());
    }

    @Test
    void retrieveSupplier() {
        Mockito.when(supplierRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(supplier));
        Supplier supp1 = supplierService.retrieveSupplier(1L);
        //Assertions.assertNotNull(supp1);
        Mockito.verify(supplierRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }
}