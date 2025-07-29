package src.model;

import java.io.IOException;
import java.util.Set;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.dao.GenericDao;

public class Janelas {

    private JTable tabela;
    private DefaultTableModel model;

    public JTable tabelaProdutos(GenericDao<Produto> dao) throws IOException, ClassNotFoundException {
        String[] colunas = {"Código", "Descrição", "Preço"};
        Set<Produto> produtos = dao.getAll();
        Object[][] dados = new Object[produtos.size()][3];

        int i = 0;
        for (Produto p : produtos) {
            dados[i][0] = p.getCodigo();     
            dados[i][1] = p.getDescricao();  
            dados[i][2] = p.getPreco();      
            i++;
        }

        model = new DefaultTableModel(dados, colunas);
        tabela = new JTable(model);
        return tabela;
    }

    public JPanel menuBotoes(GenericDao<Produto> dao){
        JPanel panel = new JPanel();

        JButton add = new JButton("Adicionar");
        add.addActionListener(e -> {
            JFrame adicionar = new JFrame("Adicionar produto");
            adicionar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            adicionar.setSize(400, 200);
            adicionar.setLocationRelativeTo(null);
            adicionar.setLayout(new BoxLayout(adicionar.getContentPane(), BoxLayout.Y_AXIS));

            JTextField cod = new JTextField();
            JTextField desc = new JTextField();
            JTextField valor = new JTextField();
            JButton ok = new JButton("Finalizar");

            adicionar.add(new JLabel("Código:"));
            adicionar.add(cod);
            adicionar.add(new JLabel("Descrição:"));
            adicionar.add(desc);
            adicionar.add(new JLabel("Preço:"));
            adicionar.add(valor);
            adicionar.add(ok);

            ok.addActionListener(ev -> {
                Produto p = new Produto();
                p.setCodigo(cod.getText());
                p.setDescricao(desc.getText());
                p.setPreco(valor.getText());
                try {
                    if (dao.salvar(p)) {
                        model.addRow(new Object[]{p.getCodigo(), p.getDescricao(), p.getPreco()});
                        adicionar.dispose();
                    } else {
                        JOptionPane.showMessageDialog(adicionar, "Produto com este código já existe.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(adicionar, "Erro ao adicionar produto.");
                }
            });

            adicionar.setVisible(true);
        });

        JButton rem = new JButton("Remover");
        rem.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                String codigo = (String) model.getValueAt(linha, 0);
                Produto p = new Produto();
                p.setCodigo(codigo);
                try {
                    if (dao.remover(p)) {
                        model.removeRow(linha);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao remover produto.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha para remover.");
            }
        });

        JButton up = new JButton("Atualizar");
        up.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                String codigoOriginal = (String) model.getValueAt(linha, 0);

                JFrame editar = new JFrame("Atualizar produto");
                editar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editar.setSize(400, 200);
                editar.setLocationRelativeTo(null);
                editar.setLayout(new BoxLayout(editar.getContentPane(), BoxLayout.Y_AXIS));

                JTextField cod = new JTextField((String) model.getValueAt(linha, 0));
                JTextField desc = new JTextField((String) model.getValueAt(linha, 1));
                JTextField valor = new JTextField((String) model.getValueAt(linha, 2));
                JButton ok = new JButton("Salvar alterações");

                editar.add(new JLabel("Código:"));
                editar.add(cod);
                editar.add(new JLabel("Descrição:"));
                editar.add(desc);
                editar.add(new JLabel("Preço:"));
                editar.add(valor);
                editar.add(ok);

                ok.addActionListener(ev -> {
                    Produto p = new Produto();
                    p.setCodigo(cod.getText());
                    p.setDescricao(desc.getText());
                    p.setPreco(valor.getText());
                    try {
                        if (dao.atualizar(p)) {
                            model.setValueAt(cod.getText(), linha, 0);
                            model.setValueAt(desc.getText(), linha, 1);
                            model.setValueAt(valor.getText(), linha, 2);
                            editar.dispose();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(editar, "Erro ao atualizar produto.");
                    }
                });

                editar.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha para atualizar.");
            }
        });

        panel.add(add);
        panel.add(rem);
        panel.add(up);
        return panel;
    }

    public void mainFrame(String title, GenericDao<Produto> dao) throws IOException, ClassNotFoundException {
        JFrame mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        JTable table = tabelaProdutos(dao);
        mainFrame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel menu = menuBotoes(dao);
        mainFrame.add(menu, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    public Janelas(String title, GenericDao<Produto> dao) throws IOException, ClassNotFoundException {
        mainFrame(title, dao);
    }
}
