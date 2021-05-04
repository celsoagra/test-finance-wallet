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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Initializer {

    @Autowired
    private Wallet wallet;

    @Autowired
    private TestFinanceClient client;

    @PostConstruct
    private void postConstruct() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            SignatureException, UnsupportedEncodingException {
        String coinbase = client.coinbase().getBody().getId();
        
        log.info("coinbase ID: {}", coinbase);
        log.info("mywallet ID: {}", wallet.getPublicKeyAsString());
        
        log.info("coinbase balance: {}", client.balance(CoinbaseDTO.newInstance(coinbase)).getBody() );
        log.info("my wallet balance: {}", client.balance(CoinbaseDTO.newInstance(wallet.getPublicKeyAsString())).getBody() );
        
        log.info("my wallet faucet: {}", client.faucet(CoinbaseDTO.newInstance(wallet.getPublicKeyAsString())).getBody() );
        
        log.info("coinbase balance: {}", client.balance(CoinbaseDTO.newInstance(coinbase)).getBody() );
        log.info("my wallet balance: {}", client.balance(CoinbaseDTO.newInstance(wallet.getPublicKeyAsString())).getBody() );
    }

}
