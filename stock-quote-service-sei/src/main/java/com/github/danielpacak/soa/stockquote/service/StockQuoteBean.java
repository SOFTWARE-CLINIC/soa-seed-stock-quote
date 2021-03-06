package com.github.danielpacak.soa.stockquote.service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import com.example.stockquote.ObjectFactory;
import com.example.stockquote.StockQuotePortType;
import com.example.stockquote.TradePriceRQ;
import com.example.stockquote.TradePriceRS;
import com.github.danielpacak.soa.stockquote.repository.StockQuoteRepository;
import com.github.danielpacak.soa.stockquote.repository.memory.InMemoryStockQuoteRepository;

@WebService(portName = "StockQuotePort",
        serviceName = "StockQuoteService",
        targetNamespace = "http://example.com/stockquote",
        endpointInterface = "com.example.stockquote.StockQuotePortType",
        wsdlLocation = "/META-INF/wsdl/stock-quote.wsdl")
public class StockQuoteBean implements StockQuotePortType {

    @Resource
    private WebServiceContext context;

    private StockQuoteRepository repository;

    public StockQuoteBean(StockQuoteRepository repository) {
        this.repository = repository;
    }

    public StockQuoteBean() {
        this(new InMemoryStockQuoteRepository());
    }

    @Override
    public TradePriceRS getLastTradePrice(TradePriceRQ request) {
        ObjectFactory of = new ObjectFactory();
        TradePriceRS response = of.createTradePriceRS();
        response.setPrice(repository.getLastTradePrice(request.getTickerSymbol()));
        return response;
    }

}