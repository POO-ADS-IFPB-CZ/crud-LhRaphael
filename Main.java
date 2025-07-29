import java.io.IOException;

import src.dao.GenericDao;
import src.model.Janelas;
import src.model.Produto;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        GenericDao<Produto> dao = new GenericDao<>("atencao.dat");

        // Inicializa o arquivo se estiver vazio ou inválido
        if (dao.getAll().isEmpty()) {
            dao.salvar(criarProdutoDeTeste());
        }

        new Janelas("Product Management", dao);
    }

    // Produto de teste (evita tabela vazia e inicializa arquivo)
    private static Produto criarProdutoDeTeste() {
        Produto p = new Produto();
        p.setCodigo("001");
        p.setDescricao("Notebook");
        p.setPreco("4500.00");
        return p;
    }
}
