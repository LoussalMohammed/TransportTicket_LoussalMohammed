package app.Models.Entities;

import app.Models.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Admin extends Person {
    private List<Contract> contracts; // Association 1 to *

    public Admin(UUID id, String firstName, String lastName, String email, String phone, Role role, LocalDateTime createdAt, List<Contract> contracts) {
        super(id, firstName, lastName, email, phone, role, createdAt);
        this.contracts = contracts;
    }

    // Getters and setters for contracts
    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "contracts=" + contracts +
                ", Person=" + super.toString() +
                '}';
    }
}
