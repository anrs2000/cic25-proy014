package es.cic.curso25.proy014.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;

@Entity
public class Coche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Max(12)
    private int numPuertas;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Plaza plaza;

    private int numPlazaAparcada;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "plaza_id")
    private List<Multa> multas = new ArrayList<>();

    public void addMulta(Multa multa) {
        this.multas.add(multa);
        multa.setCoche(this);
    }

    public void addPlaza(Plaza plaza) {
        plaza.setCoche(this);
        this.setPlaza(plaza);
    }

    public void deleteMulta(Multa multa) {
        this.multas.remove(multa);
        multa.setCoche(null);
    }

    public void deletePlaza(Plaza plaza) {
        this.setPlaza(null);
        plaza.setCoche(null);
    }

    public Coche() {
    }

    public Coche(String color, int numPuertas, Plaza plaza) {
        this.color = color;
        this.numPuertas = numPuertas;
        this.plaza = plaza;
    }

    public Coche(String color, int numPuertas) {
        this.color = color;
        this.numPuertas = numPuertas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumPuertas() {
        return numPuertas;
    }

    public void setNumPuertas(int numPuertas) {
        this.numPuertas = numPuertas;
    }

    public Plaza getPlaza() {
        return plaza;
    }

    public void setPlaza(Plaza plaza) {
        this.plaza = plaza;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }

    public int getNumPlazaAparcada() {
        return numPlazaAparcada;
    }

    public void setNumPlazaAparcada(int numPlazaAsignada) {
        this.numPlazaAparcada = numPlazaAsignada;
    }
}
