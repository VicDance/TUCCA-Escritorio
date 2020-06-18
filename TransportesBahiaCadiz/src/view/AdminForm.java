/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import connector.Conector;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import scripts.Inserts;
import serializable.Cliente;
import serializable.Linea;
import serializable.Municipio;
import serializable.Nucleo;
import serializable.Parada;
import serializable.Usuario;
import serializable.Zona;
import static view.LoginForm.objectIn;
import static view.LoginForm.objectOut;

/**
 *
 * @author Vicky
 */
public class AdminForm extends javax.swing.JFrame {

    private DefaultTableModel model;
    private Object[] columnas;
    private String[] newDatos;
    private int size;
    private InsertForm inf;
    private DeleteForm df;

    public AdminForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        setColumnasUsuarios();
        setFilasUsuarios();
        lblTitle.setText(lblUsuarios.getText());
    }

    private void enviaTexto(String texto) {
        try {
            objectOut.writeUTF(texto);
            objectOut.flush();
            objectOut.reset();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setColumnasUsuarios() {
        columnas = new Object[]{"Id usuario", "Nombre", "Email", "Fecha nacimiento", "Teléfono"};
        try {
            enviaTexto("usuarios");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
        //model = new DefaultTableModel(columnas, size);
    }

    private void setFilasUsuarios() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                Usuario usuario = (Usuario) objectIn.readObject();
                filas = new Object[]{usuario.getId(), usuario.getNombre(), usuario.getCorreo(),
                    usuario.getFecha_nac(), usuario.getTfno()};

                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));

        //table.setModel(model);
    }

    private void setColumnasLineas() {
        columnas = new Object[]{"Id linea", "Nombre linea"};
        try {
            enviaTexto("lineas");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
    }

    private void setFilasLineas() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                Linea linea = (Linea) objectIn.readObject();
                filas = new Object[]{linea.getIdLinea(), linea.getNombreLinea()};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        //table.setModel(model);
    }

    private void setColumnasParadas() {
        columnas = new Object[]{"Id parada", "Id zona", "Nombre parada", "Latitud", "Longitud"};
        try {
            enviaTexto("paradas");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
        //model = new DefaultTableModel(columnas, size);
    }

    private void setFilasParadas() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                Parada parada = (Parada) objectIn.readObject();
                filas = new Object[]{parada.getIdParada(), parada.getIdZona(), parada.getNombreParada()
                , parada.getLatitud(), parada.getLongitud()};

                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        //table.setModel(model);
    }

    private void setColumnasMunicipios() {
        columnas = new Object[]{"Id municipio", "Nombre municipio"};
        try {
            enviaTexto("municipios");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
        //model = new DefaultTableModel(columnas, size);
    }

    private void setFilasMunicipios() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                Municipio mun = (Municipio) objectIn.readObject();
                filas = new Object[]{mun.getIdMunicipio(), mun.getNombreMunicipio()};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        //table.setModel(model);
    }

    private void setColumnasNucleos() {
        columnas = new Object[]{"Id nucleo", "Id municipio", "Id zona", "Nombre nucleo"};
        try {
            enviaTexto("nucleos");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
        //model = new DefaultTableModel(columnas, size);
    }

    private void setFilasNucleos() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                Nucleo nucleo = (Nucleo) objectIn.readObject();
                filas = new Object[]{nucleo.getIdNucleo(), nucleo.getIdMunicipio(), nucleo.getIdZona(), nucleo.getNombreNucleo()};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        //table.setModel(model);
    }

    private void setColumnasZonas() {
        columnas = new Object[]{"Id zona", "Nombre zona"};
        try {
            enviaTexto("zonas");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
        //model = new DefaultTableModel(columnas, size);
    }

    private void setFilasZonas() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            String datos;
            try {
                Zona zona = (Zona) objectIn.readObject();
                filas = new Object[]{zona.getIdZona(), zona.getNombreZona()};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        //table.setModel(model);
    }

    private void setColumnasClientes() {
        columnas = new Object[]{"Id cliente", "Nombre", "Email", "Fecha nacimiento", "Teléfono"};
        try {
            enviaTexto("clientes");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
        //model = new DefaultTableModel(columnas, size);
    }

    private void setFilasClientes() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                Cliente cli = (Cliente) objectIn.readObject();
                filas = new Object[]{cli.getIdCliente(), cli.getNombre(), cli.getCorreo(), cli.getFecha_nac(), cli.getTfno()};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        //table.setModel(model);
    }

    private void setColumnasRevisores() {
        columnas = new Object[]{"Id revisor", "Nombre", "Email", "Fecha nacimiento", "Teléfono"};
        try {
            enviaTexto("revisores");
            size = objectIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = (DefaultTableModel) table.getModel();
        model.setColumnCount(0);
        for (int i = 0; i < columnas.length; i++) {
            model.addColumn(columnas[i], columnas);
        }
        //model = new DefaultTableModel(columnas, size);
    }

    private void setFilasRevisores() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                Cliente cli = (Cliente) objectIn.readObject();
                filas = new Object[]{cli.getIdCliente(), cli.getNombre(), cli.getCorreo(), cli.getFecha_nac(), cli.getTfno()};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
        //table.setModel(model);
    }

    private void buscar(String campoBusqueda, String texto) {
        try {
            switch (campoBusqueda) {
                case "usuario":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasUsuarios();
                    break;

                case "clientes_busqueda":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasClientes();
                    break;

                case "revisor":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasRevisores();
                    break;

                case "linea":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasLineas();
                    break;

                case "municipio":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasMunicipios();
                    break;

                case "nucleo":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasNucleos();
                    break;

                case "parada":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasParadas();
                    break;

                case "zona":
                    enviaTexto(campoBusqueda);
                    enviaTexto(texto);
                    //recibe tamaño array
                    size = objectIn.readInt();
                    setFilasZonas();
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarInsert() {
        inf = new InsertForm();
        inf.setVisible(true);
        inf.setLocationRelativeTo(null);
    }

    private void mostrarBorrado() {
        df = new DeleteForm();
        df.setVisible(true);
        df.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        lblMin = new javax.swing.JLabel();
        lblMenu = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pnlLateral = new javax.swing.JPanel();
        lblUsuarios = new javax.swing.JLabel();
        lblRevisor = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        lblLineas = new javax.swing.JLabel();
        lblMunicipios = new javax.swing.JLabel();
        lblCabecera = new javax.swing.JLabel();
        lblRegular = new javax.swing.JLabel();
        lblParadas = new javax.swing.JLabel();
        lblNucleos = new javax.swing.JLabel();
        lblZonas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnAñadir = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblRefresh = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(44, 62, 80));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(248, 148, 6));

        lblClose.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        lblClose.setForeground(new java.awt.Color(255, 255, 255));
        lblClose.setText("X");
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });

        lblMin.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        lblMin.setForeground(new java.awt.Color(255, 255, 255));
        lblMin.setText("-");
        lblMin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinMouseClicked(evt);
            }
        });

        lblMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/indice.png"))); // NOI18N
        lblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMenuMouseClicked(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Usuarios");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMin)
                .addGap(31, 31, 31)
                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMin)
                    .addComponent(lblClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));

        pnlLateral.setBackground(new java.awt.Color(44, 62, 100));

        lblUsuarios.setBackground(new java.awt.Color(44, 62, 100));
        lblUsuarios.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuarios.setText("Usuarios");
        lblUsuarios.setOpaque(true);
        lblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseExited(evt);
            }
        });

        lblRevisor.setBackground(new java.awt.Color(44, 62, 100));
        lblRevisor.setForeground(new java.awt.Color(255, 255, 255));
        lblRevisor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRevisor.setText("Revisor");
        lblRevisor.setOpaque(true);
        lblRevisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRevisorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRevisorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRevisorMouseExited(evt);
            }
        });

        lblCliente.setBackground(new java.awt.Color(44, 62, 100));
        lblCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCliente.setText("Cliente");
        lblCliente.setOpaque(true);
        lblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblClienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblClienteMouseExited(evt);
            }
        });

        lblLineas.setBackground(new java.awt.Color(44, 62, 100));
        lblLineas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLineas.setForeground(new java.awt.Color(255, 255, 255));
        lblLineas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLineas.setText("Líneas");
        lblLineas.setOpaque(true);
        lblLineas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLineasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLineasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLineasMouseExited(evt);
            }
        });

        lblMunicipios.setBackground(new java.awt.Color(44, 62, 100));
        lblMunicipios.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMunicipios.setForeground(new java.awt.Color(255, 255, 255));
        lblMunicipios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMunicipios.setText("Municipios");
        lblMunicipios.setOpaque(true);
        lblMunicipios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMunicipiosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMunicipiosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMunicipiosMouseExited(evt);
            }
        });

        lblCabecera.setBackground(new java.awt.Color(44, 62, 100));
        lblCabecera.setForeground(new java.awt.Color(255, 255, 255));
        lblCabecera.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCabecera.setText("Cabecera");
        lblCabecera.setOpaque(true);
        lblCabecera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCabeceraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCabeceraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCabeceraMouseExited(evt);
            }
        });

        lblRegular.setBackground(new java.awt.Color(44, 62, 100));
        lblRegular.setForeground(new java.awt.Color(255, 255, 255));
        lblRegular.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRegular.setText("Regular");
        lblRegular.setOpaque(true);
        lblRegular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegularMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRegularMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRegularMouseExited(evt);
            }
        });

        lblParadas.setBackground(new java.awt.Color(44, 62, 100));
        lblParadas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblParadas.setForeground(new java.awt.Color(255, 255, 255));
        lblParadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblParadas.setText("Paradas");
        lblParadas.setOpaque(true);
        lblParadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblParadasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblParadasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblParadasMouseExited(evt);
            }
        });

        lblNucleos.setBackground(new java.awt.Color(44, 62, 100));
        lblNucleos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNucleos.setForeground(new java.awt.Color(255, 255, 255));
        lblNucleos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNucleos.setText("Núcleos");
        lblNucleos.setOpaque(true);
        lblNucleos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNucleosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNucleosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNucleosMouseExited(evt);
            }
        });

        lblZonas.setBackground(new java.awt.Color(44, 62, 100));
        lblZonas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblZonas.setForeground(new java.awt.Color(255, 255, 255));
        lblZonas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblZonas.setText("Zonas");
        lblZonas.setOpaque(true);
        lblZonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblZonasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblZonasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblZonasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlLateralLayout = new javax.swing.GroupLayout(pnlLateral);
        pnlLateral.setLayout(pnlLateralLayout);
        pnlLateralLayout.setHorizontalGroup(
            pnlLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
            .addComponent(lblLineas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMunicipios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblRevisor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblCabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblRegular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblNucleos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblParadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblZonas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlLateralLayout.setVerticalGroup(
            pnlLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLateralLayout.createSequentialGroup()
                .addComponent(lblUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRevisor, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblLineas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblParadas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCabecera, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRegular, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMunicipios, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNucleos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblZonas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(44, 62, 80));
        jScrollPane1.setAlignmentX(0.0F);
        jScrollPane1.setAlignmentY(0.0F);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setAlignmentX(0.0F);
        table.setAlignmentY(0.0F);
        table.setColumnSelectionAllowed(true);
        table.setGridColor(new java.awt.Color(248, 148, 6));
        jScrollPane1.setViewportView(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        btnAñadir.setBackground(new java.awt.Color(34, 167, 240));
        btnAñadir.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnAñadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAñadir.setText("Añadir");
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });

        btnBorrar.setBackground(new java.awt.Color(240, 15, 12));
        btnBorrar.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Búsqueda");

        txtBusqueda.setToolTipText("Búsqueda por nombre");
        txtBusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyTyped(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Refrescar datos");

        lblRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh.png"))); // NOI18N
        lblRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRefreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pnlLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(btnAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(145, 145, 145)
                        .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addComponent(lblRefresh, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        enviaTexto("exit");
        System.exit(0);
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinMouseClicked

    private void lblMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenuMouseClicked
        int posicion = pnlLateral.getX();
        if (posicion > -1) {
            Animacion.Animacion.mover_izquierda(0, -188, 2, 2, pnlLateral);
            jPanel2.setSize(jPanel2.getWidth() + 142, jPanel2.getHeight());
            jScrollPane1.setBounds(jScrollPane1.getX() - 142, jScrollPane1.getY(), jPanel2.getWidth() - 155, jScrollPane1.getHeight());
            table.setSize(jScrollPane1.getWidth(), table.getHeight());

        } else {
            Animacion.Animacion.mover_derecha(-188, 0, 2, 2, pnlLateral);
            jPanel2.setSize(jPanel2.getWidth() - 142, jPanel2.getHeight());
            jScrollPane1.setBounds(jScrollPane1.getX() + 142, jScrollPane1.getY(), jPanel2.getWidth() - 155, jScrollPane1.getHeight());
            table.setSize(jPanel2.getWidth(), table.getHeight());
        }
    }//GEN-LAST:event_lblMenuMouseClicked

    private void lblUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseEntered
        lblUsuarios.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblUsuariosMouseEntered

    private void lblUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseExited
        lblUsuarios.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblUsuariosMouseExited

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked
        setColumnasUsuarios();
        setFilasUsuarios();
        btnAñadir.setEnabled(true);
        btnBorrar.setEnabled(true);
        lblTitle.setText(lblUsuarios.getText());
    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void lblRevisorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRevisorMouseEntered
        lblRevisor.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblRevisorMouseEntered

    private void lblRevisorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRevisorMouseExited
        lblRevisor.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblRevisorMouseExited

    private void lblRevisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRevisorMouseClicked
        btnAñadir.setEnabled(true);
        btnBorrar.setEnabled(true);
        lblTitle.setText(lblRevisor.getText());
        setColumnasRevisores();
        setFilasRevisores();
    }//GEN-LAST:event_lblRevisorMouseClicked

    private void lblClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClienteMouseEntered
        lblCliente.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblClienteMouseEntered

    private void lblClienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClienteMouseExited
        lblCliente.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblClienteMouseExited

    private void lblLineasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLineasMouseClicked
        setColumnasLineas();
        setFilasLineas();
        btnAñadir.setEnabled(false);
        btnBorrar.setEnabled(false);
        lblTitle.setText(lblLineas.getText());
    }//GEN-LAST:event_lblLineasMouseClicked

    private void lblLineasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLineasMouseEntered
        lblLineas.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblLineasMouseEntered

    private void lblLineasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLineasMouseExited
        lblLineas.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblLineasMouseExited

    private void lblMunicipiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMunicipiosMouseClicked
        setColumnasMunicipios();
        setFilasMunicipios();
        btnAñadir.setEnabled(false);
        btnBorrar.setEnabled(false);
        lblTitle.setText(lblMunicipios.getText());
    }//GEN-LAST:event_lblMunicipiosMouseClicked

    private void lblMunicipiosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMunicipiosMouseEntered
        lblMunicipios.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblMunicipiosMouseEntered

    private void lblMunicipiosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMunicipiosMouseExited
        lblMunicipios.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblMunicipiosMouseExited

    private void lblCabeceraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCabeceraMouseClicked
        lblTitle.setText(lblParadas.getText());
    }//GEN-LAST:event_lblCabeceraMouseClicked

    private void lblCabeceraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCabeceraMouseEntered
        lblCabecera.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblCabeceraMouseEntered

    private void lblCabeceraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCabeceraMouseExited
        lblCabecera.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblCabeceraMouseExited

    private void lblRegularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegularMouseClicked
        lblTitle.setText(lblParadas.getText());
    }//GEN-LAST:event_lblRegularMouseClicked

    private void lblRegularMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegularMouseEntered
        lblRegular.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblRegularMouseEntered

    private void lblRegularMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegularMouseExited
        lblRegular.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblRegularMouseExited

    private void lblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClienteMouseClicked
        System.out.println("click");
        setColumnasClientes();
        setFilasClientes();
        btnAñadir.setEnabled(false);
        btnBorrar.setEnabled(false);
        lblTitle.setText(lblCliente.getText());
    }//GEN-LAST:event_lblClienteMouseClicked

    private void lblParadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblParadasMouseClicked
        setColumnasParadas();
        setFilasParadas();
        btnAñadir.setEnabled(false);
        btnBorrar.setEnabled(false);
        lblTitle.setText(lblParadas.getText());
    }//GEN-LAST:event_lblParadasMouseClicked

    private void lblParadasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblParadasMouseEntered
        lblParadas.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblParadasMouseEntered

    private void lblParadasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblParadasMouseExited
        lblParadas.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblParadasMouseExited

    private void lblNucleosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNucleosMouseClicked
        setColumnasNucleos();
        setFilasNucleos();
        btnAñadir.setEnabled(false);
        btnBorrar.setEnabled(false);
        lblTitle.setText(lblNucleos.getText());
    }//GEN-LAST:event_lblNucleosMouseClicked

    private void lblNucleosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNucleosMouseEntered
        lblNucleos.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblNucleosMouseEntered

    private void lblNucleosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNucleosMouseExited
        lblNucleos.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblNucleosMouseExited

    private void txtBusquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyPressed

    }//GEN-LAST:event_txtBusquedaKeyPressed

    private void txtBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyTyped

    }//GEN-LAST:event_txtBusquedaKeyTyped

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        String texto = txtBusqueda.getText();
        String campo;
        if (table.getColumnName(0).trim().toLowerCase().contains("usuario")) {
            campo = "usuario";
            buscar(campo, texto);
        } else if (table.getColumnName(0).trim().toLowerCase().contains("cliente")) {
            campo = "cliente";
            buscar(campo, texto);
        } else if (table.getColumnName(0).trim().toLowerCase().contains("revisor")) {
            campo = "revisor";
            buscar(campo, texto);
        } else if (table.getColumnName(0).trim().toLowerCase().contains("linea")) {
            campo = "linea";
            buscar(campo, texto);
        } else if (table.getColumnName(0).trim().toLowerCase().contains("municipio")) {
            campo = "municipio";
            buscar(campo, texto);
        } else if (table.getColumnName(0).trim().toLowerCase().contains("nucleo")) {
            campo = "nucleo";
            buscar(campo, texto);
        } else if (table.getColumnName(0).trim().toLowerCase().contains("parada")) {
            campo = "parada";
            buscar(campo, texto);
        } else if (table.getColumnName(0).trim().toLowerCase().contains("zona")) {
            campo = "zona";
            buscar(campo, texto);
        }
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        mostrarInsert();
        inf.getLblTitulo().setText(inf.getLblTitulo().getText().concat(" revisor"));
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void lblZonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZonasMouseClicked
        setColumnasZonas();
        setFilasZonas();
        btnAñadir.setEnabled(false);
        btnBorrar.setEnabled(false);
        lblTitle.setText(lblZonas.getText());
    }//GEN-LAST:event_lblZonasMouseClicked

    private void lblZonasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZonasMouseEntered
        lblZonas.setBackground(new Color(248, 148, 6));
    }//GEN-LAST:event_lblZonasMouseEntered

    private void lblZonasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZonasMouseExited
        lblZonas.setBackground(new Color(44, 62, 100));
    }//GEN-LAST:event_lblZonasMouseExited

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        mostrarBorrado();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void lblRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseClicked
        enviaTexto("refrescar");
        GifForm gif = new GifForm();
        gif.setVisible(true);
        gif.setLocationRelativeTo(null);
        gif.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    String fin = objectIn.readUTF();
                    System.out.println(fin);
                    gif.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(r).start();
    }//GEN-LAST:event_lblRefreshMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCabecera;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblLineas;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblMin;
    private javax.swing.JLabel lblMunicipios;
    private javax.swing.JLabel lblNucleos;
    private javax.swing.JLabel lblParadas;
    private javax.swing.JLabel lblRefresh;
    private javax.swing.JLabel lblRegular;
    private javax.swing.JLabel lblRevisor;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JLabel lblZonas;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
