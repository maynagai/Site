package controle;

import dao.UsuarioDao;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;


 
@ManagedBean(name = "novoUsuario")
@ViewScoped
public class UsuarioMB implements Serializable {

    private Usuario usuario;
    private Usuario aux;            
    private List<Usuario> lista;
    private UsuarioDao dao;

    public UsuarioMB() {
        usuario = new Usuario();
        dao = new UsuarioDao();
        lista = dao.listarTodos();
    }

    public void inserirUsuario() {
        getDao().inserir(getUsuario());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado", null));
        lista.add(usuario);
        limpar();
    }

    public void limpar() {
        usuario = new Usuario();
    } 
    
    public void listar() {
        lista = dao.listarTodos();
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public UsuarioDao getDao() {
        return dao;
    }

    public void setDao(UsuarioDao dao) {
        this.dao = dao;
    }
    
}