package io.celsoagra.finance.start;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.celsoagra.finance.base.Wallet;
import io.celsoagra.finance.client.TestFinanceClient;
import io.celsoagra.finance.dto.CoinbaseDTO;
import io.celsoagra.finance.dto.TransactionDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Initializer {

    @Autowired
    private TestFinanceClient client;

    @PostConstruct
    private void postConstruct() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            SignatureException, UnsupportedEncodingException {
        String coinbase = client.coinbase().getBody().getId();
        
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        Wallet walletC = new Wallet();
        
        log.info("coinbase ID: {}", coinbase);
        log.info("walletA ID: {}", walletA.getPublicKeyAsString());
        log.info("walletB ID: {}", walletB.getPublicKeyAsString());
        log.info("walletC ID: {}", walletC.getPublicKeyAsString());
        
        log.info("coinbase balance: {}", client.balance(CoinbaseDTO.newInstance(coinbase)).getBody() );
        log.info("walletA balance: {}", client.balance(CoinbaseDTO.newInstance(walletA.getPublicKeyAsString())).getBody() );
        log.info("walletB balance: {}", client.balance(CoinbaseDTO.newInstance(walletB.getPublicKeyAsString())).getBody() );
        log.info("walletC balance: {}", client.balance(CoinbaseDTO.newInstance(walletC.getPublicKeyAsString())).getBody() );
        
        log.info("walletA faucet: {}", client.faucet(CoinbaseDTO.newInstance(walletA.getPublicKeyAsString())).getBody() );
        log.info("walletB faucet: {}", client.faucet(CoinbaseDTO.newInstance(walletB.getPublicKeyAsString())).getBody() );
        log.info("walletC faucet: {}", client.faucet(CoinbaseDTO.newInstance(walletC.getPublicKeyAsString())).getBody() );
        log.info("walletC faucet: {}", client.faucet(CoinbaseDTO.newInstance(walletC.getPublicKeyAsString())).getBody() );
        
        log.info("coinbase balance: {}", client.balance(CoinbaseDTO.newInstance(coinbase)).getBody() );
        log.info("walletA balance: {}", client.balance(CoinbaseDTO.newInstance(walletA.getPublicKeyAsString())).getBody() );
        log.info("walletB balance: {}", client.balance(CoinbaseDTO.newInstance(walletB.getPublicKeyAsString())).getBody() );
        log.info("walletC balance: {}", client.balance(CoinbaseDTO.newInstance(walletC.getPublicKeyAsString())).getBody() );
        
        double valueToSend = 3; 
        TransactionDTO sendAToB = TransactionDTO.builder().sender(walletA.getPublicKeyAsString()).receiver(walletB.getPublicKeyAsString()).value(valueToSend).build();
        sendAToB.setSignature( walletA.generateSignature(walletB.getPublicKeyAsString(), valueToSend) );
        log.info("send 3 bucks from walletA to walletB: {}", client.send(sendAToB).getBody() );
        
        double valueToSendFromC = 7.00005d; 
        TransactionDTO sendCToB = TransactionDTO.builder().sender(walletC.getPublicKeyAsString()).receiver(walletB.getPublicKeyAsString()).value(valueToSendFromC).build();
        sendCToB.setSignature( walletC.generateSignature(walletB.getPublicKeyAsString(), valueToSendFromC) );
        log.info("send bucks from walletC to walletB: {}", client.send(sendCToB).getBody() );
        
        log.info("coinbase balance: {}", client.balance(CoinbaseDTO.newInstance(coinbase)).getBody() );
        log.info("walletA balance: {}", client.balance(CoinbaseDTO.newInstance(walletA.getPublicKeyAsString())).getBody() );
        log.info("walletB balance: {}", client.balance(CoinbaseDTO.newInstance(walletB.getPublicKeyAsString())).getBody() );
        log.info("walletC balance: {}", client.balance(CoinbaseDTO.newInstance(walletC.getPublicKeyAsString())).getBody() );
        
    }

}
