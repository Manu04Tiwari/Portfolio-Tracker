package com.example.portfolio.service;

import com.example.portfolio.exception.ResourceNotFoundException;
import com.example.portfolio.model.Stock;
import com.example.portfolio.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock stockDetails) {
        return stockRepository.findById(id).map(stock -> {
            stock.setSymbol(stockDetails.getSymbol());
            stock.setCompanyName(stockDetails.getCompanyName());
            stock.setShares(stockDetails.getShares());
            stock.setPurchasePrice(stockDetails.getPurchasePrice());
            stock.setPurchaseDate(stockDetails.getPurchaseDate());
            return stockRepository.save(stock);
        }).orElseThrow(() -> new ResourceNotFoundException("Stock not found with id " + id));
    }
//deleting a stock
    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id " + id));
        stockRepository.delete(stock);
    }
//fetch all stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public double calculatePortfolioValue(Long portfolioId) {
        double totalValue = 0.0;
        List<Stock> stocks = stockRepository.findByPortfolioId(portfolioId);
        for (Stock stock : stocks) {
            double price = fetchStockPrice(stock.getSymbol()); // Replace with actual price fetching logic
            totalValue += price * stock.getShares();
        }
        return totalValue;
    }
    

    private double fetchStockPrice(String symbol) {
        return 100.0;
    }
}


