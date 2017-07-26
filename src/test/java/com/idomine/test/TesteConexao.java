package com.idomine.test;

import org.junit.Test;

import com.idomine.domain.Customer;

public class TesteConexao
{

    @Test
    public void teste() {
        Conexao conexao = new Conexao();
        conexao.getEntityManager().getTransaction().begin();
        Customer c = new Customer();
        c.setEmail("teste@email.com");
        c.setName("nome 1");
        c.setId(10L);
        conexao.getEntityManager().merge(c);
        conexao.getEntityManager().getTransaction().commit();
        conexao.getEntityManager().close();
    }
    
}
