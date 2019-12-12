package es.uvigo.esei.dagss.facturaaas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FACTURA")
public class Factura implements Serializable{


   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private EstadoFactura estadoFactura;
    
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;
    
    private double importe;
    private double ivaPagado;
    private double sumaTotal;
    private Date fechaEmision;
    private String comentarios;

    public Long getId() {
        return id;
    }

    public EstadoFactura getEstadoFactura() {
        return estadoFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getImporte() {
        return importe;
    }

    public double getIvaPagado() {
        return ivaPagado;
    }

    public double getSumaTotal() {
        return sumaTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEstadoFactura(EstadoFactura estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    private void setIvaPagado(double ivaPagado) {
        this.ivaPagado = ivaPagado;
    }

    public void setSumaTotal(double sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    
   
    
}
