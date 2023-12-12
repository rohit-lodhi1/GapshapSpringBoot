package com.gapshap.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gapshap.app.model.chat.Contacts;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, Long>{

}
