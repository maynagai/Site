package dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import modelo.Usuario;
import util.JpaUtil;

public class UsuarioDao implements Serializable { 
    
    EntityManager manager;
    
    public boolean inserir(Usuario user) {
        EntityManager manager = JpaUtil.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        manager.persist(user);
        tx.commit();
        manager.close();
        return true;
    } 
    
    public Usuario alterar(Usuario objeto) {
        manager = JpaUtil.getEntityManager();
        manager.getTransaction().begin();
        objeto = manager.merge(objeto);
        manager.getTransaction().commit();
        manager.close();
        return objeto;
    }

    public Usuario buscarPorCodigo(Object id) {
        Usuario objeto;
        manager = JpaUtil.getEntityManager();
        objeto = manager.find(Usuario.class, id);
        manager.close();
        return objeto;
    }

    public void excluir(Integer id) {
        manager = JpaUtil.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        Usuario temp = manager.find(Usuario.class, id);
        manager.remove(temp);
        tx.commit();
        manager.close();
    }
    
    public Usuario autenticar(Usuario usuario){
        Usuario temp = null; // administrador retornado na consulta ao banco
        EntityManager manager = JpaUtil.getEntityManager();
        TypedQuery<Usuario> query = manager.createQuery("SELECT a FROM Usuario a WHERE a.login = :login AND a.senha = :senha",
                Usuario.class); 
        query.setParameter("login", usuario.getLogin());
        query.setParameter("senha", usuario.getSenha());
        try{
            temp = query.getSingleResult(); 
        }
        catch(Exception e){ 
        }     //aqui poderia haver um tratamento de exceção mais decente
        finally{
            manager.close();
        }        
        return temp;
    }
     
    public List<Usuario> listarTodos() {        
        manager = JpaUtil.getEntityManager();
        CriteriaQuery<Usuario> query = manager.getCriteriaBuilder().createQuery(Usuario.class);
        query.select(query.from(Usuario.class));
        List<Usuario> lista = manager.createQuery(query).getResultList();
        manager.close();      
        return lista;
    }
}