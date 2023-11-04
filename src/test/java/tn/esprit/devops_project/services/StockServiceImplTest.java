package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class StockServiceImplTest {
    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @Test
    void addStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setTitle("Sample Stock");
        stock.setProducts(new HashSet<>());

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock savedStock = stockService.addStock(stock);

        assertNotNull(savedStock);
        assertEquals(1L, savedStock.getIdStock());
        assertEquals("Sample Stock", savedStock.getTitle());

    }
    @Test
    void retrieveStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Stock retrievedStock = stockService.retrieveStock(1L);
        verify(stockRepository, times(1)).findById(1L);
        assertSame(stock, retrievedStock);
    }

    @Test
    void retrieveAllStock() {
        // Create a list of sample Stock objects for testing
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        when(stockRepository.findAll()).thenReturn(List.of(stock1, stock2));

        List<Stock> stocks = stockService.retrieveAllStock();

        verify(stockRepository, times(1)).findAll();
        assertEquals(2, stocks.size());
    }
}
