package com.example.mscard.services;

import com.example.mscard.model.entities.Card;
import com.example.mscard.repositories.ICardRepository;
import com.example.mscard.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService extends BaseService<Card, String> implements ICardService {

    private final ICardRepository iCreditRepository;

    @Autowired
    public CardService(ICardRepository iCreditRepository) {
        this.iCreditRepository = iCreditRepository;
    }

    @Override
    protected IRepository<Card, String> getRepository() {
        return iCreditRepository;
    }
}
