/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author danid
 */
@Entity
@Table(name = "LINEAFACTURA")
public class LineaDeFactura{

           
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "FACTURA_ID")
    private Factura factura;
    
    private String concepto;
    private Double cantidad;
    private Double precioUnitario;
    private Double porcentajeDescuento;
    
    @ManyToOne
    @JoinColumn(name = "TIPO_IVA")
    private TipoIVA tipoIva;
    
    private Double total;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    public LineaDeFactura(){
        
    }
    
    public LineaDeFactura(Factura factura, String concepto, Double cantidad, Double precioUnitario, Double porcentajeDescuento, TipoIVA tipoIva, Double total, Cliente cliente) {

        this.factura = factura;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.porcentajeDescuento = porcentajeDescuento;
        this.tipoIva = tipoIva;
        this.total = total;
        this.cliente = cliente;
    }
    
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public long getId() {
        return id;
    }


    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public TipoIVA getTipoIva() {
        return tipoIva;
    }

    public void setTipoIva(TipoIVA tipoIva) {
        this.tipoIva = tipoIva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
}
