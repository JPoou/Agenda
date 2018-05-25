/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.*;
import helpers.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author jpoou
 */
public class HomeController extends Request implements ActionListener {

    ArrayList<Model> list = new ArrayList<>();
    JFileChooser open = new JFileChooser();
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem menuItemEdit = new JMenuItem("Editar");
    JMenuItem menuItemRemove = new JMenuItem("Eliminar");
   
    Home home = new Home();
    Create create = new Create();
    Edit edit = new Edit();
    Model model = new Model();
    String image = "";

    /**
     *
     * @param view
     * @param model
     */
    public HomeController(Home view, Model model) {
        this.home = view;
        this.model = model;
        /*
        *  Botones
        */
        this.home.jBCreate.addActionListener(this);
        this.home.jBAbout.addActionListener(this);
        this.home.jBDelete.addActionListener(this);
        this.home.jBSave.addActionListener(this);
        this.create.jBCreated.addActionListener(this);
        this.create.jBCancel.addActionListener(this);
        this.create.jBFile.addActionListener(this);
        this.edit.jBCancel.addActionListener(this);
        
        /*
        *  Item
        */
        this.home.jMCreated.addActionListener(this);
        this.home.jMDelete.addActionListener(this);
        
        /*
        * Option item table
        */
        this.menuItemEdit.addActionListener(this);
        this.menuItemRemove.addActionListener(this);
        
        this.fileUloader();
    }

    public void setContact() {

        Model contact = new Model();
        
        int sex = (create.JRmale.isSelected()) ? 1 : 2;
        
        this.isImagen();
                
        contact.setName(create.jTName.getText());
        contact.setSurname(create.jTSurname.getText());
        contact.setSex(sex); 
        contact.setAddress(create.jTaddress.getText());
        contact.setDate(create.jTDate.getText());
        contact.setEmail(create.jTEmail.getText());
        contact.setPhone(create.jTPhone.getText());
        contact.setNationality(create.jTNationality.getText());
        contact.setImage(this.image);
        
        list.add(contact);
        
        this.toEmpty();
    }

    public void addRowToJTable() {
       
        clearTable();
        
        DefaultTableModel table = (DefaultTableModel) this.home.jTContact.getModel();
        
        Object rowData[] = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).name;
            rowData[1] = list.get(i).surname;
            rowData[2] = list.get(i).email;
            rowData[3] = list.get(i).phone;
            
            table.addRow(rowData);
        }
        
        addOption();
        
        if (list.size() > 0) {
            this.home.jBSave.setEnabled(true);
        }else{
            this.home.jBSave.setEnabled(false);
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /*
        * Home
        */
        if (e.getSource() == this.home.jBCreate) {
            this.showCreate();
        }
                
        if (e.getSource() == this.home.jBDelete) {
            delete();
        }
        
        if (e.getSource() == this.home.jBSave) {
            this.backup();
        }
        
        if (e.getSource() == this.home.jBAbout) {

        }
        
        /*
        * Create
        */
        if (e.getSource() == this.create.jBCreated) { 
            this.setContact();
            this.addRowToJTable();
            this.showHome();
        }
        
        if (e.getSource() == this.create.jBCancel) {
            this.showHome();
        }
        
        if (e.getSource() == this.create.jBFile) {
            FileNameExtensionFilter file = new FileNameExtensionFilter("Imagen", "jpeg", "png");
            open.setFileFilter(file);
            int request = open.showOpenDialog(null);
            if (request == JFileChooser.APPROVE_OPTION) {
                this.image = open.getSelectedFile().getAbsolutePath();
            }
        }
        
        /*
        * Edit
        */
        if (e.getSource() == this.edit.jBCancel) { 
            this.showHome();
        }
        
        /*
        * Tool bar
        */
        if (e.getSource() == this.home.jMCreated) {            
            showCreate();
        }
        if (e.getSource() == this.home.jMDelete) {
            System.out.println("aqu.");
            delete();
        }
        
        /*
        * Item table
        */
        if (e.getSource() == this.menuItemEdit) {
            showEdit();
            showContac(this.home.jTContact.getSelectedRow());
        }
        if (e.getSource() == this.menuItemRemove) {
            delete();
        }
        
    }
    
    protected void showContac(int contact) {
        System.out.println("QUi.");
        this.edit.jTName.setText(list.get(contact).name);
        this.edit.jTSurname.setText(list.get(contact).surname);
        this.edit.jTPhone.setText(list.get(contact).phone);
        this.edit.jTaddress.setText(list.get(contact).address);
        this.edit.jTDate.setText(list.get(contact).date);
        this.edit.jTNationality.setText(list.get(contact).name);
        this.edit.jTEmail.setText(list.get(contact).email);
    }
    
    protected void delete(){
        if (this.home.jTContact.getSelectedRow() >= 0) {
            list.remove(this.home.jTContact.getSelectedRow());
            addRowToJTable();
        }else{
            JOptionPane.showMessageDialog(null, "No seleciono un contacto para eliminar");
        }
    }
   
    protected void showHome() {
        this.create.dispose();
        this.home.setVisible(true);
        this.home.setLocationRelativeTo(null);
    }
    
    protected void showCreate() {
        this.home.dispose();
        this.create.setVisible(true);
        this.create.setLocationRelativeTo(null);
    }
    
    protected void showEdit() {
        this.home.dispose();
        this.edit.setVisible(true);
        this.edit.setLocationRelativeTo(null);
    }
    
    protected void toEmpty() {
        this.create.jTName.setText("");
        this.create.jTSurname.setText("");
        this.create.jTDate.setText("");
        this.create.jTaddress.setText("");
        this.create.jTNationality.setText("");
        this.create.jTPhone.setText("");
        this.create.jTEmail.setText("");
    }
    
    protected void clearTable() {
       
        DefaultTableModel table = (DefaultTableModel) this.home.jTContact.getModel();
        for (int i = 0; i < this.home.jTContact.getRowCount(); i++) {
            table.setRowCount(i);
        }
        this.image = "";
    }
    
    protected void addOption(){
        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemRemove);
        this.home.jTContact.setComponentPopupMenu(popupMenu);
    }

    protected void uploader() {
        try {
            
            File carga = new File (this.image);
            String ext = getFileExtension(carga);
            String dir = System.getProperty("user.dir")+"/perfiles/"+getSaltString()+".bin";
            
            BufferedImage bImage = ImageIO.read (carga);
            ByteArrayOutputStream bos = new ByteArrayOutputStream ();
            ImageIO.write(bImage, ext, bos);
            byte [] data = bos.toByteArray();
            FileOutputStream stream = new FileOutputStream(dir);
            stream.write(data);
            
            System.out.println("Imagen cargada.");
            this.image = dir;
        } catch (IOException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }
    
    protected void fileUloader() {
        File archive = new File(System.getProperty("user.dir")+"/backup.txt");
        Scanner s = null;
        
        try{
            s = new Scanner(archive);
            while(s.hasNextLine()){
                
                String[] conta = s.nextLine().split(",");
                
                Model contact = new Model();
       
                contact.setName(conta[0]);
                contact.setSurname(conta[1]);
                contact.setSex(Integer.parseInt(conta[2])); 
                contact.setPhone(conta[3]);
                contact.setAddress(conta[4]);
                contact.setDate(conta[5]);
                contact.setAge(Integer.parseInt(conta[6]));
                contact.setNationality(conta[7]);
                contact.setEmail(conta[8]);
                contact.setImage(conta[9]);

                list.add(contact);
            }
            JOptionPane.showMessageDialog(null,"Personas cargadas correctamente.");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }finally{
            if (s != null) {
                s.close();
            }
        }
    }
    
    protected void backup() {
        FileWriter file;
        try{
            file = new FileWriter("backup.txt");
            for (int i = 0; i < list.size(); i++) {
                file.write(list.get(i).name+","+list.get(i).surname+","+list.get(i).sex+","+list.get(i).phone+","+list.get(i).address+","+list.get(i).date+","+list.get(i).age+","+list.get(i).nationality+","+list.get(i).email+","+list.get(i).image+"\n");
            }
            file.close();
            JOptionPane.showMessageDialog(null,"Guardado correctamente.");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
    
    protected void isImagen() {
        if (!this.image.equals("")) {
            uploader();
        }else{
            this.image = System.getProperty("user.dir")+"/perfiles/profile.png";
        }
    }    
}
