package ru.mmote.crudexample.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.mmote.crudexample.dao.PaymentUpdateDAO;
import ru.mmote.crudexample.entity.Payment;
import ru.mmote.crudexample.repository.PaymentsRepository;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentsRepository paymentsRepo;

    @Autowired
    public void setPaymentsRepo(PaymentsRepository paymentsRepo) {
        this.paymentsRepo = paymentsRepo;
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentsRepo.findAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public List<Payment> updatePayments(final @RequestBody List<PaymentUpdateDAO> list) {
        List<Payment> toDelete = list.stream().filter(o -> o.getAction() == PaymentUpdateDAO.Action.DELETE)
                .map(PaymentUpdateDAO::getData).collect(Collectors.toList());
        List<Payment> toUpdate = list.stream().filter(o -> o.getAction() == PaymentUpdateDAO.Action.UPDATE)
                .map(PaymentUpdateDAO::getData).collect(Collectors.toList());

        List<Payment> result = new ArrayList<>();

        if(!toDelete.isEmpty()){
            paymentsRepo.deleteInBatch(toDelete);
        }
        if(!toUpdate.isEmpty()){
            result = paymentsRepo.saveAll(toUpdate);
        }

        return result;
    }
}
