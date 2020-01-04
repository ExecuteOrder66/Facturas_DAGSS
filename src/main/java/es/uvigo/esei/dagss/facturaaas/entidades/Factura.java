package es.uvigo.esei.dagss.facturaaas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    
    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario propietario;
    
    
    private double importe;
    private double ivaPagado;
    private double sumaTotal;
    private Date fechaEmision;
    private String comentarios;
    
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "factura")
    private List<LineaDeFactura> lineasDeFactura;

            
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

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }


    public List<LineaDeFactura> getLineasDeFactura() {
        return lineasDeFactura;
    }

    public void setLineasDeFactura(List<LineaDeFactura> lineasDeFactura) {
        this.lineasDeFactura = lineasDeFactura;
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
