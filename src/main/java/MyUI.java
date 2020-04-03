import com.ibm.watson.visual_recognition.v3.model.ClassResult;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyUI extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel13;
    private JLabel labelAPI;
    private JLabel labelURL;
    private JLabel label1;
    private JButton button1;
    private JTextField textField1;
    private JTextField textFieldAPI;
    private JTextField textFieldURL;
    private JTable textArea;
    private DefaultTableModel model;
    private JLabel image1;
    Service service;

    public MyUI(){
        service= new Service();

        this.setLayout(new GridLayout(1,2,10,10));

        panel13=new JPanel();
        panel13.setLayout(new GridLayout(2,1,10,10));
        this.add(panel13,BorderLayout.WEST);
        panel1=new JPanel();
        panel1.setLayout(new GridLayout(4,2,5,10));
        panel13.add(panel1,BorderLayout.SOUTH);
        panel2=new JPanel();
        panel2.setLayout(new FlowLayout());
        this.add(panel2,BorderLayout.EAST);
        panel3=new JPanel();
        panel3.setLayout(new BorderLayout());
        panel13.add(panel3,BorderLayout.NORTH);

        labelAPI=new JLabel("Ingresa un API de IBM cloud (opcional)");
        panel1.add(labelAPI);

        textFieldAPI=new JTextField();
        panel1.add(textFieldAPI);

        labelURL=new JLabel("Ingresa un link de IMB cloud (opcional)");
        panel1.add(labelURL);

        textFieldURL=new JTextField();
        panel1.add(textFieldURL);

        label1=new JLabel("Ingresa un link de una imagen");
        panel1.add(label1);

        textField1=new JTextField();
        panel1.add(textField1);

        textField1.setAlignmentX(Component.CENTER_ALIGNMENT);

        button1=new JButton(("Reconocer imagen"));
        panel1.add(button1);
        button1.addActionListener(this);

        model = new DefaultTableModel();
        model.addColumn("Code");
        model.addColumn("Name");
        textArea=new JTable(model);
        panel3.add(textArea);

        try {
            URL url = null;
            url = new URL("https://seeba.se/wp-content/themes/consultix/images/no-image-found-360x260.png");
            BufferedImage image = ImageIO.read(url);
            image1 = new JLabel(new ImageIcon(image));
            panel2.add(image1,BorderLayout.CENTER);
        } catch (MalformedURLException error) {
            error.printStackTrace();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            model.setRowCount(0);
            List<ClassResult>classes=service.Reconice(textFieldAPI.getText(),textFieldURL.getText(),textField1.getText());
            for (int i=0; i<classes.size();i++) {
                 model.addRow(new Object[]{classes.get(i).getXClass(),classes.get(i).getScore()});
            }
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            textArea.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>(25);
            sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
            sorter.setSortKeys(sortKeys);
            try {
                URL url = null;
                url = new URL(textField1.getText());
                BufferedImage image = ImageIO.read(url);

                image1.setIcon(new ImageIcon(image));
            } catch (MalformedURLException error) {
                error.printStackTrace();
            } catch (IOException error) {
                error.printStackTrace();
            }

        }
    }
}
