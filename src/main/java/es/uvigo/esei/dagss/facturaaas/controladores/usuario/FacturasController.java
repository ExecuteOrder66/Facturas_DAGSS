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
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.DatosFacturacion;
import es.uvigo.esei.dagss.facturaaas.entidades.Direccion;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
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
@Named(value = "facturasController")
@ViewScoped
public class FacturasController implements Serializable {
    
    private List<Factura> facturas;
    private Factura facturaActual;
    private boolean esNuevo;
    //No estoy seguro ---------------------------------------
    private boolean esNuevaLinea;   //es lineaDeFactura nueva
    private Cliente clienteElegido; //Para insertar el cliente (elegido en lista desplegable) vinculado a la factura
    private FormaPago formaPago;    //Para insertar la formaPago (elegido en lista desplegable) vinculada a la factura
    private DatosFacturacion datosFacturacion;

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

   
    private Cliente clienteBusqueda;

    private EstadoFactura[] estadosFactura = EstadoFactura.values();
   
    @Inject
    private FacturaDAO facturaDAO;

    @Inject
    private ClienteDAO clienteDAO;
    
    @Inject
    private FormaPagoDAO formaPagoDAO;
    
    @Inject
    private AutenticacionController autenticacionController;

    @Inject
    private DatosFacturacionDAO datosFacturacionDAO;  //Para acceder a la formaPago por defecto del usuario
        
    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Factura getFacturaActual() {
        return facturaActual;
    }

    public void setFacturaActual(Factura facturaActual) {
        this.facturaActual = facturaActual;
    }

    
    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Cliente getClienteElegido() {
        return clienteElegido;
    }

    public void setClienteElegido(Cliente clienteElegido) {
        this.clienteElegido = clienteElegido;
    }
    
    public EstadoFactura[] getEstadosFactura() {
        return estadosFactura;
    }
    
    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    //No estoy seguro ---------------------------------------
     public boolean isEsNuevaLinea() {
        return esNuevaLinea;
    }

    public void setEsNuevaLinea(boolean esNuevaLinea) {
        this.esNuevaLinea = esNuevaLinea;
    }
    
    @PostConstruct
    public void cargaInicial() {
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
    }

    
    public void doBuscarConPropietario() {
        //this.facturas = dao.buscarConPropietario(autenticacionController.getUsuarioLogueado());
        this.facturas = refrescarLista();
    }
    
    public void doBuscarConPropietarioPorCliente(Cliente cli) {
        this.facturas = facturaDAO.buscarPorClienteConPropietario(autenticacionController.getUsuarioLogueado(), cli);
    }
    
    public void doBuscarTodos() {
        this.facturas = refrescarLista();
    }
    
    //La forma de pago debe estar inicializada con la forma de pago por defecto del usuario actual
    public void doNuevo() {
        this.esNuevo = true;
        this.facturaActual = new Factura();
        this.facturaActual.setPropietario(autenticacionController.getUsuarioLogueado());
        this.datosFacturacion= cargarDatosFacturacion();
        //Forma de pago por defecto del usuario extraido de sus datos de facturacion
        this.facturaActual.setFormaPago(datosFacturacion.getFormaPagoPorDefecto());

        this.facturaActual.setLineasDeFactura(new ArrayList<LineaDeFactura>()); //puse ArrayList por poner una implementacion de List con la que inicializar
        
    }
    
    public void doEditar(Factura factura) {
        this.esNuevo = false;
        this.facturaActual = factura;
    }


    public void doGuardarEditado() {
        if (this.esNuevo) {
            facturaActual.setCliente(clienteElegido);
            //facturaActual.setFormaPago(formaPago);
            facturaDAO.crear(facturaActual);
        } else {
            facturaDAO.actualizar(facturaActual);
        }
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNuevo = false;
    }

    public void doCancelarEditado() {
        this.facturaActual = null;
        this.esNuevo = false;
    }

    private List<Factura> refrescarLista() {
        return facturaDAO.buscarConPropietario(autenticacionController.getUsuarioLogueado());
    }
    
    
    public List<Cliente> listadoClientes(){
        return clienteDAO.buscarTodosConPropietario(autenticacionController.getUsuarioLogueado());
    }
    
    public List<FormaPago> listadoFormasPago() {
        return formaPagoDAO.buscarActivas();
    }
    
    private DatosFacturacion cargarDatosFacturacion(){
        return datosFacturacionDAO.buscarConPropietario(autenticacionController.getUsuarioLogueado());
    }
    //No estoy seguro ---------------------------------------
    //Editar una linea de factura
    
    /*
    public void doNuevaLinea() {
        this.esNuevaLinea = true;
        this.facturaActual = new Factura();
        this.facturaActual.setPropietario(autenticacionController.getUsuarioLogueado());
        //Forma de pago por defecto del usuario extraido de sus datos de facturacion
        this.facturaActual.setFormaPago(
                datosFacturacionDAO.buscarConPropietario(autenticacionController.getUsuarioLogueado()).getFormaPagoPorDefecto());

        this.facturaActual.setLineasDeFactura(new ArrayList<LineaDeFactura>()); //puse ArrayList por poner una implementacion de List con la que inicializar
        
    }
    */
}
