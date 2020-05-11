/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ClienteDAOImp;
import dao.LineaDAOImp;
import dao.MunicipioDAOImp;
import dao.NucleoDAOImp;
import dao.ParadaDAOImp;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Linea;
import model.Municipio;
import model.Nucleo;
import model.Parada;
import model.Usuario;
import static view.LoginForm.dataIn;
import static view.LoginForm.dataOut;

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

    public AdminForm() {
        initComponents();
        this.setLocationRelativeTo(null);
        setColumnasUsuarios();
        setFilasUsuarios();
    }

    private void setColumnasUsuarios() {
        columnas = new Object[]{"Id usuario", "Nombre", "Email", "Fecha nacimiento", "Teléfono"};
        try {
            dataOut.writeUTF("usuarios");
            dataOut.flush();
            size = dataIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = new DefaultTableModel(columnas, size);
    }

    private void setFilasUsuarios() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            try {
                String datos = dataIn.readUTF();
                newDatos = datos.split("/");
                filas = new Object[]{newDatos[0], newDatos[1], newDatos[2],
                    newDatos[3], newDatos[4]};

                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setModel(model);
    }

    private void setColumnasLineas() {
        columnas = new Object[]{"Id linea", "Nombre linea"};
        try {
            dataOut.writeUTF("lineas");
            dataOut.flush();
            size = dataIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = new DefaultTableModel(columnas, size);
    }

    private void setFilasLineas() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            String datos;
            try {
                datos = dataIn.readUTF();
                newDatos = datos.split("/");
                filas = new Object[]{newDatos[0], newDatos[1]};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setModel(model);
    }

    private void setColumnasParadas() {
        columnas = new Object[]{"Id parada", "Id zona", "Nombre parada", "Latitud", "Longitud"};
        try {
            dataOut.writeUTF("paradas");
            dataOut.flush();
            size = dataIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = new DefaultTableModel(columnas, size);
    }

    private void setFilasParadas() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            String datos;
            try {
                datos = dataIn.readUTF();
                newDatos = datos.split("/");
                filas = new Object[]{newDatos[0], newDatos[1], newDatos[2],
                    newDatos[3], newDatos[4]};

                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setModel(model);
    }

    private void setColumnasMunicipios() {
        columnas = new Object[]{"Id municipio", "Nombre municipio"};
        try {
            dataOut.writeUTF("municipios");
            dataOut.flush();
            size = dataIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = new DefaultTableModel(columnas, size);
    }

    private void setFilasMunicipios() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            String datos;
            try {
                datos = dataIn.readUTF();
                newDatos = datos.split("/");
                filas = new Object[]{newDatos[0], newDatos[1]};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setModel(model);
    }

    private void setColumnasNucleos() {
        columnas = new Object[]{"Id nucleo", "Id municipio", "Id zona", "Nombre nucleo"};
        try {
            dataOut.writeUTF("nucleos");
            dataOut.flush();
            size = dataIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = new DefaultTableModel(columnas, size);
    }

    private void setFilasNucleos() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            String datos;
            try {
                datos = dataIn.readUTF();
                newDatos = datos.split("/");
                filas = new Object[]{newDatos[0], newDatos[1], newDatos[2], newDatos[3]};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setModel(model);
    }

    private void setColumnasZonas() {
        columnas = new Object[]{"Id zona", "Nombre zona"};
        try {
            dataOut.writeUTF("zonas");
            dataOut.flush();
            size = dataIn.readInt();
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        model = new DefaultTableModel(columnas, size);
    }

    private void setFilasZonas() {
        Object[] filas;
        model.setRowCount(0);
        for (int i = 0; i < size; i++) {
            String datos;
            try {
                datos = dataIn.readUTF();
                newDatos = datos.split("/");
                filas = new Object[]{newDatos[0], newDatos[1]};
                model.addRow(filas);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        table.setModel(model);
    }

    private void buscar(String campoBusqueda, String texto) {
        try {
            switch (campoBusqueda) {
                case "usuario":
                    dataOut.writeUTF(campoBusqueda);
                    dataOut.flush();
                    dataOut.writeUTF(texto);
                    dataOut.flush();
                    //recibe tamaño array
                    size = dataIn.readInt();
                    setFilasUsuarios();
                    break;

                case "linea":
                    dataOut.writeUTF(campoBusqueda);
                    dataOut.flush();
                    dataOut.writeUTF(texto);
                    dataOut.flush();
                    //recibe tamaño array
                    size = dataIn.readInt();
                    setFilasLineas();
                    break;

                case "municipio":
                    dataOut.writeUTF(campoBusqueda);
                    dataOut.flush();
                    dataOut.writeUTF(texto);
                    dataOut.flush();
                    //recibe tamaño array
                    size = dataIn.readInt();
                    setFilasMunicipios();
                    break;

                case "nucleo":
                    dataOut.writeUTF(campoBusqueda);
                    dataOut.flush();
                    dataOut.writeUTF(texto);
                    dataOut.flush();
                    //recibe tamaño array
                    size = dataIn.readInt();
                    setFilasNucleos();
                    break;

                case "parada":
                    dataOut.writeUTF(campoBusqueda);
                    dataOut.flush();
                    dataOut.writeUTF(texto);
                    dataOut.flush();
                    //recibe tamaño array
                    size = dataIn.readInt();
                    setFilasParadas();
                    break;

                case "zona":
                    dataOut.writeUTF(campoBusqueda);
                    dataOut.flush();
                    dataOut.writeUTF(texto);
                    dataOut.flush();
                    //recibe tamaño array
                    size = dataIn.readInt();
                    setFilasZonas();
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void añade(String campo) {
        try {
            switch (campo) {
                case "usuario":
                    dataOut.writeUTF(campo);
                    dataOut.flush();
                    break;

                case "linea":
                    dataOut.writeUTF(campo);
                    dataOut.flush();
                    break;

                case "municipio":
                    dataOut.writeUTF(campo);
                    dataOut.flush();
                    break;

                case "nucleo":
                    dataOut.writeUTF(campo);
                    dataOut.flush();
                    break;

                case "parada":
                    dataOut.writeUTF(campo);
                    dataOut.flush();
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

    private void muestraCamposLinea() {
        inf.getLblTitulo().setText(inf.getLblTitulo().getText().concat(" línea"));
        inf.getLblUsuario().setAlignmentX(LEFT_ALIGNMENT);
        inf.getLblUsuario().setHorizontalTextPosition((int) LEFT_ALIGNMENT);
        inf.getLblUsuario().setText("Nombre línea");
        inf.getLblContraseña().setVisible(false);
        inf.getLblContraseña().setEnabled(false);
        inf.getTxtPassword().setVisible(false);
        inf.getTxtPassword().setEnabled(false);
        inf.getLblRepetirContraseña().setVisible(false);
        inf.getLblRepetirContraseña().setEnabled(false);
        inf.getTxtRepetirContraseña().setVisible(false);
        inf.getTxtRepetirContraseña().setEnabled(false);
        inf.getLblCorreo().setVisible(false);
        inf.getLblCorreo().setEnabled(false);
        inf.getTxtCorreo().setVisible(false);
        inf.getTxtCorreo().setEnabled(false);
        inf.getLblTfno().setVisible(false);
        inf.getLblTfno().setEnabled(false);
        inf.getTxtTfno().setVisible(false);
        inf.getTxtTfno().setEnabled(false);
        inf.getLblFechaNac().setVisible(false);
        inf.getLblFechaNac().setEnabled(false);
        inf.getDateChooserNacimiento().setVisible(false);
        inf.getDateChooserNacimiento().setEnabled(false);
        inf.getBtnInsertar().setLocation(inf.getBtnInsertar().getX(), inf.getBtnInsertar().getY() + 200);
        /*inf.getjPanel2().setSize(inf.getjPanel2().getWidth(), 400);*/
        inf.setSize(inf.getjPanel2().getWidth(), 400);
    }

    private void muestraCamposMunicipio() {
        inf.getLblTitulo().setText(inf.getLblTitulo().getText().concat(" municipio"));
        inf.getLblUsuario().setAlignmentX(LEFT_ALIGNMENT);
        inf.getLblUsuario().setHorizontalTextPosition((int) LEFT_ALIGNMENT);
        inf.getLblUsuario().setText("Nombre municipio");
        inf.getLblContraseña().setVisible(false);
        inf.getLblContraseña().setEnabled(false);
        inf.getTxtPassword().setVisible(false);
        inf.getTxtPassword().setEnabled(false);
        inf.getLblRepetirContraseña().setVisible(false);
        inf.getLblRepetirContraseña().setEnabled(false);
        inf.getTxtRepetirContraseña().setVisible(false);
        inf.getTxtRepetirContraseña().setEnabled(false);
        inf.getLblCorreo().setVisible(false);
        inf.getLblCorreo().setEnabled(false);
        inf.getTxtCorreo().setVisible(false);
        inf.getTxtCorreo().setEnabled(false);
        inf.getLblTfno().setVisible(false);
        inf.getLblTfno().setEnabled(false);
        inf.getTxtTfno().setVisible(false);
        inf.getTxtTfno().setEnabled(false);
        inf.getLblFechaNac().setVisible(false);
        inf.getLblFechaNac().setEnabled(false);
        inf.getDateChooserNacimiento().setVisible(false);
        inf.getDateChooserNacimiento().setEnabled(false);
        inf.getBtnInsertar().setLocation(inf.getBtnInsertar().getX(), inf.getBtnInsertar().getY() + 200);
        /*inf.getjPanel2().setSize(inf.getjPanel2().getWidth(), 400);*/
        inf.setSize(inf.getjPanel2().getWidth(), 400);
    }

    private void muestraCamposZonas() {
        inf.getLblTitulo().setText(inf.getLblTitulo().getText().concat(" zonas"));
        inf.getLblUsuario().setAlignmentX(LEFT_ALIGNMENT);
        inf.getLblUsuario().setHorizontalTextPosition((int) LEFT_ALIGNMENT);
        inf.getLblUsuario().setText("Id zona");
        inf.getLblContraseña().setVisible(false);
        inf.getLblContraseña().setEnabled(false);
        inf.getTxtPassword().setVisible(false);
        inf.getTxtPassword().setEnabled(false);
        inf.getLblRepetirContraseña().setVisible(false);
        inf.getLblRepetirContraseña().setEnabled(false);
        inf.getTxtRepetirContraseña().setVisible(false);
        inf.getTxtRepetirContraseña().setEnabled(false);
        inf.getLblCorreo().setText("Nombre zona");
        /*inf.getLblCorreo().setVisible(false);
         inf.getLblCorreo().setEnabled(false);
         inf.getTxtCorreo().setVisible(false);
         inf.getTxtCorreo().setEnabled(false);*/
        inf.getLblTfno().setVisible(false);
        inf.getLblTfno().setEnabled(false);
        inf.getTxtTfno().setVisible(false);
        inf.getTxtTfno().setEnabled(false);
        inf.getLblFechaNac().setVisible(false);
        inf.getLblFechaNac().setEnabled(false);
        inf.getDateChooserNacimiento().setVisible(false);
        inf.getDateChooserNacimiento().setEnabled(false);
        inf.getBtnInsertar().setLocation(inf.getBtnInsertar().getX(), inf.getBtnInsertar().getY() + 200);
        /*inf.getjPanel2().setSize(inf.getjPanel2().getWidth(), 400);*/
        inf.setSize(inf.getjPanel2().getWidth(), 400);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(lblMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));

        pnlLateral.setBackground(new java.awt.Color(248, 135, 37));

        lblUsuarios.setBackground(new java.awt.Color(248, 135, 37));
        lblUsuarios.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuarios.setText("USUARIOS");
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

        lblRevisor.setBackground(new java.awt.Color(248, 135, 37));
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

        lblCliente.setBackground(new java.awt.Color(248, 135, 37));
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

        lblLineas.setBackground(new java.awt.Color(248, 135, 37));
        lblLineas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        lblMunicipios.setBackground(new java.awt.Color(248, 135, 37));
        lblMunicipios.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        lblCabecera.setBackground(new java.awt.Color(248, 135, 37));
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

        lblRegular.setBackground(new java.awt.Color(248, 135, 37));
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

        lblParadas.setBackground(new java.awt.Color(248, 135, 37));
        lblParadas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        lblNucleos.setBackground(new java.awt.Color(248, 135, 37));
        lblNucleos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        lblZonas.setBackground(new java.awt.Color(248, 135, 37));
        lblZonas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
            .addComponent(lblParadas, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setAlignmentX(0.0F);
        table.setAlignmentY(0.0F);
        table.setColumnSelectionAllowed(true);
        table.setGridColor(new java.awt.Color(248, 148, 6));
        jScrollPane1.setViewportView(table);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pnlLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(btnAñadir, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(145, 145, 145)
                                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(291, 291, 291)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        try {
            dataOut.writeUTF("exit");
            dataOut.flush();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinMouseClicked

    private void lblMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenuMouseClicked
        int posicion = pnlLateral.getX();
        if (posicion > -1) {
            Animacion.Animacion.mover_izquierda(0, -188, 2, 2, pnlLateral);
            jPanel2.setSize(jPanel2.getWidth() + 142, jPanel2.getHeight());
            jScrollPane1.setBounds(jScrollPane1.getX() - 142, jScrollPane1.getY(), jPanel2.getWidth(), jScrollPane1.getHeight());
            table.setSize(jPanel2.getWidth(), table.getHeight());

        } else {
            Animacion.Animacion.mover_derecha(-188, 0, 2, 2, pnlLateral);
            jPanel2.setSize(jPanel2.getWidth() - 142, jPanel2.getHeight());
            jScrollPane1.setBounds(jScrollPane1.getX() + 142, jScrollPane1.getY(), jPanel2.getWidth()-142, jScrollPane1.getHeight());
            table.setSize(jPanel2.getWidth(), table.getHeight());
        }
    }//GEN-LAST:event_lblMenuMouseClicked

    private void lblUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseEntered
        lblUsuarios.setBackground(new Color(44, 62, 80));
        lblUsuarios.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblUsuariosMouseEntered

    private void lblUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseExited
        lblUsuarios.setBackground(new Color(248, 135, 37));
        lblUsuarios.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblUsuariosMouseExited

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked
        setColumnasUsuarios();
        setFilasUsuarios();
    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void lblRevisorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRevisorMouseEntered
        lblRevisor.setBackground(new Color(44, 62, 80));
        lblRevisor.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblRevisorMouseEntered

    private void lblRevisorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRevisorMouseExited
        lblRevisor.setBackground(new Color(248, 135, 37));
        lblRevisor.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblRevisorMouseExited

    private void lblRevisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRevisorMouseClicked

    }//GEN-LAST:event_lblRevisorMouseClicked

    private void lblClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClienteMouseEntered
        lblCliente.setBackground(new Color(44, 62, 80));
        lblCliente.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblClienteMouseEntered

    private void lblClienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClienteMouseExited
        lblCliente.setBackground(new Color(248, 135, 37));
        lblCliente.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblClienteMouseExited

    private void lblLineasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLineasMouseClicked
        setColumnasLineas();
        setFilasLineas();
    }//GEN-LAST:event_lblLineasMouseClicked

    private void lblLineasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLineasMouseEntered
        lblLineas.setBackground(new Color(44, 62, 80));
        lblLineas.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblLineasMouseEntered

    private void lblLineasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLineasMouseExited
        lblLineas.setBackground(new Color(248, 135, 37));
        lblLineas.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblLineasMouseExited

    private void lblMunicipiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMunicipiosMouseClicked
        setColumnasMunicipios();
        setFilasMunicipios();
    }//GEN-LAST:event_lblMunicipiosMouseClicked

    private void lblMunicipiosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMunicipiosMouseEntered
        lblMunicipios.setBackground(new Color(44, 62, 80));
        lblMunicipios.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblMunicipiosMouseEntered

    private void lblMunicipiosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMunicipiosMouseExited
        lblMunicipios.setBackground(new Color(248, 135, 37));
        lblMunicipios.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblMunicipiosMouseExited

    private void lblCabeceraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCabeceraMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCabeceraMouseClicked

    private void lblCabeceraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCabeceraMouseEntered
        lblCabecera.setBackground(new Color(44, 62, 80));
        lblCabecera.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblCabeceraMouseEntered

    private void lblCabeceraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCabeceraMouseExited
        lblCabecera.setBackground(new Color(248, 135, 37));
        lblCabecera.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblCabeceraMouseExited

    private void lblRegularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegularMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblRegularMouseClicked

    private void lblRegularMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegularMouseEntered
        lblRegular.setBackground(new Color(44, 62, 80));
        lblRegular.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblRegularMouseEntered

    private void lblRegularMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegularMouseExited
        lblRegular.setBackground(new Color(248, 135, 37));
        lblRegular.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblRegularMouseExited

    private void lblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblClienteMouseClicked

    private void lblParadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblParadasMouseClicked
        setColumnasParadas();
        setFilasParadas();
    }//GEN-LAST:event_lblParadasMouseClicked

    private void lblParadasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblParadasMouseEntered
        lblParadas.setBackground(new Color(44, 62, 80));
        lblParadas.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblParadasMouseEntered

    private void lblParadasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblParadasMouseExited
        lblParadas.setBackground(new Color(248, 135, 37));
        lblParadas.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblParadasMouseExited

    private void lblNucleosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNucleosMouseClicked
        setColumnasNucleos();
        setFilasNucleos();
    }//GEN-LAST:event_lblNucleosMouseClicked

    private void lblNucleosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNucleosMouseEntered
        lblNucleos.setBackground(new Color(44, 62, 80));
        lblNucleos.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblNucleosMouseEntered

    private void lblNucleosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNucleosMouseExited
        lblNucleos.setBackground(new Color(248, 135, 37));
        lblNucleos.setForeground(new Color(0, 0, 0));
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
        if (table.getColumnName(0).trim().toLowerCase().contains("usuario")) {
            mostrarInsert();
            inf.getLblTitulo().setText(inf.getLblTitulo().getText().concat(" revisor"));
        } else if (table.getColumnName(0).trim().toLowerCase().contains("linea")) {
            mostrarInsert();
            muestraCamposLinea();
        } else if (table.getColumnName(0).trim().toLowerCase().contains("municipio")) {
            mostrarInsert();
            muestraCamposMunicipio();
        } else if (table.getColumnName(0).trim().toLowerCase().contains("nucleo")) {

        } else if (table.getColumnName(0).trim().toLowerCase().contains("parada")) {
            /*campo = "bparada";
             inf.getLblTitulo().setText(inf.getLblTitulo().getText().concat(" parada"));*/
        } else if (table.getColumnName(0).trim().toLowerCase().contains("zona")) {
            mostrarInsert();
            muestraCamposZonas();
        }
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void lblZonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZonasMouseClicked
        setColumnasZonas();
        setFilasZonas();
    }//GEN-LAST:event_lblZonasMouseClicked

    private void lblZonasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZonasMouseEntered
        lblZonas.setBackground(new Color(44, 62, 80));
        lblZonas.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblZonasMouseEntered

    private void lblZonasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblZonasMouseExited
        lblZonas.setBackground(new Color(248, 135, 37));
        lblZonas.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_lblZonasMouseExited

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        try {
            /*dataOut.writeUTF("bzona");
             dataOut.flush();
             dataOut.writeUTF((String) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
             dataOut.flush();
             String estado = dataIn.readUTF();
             if(estado.equalsIgnoreCase("correcto")){
             JOptionPane.showMessageDialog(this, "Borrado correcto");
             }else{
             JOptionPane.showMessageDialog(this, "Borrado incorrecto");
             }*/
            dataOut.writeUTF("bzona");
            dataOut.flush();
            if (table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString().length() == 1) {
                String id = (String) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                String nombre = (String) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn() + 1);
                dataOut.writeUTF(id + "/" + nombre);
                dataOut.flush();
            } else {
                String id = (String) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn() - 1);
                String nombre = (String) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                dataOut.writeUTF(id + "/" + nombre);
                dataOut.flush();
            }
            String estado = dataIn.readUTF();
            if (estado.equalsIgnoreCase("correcto")) {
                JOptionPane.showMessageDialog(this, "Borrado correcto");
            } else {
                JOptionPane.showMessageDialog(this, "Borrado incorrecto");
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
    }//GEN-LAST:event_btnBorrarActionPerformed

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
    private javax.swing.JLabel lblRegular;
    private javax.swing.JLabel lblRevisor;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JLabel lblZonas;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
