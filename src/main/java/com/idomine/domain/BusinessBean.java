package com.idomine.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.omnifaces.cdi.ViewScoped;

@Named
@ViewScoped
public class BusinessBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List<Customer> customers;

    @Inject
    private CustomerRepository customerRepository;

    @PostConstruct
    public void init()
    {
        popule();
        customers = customerRepository.findAll();
    }
    
    public List<Customer> lista()
    {
        List<Customer> l = new ArrayList<>();
        l.add( Customer.getFake());
        return l;
    }

    public void fireRules()
    {
    }

    public List<Customer> getCustomers()
    {
        return customers;
    }
   
    @Transactional
    public void popule()
    {
        if (customerRepository.count() == 0L)
        {

            for (int i = 200; i < 300; i++)
            {
                Customer c = new Customer();
                c.setName("Customer " + 1);
                c.setEmail("email_" + i + "@email.com");
                Customer c2 = customerRepository.save(c);
                System.out.println(c2);
            }
        }
    }

}
