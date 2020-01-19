/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaDeFactura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author danid
 */
@Stateless
public class LineaDeFacturaDAOJPA extends GenericoDAOJPA<LineaDeFactura, Long> implements LineaDeFacturaDAO {

    @Override
    public List<LineaDeFactura> buscarConFactura(Factura factura) {
            TypedQuery<LineaDeFactura> query = em.createQuery("SELECT lin FROM LineaDeFactura AS lin WHERE lin.factura.id = :idFactura", LineaDeFactura.class);
        query.setParameter("idFactura", factura.getId());
        List<LineaDeFactura> resultado = query.getResultList();
        if ((resultado != null) && !resultado.isEmpty()) {
            return resultado;
        } else {
            return null;
        }
    }
    
}
