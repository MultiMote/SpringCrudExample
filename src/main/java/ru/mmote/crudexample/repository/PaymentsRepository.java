package ru.mmote.crudexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mmote.crudexample.entity.Payment;

public interface PaymentsRepository extends JpaRepository<Payment, Integer> {

}
