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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    
    //no se que relacion poner
    @ManyToOne
    private FormaPago formaPago;
  
    private double importe;
    //private double sumaTotal;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    private String comentarios;
    private String ejercicio;
    
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "factura")
    private List<LineaDeFactura> lineasDeFactura;
    
    public Factura(){
    }

    public Factura(EstadoFactura estadoFactura, Cliente cliente, Usuario propietario, FormaPago formaPago, double importe, Date fechaEmision, String comentarios, String ejercicio, List<LineaDeFactura> lineasDeFactura) {
        this.estadoFactura = estadoFactura;
        this.cliente = cliente;
        this.propietario = propietario;
        this.formaPago = formaPago;
        this.importe = importe;
        this.fechaEmision = fechaEmision;
        this.comentarios = comentarios;
        this.ejercicio = ejercicio;
        this.lineasDeFactura = lineasDeFactura;
    }
    
    
    
    //GETTERS
    
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
    /*
    public double getSumaTotal() {
        return sumaTotal;
    }
    */
    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public String getEjercicio() {
        return ejercicio;
    }
    
    
    public String getComentarios() {
        return comentarios;
    }

    
    public FormaPago getFormaPago() {
        return formaPago;
    }
    
    //SETTERS

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public void setEstadoFactura(EstadoFactura estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setImporte(double importe) {
        this.importe = importe;
    }
    /*
    public void setSumaTotal(double sumaTotal) {
        this.sumaTotal = sumaTotal;
    }
    */
    
       public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }
       
     
    
}
