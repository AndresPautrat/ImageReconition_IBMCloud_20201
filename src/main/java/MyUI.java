import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyUI extends JFrame implements ActionListener {
    private JLabel label1;
    private JButton button1;
    private JTextField textField1;
    private JTextArea textArea;
    Service service;

    public MyUI(){
        service= new Service();
        setLayout(null);

        label1=new JLabel("Ingresa un link de una imagen");
        label1.setBounds(100,10,200,20);
        add(label1);

        textField1=new JTextField();
        textField1.setBounds(100,30,200,20);
        add(textField1);

        button1=new JButton(("Reconocer imagen"));
        button1.setBounds(150,50,100,30);
        add(button1);
        button1.addActionListener(this);

        textArea=new JTextArea();
        textArea.setBounds(10,100,360,350);
        add(textArea);
        textArea.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            label1.setText(service.Reconice(textField1.getText()));
            textArea.setText(service.Reconice(textField1.getText()));
        }
    }
}
