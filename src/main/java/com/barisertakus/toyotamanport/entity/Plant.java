package com.barisertakus.toyotamanport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(of = { "id", "country" })
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

    @JsonIgnore
    @OneToMany(mappedBy = "plant")
    private List<ApplicationPlant> applicationPlants = new ArrayList<>();

    @JsonManagedReference
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
