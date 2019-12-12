/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;

/**
 *
 * @author danid
 */
public interface PagoDAO extends GenericoDAO<Pago, Long>{
     public Pago buscarConPropietario(Usuario propietario);
}
