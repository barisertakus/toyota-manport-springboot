package com.barisertakus.toyotamanport.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Plant extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    @Column(unique = true)
    private String fullName;

    @Column(unique = true)
    private String shortCode;

    private int liveAppCount;

    @OneToMany(mappedBy = "plant")
    private List<ApplicationPlant> applicationPlants = new ArrayList<>();

    @OneToMany(mappedBy = "plant")
    private List<Server> servers = new ArrayList<>();

    public void addServer(Server server){
        if (!this.servers.contains(server)) {
            this.servers.add(server);
            server.setPlant(this);
        }
    }

    public void removeServer(Server server){
        if (this.servers.contains(server)) {
            this.servers.remove(server);
            server.setPlant(null);
        }
    }
}
