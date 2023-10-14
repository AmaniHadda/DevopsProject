package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.OperatorServiceImpl;
import static org.assertj.core.api.Assertions.assertThat ;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OperatorServiceImplTest {

  @InjectMocks
  OperatorServiceImpl operatorService;
  @Mock
   OperatorRepository operatorRepository;

   Operator  operator = new Operator (1L,"haykel", "mhadhbi", "love");

   List <Operator> operatorList = new ArrayList<Operator>(){
       {
           add(operator);
           add(new Operator( 2L,"Fatma", "Nouioui","fatma123"));

       }
   };
    private Operator createOperatorWithValues() {
        Operator operator = new Operator();
        operator.setFname("iheb");
        operator.setLname("kchini");
        operator.setPassword("iheb123");
        return operator;
    }
    @Test
    void retrieveAllOperators() {
            List<Operator>operatorList=new ArrayList<Operator>(){
                {
                    add(new Operator(1L,"haykel","mhadhbi", "haykel123"));
                    add(new Operator(2L,"fatma","noioui","fatma123"));
                    add(new Operator(3L,"baher","amari" ,"baher123"));
                }
            };
            Mockito.when(operatorRepository.findAll()).thenReturn(operatorList);
            //test
            List<Operator>operatorList1=operatorService.retrieveAllOperators();
            Assertions.assertEquals(3, operatorList1.size());
            Mockito.verify(operatorRepository, Mockito.times(1)).findAll();
    }


    @Test
    void addOperator() {

//        Operator op = new Operator();
//        op.setFname("iheb");
//
//        Operator operatorResult = operatorService.addOperator(op);
//
//        assertThat(operatorResult).isNotNull();
//        assertThat(operatorResult.getIdOperateur()).isNotNull();
//        assertThat(operatorResult.getFname()).isEqualTo("iheb");
//
//        Operator operatorDataBase = operatorRepository.findById(operatorResult.getIdOperateur()).orElse(null);
//        assertThat(operatorDataBase).isNotNull();
//        assertThat(operatorDataBase.getFname()).isEqualTo("iheb");

        Operator  op = new Operator (1L, "haykel", "mhadhbi", "love");
        Mockito.when(operatorRepository.save(Mockito.any(Operator.class))).thenReturn(op);

        Operator productTest=operatorService.addOperator(op);
        Assertions.assertNotNull(productTest);
        //Mockito.verify(productRepository, Mockito.times(1)).save(product);


    }


    @Test
    void deleteOperator() {

        //Créez un opérateur fictif pour le test (peut provenir de votre base de données de test).
        Operator operatorToDelete = new Operator();
        operatorToDelete.setIdOperateur(1L); // Remplacez par l'ID de l'opérateur à supprimer.

         //Configurez le comportement de Mockito pour la méthode deleteById() de l'opérateurRepository.
        Mockito.doNothing().when(operatorRepository).deleteById(operatorToDelete.getIdOperateur());//donothing 9oul ll  mockito de secifier le methode delebyid pour ne rien faire, c'est-à-dire qu'elle n'effectuera aucune action lorsqu'elle sera appelée khater trajalk void mais optionelle.

        // Appelez la méthode deleteOperator() de votre service.
        operatorService.deleteOperator(operatorToDelete.getIdOperateur());

        //  Vérifiez que la méthode deleteById() de l'opérateurRepository a été appelée une fois.
        Mockito.verify(operatorRepository, Mockito.times(1)).deleteById(operatorToDelete.getIdOperateur());
    }

    @Test
    void updateOperator() {
            Operator operatorToUpdate = new Operator();
            operatorToUpdate.setIdOperateur(1L); // ID de l'opérateur à mettre à jour
            operatorToUpdate.setFname("asma");
            operatorToUpdate.setLname("nouioui");
            operatorToUpdate.setPassword("asma1233");

            Mockito.when(operatorRepository.save(Mockito.any(Operator.class))).thenReturn(operatorToUpdate);
            Operator updatedOperator = operatorService.updateOperator(operatorToUpdate);

            // Vérifiez que la méthode save() de l'opérateurRepository a été appelée une fois avec l'opérateur à mettre à jour.
            Mockito.verify(operatorRepository, Mockito.times(1)).save(operatorToUpdate);

            //  Vérifiez que l'opérateur renvoyé par la méthode updateOperator() correspond à l'opérateur mis à jour.
            Assertions.assertEquals(operatorToUpdate, updatedOperator);

    }

    @Test
    void retrieveOperator() {
      Mockito.when(operatorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(operator));
    Operator  operator = operatorService.retrieveOperator(2L);
       Assertions.assertNotNull(operator);//verifiyer qui opertater nonull
    }
}