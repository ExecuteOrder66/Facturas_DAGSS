/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.DatosFacturacion;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author danid
 */
@Stateless
public class FacturaDAOJPA extends GenericoDAOJPA<Factura, Long> implements FacturaDAO {

    @Override
    public Factura buscarConPropietario(Usuario propietario) {
        TypedQuery<Factura> query = em.createQuery("SELECT fac FROM Factura AS fac WHERE fac.cliente.id = :idPropietario", Factura.class);
        query.setParameter("idPropietario", propietario.getId());
        List<Factura> resultado = query.getResultList();
        if ((resultado != null) && !resultado.isEmpty()) {
            return resultado.get(0);
        } else {
            return null;
        }
    }   
}