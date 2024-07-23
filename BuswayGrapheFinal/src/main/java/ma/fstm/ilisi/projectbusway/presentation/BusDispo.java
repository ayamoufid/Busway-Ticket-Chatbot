/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ma.fstm.ilisi.projectbusway.metier.bo.Arret;
import ma.fstm.ilisi.projectbusway.metier.bo.Bus;
import ma.fstm.ilisi.projectbusway.metier.bo.Voyage;

/**
 *
 * @author Aya
 */
public class BusDispo extends javax.swing.JFrame {

    /**
     * Creates new form BusDispo
     */
	
	
    public BusDispo() {
       
       // initComponents();
    }
    
    public BusDispo(List<Bus> busesDispo) 
    {
         initComponents1(busesDispo);

    }
    
    
    
    private void initComponents1(List<Bus> busesDispo) {
         System.out.println("its mee2");
        // Arret.CreerArrets(); 
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        //jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(jTable1);
  

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane3.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //jLabel1.setText("Selectionnez la date de votre voyage :");

        //jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jPanel2.setLayout(new java.awt.GridLayout(0, 1)); // Modifiez le layout pour Grid pour aligner les panneaux verticalement

        // Boucle pour créer les panneaux
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Bus bus : busesDispo) 
        {
        	
        	System.out.println("je suis bus");
            if (!bus.getLesVoyages().isEmpty()) 
            {      
            	
                //System.out.println("????????????????????????/lesbuss : "+bus.getLesVoyages().getFirst().getLeDepart().getNomStation());
                for (Voyage voyage : bus.getLesVoyages()) 
                {
                	System.out.println("je suis voyage");
                    JPanel jPanel = new JPanel();
                    jPanel.setBackground(jPanel.getComponentCount() % 2 == 0 ? new java.awt.Color(255, 204, 255) : new java.awt.Color(204, 204, 255));

                    JLabel jLabelDepart = new JLabel("Depart :");
                    JLabel jLabelArrivee = new JLabel("Arrivee: ");
                    JLabel jLabelPlacesLibres = new JLabel("Places libres :");
                    JLabel jLabelNumBus = new JLabel("num bus :");

                    JTextField jTextFieldDepart = new JTextField();
                    jTextFieldDepart.setEditable(false);
                   
                    jTextFieldDepart.setText(voyage.getLeDepart().getNomStation() + " " +voyage.getDateDepart().toString());

                    JTextField jTextFieldArrivee = new JTextField();
                    jTextFieldArrivee.setEditable(false);
                    jTextFieldArrivee.setText(voyage.getlArrivee().getNomStation() + " " +voyage.getDateArrivee().toString());

                    JTextField jTextFieldPlacesLibres = new JTextField();
                    jTextFieldPlacesLibres.setEditable(false);
                    jTextFieldPlacesLibres.setText(String.valueOf(bus.getNumeroPlacesLibres()));

                    JTextField jTextFieldNumBus = new JTextField();
                    jTextFieldNumBus.setEditable(false);
                    jTextFieldNumBus.setText(String.valueOf(bus.getNumeroBus()));

                    JButton jButtonVoirVoyage = new JButton("voir voyage");

                    // Ajout d'un gestionnaire d'événements pour le bouton "voir voyage"
                    jButtonVoirVoyage.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            infoBusVoyage a = new infoBusVoyage(voyage);
                            a.setVisible(true);
                            
                        }
                    });

                    jPanel.add(jLabelDepart);
                    jPanel.add(jTextFieldDepart);
                    jPanel.add(jLabelArrivee);
                    jPanel.add(jTextFieldArrivee);
                    jPanel.add(jLabelPlacesLibres);
                    jPanel.add(jTextFieldPlacesLibres);
                    jPanel.add(jLabelNumBus);
                    jPanel.add(jTextFieldNumBus);
                    jPanel.add(jButtonVoirVoyage);
                    jPanel2.add(jPanel);
                }
            }
        }

        jScrollPane4.setViewportView(jPanel2);
        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 18));
        jTextField4.setText("Bus disponible");

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10)); // Modifiez le layout pour FlowLayout

        jPanel6.add(jTextField4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                //.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        //.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//    private void initComponents() {
//
//        jScrollPane1 = new javax.swing.JScrollPane();
//        jTable1 = new javax.swing.JTable();
//        jScrollPane2 = new javax.swing.JScrollPane();
//        jList1 = new javax.swing.JList<>();
//        jScrollPane3 = new javax.swing.JScrollPane();
//        jTable2 = new javax.swing.JTable();
//        jPanel1 = new javax.swing.JPanel();
//        jLabel3 = new javax.swing.JLabel();
//        jLabel10 = new javax.swing.JLabel();
//        jLabel11 = new javax.swing.JLabel();
//        jPanel4 = new javax.swing.JPanel();
//        jLabel1 = new javax.swing.JLabel();
//        jComboBox1 = new javax.swing.JComboBox<>();
//        jScrollPane4 = new javax.swing.JScrollPane();
//        jPanel2 = new javax.swing.JPanel();
//        jPanel8 = new javax.swing.JPanel();
//        jLabel20 = new javax.swing.JLabel();
//        jLabel22 = new javax.swing.JLabel();
//        jLabel23 = new javax.swing.JLabel();
//        jButton4 = new javax.swing.JButton();
//        jTextField8 = new javax.swing.JTextField();
//        jTextField9 = new javax.swing.JTextField();
//        jTextField10 = new javax.swing.JTextField();
//        jTextField11 = new javax.swing.JTextField();
//        jLabel9 = new javax.swing.JLabel();
//        jPanel9 = new javax.swing.JPanel();
//        jLabel21 = new javax.swing.JLabel();
//        jLabel24 = new javax.swing.JLabel();
//        jLabel25 = new javax.swing.JLabel();
//        jButton5 = new javax.swing.JButton();
//        jTextField12 = new javax.swing.JTextField();
//        jTextField13 = new javax.swing.JTextField();
//        jTextField14 = new javax.swing.JTextField();
//        jTextField15 = new javax.swing.JTextField();
//        jLabel17 = new javax.swing.JLabel();
//        jPanel6 = new javax.swing.JPanel();
//        jTextField4 = new javax.swing.JTextField();
//
//        jTable1.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null}
//            },
//            new String [] {
//                "Title 1", "Title 2", "Title 3", "Title 4"
//            }
//        ));
//        jScrollPane1.setViewportView(jTable1);
//
//        jList1.setModel(new javax.swing.AbstractListModel<String>() {
//            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
//            public int getSize() { return strings.length; }
//            public String getElementAt(int i) { return strings[i]; }
//        });
//        jScrollPane2.setViewportView(jList1);
//
//        jTable2.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null}
//            },
//            new String [] {
//                "Title 1", "Title 2", "Title 3", "Title 4"
//            }
//        ));
//        jScrollPane3.setViewportView(jTable2);
//
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 293, Short.MAX_VALUE)
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 100, Short.MAX_VALUE)
//        );
//
//        jLabel3.setText("jLabel3");
//
//        jLabel10.setText("jLabel10");
//
//        jLabel11.setText("jLabel11");
//
//        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
//        jPanel4.setLayout(jPanel4Layout);
//        jPanel4Layout.setHorizontalGroup(
//            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 0, Short.MAX_VALUE)
//        );
//        jPanel4Layout.setVerticalGroup(
//            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 51, Short.MAX_VALUE)
//        );
//
//        jLabel1.setText("Selectionnez la date de votre voyage :");
//
//        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//
//        jPanel8.setBackground(new java.awt.Color(255, 204, 255));
//
//        jLabel20.setText("Depart :");
//
//        jLabel22.setText("Places libres :");
//
//        jLabel23.setText("Arrivee: ");
//
//        jButton4.setBackground(new java.awt.Color(204, 255, 255));
//        jButton4.setText("voir voyage");
//
//        jTextField8.setEditable(false);
//        jTextField8.setText("Facultes 07:15");
//
//        jTextField9.setEditable(false);
//        jTextField9.setText("Casa Port 08:00");
//
//        jTextField10.setEditable(false);
//        jTextField10.setText("3");
//
//        jTextField11.setEditable(false);
//        jTextField11.setText("800");
//        jTextField11.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField11ActionPerformed(evt);
//            }
//        });
//
//        jLabel9.setText("numero bus :");
//
//        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
//        jPanel8.setLayout(jPanel8Layout);
//        jPanel8Layout.setHorizontalGroup(
//            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel8Layout.createSequentialGroup()
//                .addGap(21, 21, 21)
//                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel22)
//                    .addComponent(jLabel20))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addComponent(jLabel9)
//                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                        .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
//                        .addComponent(jTextField10)))
//                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(jPanel8Layout.createSequentialGroup()
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
//                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
//                                .addComponent(jLabel23)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                        .addGap(18, 18, 18))
//                    .addGroup(jPanel8Layout.createSequentialGroup()
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
//        );
//        jPanel8Layout.setVerticalGroup(
//            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel8Layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel9))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
//                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel20)
//                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel23))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel22)
//                    .addComponent(jButton4)
//                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//        );
//
//        jPanel9.setBackground(new java.awt.Color(204, 204, 255));
//
//        jLabel21.setText("Depart :");
//
//        jLabel24.setText("Places libres :");
//
//        jLabel25.setText("Arrivee: ");
//
//        jButton5.setBackground(new java.awt.Color(204, 255, 255));
//        jButton5.setText("voir voyage");
//
//        jTextField12.setEditable(false);
//        jTextField12.setText("Facultes 07:15");
//
//        jTextField13.setEditable(false);
//        jTextField13.setText("Casa Port 08:00");
//
//        jTextField14.setEditable(false);
//        jTextField14.setText("3");
//
//        jTextField15.setEditable(false);
//        jTextField15.setText("800");
//
//        jLabel17.setText("num bus :");
//
//        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
//        jPanel9.setLayout(jPanel9Layout);
//        jPanel9Layout.setHorizontalGroup(
//            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel9Layout.createSequentialGroup()
//                .addGap(21, 21, 21)
//                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(jLabel24)
//                    .addComponent(jLabel21))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(jPanel9Layout.createSequentialGroup()
//                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                            .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
//                            .addComponent(jTextField14))
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
//                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
//                                .addComponent(jLabel25)
//                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                        .addGap(18, 18, 18))
//                    .addGroup(jPanel9Layout.createSequentialGroup()
//                        .addGap(75, 75, 75)
//                        .addComponent(jLabel17)
//                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
//        );
//        jPanel9Layout.setVerticalGroup(
//            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel9Layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel17))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
//                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel21)
//                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel25))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(jLabel24)
//                    .addComponent(jButton5)
//                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//        );
//
//        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
//        jPanel2.setLayout(jPanel2Layout);
//        jPanel2Layout.setHorizontalGroup(
//            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel2Layout.createSequentialGroup()
//                .addGap(23, 23, 23)
//                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addContainerGap(133, Short.MAX_VALUE))
//        );
//        jPanel2Layout.setVerticalGroup(
//            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel2Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(30, 30, 30)
//                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(53, Short.MAX_VALUE))
//        );
//
//        jScrollPane4.setViewportView(jPanel2);
//
//        jTextField4.setEditable(false);
//        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
//        jTextField4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
//        jTextField4.setText("Bus disponible");
//        jTextField4.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField4ActionPerformed(evt);
//            }
//        });
//
//        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
//        jPanel6.setLayout(jPanel6Layout);
//        jPanel6Layout.setHorizontalGroup(
//            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel6Layout.createSequentialGroup()
//                .addGap(260, 260, 260)
//                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(277, Short.MAX_VALUE))
//        );
//        jPanel6Layout.setVerticalGroup(
//            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel6Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(17, Short.MAX_VALUE))
//        );
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(layout.createSequentialGroup()
//                        .addContainerGap()
//                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(56, 56, 56)
//                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(46, 46, 46)
//                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(36, 36, 36))
//        );
//
//        pack();
//    }// </editor-fold>                        

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

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
            java.util.logging.Logger.getLogger(BusDispo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BusDispo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BusDispo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BusDispo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BusDispo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration                   
}
