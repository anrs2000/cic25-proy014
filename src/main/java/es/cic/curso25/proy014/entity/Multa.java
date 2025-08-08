package es.cic.curso25.proy014.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double precio;

    private LocalDate fechaMaximaPago;

    @ManyToOne
    @JoinColumn(name = "coche_id")
    private Coche coche;

    public Multa() {
        this.precio = 100;
        this.fechaMaximaPago = LocalDate.now().plusWeeks(2);
    }

    public Long getId() {
        return id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDate getFechaMaximaPago() {
        return fechaMaximaPago;
    }

    public void setFechaMaximaPago(LocalDate fechaMaximaPago) {
        this.fechaMaximaPago = fechaMaximaPago;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }
}
