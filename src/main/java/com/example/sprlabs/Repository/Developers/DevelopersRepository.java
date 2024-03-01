package com.example.sprlabs.Repository.Developers;

import com.example.sprlabs.Classes.Developers;

import java.util.Optional;

public interface DevelopersRepository {
    Iterable<Developers> findAll();
    Optional<Developers> findById(long id);
    Developers addDev(Developers developers);
    Developers updateDev(Developers developers);
    void deleteDev(long id);
}
