/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.ClienteDAO;
import es.uvigo.esei.dagss.facturaaas.daos.DatosFacturacionDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FormaPagoDAO;
import es.uvigo.esei.dagss.facturaaas.daos.LineaDeFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.TipoIVADAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.DatosFacturacion;
import es.uvigo.esei.dagss.facturaaas.entidades.Direccion;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.TipoIVA;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author danid
 */
@Named(value = "lineasDeFacturaController")
@ViewScoped
public class LineasDeFacturaController implements Serializable {
    
    
    
    private List<LineaDeFactura> lineas;
    private LineaDeFactura lineaActual;
    private boolean esNuevaLinea;
    
    private Factura factura; //Como inicializar este valor al pulsar el boton???--------------------------

    private DatosFacturacion datosFacturacion;
    
    private TipoIVA tipoIVA;

    
    @Inject
    private LineaDeFacturaDAO lineadefacturaDAO;

    @Inject
    private FacturaDAO facturaDAO;
    
    @Inject
    private AutenticacionController autenticacionController;
       
    @Inject
    private DatosFacturacionDAO datosFacturacionDAO;
    
    @Inject 
    private TipoIVADAO tipoIVADAO;
    
    public List<LineaDeFactura> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaDeFactura> lineas) {
        this.lineas = lineas;
    }

    public LineaDeFactura getLineaActual() {
        return lineaActual;
    }

    public void setLineaActual(LineaDeFactura lineaActual) {
        this.lineaActual = lineaActual;
    }
    
    public Factura getFactura(){
        return factura;
    }
    
    public void setFactura(Factura factura){
        this.factura = factura;
    }

    
    public boolean isEsNuevaLinea() {
        return esNuevaLinea;
    }

    public void setEsNuevaLinea(boolean esNuevaLinea) {
        this.esNuevaLinea = esNuevaLinea;
    }
    
    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }
    
    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }
    
    public TipoIVA getTipoIVA() {
        return tipoIVA;
    }

    public void setTipoIVA(TipoIVA tipoIVA) {
        this.tipoIVA = tipoIVA;
    }
    
    @PostConstruct
    public void cargaInicial() {
        this.lineas = refrescarLista();
        this.lineaActual = null;
        this.esNuevaLinea = false;
    }

    
    public void doBuscarConFactura() {
        //this.facturas = dao.buscarConPropietario(autenticacionController.getUsuarioLogueado());
        this.lineas = refrescarLista();
    }

    public void doBuscarTodos() {
        this.lineas = refrescarLista();
    }
    
    //La forma de pago debe estar inicializada con la forma de pago por defecto del usuario actual
    public void doNuevo() {
        this.esNuevaLinea = true;
        this.lineaActual = new LineaDeFactura();
        //Asociar factura a la nueva línea
        this.lineaActual.setFactura(factura);
        //Recuperamos datos facturacion usuario
        this.datosFacturacion = datosFacturacionDAO.buscarConPropietario(autenticacionController.getUsuarioLogueado());
        //Asignacion tipo iva por defecto del usuario
        this.tipoIVA = datosFacturacion.getTipoIVAPorDefecto();
    }

    public void doEditar(LineaDeFactura linea) {
        this.esNuevaLinea = false;
        this.lineaActual = linea;
    }


    public void doGuardarEditado() {
        //Actualiza el total mediante (cantidad * precio) * ( (100-porcentaje)/100 )
        lineaActual.setTotal( (lineaActual.getCantidad() * lineaActual.getPrecioUnitario()) * ( (100 - lineaActual.getPorcentajeDescuento()) /100) );
        if (this.esNuevaLinea) {
            lineadefacturaDAO.crear(lineaActual);
        } else {
            lineadefacturaDAO.actualizar(lineaActual);
        }
        this.lineas = refrescarLista();
        this.lineaActual = null;
        this.esNuevaLinea = false;
    }

    public void doCancelarEditado() {
        this.lineaActual = null;
        this.esNuevaLinea = false;
    }

    private List<LineaDeFactura> refrescarLista() {
        return lineadefacturaDAO.buscarConFactura(this.getFactura());
    }
    
    public List<TipoIVA> listadoTipoIVA(){
        return this.tipoIVADAO.buscarActivos();
    }
}
