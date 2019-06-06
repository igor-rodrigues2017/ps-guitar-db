package com.guitar.db.repository;

/**
 * Importante o nome da Classe com sufixo Impl
 */
public class ModelJpaRepositoryImpl implements ModelJpaRepositoryCustom {
    @Override
    public void aCustomMethod() {
        System.out.println("I'm a custom method");
    }
}
